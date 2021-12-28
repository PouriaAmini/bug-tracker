import React, { useEffect, useRef, useState } from "react";

import './topnav.css'

import { Link } from 'react-router-dom'

import Dropdown from '../dropdown/Dropdown'

import ThemeMenu from '../thememenu/ThemeMenu'

import notifications from '../../assets/JsonData/notification.json'

import user_image from '../../assets/images/user.png'

import user_menu from '../../assets/JsonData/user_menus.json'
import { SearchAction } from "../../services/SearchAction";
import Badge from "../badge/Badge";


const renderNotificationItem = (item, index) => (
    <div className="notification-item" key={index}>
        <i className={item.icon}/>
        <span>{item.content}</span>
    </div>
)

const renderSearchItem = (item, index) => (
    <div className="search-item" key={index}>
        <i className={
            item.firstName ? "bx bx-user" :
                item.groups ? "bx bx-folder-open" :
                    item.bugs ? "bx bx-group" : ''
        }/>
        <span className="name__search-item">
            {
                item.firstName ? `${item.firstName} ${item.lastName}`: item.name
            }
        </span>
        <span className="data__search-item">
            {
                item.firstName ? item.email : `${item.briefDescription.substring(0,50)}...`
            }
        </span>
        <span className="priority__search-item">
            {
                item.name ?
                    <Badge
                        type={item.priorityAverage}
                        content={`${item.priorityAverage} priority`}
                    /> : ''
            }
        </span>
    </div> 
)

const renderUserToggle = (user) => (
    <div className="topnav__right-user">
        <div className="topnav__right-user__image">
            <img src={user.image} alt="" />
        </div>
        <div className="topnav__right-user__name">
            {user.display_name}
        </div>
    </div>
)

const renderSearchToggle = (onFetch) => {
    return (
        <div className="topnav__search-toggle">
            <input
                type="text"
                placeholder='Search here...'
                onChange={(e) => onFetch(e.target.value)}
            />
            <i className="bx bx-search"/>
        </div>
    )
}

const renderUserMenu = (item, index) => {
    return (
        <Link to={item.route} key={index}>
            <div className="notification-item" onClick={ () => {
                    if(item.content === "Logout") {
                        localStorage.removeItem("access_token");
                        localStorage.removeItem("refresh_token");
                        localStorage.removeItem("projects");
                        localStorage.removeItem("user");
                    }
                }}>
                <i className={item.icon}/>
                <span>{item.content}</span>
            </div>
        </Link>
        )
    }

const TopNav = () => {

    let url = '';
    const [search, setSearch] = useState([]);

    const token = localStorage.getItem("access_token");
    const user = JSON.parse(localStorage.getItem("user"))["Users"][0];
    const curr_user = {
        display_name: `${user.firstName} ${user.lastName}`,
        image: user_image
    }

    const onFetch = (newUrl) => {
        if(newUrl.trim()){
            url = newUrl;
            SearchAction(url, token, [])
                .then(
                    response => setSearch(response)
                );
        }
        else if(!newUrl.trim()) {
            setSearch([]);
        }
    }

    return (
        <div className='topnav'>
            <div className="topnav__search">
                <div className="topnav__search-item">
                    <Dropdown
                        customToggle={() => renderSearchToggle(onFetch)}
                        contentData={search}
                        renderItems={(item, index) => renderSearchItem(item, index)}
                    />
                </div>
            </div>
            <div className="topnav__right">
                <div className="topnav__right-item">
                    <Dropdown
                        customToggle={() => renderUserToggle(curr_user)}
                        contentData={user_menu}
                        renderItems={(item, index) => renderUserMenu(item, index)}
                    />
                </div>
                <div className="topnav__right-item">
                    <Dropdown
                        icon='bx bx-bell'
                        badge='12'
                        contentData={notifications}
                        renderItems={(item, index) => renderNotificationItem(item, index)}
                        renderFooter={() => <Link to='/'>View All</Link>}
                    />
                </div>
                <div className="topnav__right-item">
                    <ThemeMenu/>
                </div>
            </div>
        </div>
    )
}

export default TopNav
