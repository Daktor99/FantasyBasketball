import {Component} from "react";
import {CLIENT_GOOGLE_ID} from "../Constants";
import {Dimmer, Loader} from "semantic-ui-react";
import {withCookies} from "react-cookie";

class TeamInfo extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loggedIn: this.props.cookies.get('loggedIn') || false,
            givenName: this.props.cookies.get("givenName") || null,
            familyName: this.props.cookies.get("familyName") || null,
            googleId: this.props.cookies.get("googleId") || null,
            email: this.props.cookies.get("email") || null,
            user_id: this.props.cookies.get("user_id") || null,
            league_id: this.props.cookies.get("league_id") || null,
            team_id: this.props.cookies.get("team_id") || null,
            team: [],
            isLoading: false
        }
        this.state.loggedIn = this.state.loggedIn === "true";
    }

    async componentWillMount() {
        if (this.state.loggedIn) {
            if (this.state.team_id !== 'null' && this.state.league_id !== 'null') {
                this.setState({
                    isLoading: true
                })
                const input = '/fantasyTeams?team_id=' + this.state.team_id

                await fetch(input, {
                    headers: {
                        Accept: 'application/json',
                        'token': CLIENT_GOOGLE_ID
                    }
                })
                    .then(resp => resp.json())
                    .then(data => {
                        this.setState({
                            team: data
                        })
                        console.log("Success fetching team data for team_id " + this.state.team_id)
                    })
                    .catch(error => {
                        console.log("Error fetching team data for team_id " + this.state.team_id + ": " + error)
                        window.alert("Error fetching team data, reload page")
                    })
                this.setState({
                    isLoading: false
                })
            }
        }
    }


    render() {
        return (
            <div>
                <Dimmer active={this.state.isLoading}>
                    <Loader size='big'/>
                </Dimmer>
            </div>
        );
    }
}

export default withCookies(TeamInfo)