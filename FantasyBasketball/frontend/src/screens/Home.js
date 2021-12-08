import React, {Component} from "react";
import {Divider, Header, Icon, Table} from "semantic-ui-react";
import {withCookies} from "react-cookie";
import {Redirect} from 'react-router-dom';


class Home extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loggedIn: this.props.cookies.get("loggedIn") || false,
            givenName: this.props.cookies.get("givenName") || null,
            familyName: this.props.cookies.get("familyName") || null,
            googleId: this.props.cookies.get("googleId") || null,
            email: this.props.cookies.get("email") || null,
            registered: true
        }
        this.state.loggedIn = this.state.loggedIn === "true";
    }

    async componentDidMount() {
        const input = '/getClient?google_id=' + this.state.googleId
        const response = await fetch(input);
        const body = await response.json();
        const {cookies} = this.props;
        if (body.length > 0) {
            const client = body[0]
            console.log(body[0])
            cookies.set("registered", true, {path: "/"})
            this.setState({
                assist_weight: client.assist_weight,
                block_weight: client.block_weight,
                ft_weight: client.ft_weight,
                max_team_size: client.max_team_size,
                min_league_dur: client.min_league_dur,
                min_league_size: client.min_league_size,
                rebound_weight: client.rebound_weight,
                three_p_weight: client.three_p_weight,
                turnover_weight: client.turnover_weight,
                two_p_weight: client.two_p_weight,
                steal_weight: client.steal_weight,
                registered: true
            })
        } else {
            cookies.set("registered", false, {path: "/"})
            this.setState({
                    registered: false
                }
            )
            console.log("BING BONG")
        }
    }


    render() {
        if (this.state.loggedIn) {
            if (this.state.registered) {
                return (
                    <div className="home">
                        <Header
                            as='h2'
                            content={'Welcome ' + this.state.givenName}
                            inverted
                            style={{
                                fontSize: '4em',
                                fontWeight: 'normal',
                                paddingBottom: '10px',
                                color: '#FD904DFF',
                                textAlign: 'center',
                                paddingTop: '10px'
                            }}
                        />
                        <div className='client-info'>
                            <Divider horizontal>
                                <Header as='h4' style={{color: 'white'}}>
                                    <Icon name='tag'/>
                                    Description
                                </Header>
                            </Divider>

                            <p style={{textAlign: 'center'}}>
                                In this web page you will be able to control all of your fantasy basketball settings
                            </p>

                            <Divider horizontal inverted>
                                <Header as='h4' style={{color: 'white', marginBottom: 20}}>
                                    <Icon name='bar chart'/>
                                    Weights
                                </Header>
                            </Divider>

                            <Table definition className="client-info-table" basic='very' inverted>
                                <Table.Body>
                                    <Table.Row>
                                        <Table.Cell width={2}>Three point weight</Table.Cell>
                                        <Table.Cell className="weight_number">{this.state.three_p_weight}</Table.Cell>
                                    </Table.Row>
                                    <Table.Row>
                                        <Table.Cell>Two point weight</Table.Cell>
                                        <Table.Cell className="weight_number">{this.state.two_p_weight}</Table.Cell>
                                    </Table.Row>
                                    <Table.Row>
                                        <Table.Cell>Free Throw weight</Table.Cell>
                                        <Table.Cell className="weight_number">{this.state.ft_weight}</Table.Cell>
                                    </Table.Row>
                                    <Table.Row>
                                        <Table.Cell>Rebound Weight</Table.Cell>
                                        <Table.Cell className="weight_number">{this.state.rebound_weight}</Table.Cell>
                                    </Table.Row>
                                    <Table.Row>
                                        <Table.Cell>Assist Weight</Table.Cell>
                                        <Table.Cell className="weight_number">{this.state.assist_weight}</Table.Cell>
                                    </Table.Row>
                                    <Table.Row>
                                        <Table.Cell>Block Weight</Table.Cell>
                                        <Table.Cell className="weight_number">{this.state.block_weight}</Table.Cell>
                                    </Table.Row>
                                    <Table.Row>
                                        <Table.Cell>Turnover Weight</Table.Cell>
                                        <Table.Cell className="weight_number">{this.state.turnover_weight}</Table.Cell>
                                    </Table.Row>
                                    <Table.Row>
                                        <Table.Cell>Steal Weight</Table.Cell>
                                        <Table.Cell className="weight_number">{this.state.steal_weight}</Table.Cell>
                                    </Table.Row>
                                    <Table.Row>
                                        <Table.Cell>Min League Duration</Table.Cell>
                                        <Table.Cell className="weight_number">{this.state.min_league_dur}</Table.Cell>
                                    </Table.Row>
                                    <Table.Row>
                                        <Table.Cell>Min League Size</Table.Cell>
                                        <Table.Cell className="weight_number">{this.state.min_league_size}</Table.Cell>
                                    </Table.Row>
                                    <Table.Row>
                                        <Table.Cell>Max Team Size</Table.Cell>
                                        <Table.Cell className="weight_number">{this.state.max_team_size}</Table.Cell>
                                    </Table.Row>
                                </Table.Body>
                            </Table>


                        </div>
                    </div>
                )
            } else {
                return (
                    <Redirect to='/register'/>
                )
            }
        }
        else {
            return <Redirect to='/'/>
        }
    }
}

export default withCookies(Home);