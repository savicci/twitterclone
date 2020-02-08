import React, {Component} from "react";
import {Button, Form, Jumbotron} from "react-bootstrap";
import styled from "styled-components";
import {login} from "../utils/APIUtils";
import {ACCESS_TOKEN} from "../constants";

const LoginWrapper = styled.div`
{
    display: flex;
    flex-direction: column;
    padding: 16px;
}
`;


export default class Login extends Component {
    constructor(props) {
        super(props);

        this.state = {
            username: "",
            password: ""
        }
    }

    render() {
        const textRef = React.createRef();
        const passRef = React.createRef();

        return (
            <LoginWrapper>
                <Jumbotron>
                    <Form onSubmit={(event) => {
                        event.preventDefault();

                        const loginRequest = Object.assign({}, this.state);
                        login(loginRequest)
                            .then(response => {
                                localStorage.setItem(ACCESS_TOKEN, response.accessToken);
                                this.props.onLogin();
                            })
                    }}>
                        <h1>Twitter clone</h1>
                        <Form.Group controlId="formBasicUsername">
                            <Form.Label>Username</Form.Label>
                            <Form.Control ref={textRef} type="text" placeholder="Enter username"
                                          aria-describedby="basic-addon3" onChange={() => this.setState(prevState => {
                                return {
                                    ...prevState,
                                    username: textRef.current.value,
                                }
                            })}/>
                        </Form.Group>

                        <Form.Group controlId="formBasicPassword">
                            <Form.Label>Password</Form.Label>
                            <Form.Control ref={passRef} type="password" placeholder="Password"
                                          aria-describedby="basic-addon3" onChange={() => this.setState(prevState => {
                                return {
                                    ...prevState,
                                    password: passRef.current.value,
                                }
                            })}
                            />
                        </Form.Group>

                        <Button variant="primary" type="submit">
                            Log in
                        </Button>
                    </Form>

                </Jumbotron>
            </LoginWrapper>
        )
    }
}