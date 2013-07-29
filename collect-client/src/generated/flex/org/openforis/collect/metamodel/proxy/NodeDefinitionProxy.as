/**
 * Generated by Gas3 v2.2.0 (Granite Data Services).
 *
 * NOTE: this file is only generated if it does not exist. You may safely put
 * your custom code here.
 */

package org.openforis.collect.metamodel.proxy {
	import mx.collections.ArrayCollection;
	import mx.collections.IList;
	
	import org.openforis.collect.Application;
	import org.openforis.collect.util.CollectionUtil;
	import org.openforis.collect.util.StringUtil;
	import org.openforis.collect.util.TextUtil;
	import org.openforis.collect.util.UIUtil;
	
	/**
	 * @author M. Togna
	 * @author S. Ricci
	 */
    [Bindable]
    [RemoteClass(alias="org.openforis.collect.metamodel.proxy.NodeDefinitionProxy")]
    public class NodeDefinitionProxy extends NodeDefinitionProxyBase {
		
		private static const NUMBERED_LABEL_SEPARATOR:String = ". ";
		
		private var _survey:SurveyProxy;
		
		public function getNumberAndHeadingLabelText():String {
			var numberPart:String = getSpecificLabelText([NodeLabelProxy$Type.NUMBER], false);
			var labelPart:String = getSpecificLabelText([NodeLabelProxy$Type.HEADING, NodeLabelProxy$Type.INSTANCE], false);
			if ( numberPart == null && labelPart == null ) {
				labelPart = name;
			}
			var result:String = StringUtil.concat(NUMBERED_LABEL_SEPARATOR, numberPart, labelPart);
			return result;
		}
			
		
		public function getInstanceOrHeadingLabelText():String {
			var labelTypes:Array = [NodeLabelProxy$Type.INSTANCE, NodeLabelProxy$Type.HEADING];
			return getSpecificLabelText(labelTypes);
		}
		
		public function getHeadingOrInstanceLabelText():String {
			var labelTypes:Array = [NodeLabelProxy$Type.HEADING, NodeLabelProxy$Type.INSTANCE];
			return getSpecificLabelText(labelTypes);
		}
		
		public function getNumberLabelText():String {
			var labelTypes:Array = [NodeLabelProxy$Type.NUMBER];
			return getSpecificLabelText(labelTypes, false);
		}
		
		public function getSpecificLabelText(typesStack:Array, nameIfNotFound:Boolean = true):String {
			var label:NodeLabelProxy = null;
			for each (var type:NodeLabelProxy$Type in typesStack) {
				label = getLabel(type);
				if(label != null) {
					break;
				}
			}
			if(label != null) {
				return TextUtil.trimLeadingWhitespace(label.text);
			} else if ( nameIfNotFound ) {
				return name;
			} else {
				return null;
			}
		}
		
		public function getLabel(type:NodeLabelProxy$Type):NodeLabelProxy {
			if(CollectionUtil.isNotEmpty(labels)) {
				var labelsPerType:IList = getLabelsByType(type);
				var langCode:String = Application.localeLanguageCode;
				var isDefaultLang:Boolean = langCode == Application.activeSurvey.defaultLanguageCode;
				for each(var label:NodeLabelProxy in labelsPerType) {
					if ( label.language == null && isDefaultLang || label.language == langCode) {
						return label;
					}
				}
				return getDefaultLanguageLabelByType(type);
			} else {
				return null;
			}
		}
		
		public function getLabelsByType(type:NodeLabelProxy$Type):IList {
			var result:IList = new ArrayCollection();
			for each(var label:NodeLabelProxy in labels) {
				if(label.type == type) {
					result.addItem(label);
				}
			}
			return result;
		}
		
		public function getDefaultLanguageLabelByType(type:NodeLabelProxy$Type):NodeLabelProxy {
			var labelsPerType:IList = getLabelsByType(type);
			var defaultLangCode:String = Application.activeSurvey.defaultLanguageCode;
			for each(var label:NodeLabelProxy in labelsPerType) {
				if ( label.language == null || label.language == defaultLangCode) {
					return label;
				}
			}
			return null;
		}
		
		public function getDescription():String {
			var langCode:String = Application.localeLanguageCode;
			var defaultLangCode:String = Application.activeSurvey.defaultLanguageCode;
			var isDefaultLang:Boolean = langCode == defaultLangCode;
			var result:String = LanguageSpecificTextProxy.getLocalizedText(descriptions, langCode, isDefaultLang);
			if ( result == null && ! isDefaultLang) {
				result = LanguageSpecificTextProxy.getLocalizedText(descriptions, defaultLangCode, true);;
			}
			result = TextUtil.trimLeadingWhitespace(result);
			return result;
		}
		
		public function get rootEntity():EntityDefinitionProxy {
			var parentEntity:EntityDefinitionProxy = this.parent;
			if ( parentEntity == null ) {
				return this as EntityDefinitionProxy;
			}
			while ( parentEntity.parent != null ) {
				parentEntity = parentEntity.parent;
			}
			return parentEntity;
		}
		
		[Bindable]
		public function get parentLayout():String {
			if(parent != null) {
				return parent.layout;
			} else {
				return UIUtil.LAYOUT_FORM;
			}
		}
		
		public function get survey():SurveyProxy {
			return _survey;
		}
		
		public function set survey(value:SurveyProxy):void {
			_survey = value;
		}
    }
}