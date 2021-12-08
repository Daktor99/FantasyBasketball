import React, {Component} from 'react';
import '../App.css';
import {Container, Header, Image} from "semantic-ui-react";
import logo_icon from '../resources/images/logo_only.png';
import {withCookies} from "react-cookie";
import {Redirect} from "react-router-dom";

class Welcome extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loggedIn: this.props.cookies.get("loggedIn") || false
        }
        this.state.loggedIn = this.state.loggedIn === "true";
    }

    async componentDidMount() {

    }

    render() {
        if (this.state.loggedIn) {
            return <Redirect to='/home'/>
        }
        else {
            return (
                <div className="home" style={{textAlign: 'center'}}>
                    <Container text>
                        <div className="top-text">
                            <Image
                                style={{width: 100, height: 100, marginTop: 190, marginRight: 10}}
                                centered
                                src={logo_icon}
                            />
                            <Header
                                as='h1'
                                content='STIPÉ'
                                inverted
                                style={{
                                    fontWeight: 'bold',
                                    fontStyle: 'italic',
                                    fontSize: '4em',
                                    marginBottom: 0,
                                    marginTop: 200,
                                    color: '#FD904DFF'
                                }}
                            />
                        </div>
                        <Header
                            as='h2'
                            content='Connécting Baskétball'
                            inverted
                            style={{
                                fontSize: '1.7em',
                                fontWeight: 'normal',
                                fontStyle: 'italic',
                                color: '#ffffff'
                            }}
                        />
                    </Container>
                </div>
            )
        }
    }
}

export default withCookies(Welcome);