import React, { useCallback } from 'react'
import { debounce } from 'throttle-debounce'

import ServiceFactory from 'services/ServiceFactory'
import Autocomplete from 'common/components/Autocomplete'
import InfoIcon from 'common/components/InfoIcon'
import Strings from 'utils/Strings'

import * as FieldsSizes from '../FieldsSizes'
import CodeFieldRadioItem from './CodeFieldRadioItem'

const CodeFieldAutocomplete = (props) => {
  const {
    parentEntity,
    fieldDef,
    inTable,
    selectedItems,
    values,
    asynchronous,
    items,
    ancestorCodes,
    itemLabelFunction,
    onSelect,
    onChangeQualifier,
  } = props
  const { survey, record } = parentEntity
  const { attributeDefinition } = fieldDef
  const { versionId } = record
  const { codeListId, multiple } = attributeDefinition

  const language = survey.defaultLanguage // TODO
  const surveyId = survey.id

  const fetchCodeItems = useCallback(
    ({ surveyId, codeListId, versionId, language, ancestorCodes }) => ({ searchString, onComplete }) =>
      debounce(Strings.isBlank(searchString) ? 0 : 1000, false, async () => {
        const items = await ServiceFactory.codeListService.findAvailableItems({
          surveyId,
          codeListId,
          versionId,
          language,
          ancestorCodes,
          searchString,
        })
        onComplete(items)
      }),
    [surveyId, codeListId, versionId, language, ancestorCodes]
  )

  return (
    <Autocomplete
      asynchronous={asynchronous}
      multiple={multiple}
      items={items}
      inputFieldWidth={FieldsSizes.getWidth({ fieldDef, inTable })}
      selectedItems={selectedItems}
      fetchFunction={fetchCodeItems({ surveyId, codeListId, versionId, language, ancestorCodes })}
      itemLabelFunction={itemLabelFunction}
      itemSelectedFunction={(item, value) => item.code === value.code}
      itemRenderFunction={(item) => (
        <span title={item.description}>
          {itemLabelFunction(item)}
          {item.description && <InfoIcon />}
        </span>
      )}
      tagsRenderFunction={(tagValue, getTagProps) =>
        tagValue.map((item, index) => {
          const tagProps = getTagProps({ index })
          return (
            <CodeFieldRadioItem
              key={item.code}
              item={item}
              itemLabelFunction={itemLabelFunction}
              multiple
              onChange={tagProps.onDelete}
              onChangeQualifier={onChangeQualifier}
              value={values?.find((value) => value.code === item.code)}
            />
          )
        })
      }
      onSelect={onSelect}
    />
  )
}

export default CodeFieldAutocomplete