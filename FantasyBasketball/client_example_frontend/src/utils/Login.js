import React, {Component} from 'react';

import {GoogleLogin} from 'react-google-login';
import {GOOGLE_CLOUD_CLIENT_ID} from "../Constants";

class Login extends Component {

    onSuccess = (res) => {
        console.log('[Login Success] currentUser:', res.profileObj)
        this.props.login(true, res.profileObj)
        window.location.reload();
    };

    onFailure = (res) => {
        console.log('[Login failed] currentUser:', res)
        this.props.login(false, null)
        window.location.reload();
    };

    render() {
        return (
            <div className="login-button">
                <GoogleLogin
                    clientId={GOOGLE_CLOUD_CLIENT_ID}
                    buttonText="Log in with Google"
                    onSuccess={this.onSuccess}
                    onFailure={this.onFailure}
                    cookiePolicy={'single_host_origin'}
                />
            </div>
        );
    }

}

export default Login;