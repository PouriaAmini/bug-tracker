import React from "react";
import Table from "../table/Table";
import Badge from "../badge/Badge";

import './projectlist.css'

let projects = [];
const bugsExtractor = JSON
    .parse(localStorage.getItem("projects"))["Projects"]
    .map((project,  index) => {
        projects.push(
            {
                "id": `${project.id}`,
                "name": `${project.name}`,
                "description": project.briefDescription ? `${project.briefDescription.substring(0,50)}...` : '',
                "dateCreated": `${project.dateCreated.substring(0,10)}`,
                "dateResolved": `${project.dateResolved ? project.dateResolved.substring(0,10) : "N/A"}`,
                "assignedTo": project.assignedTo ? project.assignedTo.map((user) => {
                    return `${user.firstName.substring(0,1).toUpperCase()}.${user.lastName.substring(0,1).toUpperCase()}.`
                }).slice(0, 3) : [],
                "groups": project.groups ? project.groups.map((group) => {
                    return group.name[4] ? `${group.name.substring(0,5)}...` : `${group.name}`
                }).slice(0, 3) : [],
                "priority": `${project.priorityAverage}`,
                "status": `${project.status ? project.status : 'UNASSIGNED'}`
            }
        )}
    );

const projectsTable = {
    header: [
        "name",
        "description",
        "date created",
        "date resolved",
        "assigned to",
        "groups",
        "priority",
        "status"
    ],
    body: [...projects]
}

const renderProjectsHead = (item, index) => (
    <th key={index}>{item}</th>
)

const renderProjectsBody = (item, index) => (
    <tr key={index} style={{ whiteSpace: "nowrap"}} onClick={() => window.location.replace(`/projects/${item.id}`)}>
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
                item.groups.map((group) => (
                    <Badge type={"GROUP"} content={`${group}`}/>
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

const ProjectsList = () => {
    return (
        <div>
            <h2 className="page-header">
                Projects
            </h2>
            <div className='new-project__toggle' onClick={() => {
                user.position === "MANAGER" ?
                    window.location.replace("/projects/create") :
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
                                headData={projectsTable.header}
                                renderHead={(item, index) => renderProjectsHead(item, index)}
                                bodyData={projectsTable.body}
                                renderBody={(item, index) => renderProjectsBody(item, index)}
                            />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ProjectsList;