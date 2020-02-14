import React, {Component} from 'react';
import {Route, Switch, withRouter} from 'react-router-dom';

import {getCurrentUser} from '../src/utils/APIUtils';
import {ACCESS_TOKEN} from '../src/constants';


import AppHeader from "./components/AppHeader";
import MainPage from "./components/MainPage";
import Login from "./components/Login";
import PrivateRoute from "./components/PrivateRoute";
import AddTweet from "./components/AddTweet";

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            currentUser: null,
            isAuthenticated: false,
            isLoading: false
        };
        this.handleLogout = this.handleLogout.bind(this);
        this.loadCurrentUser = this.loadCurrentUser.bind(this);
        this.handleLogin = this.handleLogin.bind(this);
    }

    loadCurrentUser() {
        this.setState({
            isLoading: true
        });
        getCurrentUser()
            .then(response => {
                this.setState({
                    currentUser: response,
                    isAuthenticated: true,
                    isLoading: false
                });
                this.props.history.push("/");
            }).catch(error => {
            this.setState({
                isLoading: false
            });
        });
    }

    componentDidMount() {
        this.loadCurrentUser();
    }

    // Handle Logout, Set currentUser and isAuthenticated state which will be passed to other components
    handleLogout() {
        localStorage.removeItem(ACCESS_TOKEN);

        this.setState({
            currentUser: null,
            isAuthenticated: false
        });
        this.props.history.push("/login");

    }

    /*
     This method is called by the Login component after successful login
     so that we can load the logged-in user details and set the currentUser &
     isAuthenticated state, which other components will use to render their JSX
    */
    handleLogin() {
        this.loadCurrentUser();
        this.props.history.push("/");
    }

    render() {
        return (
            <div>
                {this.state.currentUser && <AppHeader isAuthenticated={this.state.isAuthenticated}
                                                      currentUser={this.state.currentUser}
                                                      onLogout={this.handleLogout}/>}
                {this.state.currentUser && <AddTweet user={this.state.currentUser}/>}

                <Switch>
                    <PrivateRoute authenticated={this.state.isAuthenticated} exact path="/" component={MainPage}
                                  user={this.state.currentUser}
                                  handleLogout={this.handleLogout}/>
                    <Route path="/login"
                           render={(props) => <Login onLogin={this.handleLogin} {...props} />}/>
                </Switch>
            </div>
        );
    }
}

export default withRouter(App);