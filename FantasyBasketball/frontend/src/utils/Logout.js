import React, {Component} from 'react';

import {GoogleLogout} from 'react-google-login';
import {withCookies} from "react-cookie";

// TODO: CHANGE THIS TO GO TO THE ENVIRONMENT FOLDER INSTEAD
const clientId = "1096047835269-skq8123tknv8u0dcdb48f0qca5t966d3.apps.googleusercontent.com";

class Logout extends Component {

    onSuccess = (res) => {
        this.props.logout()
        console.log("User logged out successfully")
    }

    render() {
        return (
            <div className="login-button">
                <GoogleLogout
                    clientId={clientId}
                    buttonText="Logout"
                    onLogoutSuccess={this.onSuccess}
                    cookiePolicy={'single_host_origin'}
                />
            </div>
        );
    }

}

export default withCookies(Logout);