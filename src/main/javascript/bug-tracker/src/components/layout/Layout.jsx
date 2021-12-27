import React from 'react'

import './layout.css'

import Login from '../login/Login'
import Signup from '../signup/Signup'

import { BrowserRouter, Switch } from 'react-router-dom'

import Main from '../../pages/Main'
import PrivateRoute from './PrivateRoute'
import PublicRoute from './PublicRoute'


const Layout = () => {

    return (
        <BrowserRouter>
            <Switch>
                <PublicRoute path="/login" component={Login}/>
                <PublicRoute path="/signup" component={Signup}/>
                <PrivateRoute path="/" component={Main}/>
            </Switch>
        </BrowserRouter>
    )
}

export default Layout
