import React, {Component} from 'react';

import {GoogleLogout} from 'react-google-login';
import {withCookies} from "react-cookie";
import {GOOGLE_CLOUD_CLIENT_ID} from "../Constants";

class Logout extends Component {

    onSuccess = (res) => {
        console.log("User logged out successfully " + res)
        this.props.logout()
    }

    render() {
        return (
            <div className="login-button">
                <GoogleLogout
                    clientId={GOOGLE_CLOUD_CLIENT_ID}
                    buttonText="Logout"
                    onLogoutSuccess={this.onSuccess}
                    cookiePolicy={'single_host_origin'}
                />
            </div>
        );
    }

}

export default withCookies(Logout);