import React, {Component} from "react";
import {Statistic, Table} from "semantic-ui-react";
import {Redirect} from "react-router-dom";
import {withCookies} from "react-cookie";
import UserTable from "../utils/UserTable";
import LeagueTable from "../utils/LeagueTable";

class Data extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loggedIn: this.props.cookies.get("loggedIn") || false,
            givenName: this.props.cookies.get("givenName") || null,
            familyName: this.props.cookies.get("familyName") || null,
            googleId: this.props.cookies.get("googleId") || null,
            email: this.props.cookies.get("email") || null,
            registered: this.props.cookies.get("registered") || null,
            users: [],
            leagues: []
        }
        this.state.loggedIn = this.state.loggedIn === "true";
        this.state.registered = this.state.registered === "true";
    }

    async componentWillMount() {
        await this.getUsers()
        await this.getLeagues()
    }

    async getUsers() {
        const input = '/users'
        await fetch(input, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'token': this.state.googleId
            },
        })
            .then(async response => {
                    const body = await response.json();
                    this.setState({
                        users: body
                    })
                }
            )
    }

    async getLeagues() {
        const input = '/fantasyLeagues'
        await fetch(input, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'token': this.state.googleId
            },
        })
            .then(async response => {
                    const body = await response.json();
                    this.setState({
                        leagues: body
                    })
                }
            )
    }


    render() {
        if (this.state.loggedIn) {
            if (this.state.registered) {
                return (
                    <div className="home">
                        <div className='images'>
                            <Statistic>
                                <Statistic.Value>{this.state.users.length}</Statistic.Value>
                                <Statistic.Label>Users</Statistic.Label>
                            </Statistic>
                        </div>
                        <Table celled>
                            <Table.Header>
                                <Table.Row>
                                    <Table.HeaderCell>First Name</Table.HeaderCell>
                                    <Table.HeaderCell>Last Name</Table.HeaderCell>
                                    <Table.HeaderCell>Email</Table.HeaderCell>
                                </Table.Row>
                            </Table.Header>
                            <Table.Body>
                                {this.state.users.map(user => <UserTable user={user}/>)}
                            </Table.Body>
                        </Table>

                        <Table celled>
                            <Table.Header>
                                <Table.Row>
                                    <Table.HeaderCell>League Name</Table.HeaderCell>
                                    <Table.HeaderCell>Draft Finished</Table.HeaderCell>
                                    <Table.HeaderCell>League Start Date</Table.HeaderCell>
                                    <Table.HeaderCell>Num Weeks</Table.HeaderCell>
                                </Table.Row>
                            </Table.Header>

                            <Table.Body>
                                {this.state.leagues.map(league => <LeagueTable league={league}/>)}
                            </Table.Body>
                        </Table>

                    </div>
                )
            } else {
                return (
                    <Redirect to='/register'/>
                )
            }
        } else {
            return <Redirect to='/'/>
        }
    }

}

export default withCookies(Data)