import Serializable from './Serializable'
import { UIConfiguration } from './ui/UIConfiguration'

export class SurveyObject extends Serializable {
  id

  constructor(id) {
    super()
    this.id = id
  }
}

export class Survey extends Serializable {
  id
  name
  uri
  schema
  modelVersions = []
  codeLists = []
  units = []
  spatialReferenceSystems = []
  uiConfiguration
  temporary
  published
  publishedId
  languages = []

  constructor(jsonData) {
    super()
    if (jsonData) {
      this.fillFromJSON(jsonData)
    }
  }

  fillFromJSON(jsonObj) {
    super.fillFromJSON(jsonObj)

    this.codeLists = jsonObj.codeLists.map((codeListJsonObj) => {
      const codeList = new CodeList(this)
      codeList.fillFromJSON(codeListJsonObj)
      return codeList
    })
    this.units = jsonObj.units.map((unitJsonObj) => {
      const unit = new Unit(this)
      unit.fillFromJSON(unitJsonObj)
      return unit
    })
    this.spatialReferenceSystems = jsonObj.spatialReferenceSystems.map((srsJsonObj) => {
      const srs = new SpatialReferenceSystem(this)
      srs.fillFromJSON(srsJsonObj)
      return srs
    })
    this.languages = jsonObj.languages
    this.modelVersions = jsonObj.modelVersions
    this.schema = new Schema(this)
    this.schema.fillFromJSON(jsonObj.schema)
    this.uiConfiguration = new UIConfiguration(this)
    this.uiConfiguration.fillFromJSON(jsonObj.uiConfiguration)
  }
}

export class CodeList extends Serializable {
  survey
  id
  name
  items = []
  hierarchycal

  constructor(survey) {
    super()
    this.survey = survey
  }

  fillFromJSON(jsonObj) {
    super.fillFromJSON(jsonObj)

    this.items = jsonObj.items.map((itemJsonObj) => {
      return new CodeListItem(itemJsonObj)
    })
  }
}

export class CodeListItem extends Serializable {
  code
  label
  color
}

export class Unit extends Serializable {
  id
  conversionFactor
  abbreviation
  label

  constructor(survey) {
    super()
    this.survey = survey
  }
}

export class SpatialReferenceSystem extends Serializable {
  id
  label
  description

  constructor(survey) {
    super()
    this.survey = survey
  }
}

export class Schema extends Serializable {
  survey
  rootEntities = []
  definitions = {} //cache

  constructor(survey) {
    super()
    this.survey = survey
    this.definitions = []
  }

  fillFromJSON(jsonObj) {
    super.fillFromJSON(jsonObj)

    let $this = this

    this.rootEntities = jsonObj.rootEntities.map((rootEntityJsonObj) => {
      var rootEntity = new EntityDefinition(rootEntityJsonObj.id, this.survey, null)
      rootEntity.fillFromJSON(rootEntityJsonObj)
      rootEntity.traverse(function (nodeDef) {
        $this.definitions[nodeDef.id] = nodeDef
      })
      return rootEntity
    })
  }

  get firstRootEntityDefinition() {
    return this.rootEntities[0]
  }

  getDefinitionById(id) {
    return this.definitions[id]
  }
}

export class NodeDefinition extends SurveyObject {
  survey
  parent
  name
  label
  multiple

  static Types = {
    ENTITY: 'ENTITY',
    ATTRIBUTE: 'ATTRIBUTE',
  }

  constructor(id, survey, parent) {
    super(id)
    this.survey = survey
    this.parent = parent
  }

  get rootEntity() {
    let currentParent = this.parent
    while (currentParent.parent != null) {
      currentParent = currentParent.parent
    }
    return currentParent
  }

  visitAncestors(visitor) {
    let currentParent = this.parent
    while (currentParent != null) {
      visitor(currentParent)
      currentParent = currentParent.parent
    }
  }

  get ancestorIds() {
    const ancestorIds = []
    this.visitAncestors((ancestor) => ancestorIds.push(ancestor.id))
    return ancestorIds
  }

  get single() {
    return !this.multiple
  }
}

export class EntityDefinition extends NodeDefinition {
  children = []

  fillFromJSON(jsonObj) {
    super.fillFromJSON(jsonObj)

    this.children = jsonObj.children.map((nodeJsonObj) => {
      const { id, type, attributeType } = nodeJsonObj
      let nodeDef
      if (type === NodeDefinition.Types.ENTITY) {
        nodeDef = new EntityDefinition(id, this.survey, this)
      } else {
        const nodeDefClass = attributeDefinitionClassByType[attributeType] || AttributeDefinition
        nodeDef = new nodeDefClass(id, this.survey, this)
      }
      nodeDef.fillFromJSON(nodeJsonObj)
      return nodeDef
    })
  }

  traverse(visitor) {
    const stack = []
    stack.push(this)
    while (stack.length > 0) {
      const def = stack.pop()
      visitor(def)
      if (def instanceof EntityDefinition) {
        stack.push(...def.children)
      }
    }
  }

  get keyAttributeDefinitions() {
    return this.findDefinitions((def) => def instanceof AttributeDefinition && def.key, true)
  }

  get attributeDefinitionsShownInRecordSummaryList() {
    return this.findDefinitions((def) => def instanceof AttributeDefinition && def.showInRecordSummaryList, true)
  }

  get qualifierAttributeDefinitions() {
    return this.findDefinitions((def) => def instanceof AttributeDefinition && def.qualifier, true)
  }

  visit(visitor, onlyInsideSingleEntities) {
    const queue = []

    this.children.forEach((child) => queue.push(child))

    while (queue.length > 0) {
      const item = queue.shift()
      visitor(item)
      if (item instanceof EntityDefinition && !(onlyInsideSingleEntities && item.multiple)) {
        item.children.forEach((child) => queue.push(child))
      }
    }
  }

  findDefinitions(predicate, onlyInsideSingleEntities) {
    const result = []
    this.visit(function (n) {
      if (predicate(n)) {
        result.push(n)
      }
    }, onlyInsideSingleEntities)
    return result
  }

  getChildDefinitionIndexById(childDefId) {
    return this.children.findIndex((childDef) => childDef.id === childDefId)
  }

  getChildDefinitionByName(childName) {
    return this.children.find((childDef) => childDef.name === childName)
  }
}

export class AttributeDefinition extends NodeDefinition {
  key
  attributeType
  fieldNames
  fieldLabels
  mandatoryFieldNames

  static Types = {
    BOOLEAN: 'BOOLEAN',
    CODE: 'CODE',
    COORDINATE: 'COORDINATE',
    DATE: 'DATE',
    FILE: 'FILE',
    NUMBER: 'NUMBER',
    TAXON: 'TAXON',
    TEXT: 'TEXT',
  }

  getFieldLabel(fieldName) {
    const index = this.fieldNames.indexOf(fieldName)
    return this.fieldLabels[index]
  }
}

export class CodeAttributeDefinition extends AttributeDefinition {
  codeListId
  parentCodeAttributeDefinitionId
  mandatoryFieldNames = ['code']
}

export class CoordinateAttributeDefinition extends AttributeDefinition {
  fieldsOrder
  showSrsField
  includeAltitudeField
  includeAccuracyField

  static FieldsOrder = {
    SRS_X_Y: 'SRS_X_Y',
    SRS_Y_X: 'SRS_Y_X',
    X_Y_SRS: 'X_Y_SRS',
    Y_X_SRS: 'Y_X_SRS',
  }
}

export class TaxonAttributeDefinition extends AttributeDefinition {
  static QUERY_FIELDS = {
    CODE: 'CODE',
    SCIENTIFIC_NAME: 'SCIENTIFIC_NAME',
    VERNACULAR_NAME: 'VERNACULAR_NAME',
  }

  static FIELDS = {
    CODE: 'code',
    SCIENTIFIC_NAME: 'scientific_name',
    VERNACULAR_NAME: 'vernacular_name',
    LANGUAGE_CODE: 'language_code',
    LANGUAGE_VARIETY: 'language_variety',
    FAMILY_CODE: 'family_code',
    FAMILY_SCIENTIFIC_NAME: 'family_scientific_name',
  }

  static VALUE_FIELDS = {
    CODE: 'code',
    SCIENTIFIC_NAME: 'scientificName',
    VERNACULAR_NAME: 'vernacularName',
    LANGUAGE_CODE: 'languageCode',
    LANGUAGE_VARIETY: 'languageVariety',
  }

  static VALUE_FIELD_BY_FIELD = {
    [TaxonAttributeDefinition.FIELDS.CODE]: TaxonAttributeDefinition.VALUE_FIELDS.CODE,
    [TaxonAttributeDefinition.FIELDS.SCIENTIFIC_NAME]: TaxonAttributeDefinition.VALUE_FIELDS.SCIENTIFIC_NAME,
    [TaxonAttributeDefinition.FIELDS.VERNACULAR_NAME]: TaxonAttributeDefinition.VALUE_FIELDS.VERNACULAR_NAME,
    [TaxonAttributeDefinition.FIELDS.LANGUAGE_CODE]: TaxonAttributeDefinition.VALUE_FIELDS.LANGUAGE_CODE,
    [TaxonAttributeDefinition.FIELDS.LANGUAGE_VARIETY]: TaxonAttributeDefinition.VALUE_FIELDS.LANGUAGE_VARIETY,
  }

  static FIELD_BY_VALUE_FIELD = {
    [TaxonAttributeDefinition.VALUE_FIELDS.CODE]: TaxonAttributeDefinition.FIELDS.CODE,
    [TaxonAttributeDefinition.VALUE_FIELDS.SCIENTIFIC_NAME]: TaxonAttributeDefinition.FIELDS.SCIENTIFIC_NAME,
    [TaxonAttributeDefinition.VALUE_FIELDS.VERNACULAR_NAME]: TaxonAttributeDefinition.FIELDS.VERNACULAR_NAME,
    [TaxonAttributeDefinition.VALUE_FIELDS.LANGUAGE_CODE]: TaxonAttributeDefinition.FIELDS.LANGUAGE_CODE,
    [TaxonAttributeDefinition.VALUE_FIELDS.LANGUAGE_VARIETY]: TaxonAttributeDefinition.FIELDS.LANGUAGE_VARIETY,
  }

  taxonomyName
  highestRank
  codeVisible
  scientificNameVisible
  vernacularNameVisible
  languageCodeVisible
  languageVarietyVisible
  showFamily
  includeUniqueVernacularName
  allowUnlisted
}

export class FileAttributeDefinition extends AttributeDefinition {
  fileType
  extensions
  maxSize

  static FileTypes = {
    AUDIO: 'AUDIO',
    DOCUMENT: 'DOCUMENT',
    IMAGE: 'IMAGE',
    VIDEO: 'VIDEO',
  }
}

export class NumericAttributeDefinition extends AttributeDefinition {
  numericType
  precisions
  mandatoryFieldNames = ['value']
}

const attributeDefinitionClassByType = {
  [AttributeDefinition.Types.CODE]: CodeAttributeDefinition,
  [AttributeDefinition.Types.COORDINATE]: CoordinateAttributeDefinition,
  [AttributeDefinition.Types.FILE]: FileAttributeDefinition,
  [AttributeDefinition.Types.NUMBER]: NumericAttributeDefinition,
  [AttributeDefinition.Types.TAXON]: TaxonAttributeDefinition,
}
