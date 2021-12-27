import React from 'react'
import { ValidateUser } from '../../services/ValidateUser'

import { Route, Redirect } from 'react-router-dom';

const PrivateRoute = ({ component: Component, ...rest }) => {

    const isLoggedIn = ValidateUser();
  
    return (
      <Route
        {...rest}
        render={props =>
          isLoggedIn ? (
            <Component {...props}/>
          ) : (
            <Redirect to="/login" />
          )
        }
      />
    )
  }

export default PrivateRoute
