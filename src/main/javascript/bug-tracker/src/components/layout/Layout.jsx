import React, {useEffect} from 'react'

import './layout.css'

import Sidebar from '../sidebar/Sidebar'
import TopNav from '../topnav/TopNav'
import Routes from '../Routes'
import Login from '../login/Login'
import Signup from '../signup/Signup'

import { BrowserRouter, Route, Switch } from 'react-router-dom'

import { useSelector, useDispatch } from 'react-redux'

import ThemeAction from '../../redux/actions/ThemeAction'

const Layout = () => {

    const themeReducer = useSelector(state => state.ThemeReducer)

    const dispatch = useDispatch()

    useEffect(() => {
        const themeClass = localStorage.getItem('themeMode', 'theme-mode-light')

        const colorClass = localStorage.getItem('colorMode', 'theme-mode-light')

        dispatch(ThemeAction.setMode(themeClass))

        dispatch(ThemeAction.setColor(colorClass))
    }, [dispatch])

    return (
        <BrowserRouter>
            <Switch>
                <Route path="/login" render={() => (
                    <div>
                        <Login />
                    </div>
                )}/>
                <Route path="/signup" render={() => (
                    <div>
                        <Signup />
                    </div>
                )}/>
                <Route path="/register"/>~
                <Route path="/" render={(props) => (
                    <div className={`layout ${themeReducer.mode} ${themeReducer.color}`}>
                        <Sidebar {...props}/>
                        <div className="layout__content">
                            <TopNav/>
                            <div className="layout__content-main">
                                <Routes/>
                            </div>
                        </div>
                    </div>
                )}/>
            </Switch>
        </BrowserRouter>
    )
}

export default Layout
