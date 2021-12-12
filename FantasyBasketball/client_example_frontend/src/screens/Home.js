import {Component} from "react";
import {withCookies} from "react-cookie";
import {Redirect} from "react-router-dom";
import {CLIENT_GOOGLE_ID} from "../Constants";
import {Dimmer, Loader} from "semantic-ui-react";

class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loggedIn: this.props.cookies.get('loggedIn') || false,
            givenName: this.props.cookies.get("givenName") || null,
            familyName: this.props.cookies.get("familyName") || null,
            googleId: this.props.cookies.get("googleId") || null,
            email: this.props.cookies.get("email") || null,
            user_id: this.props.cookies.get("user_id") || null,
            isLoading: false
        }
        this.state.loggedIn = this.state.loggedIn === "true";
    }

    async componentWillMount() {
        this.props.cookies.set("league_id", null)
        if (this.state.loggedIn) {
            await new Promise(r => setTimeout(r, 500));
            this.setState({
                isLoading: true
            })
            const input = '/users?email=' + this.state.email
            let userList = []
            const {cookies} = this.props

            await fetch(input, {
                headers: {
                    Accept: 'application/json',
                    'token': CLIENT_GOOGLE_ID
                }
            })
                .then(resp => resp.json())
                .then(data => {
                    userList = data
                })
                .catch(error => {
                    console.log("Error Fetching User: " + error)
                    window.alert("Error Fetching account, reload page")
                })

            if (userList.length !== 1) {
                const requestOptions = {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        token: CLIENT_GOOGLE_ID
                    },
                    body: JSON.stringify({
                        email: this.state.email,
                        first_name: this.state.givenName,
                        last_name: this.state.familyName,
                        username: this.state.email
                    })
                };
                fetch('/users', requestOptions)
                    .then(response => {
                        return response.json()
                    })
                    .then(data => {
                        console.log("Successfully Created User: ")
                        this.setState({
                            user_id: data[0].user_id
                        })
                        cookies.set('user_id', this.state.user_id)
                    })
                    .catch(error => {
                        console.log("Error Creating User: " + error)
                        window.alert("Error Creating account, reload page")
                    })
            } else {
                this.setState({
                    user_id: userList[0].user_id
                })
                cookies.set('user_id', this.state.user_id)
                console.log("Successfully fetched User")
            }
            this.setState({
                isLoading: false
            })
        } else {
            console.log("Tried to reach home page without logging in")
        }
    }


    render() {
        if (this.state.loggedIn) {
            return (
                <div>
                    <Dimmer active={this.state.isLoading}>
                        <Loader size='big'/>
                    </Dimmer>
                </div>
            )
        } else {
            return <Redirect to='/'/>
        }
    }
}

export default withCookies(Home)