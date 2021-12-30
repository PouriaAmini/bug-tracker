import React from 'react'
import { ValidateUser } from '../../services/ValidateUser';

import { Route, Redirect } from 'react-router-dom';

const PublicRoute = ({ component: Component, ...rest }) => {

    const isLoggedIn = ValidateUser();
  
    return (
      <Route
        {...rest}
        render={(props) =>
          !isLoggedIn ? (
            <Component {...props}/>
          ) : (
            <Redirect to="/" />
          )
        }
      />
    )
  }

export default PublicRoute
