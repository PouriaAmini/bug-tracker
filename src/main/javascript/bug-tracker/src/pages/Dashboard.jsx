import React from 'react'

import { Link } from 'react-router-dom'

import Chart from 'react-apexcharts'

import { useSelector } from 'react-redux'

import StatusCard from '../components/status-card/StatusCard'

import Table from '../components/table/Table'

import Badge from '../components/badge/Badge'


const Dashboard = () => {

    let numberOfBugs = {
        HIGH: 0,
        MEDIUM: 0,
        LOW: 0,
        UNKNOWN: 0
    };
    const numberOfBugsExtractor = JSON
        .parse(localStorage.getItem("projects"))["Projects"]
        .map((project,  index) => {
            project.groups.map((group,  index) => {
                group.bugs.map((bug,  index) => {
                    if(bug.priority === 'HIGH') {
                        numberOfBugs.HIGH++;
                    }
                    else if(bug.priority === 'MEDIUM') {
                        numberOfBugs.MEDIUM++;
                    }
                    else if(bug.priority === 'LOW') {
                        numberOfBugs.LOW++;
                    }
                    else {
                        numberOfBugs.UNKNOWN++;
                    }
                })
            })
        });

    const statusCards = [
        {
            "icon": "bx bx-cuboid",
            "count": [numberOfBugs.HIGH],
            "title": `HIGH PRIORITY BUGS`
        },
        {
            "icon": "bx bx-cube-alt",
            "count": [numberOfBugs.MEDIUM],
            "title": "MEDIUM PRIORITY BUGS"
        },
        {
            "icon": "bx bx-pyramid",
            "count": [numberOfBugs.LOW],
            "title": "LOW PRIORITY BUGS"
        },
        {
            "icon": "bx bx-error",
            "count": [numberOfBugs.UNKNOWN],
            "title": "UNKNOWN PRIORITY BUGS"
        }
    ]

    const chartOptions = {
        series: [numberOfBugs.HIGH, numberOfBugs.MEDIUM, numberOfBugs.LOW, numberOfBugs.UNKNOWN],
        options: {
            colors: ['#ff4a6b', '#fca11a', '#4caf50', '#bdbdbd'],
            labels: ['High Priority Bugs', 'Medium Priority Bugs', 'Low Priority Bugs', 'Unknown Priority Bugs'],
            chart: {
                type: 'donut',
            },
            responsive: [{
                breakpoint: 480,
                options: {
                    chart: {
                        width: 100
                    },
                    legend: {
                        position: 'top'
                    }
                }
            }]
        }
    }

    const projects = JSON.parse(localStorage.getItem("projects"))["Projects"].map((item,  index) => (
        {
            "id": `${item.id}`,
            "name": `${item.name}`,
            "description": `${item.briefDescription.substring(0,50)}...`,
            "priority": `${item.priorityAverage}`
        }
    ));

    const projectsTable = {
        head: [
            'name',
            'description',
            'priority'
        ],
        body: [...projects]
    }

    const renderProjectsHead = (item, index) => (
        <th key={index}>{item}</th>
    )

    const renderProjectsBody = (item, index) => {
        return (
            <tr key={index} onClick={() => window.location.replace(`/projects/${item.id}`)}>
                <td>{item.name}</td>
                <td style={{ whiteSpace: "nowrap"}}>
                    {item.description}
                </td>
                <td style={{ whiteSpace: "nowrap"}}>
                    <Badge type={item.priority} content={`${item.priority} Priority`}/>
                </td>
            </tr>
        )}

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
                            "creator": `${bug.creator.firstName} ${bug.creator.lastName}`,
                            "priority": `${bug.priority}`
                        }
                    )
                ))
            })
        });

    const latestCreatedBugs = {
        header: [
            "name",
            "description",
            "date created",
            "creator",
            "priority"
        ],
        body: [...bugs]
    }

    const renderBugsHead = (item, index) => (
        <th key={index}>{item}</th>
    )

    const renderBugsBody = (item, index) => (
        <tr key={index} onClick={() => window.location.replace(`/bugs/${item.id}`)}>
            <td>{item.name}</td>
            <td>{item.description}</td>
            <td style={{ whiteSpace: "nowrap"}}>{item.dateCreated}</td>
            <td>{item.creator}</td>
            <td style={{ whiteSpace: "nowrap"}}>
                <Badge type={item.priority} content={`${item.priority} Priority`}/>
            </td>
        </tr>
    )

    const themeReducer = useSelector(state => state.ThemeReducer.mode)

    return (
        <div>
            <h2 className="page-header">Dashboard</h2>
            <div className="row">
                <div className="col-6">
                    <div className="row">
                        {
                            statusCards.map((item, index) => (
                                <div className="col-6" key={index}>
                                    <StatusCard
                                        icon={item.icon}
                                        count={item.count}
                                        title={item.title}
                                    />
                                </div>
                            ))
                        }
                    </div>
                </div>
                <div className="col-6">
                    <div className="card full-height">
                        <Chart
                            options={themeReducer === 'theme-mode-dark' ? {
                                ...chartOptions.options,
                                theme: { mode: 'dark'}
                            } : {
                                ...chartOptions.options,
                                theme: { mode: 'light'}
                            }}
                            series={chartOptions.series}
                            type='donut'
                            height='100%'
                        />
                    </div>
                </div>
                <div className="col-4">
                    <div className="card">
                        <div className="card__header">
                            <h3>Projects</h3>
                        </div>
                        <div className="card__body">
                            <Table
                                only={8}
                                headData={projectsTable.head}
                                renderHead={(item, index) => renderProjectsHead(item, index)}
                                bodyData={projectsTable.body}
                                renderBody={(item, index) => renderProjectsBody(item, index)}
                            />
                        </div>
                        <div className="card__footer">
                            <Link to='/projects'>view all</Link>
                        </div>
                    </div>
                </div>
                <div className="col-8">
                    <div className="card">
                        <div className="card__header">
                            <h3>Bugs</h3>
                        </div>
                        <div className="card__body">
                            <Table
                                only={5}
                                headData={latestCreatedBugs.header}
                                renderHead={(item, index) => renderBugsHead(item, index)}
                                bodyData={latestCreatedBugs.body}
                                renderBody={(item, index) => renderBugsBody(item, index)}
                            />
                        </div>
                        <div className="card__footer">
                            <Link to='/bugs'>view all</Link>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Dashboard
