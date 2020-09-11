package org.openforis.collect.command.handler;

import org.openforis.collect.command.DeleteNodeCommand;
import org.openforis.collect.model.CollectRecord;
import org.openforis.collect.model.NodeChangeSet;
import org.openforis.idm.model.Attribute;
import org.openforis.idm.model.Value;

public class DeleteNodeCommandHandler<C extends DeleteNodeCommand> extends NodeCommandHandler<C> {

	@Override
	public RecordCommandResult executeForResult(C command) {
		CollectRecord record = findRecord(command);
		Attribute<?, Value> attribute = findAttribute(command, record);
		NodeChangeSet changeSet = recordUpdater.deleteNode(attribute);

		return new RecordCommandResult(record, changeSet);
	}

}
