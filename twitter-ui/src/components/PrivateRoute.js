import React from 'react';
import {Redirect, Route} from "react-router-dom";


let PrivateRoute = ({component: Component, authenticated, ...rest}) => (
    <Route
        {...rest}
        render={props =>
            authenticated ? (
                <Component {...rest} {...props} />
            ) : (
                <Redirect
                    to={{
                        pathname: '/login',
                        state: {from: props.location}
                    }}
                />
            )
        }
    />
);

export default PrivateRoute;