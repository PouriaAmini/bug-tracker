import React, { useEffect } from 'react'

import { Route, Switch } from 'react-router-dom'

import Dashboard from '../pages/Dashboard'
import Customers from '../pages/Customers'
import { useHistory } from 'react-router-dom'
import jwtDecode from 'jwt-decode'

const Routes = () => {

    const token = localStorage.getItem("access_token");
    const curentTime = new Date();
    const hisotry = useHistory();
    const data = jwtDecode(token);

    if(
        !token || 
        data.exp * 1000 < curentTime.getTime()
    ) {
        localStorage.removeItem("access_token");
        localStorage.removeItem("projects");
        hisotry.push("/login")
    }

    useEffect(() => {
            fetch("http://localhost:8080/api/project/search/all", 
        {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
        .then(response => {
            response.json().then(data => {
                localStorage.setItem("projects", JSON.stringify(data.data));
            })}
        );
    }, []);



    return (
        <Switch>
            <Route path='/' exact component={Dashboard}/>
            <Route path='/bugs' component={Customers}/>
        </Switch>
    )
}

export default Routes
