import React, { useEffect } from 'react'

import { Route, Switch } from 'react-router-dom'

import Dashboard from '../pages/Dashboard'
import Customers from '../pages/Customers'
import { useHistory } from 'react-router-dom'
import jwtDecode from 'jwt-decode'

const Routes = () => {

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
            <Route path='/' exact component={Dashboard}/>
            <Route path='/bugs' component={Customers}/>
        </Switch>
    )
}

export default Routes
