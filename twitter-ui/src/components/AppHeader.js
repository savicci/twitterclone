import React, {Component} from "react";
import {Button, Nav, Navbar} from "react-bootstrap";

export default class AppHeader extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Navbar bg="dark" variant="dark" expand="lg">
                <Navbar.Brand href="#home">TwitterClone</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav"/>
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="mr-auto">
                        <Nav.Link href="/">Home</Nav.Link>
                    </Nav>
                    <Nav style={{marginRight: "16px", color: "white"}}>
                        <Nav.Item>{this.props.currentUser.nick}</Nav.Item>
                    </Nav>
                    <Button variant="outline-success" onClick={this.props.onLogout}>Logout</Button>
                </Navbar.Collapse>
            </Navbar>
        )
    }
}