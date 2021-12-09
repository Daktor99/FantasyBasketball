import React, {Component} from "react";
import {Image} from "semantic-ui-react";
import {Redirect} from "react-router-dom";
import {withCookies} from "react-cookie";
import chef_curry from '../resources/images/chef_curry.png'
import lebron_drive from '../resources/images/lebron_drive.jpg'
import michael_jordan_crying from '../resources/images/michael_jordan_crying.jpg'
import steph_points from '../resources/images/steph_points.jpeg'
import what_steph_sees from '../resources/images/what_steph_sees.jpg'


class Memes extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loggedIn: this.props.cookies.get("loggedIn") || false,
            givenName: this.props.cookies.get("givenName") || null,
            familyName: this.props.cookies.get("familyName") || null,
            googleId: this.props.cookies.get("googleId") || null,
            email: this.props.cookies.get("email") || null,
            registered: this.props.cookies.get("registered") || null
        }
        this.state.loggedIn = this.state.loggedIn === "true";
        this.state.registered = this.state.registered === "true";
    }


    render() {
        if (this.state.loggedIn) {
            if (this.state.registered) {
                return (
                    <div className="home">
                        <div className='images'>
                            <Image src={michael_jordan_crying} size='medium' rounded/>
                            <Image src={what_steph_sees} size='medium' rounded/>
                            <Image src={lebron_drive} size='medium' rounded/>
                            <Image src={steph_points} size='medium' rounded/>
                            <Image src={chef_curry} size='medium' rounded/>
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

export default withCookies(Memes)