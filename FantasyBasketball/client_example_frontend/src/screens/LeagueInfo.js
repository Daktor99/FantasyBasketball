import {withCookies} from "react-cookie";
import {Button, Dimmer, Icon, Loader, Table} from "semantic-ui-react";
import TeamTable from "../tables/TeamTable";
import {CLIENT_GOOGLE_ID} from "../Constants";
import {Redirect} from "react-router-dom";

const {Component} = require("react");

class LeagueInfo extends Component {
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
            teams: [],
            isLoading: false
        }
        this.state.loggedIn = this.state.loggedIn === "true";
        this.redirectToInfo = this.redirectToInfo.bind(this)
        this.redirectToYourTeam = this.redirectToYourTeam.bind(this)
    }

    async componentWillMount() {
        if (this.state.loggedIn) {
            if (this.state.league_id !== 'null') {
                this.setState({
                    isLoading: true
                })
                const input = '/fantasyTeams?league_id=' + this.state.league_id

                await fetch(input, {
                    headers: {
                        Accept: 'application/json',
                        'token': CLIENT_GOOGLE_ID
                    }
                })
                    .then(resp => resp.json())
                    .then(data => {
                        data.sort((a, b) => (a.team_wins < b.team_wins) ? 1 : -1)
                        this.setState({
                            teams: data
                        })
                        console.log("Success fetching teams data for league_id " + this.state.league_id)
                    })
                    .catch(error => {
                        console.log("Error fetching teams data for league_id " + this.state.league_id + ": " + error)
                        window.alert("Error fetching teams data, reload page")
                    })
                this.setState({
                    isLoading: false
                })
            }
        }
    }

    redirectToInfo(team_id) {
        console.log("Redirecting to team info with team_id: " + team_id)
        // Maybe add {path: "/"}
        this.props.cookies.set("team_id", team_id);
        this.props.history.push('/team_info')
    }

    redirectToYourTeam(user_id) {
        const team_id = this.state.teams.filter(team => team.owner_id === parseInt(user_id))[0].team_id;

        console.log("Redirecting to team info with team_id: " + team_id)
        // Maybe add {path: "/"}
        this.props.cookies.set("team_id", team_id);
        this.props.history.push('/team_info')
    }

    render() {
        if (this.state.loggedIn) {
            if (this.state.league_id !== 'null') {
                return (
                    <div>
                        <Dimmer active={this.state.isLoading}>
                            <Loader size='big'/>
                        </Dimmer>
                        <Table celled>
                            <Table.Header>
                                <Table.Row>
                                    <Table.HeaderCell>Team Name</Table.HeaderCell>
                                    <Table.HeaderCell>Team Wins</Table.HeaderCell>
                                    <Table.HeaderCell>Team Losses</Table.HeaderCell>
                                    <Table.HeaderCell>Points Scored</Table.HeaderCell>
                                    <Table.HeaderCell>Points Against</Table.HeaderCell>
                                    <Table.HeaderCell>More Info</Table.HeaderCell>
                                </Table.Row>
                            </Table.Header>

                            <Table.Body>
                                {this.state.teams.map(team =>
                                    <TeamTable
                                        team={team}
                                        redirect={this.redirectToInfo}
                                    />)}
                            </Table.Body>

                            <Table.Footer fullWidth>
                                <Table.Row>
                                    <Table.HeaderCell/>
                                    <Table.HeaderCell colSpan='6'>
                                        {this.state.teams.some(team => team.owner_id === parseInt(this.state.user_id)) ?
                                            <Button
                                                floated='right'
                                                icon
                                                labelPosition='left'
                                                primary
                                                size='small'
                                                onClick={() => {
                                                    this.redirectToYourTeam(this.state.user_id)
                                                }}
                                            >
                                                <Icon name='user'/> Look at your team
                                            </Button>
                                            :
                                            <Button
                                                floated='right'
                                                icon
                                                labelPosition='left'
                                                primary
                                                size='small'
                                            >
                                                <Icon name='user'/> Add a Team
                                            </Button>
                                        }
                                    </Table.HeaderCell>
                                </Table.Row>
                            </Table.Footer>

                        </Table>
                    </div>
                );
            } else {
                return <Redirect to='/leagues'/>
            }
        } else {
            return <Redirect to='/'/>
        }
    }
}

export default withCookies(LeagueInfo)