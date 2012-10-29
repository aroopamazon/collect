/**
 * 
 */
package org.openforis.collect.designer.viewmodel;

import org.openforis.collect.designer.form.SurveyObjectFormObject;
import org.openforis.collect.designer.form.UniquenessCheckFormObject;
import org.openforis.idm.metamodel.AttributeDefinition;
import org.openforis.idm.metamodel.validation.UniquenessCheck;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;

/**
 * @author S. Ricci
 *
 */
public class UniquenessCheckVM extends CheckVM<UniquenessCheck> {

	@Init(superclass=false)
	@Override
	public void init(@ExecutionArgParam("parentDefinition") AttributeDefinition parentDefinition,
			@ExecutionArgParam("check") UniquenessCheck check, @ExecutionArgParam("newItem") Boolean newItem ) {
		super.init(parentDefinition, check, newItem);
	}
	
	@Override
	protected SurveyObjectFormObject<UniquenessCheck> createFormObject() {
		return new UniquenessCheckFormObject();
	}

}
