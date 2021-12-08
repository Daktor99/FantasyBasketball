import React, {Component} from 'react';

import {GoogleLogin} from 'react-google-login';

// TODO: CHANGE THIS TO GO TO THE ENVIRONMENT FOLDER INSTEAD
const clientId = "1096047835269-skq8123tknv8u0dcdb48f0qca5t966d3.apps.googleusercontent.com";

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
                    clientId={clientId}
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