import React, {Component} from "react";
import {withCookies} from "react-cookie";
import {Dimmer, Form, Header, Input, Loader} from "semantic-ui-react";
import {Redirect} from "react-router-dom";


class Register extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loggedIn: this.props.cookies.get("loggedIn") || false,
            givenName: this.props.cookies.get("givenName") || null,
            familyName: this.props.cookies.get("familyName") || null,
            googleId: this.props.cookies.get("googleId") || null,
            email: this.props.cookies.get("email") || null,
            registered: this.props.cookies.get("registered") || null,
            companyName: null,
            registeredName: null,
            registeredEmail: null,
            assist_weight: null,
            block_weight: null,
            ft_weight: null,
            max_team_size: null,
            min_league_dur: null,
            min_league_size: null,
            rebound_weight: null,
            three_p_weight: null,
            turnover_weight: null,
            two_p_weight: null,
            steal_weight: null,
            isLoading: false,
            companyError: false
        }
        this.state.loggedIn = this.state.loggedIn === "true";
        this.state.registered = this.state.registered === "true";
        this.onCreateAccount = this.onCreateAccount.bind(this)
        this.handleChange = this.handleChange.bind(this)
        this.checkInputs = this.checkInputs.bind(this)
    }

    checkInputs() {
        if (this.state.companyName) {
            this.setState({
                companyError: false
            })
            return true
        } else {
            this.setState({
                companyError: true
            })
        }
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
            this.props.history.push('/home');
        } else {
            cookies.set("registered", false, {path: "/"})
            this.setState({
                    registered: false
                }
            )
        }
    }

    handleChange = (e, {name, value}) => this.setState({[name]: value})

    // async checkIfRegistered() {
    //
    // }

    async onCreateAccount() {
        if (this.checkInputs()) {
            this.setState({
                isLoading: true
            })
            await new Promise(r => setTimeout(r, 500));

            await this.checkIfRegistered()
            const {cookies} = this.props;

            if (this.state.registered) {
                console.log("You are already registered!")
                window.alert("You are already registered!")
                this.setState({
                    requestFailed: false,
                    registered: true,
                    isLoading: false
                })

            } else {
                const requestOptions = {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify({
                        "email": this.state.registeredEmail || this.props.cookies.get("email"),
                        "company_name": this.state.companyName || "",
                        "client_name": this.state.registeredName || this.props.cookies.get("givenName") + " " + this.props.cookies.get("familyName"),
                        "three_p_weight": this.state.three_p_weight || 1.0,
                        "two_p_weight": this.state.two_p_weight || 1.0,
                        "ft_weight": this.state.ft_weight || 1.0,
                        "rebound_weight": this.state.rebound_weight || 1.0,
                        "assist_weight": this.state.assist_weight || 1.0,
                        "block_weight": this.state.block_weight || 1.0,
                        "turnover_weight": this.state.turnover_weight || 1.0,
                        "min_league_dur": this.state.min_league_dur || 1,
                        "max_team_size": this.state.max_team_size || 30,
                        "min_league_size": this.state.min_league_size || 2,
                        "steal_weight": this.state.steal_weight || 1.0,
                        "google_id": this.state.googleId
                    })
                };
                fetch('/register', requestOptions)
                    .then(response => {
                        return response.json()
                    })
                    .then(data => {
                        console.log("Successfully registered Client: ")
                        cookies.set("registered", true, {path: "/"})
                        this.setState({
                            postId: data.id,
                            requestFailed: false,
                            registered: true,
                            isLoading: false
                        })
                    })
                    .catch(error => {
                        console.log("Error registering Client: " + error)
                        cookies.set("registered", false, {path: "/"})
                        window.alert("Error registering account, check your values and try again")
                        this.setState({
                            requestFailed: true,
                            registered: false,
                            isLoading: false
                        })
                    })
            }
        }
    }

    render() {
        if (this.state.loggedIn) {
            if (this.state.registered !== true) {
                return (
                    <div>
                        <Dimmer active={this.state.isLoading}>
                            <Loader size='big'>Registering to STIPE</Loader>
                        </Dimmer>
                        <Header
                            as='h2'
                            content={'Welcome ' + this.state.givenName}
                            inverted
                            className='update-title'
                            style={{
                                paddingLeft: '3px',
                                fontSize: '2.5em',
                                fontWeight: 'normal',
                                marginBottom: 0,
                                color: '#FD904DFF'
                            }}
                        />
                        <p style={{color: 'white', paddingLeft: '10px',}}>
                            Please register with us to start your fantasy league experience
                        </p>
                        <Form inverted className="register-form" onSubmit={this.onCreateAccount}>
                            <Form.Group widths='equal'>
                                <Form.Field
                                    id='form-input-control-first-name'
                                    control={Input}
                                    label="Full Name"
                                    name="registeredName"
                                    value={this.state.registeredName}
                                    placeholder={this.state.givenName + " " + this.state.familyName}
                                    onChange={this.handleChange}/>
                                <Form.Field
                                    id='form-input-error-email'
                                    control={Input}
                                    label="Email"
                                    name="registeredEmail"
                                    value={this.state.registeredEmail}
                                    placeholder={this.state.email}
                                    onChange={this.handleChange}/>
                            </Form.Group>
                            <Form.Field
                                id='form-input-control-company'
                                control={Input}
                                label="Company Name"
                                name="companyName"
                                value={this.state.companyName}
                                placeholder="Company Name"
                                onChange={this.handleChange}
                                error={this.state.companyError ? {
                                    active: this.state.companyError,
                                    content: 'Please enter a valid Company Name',
                                    pointing: 'above',
                                } : false}
                            />
                            <Form.Group inline>
                                <Form.Field>
                                    <label>Two Point Weight</label>
                                    <Input placeholder='1.0'
                                           className="input-weights"
                                           name="two_p_weight"
                                           value={this.state.two_p_weight}
                                           onChange={this.handleChange}/>
                                </Form.Field>
                            </Form.Group>
                            <Form.Group inline>
                                <Form.Field>
                                    <label>Three Point Weight</label>
                                    <Input placeholder='1.0'
                                           className="input-weights"
                                           name='three_p_weight'
                                           value={this.state.three_p_weight}
                                           onChange={this.handleChange}/>
                                </Form.Field>
                            </Form.Group>
                            <Form.Group inline>
                                <Form.Field>
                                    <label>Free Throw Weight</label>
                                    <Input placeholder='1.0'
                                           className="input-weights"
                                           name='ft_weight'
                                           value={this.state.ft_weight}
                                           onChange={this.handleChange}/>
                                </Form.Field>
                            </Form.Group>
                            <Form.Group inline>
                                <Form.Field>
                                    <label>Rebound Weight</label>
                                    <Input placeholder='1.0'
                                           className="input-weights"
                                           name='rebound_weight'
                                           value={this.state.rebound_weight}
                                           onChange={this.handleChange}/>
                                </Form.Field>
                            </Form.Group>
                            <Form.Group inline>
                                <Form.Field>
                                    <label>Assist Weight</label>
                                    <Input placeholder='1.0'
                                           className="input-weights"
                                           name='assist_weight'
                                           value={this.state.assist_weight}
                                           onChange={this.handleChange}/>
                                </Form.Field>
                            </Form.Group>
                            <Form.Group inline>
                                <Form.Field>
                                    <label>Block Weight</label>
                                    <Input placeholder='1.0'
                                           className="input-weights"
                                           name='block_weight'
                                           value={this.state.block_weight}
                                           onChange={this.handleChange}/>
                                </Form.Field>
                            </Form.Group>
                            <Form.Group inline>
                                <Form.Field>
                                    <label>Turnover Weight</label>
                                    <Input placeholder='1.0'
                                           className="input-weights"
                                           name='turnover_weight'
                                           value={this.state.turnover_weight}
                                           onChange={this.handleChange}/>
                                </Form.Field>
                            </Form.Group>
                            <Form.Group inline>
                                <Form.Field>
                                    <label>Steal Weight</label>
                                    <Input placeholder='1.0'
                                           className="input-weights"
                                           name='steal_weight'
                                           value={this.state.steal_weight}
                                           onChange={this.handleChange}/>
                                </Form.Field>
                            </Form.Group>
                            <Form.Group inline>
                                <Form.Field>
                                    <label>Min League Duration</label>
                                    <Input placeholder='1'
                                           className="input-weights"
                                           name='min_league_dur'
                                           value={this.state.min_league_dur}
                                           onChange={this.handleChange}/>
                                </Form.Field>
                            </Form.Group>
                            <Form.Group inline>
                                <Form.Field>
                                    <label>Min League Size</label>
                                    <Input placeholder='2'
                                           className="input-weights"
                                           name='min_league_size'
                                           value={this.state.min_league_size}
                                           onChange={this.handleChange}/>
                                </Form.Field>
                            </Form.Group>
                            <Form.Group inline>
                                <Form.Field>
                                    <label>Max Team Size</label>
                                    <Input placeholder='30'
                                           className="input-weights"
                                           name='max_team_size'
                                           value={this.state.max_team_size}
                                           onChange={this.handleChange}/>
                                </Form.Field>
                            </Form.Group>
                            <Form.Button
                            >Create Account
                            </Form.Button>
                        </Form>
                    </div>
                )
            } else {
                return (
                    <Redirect to='/home'/>
                )
            }
        } else {
            return <Redirect to='/'/>
        }
    }

}

export default withCookies(Register);