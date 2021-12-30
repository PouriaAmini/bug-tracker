import React from 'react'

import Table from '../table/Table'
import Badge from "../badge/Badge";

import './buglist.css'

const BugsList = () => {

    let bugs = [];
    const bugsExtractor = JSON
        .parse(localStorage.getItem("projects"))["Projects"]
        .map((project,  index) => {
            project.groups.map((group,  index) => {
                group.bugs.map((bug,  index) => (
                    bugs.push(
                        {
                            "id": `${bug.id}`,
                            "name": `${bug.name}`,
                            "description": bug.briefDescription ? `${bug.briefDescription.substring(0,50)}...` : '',
                            "dateCreated": `${bug.dateCreated.substring(0,10)}`,
                            "dateResolved": `${bug.dateResolved ? bug.dateResolved.substring(0,10) : "N/A"}`,
                            "assignedTo": bug.assignedTo ? bug.assignedTo.map((user) => {
                                return `${user.firstName.substring(0,1).toUpperCase()}.${user.lastName.substring(0,1).toUpperCase()}.`
                            }).slice(0, 3) : [],
                            "creator": `${bug.creator.firstName} ${bug.creator.lastName}`,
                            "priority": `${bug.priority}`,
                            "status": `${bug.status ? bug.status : 'UNASSIGNED'}`
                        }
                    )
                ))
            })
        });

    const bugsTable = {
        header: [
            "name",
            "description",
            "date created",
            "date resolved",
            "assigned to",
            "creator",
            "priority",
            "status"
        ],
        body: [...bugs]
    }

    const renderBugsHead = (item, index) => (
        <th key={index}>{item}</th>
    )

    const renderBugsBody = (item, index) => (
        <tr key={index} style={{ whiteSpace: "nowrap"}} onClick={() => window.location.replace(`/bugs/${item.id}`)}>
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
            <td>{item.creator}</td>
            <td>
                <Badge type={item.priority} content={`${item.priority}`}/>
            </td>
            <td>
                <Badge type={item.status} content={`${item.status}`}/>
            </td>
        </tr>
    )

    return (
        <div className='bug-list'>
            <h2 className="page-header">
                Bugs
            </h2>
            <div className='new-bug__toggle' onClick={() => window.location.replace("/bugs/create")}>
                <i className="bx bx-plus-circle"/>
            </div>
            <div className="row">
                <div className="col-12">
                    <div className="card">
                        <div className="card__body">
                            <Table
                                limit='10'
                                headData={bugsTable.header}
                                renderHead={(item, index) => renderBugsHead(item, index)}
                                bodyData={bugsTable.body}
                                renderBody={(item, index) => renderBugsBody(item, index)}
                            />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default BugsList
