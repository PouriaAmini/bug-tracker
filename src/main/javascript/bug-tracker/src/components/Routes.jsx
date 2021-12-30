import React  from 'react'

import { Route, Switch } from 'react-router-dom'

import { useHistory } from 'react-router-dom'
import jwtDecode from 'jwt-decode'
import Settings from "../pages/Settings";
import BugsList from "./bugs/BugsList";
import Profile from "../pages/Profile";
import NewBug from "./bugs/NewBug";
import Bug from "./bugs/Bug";
import GroupsList from "./groups/GroupsList";
import NewGroup from "./groups/NewGroup";
import Group from "./groups/Group";
import ProjectsList from "./projects/ProjectsList";
import NewProject from "./projects/NewProject";
import Project from "./projects/Project";
import Dashboard from '../pages/Dashboard'

const Routes = () => {

    const user = JSON.parse(localStorage.getItem("user")).Users[0];
    const token = localStorage.getItem("access_token");
    const currentTime = new Date();
    const history = useHistory();
    if(!token) {
        history.push("/login");
    }
    const data = jwtDecode(token);

    if(data.exp * 1000 < currentTime.getTime()) {
        let refreshToken = localStorage.getItem("refresh_token");
        let refreshTokenData = jwtDecode(refreshToken);

        if(
            !refreshToken || 
            refreshTokenData.exp * 1000 <= currentTime.getTime()
        ) {
            localStorage.removeItem("access_token");
            localStorage.removeItem("refresh_token");
            localStorage.removeItem("projects");
            localStorage.removeItem("user");
            history.push("/login")
        }
        else {
            fetch("http://localhost:8080/api/refresh/token", 
            {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${refreshToken}`
                }
            })
            .then(response => {
                response.json().then(data => {
                    localStorage.setItem("access_token", data.access_token);
                })}
            );
        }
    }

    return (
        <Switch>
            <Route exact path='/' component={Dashboard}/>
            <Route exact path="/bugs" component={BugsList}/>
            <Route exact path="/bugs/create" component={NewBug}/>
            {
                window.location.pathname.startsWith("/bugs/") ?
                    <Route exact path={window.location.pathname} component={() =>
                        <Bug id={window.location.pathname.slice(6)} />
                    }/> : ''
            }
            <Route exact path="/groups" component={GroupsList}/>
            {
                user.position === "MANAGER" ?
                        <Route exact path="/groups/create" component={NewGroup}/>
                     :
                    alert("Only Managers can create a new group!")
            }
            {
                window.location.pathname.startsWith("/groups/") ?
                    <Route exact path={window.location.pathname} component={() =>
                        <Group id={window.location.pathname.slice(6)} />
                    }/> : ''
            }
            <Route exact path="/projects" component={ProjectsList}/>
            {
                user.position === "MANAGER" ?
                        <Route exact path="/projects/create" component={NewProject}/>
                     :
                    alert("Only Managers can create a new project!")
            }
            {
                window.location.pathname.startsWith("/projects/") ?
                    <Route exact path={window.location.pathname} component={() =>
                        <Project id={window.location.pathname.slice(6)} />
                    }/> : ''
            }
            <Route exact path='/profile' component={Profile}/>
            <Route exact path='/settings' component={Settings}/>
        </Switch>
    )
}

export default Routes
