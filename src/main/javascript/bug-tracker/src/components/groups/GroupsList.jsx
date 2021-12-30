import React from "react";
import Table from "../table/Table";
import Badge from "../badge/Badge";

import './grouplist.css'

const GroupsList = () => {

    let groups = [];
    const bugsExtractor = JSON
        .parse(localStorage.getItem("projects"))["Projects"]
        .map((project,  index) => {
            project.groups.map((group,  index) => (
                groups.push(
                    {
                        "id": `${group.id}`,
                        "name": `${group.name}`,
                        "description": group.briefDescription ? `${group.briefDescription.substring(0,50)}...` : '',
                        "dateCreated": `${group.dateCreated.substring(0,10)}`,
                        "dateResolved": `${group.dateResolved ? group.dateResolved.substring(0,10) : "N/A"}`,
                        "assignedTo": group.assignedTo ? group.assignedTo.map((user) => {
                            return `${user.firstName.substring(0,1).toUpperCase()}.${user.lastName.substring(0,1).toUpperCase()}.`
                        }).slice(0, 3) : [],
                        "bugs": group.bugs ? group.bugs.map((bug) => {
                            return bug.name[4] ? `${bug.name.substring(0,5)}...` : `${bug.name.substring(0,5)}`
                        }).slice(0, 3) : [],
                        "priority": `${group.priorityAverage}`,
                        "status": `${group.status ? group.status : 'UNASSIGNED'}`
                    }
                )
            ))}
        );

    const groupsTable = {
        header: [
            "name",
            "description",
            "date created",
            "date resolved",
            "assigned to",
            "bugs",
            "priority",
            "status"
        ],
        body: [...groups]
    }

    const renderGroupsHead = (item, index) => (
        <th key={index}>{item}</th>
    )

    const renderGroupsBody = (item, index) => (
        <tr key={index} style={{ whiteSpace: "nowrap"}} onClick={() => window.location.replace(`/groups/${item.id}`)}>
            <td>{item.name}</td>
            <td>{item.description}</td>
            <td>{item.dateCreated}</td>
            <td>{item.dateResolved}</td>
            <td>
                {
                    item.assignedTo.map((user) => (
                        <Badge type={"ASSIGNED"} content={`${user}`}/>
                    ))
                }
            </td>
            <td>
                {
                    item.bugs.map((bug) => (
                        <Badge type={"BUG"} content={`${bug}`}/>
                    ))
                }
            </td>
            <td>
                <Badge type={item.priority} content={`${item.priority}`}/>
            </td>
            <td>
                <Badge type={item.status} content={`${item.status}`}/>
            </td>
        </tr>
    )

    const user = JSON.parse(localStorage.getItem("user")).Users[0];

    return (
        <div>
            <h2 className="page-header">
                Groups
            </h2>
            <div className='new-group__toggle' onClick={() => {
                user.position === "MANAGER" ?
                    window.location.replace("/groups/create") :
                    alert("Only Managers can create a new group!")
            }}>
                <i className="bx bx-plus-circle"/>
            </div>
            <div className="row">
                <div className="col-12">
                    <div className="card">
                        <div className="card__body">
                            <Table
                                limit='10'
                                headData={groupsTable.header}
                                renderHead={(item, index) => renderGroupsHead(item, index)}
                                bodyData={groupsTable.body}
                                renderBody={(item, index) => renderGroupsBody(item, index)}
                            />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default GroupsList;