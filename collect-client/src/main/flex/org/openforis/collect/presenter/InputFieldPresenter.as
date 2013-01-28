package org.openforis.collect.presenter {
	import flash.events.Event;
	import flash.events.FocusEvent;
	import flash.events.KeyboardEvent;
	import flash.ui.Keyboard;
	
	import mx.binding.utils.BindingUtils;
	import mx.collections.IList;
	import mx.rpc.events.ResultEvent;
	
	import org.openforis.collect.Application;
	import org.openforis.collect.client.ClientFactory;
	import org.openforis.collect.client.DataClient;
	import org.openforis.collect.event.ApplicationEvent;
	import org.openforis.collect.event.InputFieldEvent;
	import org.openforis.collect.event.NodeEvent;
	import org.openforis.collect.metamodel.proxy.AttributeDefinitionProxy;
	import org.openforis.collect.metamodel.proxy.NumericAttributeDefinitionProxy;
	import org.openforis.collect.model.FieldSymbol;
	import org.openforis.collect.model.proxy.AttributeProxy;
	import org.openforis.collect.model.proxy.EntityProxy;
	import org.openforis.collect.model.proxy.FieldProxy;
	import org.openforis.collect.model.proxy.NodeProxy;
	import org.openforis.collect.remoting.service.UpdateRequest;
	import org.openforis.collect.remoting.service.UpdateRequestOperation;
	import org.openforis.collect.remoting.service.UpdateRequestOperation$Method;
	import org.openforis.collect.remoting.service.UpdateResponse;
	import org.openforis.collect.ui.component.input.InputField;
	import org.openforis.collect.ui.component.input.InputFieldContextMenu;
	import org.openforis.collect.util.StringUtil;
	import org.openforis.collect.util.UIUtil;
	
	/**
	 * 
	 * @author M. Togna
	 * @author S. Ricci
	 * */
	public class InputFieldPresenter extends AbstractPresenter {
		
		private var _view:InputField;
		private var _contextMenu:InputFieldContextMenu;
		
		private static var _dataClient:DataClient;
		
		/*
			Static initialization
		*/
		{
			_dataClient = ClientFactory.dataClient;
			eventDispatcher.addEventListener(NodeEvent.UPDATE_SYMBOL, updateSymbolHandler);
			eventDispatcher.addEventListener(NodeEvent.UPDATE_REMARKS, updateRemarksHandler);
			eventDispatcher.addEventListener(NodeEvent.DELETE_NODE, deleteNodeHandler);
			eventDispatcher.addEventListener(NodeEvent.CONFIRM_ERROR, confirmErrorHandler);
			eventDispatcher.addEventListener(NodeEvent.APPROVE_MISSING, approveMissingHandler);
			eventDispatcher.addEventListener(NodeEvent.APPLY_DEFAULT_VALUE, applyDefaultValueHandler);
		}
		
		public function InputFieldPresenter(inputField:InputField) {
			_view = inputField;
			_contextMenu = new InputFieldContextMenu(_view);
			super();
			//updateView();
		}
		
		override internal function initEventListeners():void {
			super.initEventListeners();
			
			eventDispatcher.addEventListener(ApplicationEvent.UPDATE_RESPONSE_RECEIVED, updateResponseReceivedHandler);
			eventDispatcher.addEventListener(InputFieldEvent.SET_FOCUS, setFocusHandler);
			
			if(_view.textInput != null) {
				_view.textInput.addEventListener(KeyboardEvent.KEY_DOWN, keyDownHandler);
				_view.textInput.addEventListener(Event.CHANGE, changeHandler);
				_view.textInput.addEventListener(FocusEvent.FOCUS_OUT, focusOutHandler);
				_view.textInput.addEventListener(FocusEvent.FOCUS_IN, focusInHandler);
			}
			
			BindingUtils.bindSetter(setAttribute, _view, "attribute");
		}
		
		protected function setFocusHandler(event:InputFieldEvent):void {
			if ( _view.textInput != null && _view.attribute != null && 
					_view.attribute.id == event.attributeId && 
					_view.fieldIndex == event.fieldIdx ) {
				_view.textInput.setFocus();
			}
		}
		
		protected static function approveMissingHandler(event:NodeEvent): void {
			var updRequest:UpdateRequest = new UpdateRequest();
			if(event.node != null || event.nodes != null) {
				var node:NodeProxy = event.node;
				if(node == null) {
					var nodes:IList = event.nodes;
					if(nodes != null && nodes.length == 1) {
						//approve only first node, if it's a missing value it should be an empty node
						node = nodes.getItemAt(0) as NodeProxy;
					}
				}
				prepareApproveMissingRequests(updRequest, node, event.fieldIdx, event.applyToNonEmptyNodes);
			} else {
				var o:UpdateRequestOperation = new UpdateRequestOperation();
				o.method = UpdateRequestOperation$Method.APPROVE_MISSING;
				o.parentEntityId = event.parentEntity.id;
				o.nodeName = event.nodeName;
				updRequest.addOperation(o);
			}
			_dataClient.updateActiveRecord(updRequest, null, faultHandler);
		}
		
		protected static function applyDefaultValueHandler(event:NodeEvent):void {
			var node:NodeProxy = event.node;
			var o:UpdateRequestOperation = new UpdateRequestOperation();
			o.method = UpdateRequestOperation$Method.APPLY_DEFAULT_VALUE;
			o.parentEntityId = node.parentId;
			o.nodeName = node.name;
			o.nodeId = node.id;
			
			var updRequest:UpdateRequest = new UpdateRequest(o);
			_dataClient.updateActiveRecord(updRequest, null, faultHandler);
		}
		
		protected static function updateRemarksHandler(event:NodeEvent): void {
			var updRequest:UpdateRequest = new UpdateRequest();
			var fieldIdx:int;
			if(event.node != null) {
				var attribute:AttributeProxy = AttributeProxy(event.node);
				if(event.fieldIdx >= 0) {
					prepareUpdateRemarksRequest(updRequest, attribute, event.remarks, event.fieldIdx);
				} else {
					for (fieldIdx = 0; fieldIdx < attribute.fields.length; fieldIdx++) {
						prepareUpdateRemarksRequest(updRequest, attribute, event.remarks, fieldIdx);
					}
				}
			} else {
				//considering nodes as attributes
				var attributes:IList = event.nodes;
				for each (attribute in attributes) {
					for (fieldIdx = 0; fieldIdx < attribute.fields.length; fieldIdx++) {
						prepareUpdateRemarksRequest(updRequest, attribute, event.remarks, fieldIdx);
					}
				}
			}
			_dataClient.updateActiveRecord(updRequest, null, faultHandler);
		}
		
		protected static function prepareUpdateRemarksRequest(req:UpdateRequest, node:NodeProxy, remarks:String, fieldIdx:Number = NaN):void {
			var o:UpdateRequestOperation = new UpdateRequestOperation();
			o.method = UpdateRequestOperation$Method.UPDATE_REMARKS;
			o.parentEntityId = node.parentId;
			o.nodeName = node.name;
			o.nodeId = node.id;
			o.fieldIndex = fieldIdx;
			o.remarks = remarks;
			req.addOperation(o);
		}
		
		protected static function updateSymbolHandler(event:NodeEvent): void {
			var updRequest:UpdateRequest = new UpdateRequest();
			prepareUpdateSymbolRequests(updRequest, event.node, event.symbol, event.fieldIdx, event.applyToNonEmptyNodes);
			_dataClient.updateActiveRecord(updRequest, null, faultHandler);
		}
		
		protected static function prepareUpdateSymbolRequests(updateRequest:UpdateRequest, node:NodeProxy, symbol:FieldSymbol, fieldIdx:Number, applyToNonEmptyNodes:Boolean = false):void {
			if( node is EntityProxy ){
				var entity:EntityProxy = node as EntityProxy;
				var children:IList = entity.getChildren();
				for each (var child:NodeProxy in children) {
					prepareUpdateSymbolRequests(updateRequest, child, symbol, fieldIdx);
				}
			} else {
				var attr:AttributeProxy = AttributeProxy(node);
				var o:UpdateRequestOperation;
				var field:FieldProxy;
				if(isNaN(fieldIdx) || fieldIdx < 0){
					for(var index:int = 0; index < attr.fields.length; index ++) {
						if ( ! skippedField(attr, index) ) {
							field = attr.fields[index];
							if ( applyToNonEmptyNodes || (field.value == null && field.symbol == null)) {
								o = createUpdateSymbolOperation(node, field, index, symbol);
								updateRequest.addOperation(o);
							}
						}
					}
				} else {
					field = attr.fields[fieldIdx];
					if ( applyToNonEmptyNodes || (field.value == null && field.symbol == null)) {
						o = createUpdateSymbolOperation(node, field, fieldIdx, symbol);
						updateRequest.addOperation(o);
					}
				}
			}
		}
		
		private static function createUpdateSymbolOperation(node:NodeProxy, field:FieldProxy, fieldIdx:int, symbol:FieldSymbol):UpdateRequestOperation {
			var o:UpdateRequestOperation = new UpdateRequestOperation();
			o.method = UpdateRequestOperation$Method.UPDATE;
			o.parentEntityId = node.parentId;
			o.nodeName = node.name;
			o.nodeId = node.id;
			o.fieldIndex = fieldIdx;
			o.remarks = field.remarks;
			o.symbol = symbol;
			if ( FieldProxy.isReasonBlankSymbol(symbol) ) {
				o.value = null;
			} else {
				o.value = field.value != null ? field.value.toString(): null;
			}
			return o;
		}
		
		private static function skippedField(attr:AttributeProxy, index:int):Boolean {
			if ( attr.definition is NumericAttributeDefinitionProxy && index == 1 ) {
				//OFC-720
				return true;
			} else {
				return false;
			}
		}

		protected static function prepareApproveMissingRequests(updateRequest:UpdateRequest, node:NodeProxy, fieldIdx:Number, applyToNonEmptyNodes:Boolean = true):void {
			if( node is EntityProxy ){
				var entity:EntityProxy = node as EntityProxy;
				var children:IList = entity.getChildren();
				for each (var child:NodeProxy in children) {
					prepareApproveMissingRequests(updateRequest, child, fieldIdx, applyToNonEmptyNodes);
				}
			} else {
				var attr:AttributeProxy = AttributeProxy(node);
				var o:UpdateRequestOperation;
				var field:FieldProxy;
				if(isNaN(fieldIdx) || fieldIdx < 0){
					for(var index:int = 0; index < attr.fields.length; index ++) {
						field = attr.fields[index];
						if(applyToNonEmptyNodes || (field.value == null && (field.symbol == null || field.hasReasonBlankSpecified()))) {
							o = createApproveMissingOperation(node, index);
							updateRequest.addOperation(o);
						}
					}
				} else {
					field = attr.fields[fieldIdx];
					if(applyToNonEmptyNodes || (field.value == null && (field.symbol == null || field.hasReasonBlankSpecified()))) {
						o = createApproveMissingOperation(node, fieldIdx);
						updateRequest.addOperation(o);
					}
				}
			}
		}
		
		private static function createApproveMissingOperation(node:NodeProxy, fieldIdx:int):UpdateRequestOperation {
			var o:UpdateRequestOperation = new UpdateRequestOperation();
			o.method = UpdateRequestOperation$Method.APPROVE_MISSING;
			o.parentEntityId = node.parentId;
			o.nodeName = node.name;
			o.nodeId = node.id;
			o.fieldIndex = fieldIdx;
			return o;
		}
		
		protected static function confirmErrorHandler(event:NodeEvent):void {
			var updRequest:UpdateRequest = new UpdateRequest();
			var op:UpdateRequestOperation;
			if ( event.node != null ) {
				op = createConfirmErrorOperation(event.node);
				updRequest.addOperation(op);
			} else {
				for each (var node:NodeProxy in event.nodes) {
					var a:AttributeProxy = AttributeProxy(node);
					if ( a.hasErrors() && ! a.errorConfirmed ) { 
						op = createConfirmErrorOperation(node);
						updRequest.addOperation(op);
					}
				}
			}
			_dataClient.updateActiveRecord(updRequest, null, faultHandler);
		}
		
		protected static function createConfirmErrorOperation(node:NodeProxy):UpdateRequestOperation {
			var updRequestOp:UpdateRequestOperation = new UpdateRequestOperation();
			updRequestOp.method = UpdateRequestOperation$Method.CONFIRM_ERROR;
			updRequestOp.parentEntityId = node.parentId;
			updRequestOp.nodeName = node.name;
			updRequestOp.nodeId = node.id;
			return updRequestOp;
		}
		
		protected static function deleteNodeHandler(event:NodeEvent):void {
			var node:NodeProxy = event.node;
			var updRequestOp:UpdateRequestOperation = new UpdateRequestOperation();
			updRequestOp.method = UpdateRequestOperation$Method.DELETE;
			updRequestOp.parentEntityId = node.parentId;
			updRequestOp.nodeName = node.name;
			updRequestOp.nodeId = node.id;
			var updRequest:UpdateRequest = new UpdateRequest(updRequestOp);
			_dataClient.updateActiveRecord(updRequest, null, faultHandler);
		}
		
		protected function updateResponseReceivedHandler(event:ApplicationEvent):void {
			if(_view.attribute != null) {
				var responses:IList = IList(event.result);
				for each (var response:UpdateResponse in responses) {
					if(response.nodeId == _view.attribute.id) {
						_view.changed = false
						updateView();
						return;
					}
				}
			}
		}
		
		protected function setAttribute(value:AttributeProxy):void {
			_view.changed = false;
			_view.visited = false;
			_view.updating = false;
			updateView();
		}
		
		protected function changeHandler(event:Event):void {
			//TODO if autocomplete enabled show autocomplete popup...
			_view.changed = true;
			var inputFieldEvent:InputFieldEvent = new InputFieldEvent(InputFieldEvent.CHANGING);
			_view.dispatchEvent(inputFieldEvent);
		}
		
		protected function focusInHandler(event:FocusEvent):void {
			UIUtil.ensureElementIsVisible(event.target);
		}
		
		protected function focusOutHandler(event:FocusEvent):void {
			if(_view.applyChangesOnFocusOut && _view.changed) {
				updateValue();
			}
			_view.visited = true;
			var inputFieldEvent:InputFieldEvent = new InputFieldEvent(InputFieldEvent.VISITED);
			inputFieldEvent.inputField = _view;
			eventDispatcher.dispatchEvent(inputFieldEvent);
		}
		
		protected function keyDownHandler(event:KeyboardEvent):void {
			var keyCode:uint = event.keyCode;
			switch(keyCode) {
				case Keyboard.ESCAPE:
					undoLastChange();
					break;
				case Keyboard.DOWN:
					setFocusOnNextSiblingEntity();
					break;
				case Keyboard.UP:
					setFocusOnPreviousSiblingEntity();
					break;
				case Keyboard.PAGE_DOWN:
					setFocusOnNextSiblingEntity(10);
					break;
				case Keyboard.PAGE_UP:
					setFocusOnPreviousSiblingEntity(10);
					break;
			}
		}
		
		protected function setFocusOnNextSiblingEntity(offset:int = 1):void {
			setFocusOnSiblingEntity(offset);
		}
		
		protected function setFocusOnPreviousSiblingEntity(offset:int = 1):void {
			setFocusOnSiblingEntity(- offset);
		}
		
		protected function setFocusOnSiblingEntity(offset:int):void {
			var attributeName:String = _view.attributeDefinition.name;
			var inputFieldEvent:InputFieldEvent = new InputFieldEvent(InputFieldEvent.SET_FOCUS);
			inputFieldEvent.nodeName = attributeName;
			inputFieldEvent.fieldIdx = _view.fieldIndex;
			var attributeToFocusIn:AttributeProxy;
			if ( _view.attributeDefinition.multiple ) {
				var attribute:AttributeProxy = _view.attribute;
				attributeToFocusIn = AttributeProxy(attribute.getSibling(offset));
			} else {
				var siblingEntity:EntityProxy = EntityProxy(_view.parentEntity.getSibling(offset));
				if ( siblingEntity != null ) {
					attributeToFocusIn = siblingEntity.getSingleAttribute(attributeName);
				}
			}
			if ( attributeToFocusIn != null ) {
				inputFieldEvent.attributeId = attributeToFocusIn.id;
				inputFieldEvent.parentEntityId = attributeToFocusIn.parentId;
				eventDispatcher.dispatchEvent(inputFieldEvent);
			}
		}
		
		public function undoLastChange():void {
			_view.changed = false;
			updateView();
		}
		
		public function updateValue():void {
			var o:UpdateRequestOperation = createUpdateValueOperation();
			var value:String = null;
			var text:String = textToRequestValue();
			var symbol:FieldSymbol = null;
			if ( FieldProxy.isShortCutForReasonBlank(text) ) {
				symbol = FieldProxy.parseShortCutForReasonBlank(text);
			} else {
				value = text;
			}
			sendUpdateRequest(o);
		}
		
		public function createUpdateValueOperation():UpdateRequestOperation {
			var symbol:FieldSymbol = null;
			var value:String = null;
			var text:String = textToRequestValue();
			if ( FieldProxy.isShortCutForReasonBlank(text) ) {
				symbol = FieldProxy.parseShortCutForReasonBlank(text);
			} else {
				value = text;
			}
			var remarks:String = getRemarks(); //preserve old remarks
			var o:UpdateRequestOperation = getUpdateFieldOperation(value, symbol, remarks);
			return o;
		}

		protected function getUpdateFieldOperation(value:String, symbol:FieldSymbol = null, remarks:String = null):UpdateRequestOperation {
			var nodeId:Number = _view.attribute != null ? _view.attribute.id: NaN;
			var o:UpdateRequestOperation = getUpdateRequestOperation(UpdateRequestOperation$Method.UPDATE, nodeId, value, symbol, remarks);
			return o;
		}
		
		protected function sendUpdateRequest(o:UpdateRequestOperation):void {
			var req:UpdateRequest = new UpdateRequest(o);
			dataClient.updateActiveRecord(req, updateResultHandler, faultHandler);
			_view.updating = true;
		}
		
		protected function getUpdateRequestOperation(method:UpdateRequestOperation$Method, nodeId:Number, 
								 value:String = null, symbol:FieldSymbol = null, remarks:String = null):UpdateRequestOperation {
			var o:UpdateRequestOperation = new UpdateRequestOperation();
			var def:AttributeDefinitionProxy = _view.attributeDefinition;
			o.method = method;
			o.parentEntityId = _view.parentEntity.id;
			o.nodeName = def.name;
			o.nodeId = nodeId;
			o.fieldIndex = _view.fieldIndex;
			o.value = value;
			o.symbol = symbol;
			o.remarks = remarks;
			return o;
		}
		
		protected function updateResultHandler(event:ResultEvent, token:Object = null):void {
			_view.changed = false;
			_view.updating = false;
			//_view.currentState = InputField.STATE_SAVE_COMPLETE;
		}
		
		protected function getTextFromValue():String {
			var attribute:AttributeProxy = _view.attribute;
			if(attribute != null) {
				var field:FieldProxy = _view.attribute.getField(_view.fieldIndex);
				return field.getValueAsText();
			} else {
				return "";
			}
		}

		protected function textToRequestValue():String {
			var result:String = null;
			var text:String = _view.text;
			if(StringUtil.isNotBlank(text)) {
				result = StringUtil.trim(text);
			}
			return result;
		}
		
		protected function updateView():void {
			//update view according to attribute (generic text value)
			var hasRemarks:Boolean = false;
			if(_view.attributeDefinition != null) {
				var text:String = getTextFromValue();
				if ( ! _view.changed ) {
					_view.text = text;
				}
				hasRemarks = StringUtil.isNotBlank(getRemarks());
				_contextMenu.updateItems();
			}
			
			var newStyles:Array = [];
			if ( hasRemarks ) {
				newStyles.push(InputField.REMARKS_PRESENT_STYLE);
			}
			if ( ! Application.activeRecordEditable ) {
				newStyles.push(InputField.READONLY_STYLE);	
			}
			UIUtil.replaceStyleNames(_view.validationStateDisplay, newStyles, 
				[InputField.REMARKS_PRESENT_STYLE, InputField.READONLY_STYLE] );
			
			//_view.hasRemarks = hasRemarks;
			_view.editable = Application.activeRecordEditable;
		}
		
		protected function getField():FieldProxy {
			if(_view.attribute != null) {
				var fieldIndex:int = 0;
				if(_view.fieldIndex >= 0) {
					fieldIndex = _view.fieldIndex;
				}
				return _view.attribute.getField(fieldIndex);
			}
			return null;
		}
		
		protected function getRemarks():String {
			var f:FieldProxy = getField();
			if(f != null) {
				return f.remarks;
			} 
			return null;
		}
		
		protected function getSymbol():FieldSymbol {
			var f:FieldProxy = getField();
			if(f != null) {
				return f.symbol;
			} 
			return null;
		}
		
		protected function get contextMenu():InputFieldContextMenu {
			return _contextMenu;
		}

		protected static function get dataClient():DataClient {
			return _dataClient;
		}
	}
}
