import {Component} from "react";
import {Redirect} from "react-router-dom";
import {CLIENT_GOOGLE_ID} from "../Constants";
import {Button, Dimmer, Icon, Loader, Table} from "semantic-ui-react";
import {withCookies} from "react-cookie";
import LeagueTable from "../tables/LeagueTable";

class Leagues extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loggedIn: this.props.cookies.get('loggedIn') || false,
            givenName: this.props.cookies.get("givenName") || null,
            familyName: this.props.cookies.get("familyName") || null,
            googleId: this.props.cookies.get("googleId") || null,
            email: this.props.cookies.get("email") || null,
            user_id: this.props.cookies.get("user_id") || null,
            leagues: [],
            isLoading: false
        }
        this.state.loggedIn = this.state.loggedIn === "true";
        this.redirectToInfo = this.redirectToInfo.bind(this)
    }

    async componentWillMount() {
        if (this.state.loggedIn) {
            this.setState({
                isLoading: true
            })
            this.props.cookies.set("league_id", null)
            const input = '/fantasyLeagues'

            await fetch(input, {
                headers: {
                    Accept: 'application/json',
                    'token': CLIENT_GOOGLE_ID
                }
            })
                .then(resp => resp.json())
                .then(data => {
                    this.setState({
                        leagues: data
                    })
                })
                .catch(error => {
                    console.log("Error fetching league data: " + error)
                    window.alert("Error fetching league data, reload page")
                })
            this.setState({
                isLoading: false
            })
        }
    }

    redirectToInfo(league_id) {
        console.log("Redirecting to league info with league_id: " + league_id)
        // Maybe add {path: "/"}
        this.props.cookies.set("league_id", league_id);
        this.props.history.push('/league_info')
    }


    render() {
        if (this.state.loggedIn) {
            return (
                <div>
                    <Dimmer active={this.state.isLoading}>
                        <Loader size='big'/>
                    </Dimmer>
                    <Table celled>
                        <Table.Header>
                            <Table.Row>
                                <Table.HeaderCell>League Name</Table.HeaderCell>
                                <Table.HeaderCell>League Size</Table.HeaderCell>
                                <Table.HeaderCell>Draft Finished</Table.HeaderCell>
                                <Table.HeaderCell>League Start Date</Table.HeaderCell>
                                <Table.HeaderCell>Num Weeks</Table.HeaderCell>
                                <Table.HeaderCell>More Info</Table.HeaderCell>
                            </Table.Row>
                        </Table.Header>

                        <Table.Body>
                            {this.state.leagues.map(league =>
                                <LeagueTable
                                    league={league}
                                    redirect={this.redirectToInfo}
                                />)}
                        </Table.Body>

                        <Table.Footer fullWidth>
                            <Table.Row>
                                <Table.HeaderCell/>
                                <Table.HeaderCell colSpan='6'>
                                    <Button
                                        floated='right'
                                        icon
                                        labelPosition='left'
                                        primary
                                        size='small'
                                    >
                                        <Icon name='user'/> Add a League
                                    </Button>
                                </Table.HeaderCell>
                            </Table.Row>
                        </Table.Footer>
                    </Table>
                </div>
            )
        } else {
            return <Redirect to='/'/>
        }

    }
}

export default withCookies(Leagues)