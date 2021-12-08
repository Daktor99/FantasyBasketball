import React, {Component} from 'react';
import full_logo from '../resources/images/full_logo.png'
import {Nav, Navbar, NavDropdown} from "react-bootstrap";
import Login from "./Login";
import Logout from "./Logout";
import { withCookies } from "react-cookie";


class AppNavbar extends Component {

    constructor(props) {
        super(props);
        this.state = {
            loggedIn: this.props.cookies.get("loggedIn") || false,
            givenName: this.props.cookies.get("givenName") || null,
            familyName: this.props.cookies.get("familyName") || null,
            googleId: this.props.cookies.get("googleId") || null,
            email: this.props.cookies.get("email") || null,
        };
        this.state.loggedIn = this.state.loggedIn === "true";
        this.login = this.login.bind(this)
        this.logout = this.logout.bind(this)
    }

    async login (success, body) {
        const { cookies } = this.props;
        if (success){
            cookies.set("loggedIn", true, { path: "/" })
            cookies.set("givenName", body.givenName, { path: "/" })
            cookies.set("familyName", body.familyName, { path: "/" })
            cookies.set("googleId", body.googleId, { path: "/" })
            cookies.set("email", body.email, { path: "/" })
            this.setState({
                loggedIn: true,
                givenName: body.givenName,
                familyName:body.familyName,
                googleId:body.googleId,
                email:body.email,
            })
        } else {
            await this.logout()
        }
    }

    async logout() {
        const { cookies } = this.props;
        cookies.set("loggedIn", false, { path: "/" })
        cookies.set("givenName", null, { path: "/" })
        cookies.set("familyName", null, { path: "/" })
        cookies.set("googleId", null, { path: "/" })
        cookies.set("email", null, { path: "/" })
        this.setState({
            loggedIn: false,
            givenName:null,
            familyName:null,
            googleId:null,
            email:null,
        })
        window.location.reload(false);
    }

    render() {
        return (
            <div className="row header">
                <Navbar collapseOnSelect expand="lg" bg="light" variant="light" style={{paddingLeft:12, paddingRight:2}}>
                    <Navbar.Brand href="/home">
                        <img
                            alt=""
                            src={full_logo}
                            className="d-inline-block align-top navbar-logo"
                        />{' '}
                    </Navbar.Brand>
                    {this.state.loggedIn ? <Navbar.Toggle aria-controls="responsive-navbar-nav" /> : <Login login = {this.login}/> }
                    {this.state.loggedIn ?
                    <Navbar.Collapse id="responsive-navbar-nav">
                        <Nav className="me-auto">
                            <Nav.Link href="/data">View Your Data</Nav.Link>
                            <Nav.Link href="/pricing">Pricing</Nav.Link>
                            <NavDropdown title="Dropdown" id="collasible-nav-dropdown">
                                <NavDropdown.Item href="#action/3.1">Action</NavDropdown.Item>
                                <NavDropdown.Item href="#action/3.2">Another action</NavDropdown.Item>
                                <NavDropdown.Item href="#action/3.3">Something</NavDropdown.Item>
                                <NavDropdown.Divider />
                                <NavDropdown.Item href="#action/3.4">Separated link</NavDropdown.Item>
                            </NavDropdown>
                        </Nav>
                        <Nav>
                            <Nav.Link href="#deets">More deets</Nav.Link>
                            <Nav.Link eventKey={2} href="#memes">
                                Dank memes
                            </Nav.Link>
                            <Logout logout = {this.logout}/>
                        </Nav>
                    </Navbar.Collapse>
                        : <></> }

                </Navbar>
            </div>
        )
    }
}

export default withCookies(AppNavbar);