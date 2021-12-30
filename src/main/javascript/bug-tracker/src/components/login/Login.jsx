import React, { useState } from 'react'
import { useHistory } from "react-router-dom";
import './login.css'
import logo from '../../assets/images/logo.png'
import { LoginAction } from '../../services/LoginAction';


const Login = () => {

    const [fields, setFields] = useState({});
    const [errors, setErrors] = useState({});

    const history = useHistory();

    const signupHandler = () => {
        let path = `/signup`;
        history.push(path);
    }

    const handleValidation = () => {
        let fieldsObject = fields;
        let errorsObject = {};
        let isValid = true;

        if(!fieldsObject.email) {
            isValid = false;
            errorsObject.email = "Cannot be empty!";
        }
        else if (typeof fieldsObject.email !== "undefined") {
            if(
                !fieldsObject.email
                .toLowerCase()
                .match(
                    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
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
                errorsObject["password"] = "Password is not valid!";
            }
        }

        setErrors(errorsObject);
        return isValid;
    }

    const contactSubmit = (e) => {

        e.preventDefault();

        const email = fields.email;
        const password = fields.password;
    
        if (handleValidation()) {
            LoginAction(email, password).then();
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
                <div className="shape"/>
                <div className="shape"/>
            </div>
            <form
                className='login__form'
                onSubmit={contactSubmit}
                style={
                    errors.email || errors.password ? { height: "650px"} : { height: "620px"}
                }
            >
                <div className="login-logo">
                    <img src={logo} alt="company logo" />
                </div>
                <h3>Login</h3>
                <label htmlFor="username" style={{ color: "red" }}>{errors.email}</label>
                <input
                    type="text"
                    placeholder="Email"
                    id="username"
                    onChange={(e) => handleChange("email", e)}
                    value={fields.email || ''}
                    style={
                        errors.email ? { borderColor: "red"} : { borderColor: "#ffffff1a"}
                    }
                />
                <label htmlFor="password" style={{ color: "red" }}>{errors.password}</label>
                <input
                    type="password"
                    placeholder="Password"
                    id="password"
                    onChange={(e) => handleChange("password", e)}
                    value={fields.password || ''}
                    style={
                        errors.password ? { borderColor: "red"} : { borderColor: "#ffffff1a"}
                    }
                />
                <button className='login-button'>Log In</button>
                <button className='signup-button' onClick={signupHandler}>Sign Up</button>
            </form>
        </>
    )
}

export default Login
