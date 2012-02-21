package org.openforis.collect.presenter {
	import mx.controls.TextInput;
	
	import org.openforis.collect.i18n.Message;
	import org.openforis.collect.model.proxy.AttributeProxy;
	import org.openforis.collect.ui.component.input.BooleanInputField;
	import org.openforis.collect.util.StringUtil;
	
	/**
	 * 
	 * @author S. Ricci
	 * */
	public class BooleanInputFieldPresenter extends InputFieldPresenter {
		
		private static const TRUE:String = Message.get("edit.booleanValue.true");
		private static const FALSE:String = Message.get("edit.booleanValue.false");
		private static const RESTRICT_PATTERN:String = "[" + TRUE + FALSE + TRUE.toLowerCase() + FALSE.toLowerCase() + "\*\-\?]";
		
		private var _view:BooleanInputField;
		
		public function BooleanInputFieldPresenter(inputField:BooleanInputField) {
			_view = inputField;
			
			(inputField.textInput as TextInput).restrict = RESTRICT_PATTERN;
			super(inputField);
		}
		
		override protected function createRequestValues():Array {
			var value:String = null;
			var text:String = StringUtil.trim(_view.text);
			switch(text) {
				case TRUE:
				case TRUE.toLowerCase():
					value = "true";
					break;
				case FALSE:
				case FALSE.toLowerCase():
					value = "false";
					break;
				default:
					value = text;
			}
			var result:Array = null;
			if(StringUtil.isNotBlank(value)) {
				result = [value];
			}
			return result;
		}
		
		override protected function getTextValue():String {
			var attribute:AttributeProxy = _view.attribute;
			if(attribute != null) {
				var value:Object = attribute.value;
				if(attribute.symbol != null) {
					var shortKey:String = getReasonBlankShortKey(attribute.symbol);
					if(shortKey != null) {
						return shortKey;
					}
				}
				if(value != null) {
					var textVal:String = value.toString();
					if(textVal == "true") {
						return TRUE;
					} else if(textVal == "false") {
						return FALSE;
					}
				}
			}
			return "";
		}
		
	}
}
