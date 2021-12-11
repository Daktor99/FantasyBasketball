import React, {Component} from "react";
import {Statistic} from "semantic-ui-react";
import {Redirect} from "react-router-dom";
import {withCookies} from "react-cookie";

class Data extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loggedIn: this.props.cookies.get("loggedIn") || false,
            givenName: this.props.cookies.get("givenName") || null,
            familyName: this.props.cookies.get("familyName") || null,
            googleId: this.props.cookies.get("googleId") || null,
            email: this.props.cookies.get("email") || null,
            registered: this.props.cookies.get("registered") || null,
            userCount: 0
        }
        this.state.loggedIn = this.state.loggedIn === "true";
        this.state.registered = this.state.registered === "true";
    }

    async componentWillMount() {
        const input = '/users'
        await fetch(input, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'token': this.state.googleId
            },
        })
            .then(async response => {
                    const body = await response.json();
                    this.setState({
                        userCount: body.length
                    })
                }
            )
    }

    render() {
        if (this.state.loggedIn) {
            if (this.state.registered) {
                return (
                    <div className="home">
                        <div className='images'>
                            <Statistic>
                                <Statistic.Value>{this.state.userCount}</Statistic.Value>
                                <Statistic.Label>Users</Statistic.Label>
                            </Statistic>
                        </div>
                    </div>
                )
            } else {
                return (
                    <Redirect to='/register'/>
                )
            }
        } else {
            return <Redirect to='/'/>
        }
    }

}

export default withCookies(Data)