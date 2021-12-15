import {Component} from "react";
import {CLIENT_GOOGLE_ID} from "../Constants";
import {
    Button,
    Dimmer,
    Divider,
    Dropdown,
    Header,
    Icon,
    List,
    Loader,
    Table,
    TableBody,
    TableCell,
    TableHeader
} from "semantic-ui-react";
import {withCookies} from "react-cookie";
import {Redirect} from "react-router-dom";
import PlayerTable from "../tables/PlayerTable";


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
            team: null,
            players: [],
            playersDropdownFormat: [],
            isLoading: false,
            pg: null,
            sg: null,
            sf: null,
            pf: null,
            c: null,
            b1: null,
            b2: null,
            isOwner: false,
        }
        this.state.loggedIn = this.state.loggedIn === "true";
        this.getPlayers = this.getPlayers.bind(this)
        this.generateDropdown = this.generateDropdown.bind(this)
        this.getRoster = this.getRoster.bind(this)
        this.updateRoster = this.updateRoster.bind(this)
        this.handleChange = this.handleChange.bind(this)
    }


    async componentDidMount() {
        if (this.state.loggedIn) {
            if (this.state.team_id !== 'null') {
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
                            team: data[0]
                        })
                        console.log("Success fetching team data for team_id " + this.state.team_id)
                        // Get Players

                        this.getPlayers()

                    })
                    .catch(error => {
                        console.log("Error fetching team data for team_id " + this.state.team_id + ": " + error)
                        window.alert("Error fetching team data, reload page")
                    })
                if (parseInt(this.state.user_id) === this.state.team.owner_id) {
                    this.setState({
                        isOwner: true
                    })
                } else {
                    this.setState({
                        isOwner: false
                    })
                }
                this.setState({
                    isLoading: false
                })

            }
        }
    }

    generateDropdown() {
        const dropdownAll = this.state.players.map(player =>
            ({
                key: player.player_id,
                value: player.player_id,
                text: player.first_name + ' ' + player.last_name
            })
        )
        this.setState({
            playersDropdownFormat: dropdownAll
        })
    }

    async getPlayers() {
        this.setState({
            isLoading: true
        })
        const input = '/fantasyPlayers?team_id=' + this.state.team_id

        await fetch(input, {
            headers: {
                Accept: 'application/json',
                'token': CLIENT_GOOGLE_ID
            }
        })
            .then(resp => resp.json())
            .then(data => {
                this.setState({
                    players: data
                })
                console.log(data)
                console.log("Success fetching players data for team_id " + this.state.team_id)
            })
            .catch(error => {
                console.log("Error fetching players data for team_id " + this.state.team_id + ": " + error)
                window.alert("Error fetching players data, reload page")
            })
        this.generateDropdown()
        this.getRoster()
        this.setState({
            isLoading: false
        })
    }

    getRoster() {
        const pg = this.state.players.filter(player => player.player_id === this.state.team.start_pg_id)[0]
        const sg = this.state.players.filter(player => player.player_id === this.state.team.start_sg_id)[0]
        const pf = this.state.players.filter(player => player.player_id === this.state.team.start_pf_id)[0]
        const sf = this.state.players.filter(player => player.player_id === this.state.team.start_sf_id)[0]
        const c = this.state.players.filter(player => player.player_id === this.state.team.start_c_id)[0]
        const b1 = this.state.players.filter(player => player.player_id === this.state.team.bench_1_id)[0]
        const b2 = this.state.players.filter(player => player.player_id === this.state.team.bench_2_id)[0]
        this.setState({
            pg: pg,
            sg: sg,
            pf: pf,
            sf: sf,
            c: c,
            b1: b1,
            b2: b2
        })
    }

    updateRoster() {
        this.setState({
            isLoading: true
        })
        const requestOptions = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'token': this.state.googleId
            },
            body: JSON.stringify({
                team_id: this.state.team_id,
                start_pg_id: this.state.pg,
                start_sg_id: this.state.sg,
                start_sf_id: this.state.sf,
                start_pf_id: this.state.pf,
                start_c_id: this.state.c,
                bench_1_id: this.state.b1,
                bench_2_id: this.state.b2
            })
        };
        fetch('/fantasyTeams', requestOptions)
            .then(async response => {
                const data = await response.json();

                // check for error response
                if (!response.ok) {
                    // get error message from body or default to response status
                    const error = (data && data.message) || response.status;
                    console.log("Error Updating Team: " + error)
                    window.alert("Error updating team, verify your choices and try again")
                    this.setState({
                        requestFailed: true,
                        isLoading: false
                    })
                    return Promise.reject(error);
                }

                console.log("Successfully Updated Team: ")
                window.alert("Successfully updated roster")
                this.setState({
                    requestFailed: false,
                    isLoading: false,
                })
                window.location.reload()
            })
        this.setState({
            isLoading: false
        })
    }

    handleChange = (e, {name, value}) => {
        this.setState({[name]: value})
        console.log(name, value)
    }

    render() {
        if (this.state.loggedIn) {
            if (this.state.team !== null && this.state.pg !== null) {
                return (
                    <div>
                        <Dimmer active={this.state.isLoading}>
                            <Loader size='big'/>
                        </Dimmer>
                        <Header
                            as="h1"
                            content={this.state.team.team_name + 's Info'}
                        />
                        <List horizontal>
                            <List.Item>
                                <List.Content>
                                    <List.Header><Icon name='calendar check outline'/>Team Wins: </List.Header>
                                    {this.state.team.team_wins}
                                </List.Content>
                            </List.Item>
                            <List.Item>
                                <List.Content>
                                    <List.Header><Icon name='calendar times outline'/>Team Losses</List.Header>
                                    {this.state.team.team_losses}
                                </List.Content>
                            </List.Item>
                            <List.Item>
                                <List.Content>
                                    <List.Header><Icon name='basketball ball'/>Points Scored</List.Header>
                                    {this.state.team.points_scored}
                                </List.Content>
                            </List.Item>
                            <List.Item>
                                <List.Content>
                                    <List.Header><Icon name='minus'/>Points Against</List.Header>
                                    {this.state.team.points_against}
                                </List.Content>
                            </List.Item>
                        </List>
                        <Header
                            as="h2"
                            content={'Roster'}
                        />
                        <Table celled structured className='roster-table' size={'small'}>
                            <TableHeader>
                                <Table.Row>
                                    <Table.HeaderCell>Position</Table.HeaderCell>
                                    <Table.HeaderCell>Player Name</Table.HeaderCell>
                                </Table.Row>
                            </TableHeader>

                            <TableBody>
                                <Table.Row>
                                    <TableCell>
                                        Point Guard
                                    </TableCell>
                                    <TableCell>
                                        <Dropdown
                                            disabled={!this.state.isOwner}
                                            placeholder={this.state.pg ? this.state.pg.first_name + ' ' + this.state.pg.last_name : 'Select Player'}
                                            fluid
                                            search
                                            selection
                                            name={'pg'}
                                            onChange={this.handleChange}
                                            options={this.state.playersDropdownFormat}
                                        />
                                    </TableCell>
                                </Table.Row>
                                <Table.Row>
                                    <TableCell>
                                        Shooting Guard
                                    </TableCell>
                                    <TableCell>
                                        <Dropdown
                                            disabled={!this.state.isOwner}
                                            placeholder={this.state.sg ? this.state.sg.first_name + ' ' + this.state.sg.last_name : 'Select Player'}
                                            fluid
                                            search
                                            selection
                                            name='sg'
                                            onChange={this.handleChange}
                                            options={this.state.playersDropdownFormat}
                                        />
                                    </TableCell>
                                </Table.Row>
                                <Table.Row>
                                    <TableCell>
                                        Small Forward
                                    </TableCell>
                                    <TableCell>
                                        <Dropdown
                                            disabled={!this.state.isOwner}
                                            placeholder={this.state.sf ? this.state.sf.first_name + ' ' + this.state.sf.last_name : 'Select Player'}
                                            fluid
                                            search
                                            selection
                                            name='sf'
                                            onChange={this.handleChange}
                                            options={this.state.playersDropdownFormat}
                                        />
                                    </TableCell>
                                </Table.Row>
                                <Table.Row>
                                    <TableCell>
                                        Power Forward
                                    </TableCell>
                                    <TableCell>
                                        <Dropdown
                                            disabled={!this.state.isOwner}
                                            placeholder={this.state.pf ? this.state.pf.first_name + ' ' + this.state.pf.last_name : 'Select Player'}
                                            fluid
                                            search
                                            selection
                                            name='pf'
                                            onChange={this.handleChange}
                                            options={this.state.playersDropdownFormat}
                                        />
                                    </TableCell>
                                </Table.Row>
                                <Table.Row>
                                    <TableCell>
                                        Center
                                    </TableCell>
                                    <TableCell>
                                        <Dropdown
                                            disabled={!this.state.isOwner}
                                            placeholder={this.state.c ? this.state.c.first_name + ' ' + this.state.c.last_name : 'Select Player'}
                                            fluid
                                            search
                                            selection
                                            name={'c'}
                                            onChange={this.handleChange}
                                            options={this.state.playersDropdownFormat}
                                        />
                                    </TableCell>
                                </Table.Row>
                                <Table.Row>
                                    <TableCell>
                                        Bench 1
                                    </TableCell>
                                    <TableCell>
                                        <Dropdown
                                            disabled={!this.state.isOwner}
                                            placeholder={this.state.b1 ? this.state.b1.first_name + ' ' + this.state.b1.last_name : 'Select Player'}
                                            fluid
                                            search
                                            selection
                                            name={'b1'}
                                            onChange={this.handleChange}
                                            options={this.state.playersDropdownFormat}
                                        />
                                    </TableCell>
                                </Table.Row>
                                <Table.Row>
                                    <TableCell>
                                        Bench 2
                                    </TableCell>
                                    <TableCell>
                                        <Dropdown
                                            disabled={!this.state.isOwner}
                                            placeholder={this.state.b2 ? this.state.b2.first_name + ' ' + this.state.b2.last_name : 'Select Player'}
                                            fluid
                                            search
                                            selection
                                            name={'b2'}
                                            onChange={this.handleChange}
                                            options={this.state.playersDropdownFormat}
                                        />
                                    </TableCell>
                                </Table.Row>
                            </TableBody>

                            <Table.Footer fullWidth>
                                <Table.Row>
                                    <Table.HeaderCell colSpan='12'>
                                        <Button
                                            disabled={!this.state.isOwner}
                                            floated='right'
                                            icon
                                            labelPosition='left'
                                            primary
                                            size='small'
                                            onClick={this.updateRoster}
                                        >
                                            <Icon name='user'/> {this.state.isOwner ? 'Update Roster' : 'Not your team'}
                                        </Button>
                                    </Table.HeaderCell>
                                </Table.Row>
                            </Table.Footer>

                        </Table>
                        <Divider/>
                        <Header
                            as="h2"
                            content={'All Players'}
                        />
                        <PlayerTable isOwner={this.state.isOwner} players={this.state.players}
                                     team_id={this.state.team_id}/>
                    </div>
                );
            } else {
                return <div>

                </div>
            }
        } else {
            return <Redirect to='/'/>
        }
    }
}

export default withCookies(TeamInfo)