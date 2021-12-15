import {Component} from "react";
import {withCookies} from "react-cookie";
import {Redirect} from "react-router-dom";
import {CLIENT_GOOGLE_ID} from "../Constants";
import {Dimmer, Divider, Header, Loader, Table} from "semantic-ui-react";
import GameTable from "../tables/GameTable";
import TeamTable from "../tables/TeamTable";

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
            isLoading: false,
            games: [],
            teams: [],
            hasGames: false,
            hasTeams: false
        }
        this.state.loggedIn = this.state.loggedIn === "true";
        this.redirectToInfo = this.redirectToInfo.bind(this)
    }

    async componentWillMount() {
        this.setState({
            isLoading: true
        })
        this.props.cookies.set("league_id", null)
        if (this.state.loggedIn) {
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
                await fetch('/users', requestOptions)
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

            const input2 = '/fantasyTeams?owner_id=' + this.state.user_id
            //const {cookies} = this.props

            await fetch(input2, {
                headers: {
                    Accept: 'application/json',
                    'token': CLIENT_GOOGLE_ID
                }
            })
                .then(resp => resp.json())
                .then(data => {
                    this.setState({
                        teams: data
                    })
                })
                .catch(error => {
                    console.log("Error Fetching User games: " + error)
                    window.alert("Error Fetching User Games, reload page")
                })

            if (this.state.teams.length > 0) {
                this.setState(
                    {hasTeams: true});
                console.log(this.state.teams)
            } else {
                console.log("No teams found")
            }

            for (const team of this.state.teams) {
                await this.callGamesByHomeId(team)
                await this.callGamesByAwayId(team)
            }

            this.setState({
                games: this.state.games.sort(((a, b) => new Date(a.game_start_date) - new Date(b.game_start_date)))
            })

            if (this.state.games.length > 0) {
                this.setState({
                    hasGames: true
                })
            } else {
                this.setState({
                    hasGames: false
                })
            }
        } else {
            console.log("Tried to reach home page without logging in")
        }
        this.setState({
            isLoading: false
        })
    }

    async callGamesByAwayId(team) {
        const input1 = '/fantasyGames?away_team_id=' + team.team_id

        await fetch(input1, {
            headers: {
                Accept: 'application/json',
                'token': CLIENT_GOOGLE_ID
            }
        })
            .then(resp => resp.json())
            .then(data => {
                for (const game of data) {
                    game.team_name = team.team_name
                }
                this.setState({
                    games: this.state.games.concat(data),
                })
            })
            .catch(error => {
                console.log("Error Fetching User games: " + error)
                window.alert("Error Fetching User Games, reload page")
            })
    }

    async callGamesByHomeId(team) {
        const input1 = '/fantasyGames?home_team_id=' + team.team_id

        await fetch(input1, {
            headers: {
                Accept: 'application/json',
                'token': CLIENT_GOOGLE_ID
            }
        })
            .then(resp => resp.json())
            .then(data => {
                for (const game of data) {
                    game.team_name = team.team_name
                }
                this.setState({
                    games: this.state.games.concat(data),
                })
            })
            .catch(error => {
                console.log("Error Fetching User games: " + error)
                window.alert("Error Fetching User Games, reload page")
            })
    }

    redirectToInfo(team_id) {
        console.log("Redirecting to team info with team_id: " + team_id)
        // Maybe add {path: "/"}
        this.props.cookies.set("team_id", team_id);
        this.props.history.push('/team_info')
    }


    render() {
        if (this.state.loggedIn) {
            return (
                <div>
                    <Dimmer active={this.state.isLoading}>
                        <Loader size='big'/>
                    </Dimmer>
                    <Header as="h2"
                            content={"Your Games"}/>
                    {this.state.hasGames ?
                        <Table celled>
                            <Table.Header>
                                <Table.Row>
                                    <Table.HeaderCell>Team Name</Table.HeaderCell>
                                    <Table.HeaderCell>Game Start Date</Table.HeaderCell>
                                    <Table.HeaderCell>Game End Date</Table.HeaderCell>
                                    <Table.HeaderCell>Home Points</Table.HeaderCell>
                                    <Table.HeaderCell>Away Points</Table.HeaderCell>
                                    <Table.HeaderCell>Winner</Table.HeaderCell>
                                </Table.Row>
                            </Table.Header>

                            <Table.Body>
                                {this.state.games.map(game =>
                                    <GameTable
                                        game={game}
                                    />)}
                            </Table.Body>
                        </Table> :
                        <Header>No games for you</Header>
                    }
                    <Divider/>
                    <Header as="h2"
                            content={"Your Teams"}/>
                    {this.state.hasTeams ?
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

                        </Table>:
                        <Header>You don't have any teams yet</Header>
                    }
                </div>
            )
        } else {
            return <Redirect to='/'/>
        }
    }
}

export default withCookies(Home)