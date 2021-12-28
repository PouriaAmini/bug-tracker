import React, {useEffect} from 'react'

import { Link } from 'react-router-dom'

import Chart from 'react-apexcharts'

import { useSelector } from 'react-redux'

import StatusCard from '../components/status-card/StatusCard'

import Table from '../components/table/Table'

import Badge from '../components/badge/Badge'

import statusCards from '../assets/JsonData/status-card-data.json'

const chartOptions = {
    series: [{
        name: 'Online Customers',
        data: [40,70,20,90,36,80,30,91,60]
    }, {
        name: 'Store Customers',
        data: [40, 30, 70, 80, 40, 16, 40, 20, 51, 10]
    }],
    options: {
        color: ['#6ab04c', '#2980b9'],
        chart: {
            background: 'transparent'
        },
        dataLabels: {
            enabled: false
        },
        stroke: {
            curve: 'smooth'
        },
        xaxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep']
        },
        legend: {
            position: 'top'
        },
        grid: {
            show: false
        }
    }
}

const projects = JSON.parse(localStorage.getItem("projects"))["Projects"].map((item,  index) => (
    {
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
    <tr key={index}>
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

const latestOrders = {
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
    <tr key={index}>
        <td>{item.name}</td>
        <td>{item.description}</td>
        <td style={{ whiteSpace: "nowrap"}}>{item.dateCreated}</td>
        <td>{item.creator}</td>
        <td style={{ whiteSpace: "nowrap"}}>
            <Badge type={item.priority} content={`${item.priority} Priority`}/>
        </td>
    </tr>
)

const Dashboard = () => {

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
                            type='line'
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
                                limit={5}
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
                                headData={latestOrders.header}
                                renderHead={(item, index) => renderBugsHead(item, index)}
                                bodyData={latestOrders.body}
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
