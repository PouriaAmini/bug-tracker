import React, { useState } from 'react'
import { useHistory } from 'react-router-dom';

import './signup.css'

import logo from '../../assets/images/logo.png'
import axios from 'axios';

const Signup = () => {

    const [fields, setFields] = useState({});
    const [errors, setErrors] = useState({});

    const history = useHistory();

    if(localStorage.getItem("access_token")){
        history.push("/");
    }

    const loginHandler = () => {
        let path = `/login`; 
        history.push(path);
    }

    const handleValidation = () => {
        let fieldsObject = fields;
        let errorsObject = {};
        let isValid = true;

        if((!fieldsObject.firstName)) {
            isValid = false;
            errorsObject.firstName = "Cannot be empty!";
        } 
        else if (typeof fieldsObject.firstName !== "undefined") {
            if (
                !fieldsObject.firstName.match(/^[a-zA-Z]+$/)
            ) {
                isValid = false;
                errorsObject.firstName = "Only letters are allowed!";
            }
            else if(
                fieldsObject.firstName.length < 3
            ) {
                isValid = false;
                errorsObject.firstName = "Minimum length is 3!";
            }
        }

        if((!fieldsObject.lastName)) {
            isValid = false;
            errorsObject.lastName = "Cannot be empty!";
        } 
        else if (typeof fieldsObject.lastName !== "undefined") {
            if (
                !fieldsObject.lastName.match(/^[a-zA-Z]+$/)
            ) {
                isValid = false;
                errorsObject.lastName = "Only letters are allowed!";
            }
            else if(
                fieldsObject.lastName.length < 3
            ) {
                isValid = false;
                errorsObject.lastName = "Minimum length is 3!";
            }
        }

        if (!fieldsObject["email"]) {
            isValid = false;
            errorsObject["email"] = "Cannot be empty!";
        }
        else if (typeof fieldsObject["email"] !== "undefined") {
            if(
                !fieldsObject["email"]
                .toLowerCase()
                .match(
                    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
                )
            ) {
                isValid = false;
                errorsObject["email"] = "Email is not valid!";
            }
        }

        if (!fieldsObject["password"]) {
            isValid = false;
            errorsObject["password"] = "Cannot be empty!";
        }
        else if (typeof fieldsObject["password"] !== "undefined") {
            if(!fieldsObject["password"].match(
                /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/
                )
            ) {
                isValid = false;
                errorsObject["password"] = "Minimum eight characters, at least one uppercase letter, one lowercase letter and one number!";
            }
        }

        if (!fieldsObject["rePassword"]) {
            isValid = false;
            errorsObject["rePassword"] = "Cannot be empty!";
        }
        else if (typeof fieldsObject["password"] !== "undefined") {
            if(fieldsObject["rePassword"] !== fieldsObject["password"]
            ) {
                isValid = false;
                errorsObject["rePassword"] = "Passwords do not match!";
            }
        }

        if(!fieldsObject["userRole"]) {
            isValid = false;
            errorsObject["userRole"] = "Select an option!";
        }

        setErrors(errorsObject);
        return isValid;

    }

    const contactSubmit = (e) => {
        e.preventDefault();
    
        if (handleValidation()) {
            axios.post(`http://localhost:8080/api/user/register`, {
                "user": {
                    "firstName": fields.firstName,
                    "lastName": fields.lastName,
                    "email": fields.email,
                    "organization": fields.organization,
                    "position": fields.userRole === "employee" ? "USER" : "MANAGER"
                },
                "userAccount": {
                    "password": fields.password
                }
            }).then(respose => {
                if(respose.data.statusCode == 400) {
                    alert("User with this email address already exists!")
                }
                else {
                    loginHandler()
                }
            })
            
        }
    }

    const handleChange = (field, e) => {
        const { value } = e.target;
        setFields({
            ...fields,
            [field]: value
        })
    }

    return (
        <>
            <div className="background">
                <div className="shape"></div>
                <div className="shape"></div>
            </div>
            <form 
                className='signup__form' 
                onSubmit={contactSubmit}
                style={
                    errors.email ||
                    errors.password ||
                    errors.rePassword ||
                    errors.firstName ||
                    errors.lastName
                    ? { height: "1060px"} : { height: "950px"}
                }
                >
                <div className="login-logo">
                    <img src={
                            errors.email ||
                            errors.password ||
                            errors.rePassword ||
                            errors.firstName ||
                            errors.lastName ?
                            null : logo
                        } 
                        alt="company logo" />
                </div>
                <h3>Sign Up</h3>
                <div className='user-initials'>
                    <label htmlFor="firstname" style={{ color: "red" }}>{errors.firstName ? errors.firstName : errors.lastName}</label>
                    <input 
                        type="text" 
                        placeholder="First Name" 
                        id="firstname" 
                        onChange={(e) => handleChange("firstName", e)}
                        value={fields.firstName || ''}
                        style={
                            errors.firstName ? { borderColor: "red"} : { borderColor: "#ffffff1a"} 
                        }
                    />
                    <input 
                        type="text" 
                        placeholder="Last Name" 
                        id="lastname" 
                        onChange={(e) => handleChange("lastName", e)}
                        value={fields.lastName || ''}
                        style={
                            errors.lastName ? { borderColor: "red"} : { border: "#ffffff1a"} 
                        }
                    />
                </div>
                <div className="user-email">
                    <label htmlFor="username" style={{ color: "red" }}>{errors.email}</label>
                    <input 
                        type="text" 
                        placeholder="Email" 
                        id="username"
                        onChange={(e) => handleChange("email", e)}
                        value={fields.email || ''}
                        style={
                            errors.email ? { borderColor: "red"} : { border: "#ffffff1a"} 
                        }
                    />
                </div>
                <div className="userpasword">
                    <label htmlFor="password" style={{ color: "red" }}>{errors.password ? errors.password : errors.rePassword}</label>
                    <input 
                        type="password" 
                        placeholder="New Password" 
                        id="password"
                        onChange={(e) => handleChange("password", e)}
                        value={fields.password || ''}
                        style={
                            errors.password ? { borderColor: "red"} : { border: "#ffffff1a"} 
                        }
                    />
                    <input 
                        type="password" 
                        placeholder="Re-enter New Password" 
                        id="re-password"
                        onChange={(e) => handleChange("rePassword", e)}
                        value={fields.rePassword || ''}
                        style={
                            errors.rePassword ? { borderColor: "red"} : { border: "#ffffff1a"} 
                        }
                    />
                </div>
                <div className="organization__">
                    <input type="text" placeholder="Organization (Optional)" id="organization" />
                </div>
                <div className="userrole">
                    <label htmlFor="role" style={{ color: "red" }}>{errors.userRole}</label>
                    <select 
                        className='role'
                        onChange={(e) => handleChange("userRole", e)}
                        style={
                            errors.userRole ? { borderColor: "red"} : { border: "#ffffff1a"} 
                        }
                        >
                        <option value="">Select your role</option>
                        <option value="employee">Employee</option>
                        <option value="manager">Manager</option>
                    </select>
                </div>
                <button className='login-button'>Sign Up</button>
                <button className='signup-button' onClick={loginHandler}>Back to Login</button>
            </form>
        </>
    )
}

export default Signup
