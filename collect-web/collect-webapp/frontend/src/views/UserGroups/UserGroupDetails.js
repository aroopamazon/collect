import React, { Component } from 'react';
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { Alert, Button, ButtonGroup, ButtonToolbar, Container, Row, Col,
    Form, FormGroup, Label, Input, FormText, FormFeedback } from 'reactstrap';
import { DropdownButton, MenuItem } from 'react-bootstrap';

import {BootstrapTable, TableHeaderColumn} from 'react-bootstrap-table';
import ItemDetails from '../../components/MasterDetail/ItemDetails'
import UserGroupService from '../../services/UserGroupService';
import UserRoleEditor from './UserRoleEditor';

class UserGroupDetails extends ItemDetails {

    constructor( props ) {
		super( props );

        this.handleSaveBtnClick = this.handleSaveBtnClick.bind(this);
        this.handleAvailableUsersRowSelect = this.handleAvailableUsersRowSelect.bind(this);
        this.handleUsersInGroupRowSelect = this.handleUsersInGroupRowSelect.bind(this);
        this.handleAddSelectedUsersToGroup = this.handleAddSelectedUsersToGroup.bind(this);
        this.handleRemoveSelectedUsersToGroup = this.handleRemoveSelectedUsersToGroup.bind(this);
    }
    
    static propTypes = {
		users: PropTypes.array.isRequired,
		isFetchingUsers: PropTypes.bool.isRequired,
		lastUpdatedUsers: PropTypes.number,
		dispatch: PropTypes.func.isRequired
	}

    userGroupService = new UserGroupService();

    updateStateFromProps(props) {
        this.state = {
            newItem: ! props.userGroup.id,
            id: props.userGroup.id,
            name: props.userGroup.name,
            label: props.userGroup.label,
            description: props.userGroup.description,
            visibilityCode: props.userGroup.visibilityCode,
            enabled: props.userGroup.enabled,
            errorFeedback: [],
            alertMessageOpen: false,
            availableUsers: this.getAvailableUsers(props.users, props.userGroup.users),
            usersInGroup: props.userGroup.users,
            selectedAvailableUsers: [],
            selectedAvailableUsersIds: [],
            selectedUsersInGroup: [],
            selectedUsersInGroupIds: [],
            newUserRoleCode: "OPERATOR"
        }
    }

    getAvailableUsers(allUsers, groupUsers) {
        return allUsers.filter((user) => {
            let groupUser = groupUsers.find((u) => {
                return u.userId === user.id
            });
            return groupUser === undefined
        });
    }

    extractFormObject() {
        return {
            id: this.state.id,
            name: this.state.name,
            label: this.state.label,
            description: this.state.description,
            visibilityCode: this.state.visibilityCode,
            enabled: this.state.enabled,
            users: this.state.usersInGroup
        }
    }

    handleSaveBtnClick() {
        let formObject = this.extractFormObject();
        this.userGroupService.save(formObject).then(this.updateStateFromResponse);
    }
    
    handleAvailableUsersRowSelect(user, isSelected, e) {
        let newSelectedUsers;
        if (isSelected) {
            newSelectedUsers = this.state.selectedAvailableUsers.concat([user]);
        } else {
            let idx = this.state.selectedAvailableUsers.indexOf(user);
            newSelectedUsers = this.state.selectedAvailableUsers.slice(idx, 0);
        }
        this.setState({ ...this.state, 
            selectedAvailableUsers: newSelectedUsers,
            selectedAvailableUsersIds: newSelectedUsers.map(u => u.id)
		})
    }

    handleUsersInGroupRowSelect(user, isSelected, e) {
        let newSelectedUsers;
        if (isSelected) {
            newSelectedUsers = this.state.selectedUsersInGroup.concat([user]);
        } else {
            let idx = this.state.selectedAvailableUsers.indexOf(user);
            newSelectedUsers = this.state.selectedUsersInGroup.slice(idx, 0);
        }
        this.setState({ ...this.state, 
            selectedUsersInGroup: newSelectedUsers,
            selectedUsersInGroupIds: newSelectedUsers.map(u => u.userId)
		})
    }

    handleAddSelectedUsersToGroup() {
        let selectedUsers = this.state.selectedAvailableUsers
        let newUsersInGroup = []
        let role = this.state.newUserRoleCode

        selectedUsers.forEach(user => {
            newUsersInGroup.push({
                userId: user.id, 
                username: user.username, 
                userEnabled: user.enabled, 
                role: role,
                joinStatus: 'ACCEPTED'
            });
        })
        let newAvailableUsers = this.state.availableUsers.filter(u => selectedUsers.indexOf(u) < 0)

        this.setState({ ...this.state, 
            usersInGroup: this.state.usersInGroup.concat(newUsersInGroup),
            availableUsers: newAvailableUsers,
            selectedAvailableUsers: [],
            selectedAvailableUsersIds: [],
		})
    }

    handleRemoveSelectedUsersToGroup() {
        let selectedUsers = this.state.selectedUsersInGroup.map(userInGroup => {
            return {
                id: userInGroup.userId,
                username: userInGroup.username,
                enabled: userInGroup.userEnabled
            }
        })
        let notSelectedUsersInGroup = this.state.usersInGroup.filter(u => this.state.selectedUsersInGroup.indexOf(u) < 0)
        let newUsersInGroup = notSelectedUsersInGroup
        
        this.setState({ ...this.state, 
            selectedUsersInGroup: [],
            selectedUsersInGroupIds: [],
            availableUsers: this.state.availableUsers.concat(selectedUsers),
            usersInGroup: newUsersInGroup
		})
    }

    render() {
        let roles = ['OWNER', 'ADMINISTRATOR', 'OPERATOR', 'VIEWER']
        let joinStatuses = ['ACCEPTED', 'PENDING', 'REJECTED']

        let createRoleEditor = (onUpdate, props) => (<UserRoleEditor onUpdate={ onUpdate } {...props}/>);
        
        let isNotDescendantOf = function(group1, group2) {
            return false;
        }
        let editedUserGroup = this.props.userGroup;
        let availableParentGroups = this.props.userGroups.filter(group => {
            return group.id != editedUserGroup.id && (editedUserGroup.id == null || isNotDescendantOf(group, editedUserGroup))
        })
        let parentGroupOptions = [<option value="">---</option>]
            .concat(availableParentGroups.map(group => <option value={group.id}>{group.label}</option>))

		return (
            <div>
                <Alert color={this.state.alertMessageColor} isOpen={this.state.alertMessageOpen}>
                    {this.state.alertMessageText}
                </Alert>
                <Form>
                    <FormGroup row color={this.getFieldState('name')}>
                        <Label for="name" sm={2}>Name</Label>
                        <Col sm={10}>
                            <Input type="text" name="name" id="name" 
                                value={this.state.name}
                                state={this.getFieldState('name')}
                                onChange={(event) => this.setState({...this.state, name: event.target.value})} />
                            {this.state.errorFeedback['name'] &&
                                <FormFeedback>{this.state.errorFeedback['name']}</FormFeedback>
                            }
                        </Col>
                    </FormGroup>
                    <FormGroup row color={this.getFieldState('label')}>
                        <Label for="label" sm={2}>Label</Label>
                        <Col sm={10}>
                            <Input type="text" name="label" id="label" 
                                value={this.state.label}
                                state={this.getFieldState('label')}
                                onChange={(event) => this.setState({...this.state, label: event.target.value})} />
                            {this.state.errorFeedback['label'] &&
                                <FormFeedback>{this.state.errorFeedback['label']}</FormFeedback>
                            }
                        </Col>
                    </FormGroup>
                    <FormGroup row color={this.getFieldState('description')}>
                        <Label for="description" sm={2}>Description</Label>
                        <Col sm={10}>
                            <Input type="text" name="description" id="description" 
                                value={this.state.description}
                                state={this.getFieldState('description')}
                                onChange={(event) => this.setState({...this.state, description: event.target.value})} />
                            {this.state.errorFeedback['description'] &&
                                <FormFeedback>{this.state.errorFeedback['description']}</FormFeedback>
                            }
                        </Col>
                    </FormGroup>
                    <FormGroup row color={this.getFieldState('visibilityCode')}>
                        <Col sm={2}>
                            <Label for="visibilityCode">Visibility</Label>
                        </Col>
                        <Col sm={2}>
                            <Input type="radio" value="P" name="visibilityCode" id="visibilityCodePublic"
                                checked={this.state.visibilityCode === 'P'} 
                                state={this.getFieldState('visibilityCode')}
                                onChange={(event) => this.setState({...this.state, visibilityCode: event.target.value})} />
                            <Label for="visibilityCodePublic">Public</Label>
                        </Col>
                        <Col sm={8}>
                            <Input type="radio" value="N" name="visibilityCode" id="visibilityCodePrivate"
                                checked={this.state.visibilityCode === 'N'} 
                                state={this.getFieldState('visibilityCode')}
                                onChange={(event) => this.setState({...this.state, visibilityCode: event.target.value})} />
                            <Label for="visibilityCodePrivate">Private</Label>
                            {this.state.errorFeedback['visibilityCode'] &&
                                <FormFeedback>{this.state.errorFeedback['visibilityCode']}</FormFeedback>
                            }
                        </Col>
                    </FormGroup>
                    <FormGroup row color={this.getFieldState('parentGroup')}>
                        <Label for="parentGroupSelect" sm={2}>Parent Group</Label>
                        <Col sm={10}>
                            <Input type="select" name="parentGroup" id="parentGroupSelect"
                                state={this.getFieldState('parentGroup')}
                                onChange={(event) => this.setState({...this.state, parentGroupId: event.target.value})}
                                >{parentGroupOptions}</Input>
                            {this.state.errorFeedback['parentGroup'] &&
                                <FormFeedback>{this.state.errorFeedback['parentGroup']}</FormFeedback>
                            }
                        </Col>
                    </FormGroup>
                    <FormGroup row color={this.getFieldState('enabled')}>
                        <Label for="enabled" sm={2}>Enabled</Label>
                        <Col sm={10}>
                            <Input type="checkbox" name="enabled" id="enabled"
                                checked={this.state.enabled} 
                                state={this.getFieldState('enabled')}
                                onChange={(event) => this.setState({...this.state, enabled: event.target.checked})} />
                            {this.state.errorFeedback['enabled'] &&
                                <FormFeedback>{this.state.errorFeedback['enabled']}</FormFeedback>
                            }
                        </Col>
                    </FormGroup>
                    
                    <Row>
                        <Col sm="2">
                            <fieldset className="secondary">
                                <legend>Add/Remove Users</legend>
                                <Row>
                                    <Col sm="2">
                                        <BootstrapTable
                                            data={this.state.availableUsers}
                                            striped	hover	condensed
                                            selectRow={ {mode: 'checkbox', clickToSelect: true, hideSelectionColumn: true, bgColor: 'lightBlue', 
                                                onSelect: this.handleAvailableUsersRowSelect, selected: this.state.selectedAvailableUsersIds} }
                                            >
                                            <TableHeaderColumn dataField="id" isKey hidden>Id</TableHeaderColumn>
                                            <TableHeaderColumn dataField="username">Username</TableHeaderColumn>
                                            <TableHeaderColumn dataField="enabled">Enabled</TableHeaderColumn>
                                        </BootstrapTable>
                                    </Col>
                                    <Col sm="3">
                                        <Row>
                                            <Col sm="5">
                                                <Input type="select" name="newUserRole" id="newUserRoleSelect" 
                                                        onChange={(event) => this.setState({...this.state, newUserRoleCode: event.target.value})}
                                                        value={this.state.newUserRoleCode}>
                                                    {roles.map(role => <option key={role} value={role}>{role}</option>)}
                                                </Input>
                                            </Col>
                                            <Col sm="2">
                                                <Button onClick={this.handleAddSelectedUsersToGroup}>&gt;</Button>
                                            </Col>
                                        </Row>
                                        <Row>
                                            <Col sm={{ size: 2, offset: 6 }}>
                                                <Button onClick={this.handleRemoveSelectedUsersToGroup}>&lt;</Button>
                                            </Col>
                                        </Row>
                                    </Col>
                                </Row>
                            </fieldset>
                        </Col>
                        <Col sm="6">
                            <fieldset className="secondary">
                                <legend>Selected Users</legend>
                                <BootstrapTable
                                    data={this.state.usersInGroup}
                                    striped	hover	condensed
                                    cellEdit={ { mode: 'click' } }
                                    selectRow={ {mode: 'checkbox', clickToSelect: true, hideSelectionColumn: true, bgColor: 'lightBlue', 
                                        onSelect: this.handleUsersInGroupRowSelect, selected: this.state.selectedUsersInGroupIds} }
                                    >
                                    <TableHeaderColumn dataField="userId" isKey hidden>Id</TableHeaderColumn>
                                    <TableHeaderColumn dataField="username" editable={false}>Username</TableHeaderColumn>
                                    <TableHeaderColumn dataField="userEnabled" editable={false}>Enabled</TableHeaderColumn>
                                    <TableHeaderColumn dataField="role" editable={ { type: 'select', options: { values: roles } } }
                                        >Role</TableHeaderColumn>
                                    <TableHeaderColumn dataField="joinStatus" editable={ { type: 'select', options: { values: joinStatuses } } }>Status</TableHeaderColumn>
                                </BootstrapTable>
                            </fieldset>
                        </Col>
                    </Row>

                    <FormGroup check row>
                        <Col sm={{ size: 2, offset: 5 }}>
                            <Button color="primary" onClick={this.handleSaveBtnClick}>Save</Button>
                            <Button color="danger" onClick={this.handleDeleteBtnClick}>Delete</Button>
                        </Col>
                    </FormGroup>
                </Form>
            </div>
		);
    }
}

const mapStateToProps = state => {
    const {
        isFetching: isFetchingUserGroups,
        lastUpdated: lastUpdatedUserGroups,
        userGroups
    } = state.userGroups || {
        isFetchingUserGroups: true,
        userGroups: []
    }
    const {
        isFetching: isFetchingUsers,
        lastUpdated: lastUpdatedUsers,
        users
    } = state.users || {
        isFetchingUsers: true,
        userGroups: []
    }
    return {
        isFetchingUsers,
        lastUpdatedUsers,
        users,
        isFetchingUserGroups,
        lastUpdatedUserGroups,
        userGroups
    }
  }
  
  export default connect(mapStateToProps)(UserGroupDetails);