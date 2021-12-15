import {Component} from "react";
import {withCookies} from "react-cookie";
import {Header} from "semantic-ui-react";
import {Redirect} from "react-router-dom";

class Welcome extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loggedIn: this.props.cookies.get('loggedIn') || false,
        }
        this.state.loggedIn = this.state.loggedIn === "true";
    }

    render() {
        if (this.state.loggedIn) {
            return <Redirect to='/home'/>
        } else {
            return (
                <div>
                    <Header as={'h1'}>
                        Welcome to Bebeco League, Login with google
                    </Header>
                </div>
            )
        }
    }
}

export default withCookies(Welcome)