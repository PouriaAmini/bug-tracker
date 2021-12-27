import React, {useEffect} from 'react'

import { useSelector, useDispatch } from 'react-redux'

import Sidebar from '../components/sidebar/Sidebar'
import TopNav from '../components/topnav/TopNav'
import Routes from '../components/Routes'

import ThemeAction from '../redux/actions/ThemeAction'

const Main = (props) => {

    const themeReducer = useSelector(state => state.ThemeReducer);

    const dispatch = useDispatch();

    useEffect(() => {
        const themeClass = localStorage.getItem('themeMode', 'theme-mode-light')

        const colorClass = localStorage.getItem('colorMode', 'theme-mode-light')

        dispatch(ThemeAction.setMode(themeClass))

        dispatch(ThemeAction.setColor(colorClass))
    }, [dispatch])
    
    return (
        <div className={`layout ${themeReducer.mode} ${themeReducer.color}`}>
            <Sidebar {...props}/>
            <div className="layout__content">
                <TopNav />
                <div className="layout__content-main">
                    <Routes />
                </div>
            </div>
        </div>
    )
}

export default Main
