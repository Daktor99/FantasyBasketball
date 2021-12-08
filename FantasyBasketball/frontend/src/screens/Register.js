import React, {Component} from "react";
import {withCookies} from "react-cookie";
import {Form, Header, Input} from "semantic-ui-react";
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
            companyName:'',
            registeredName:'',
            registeredEmail:'',
            assist_weight: '',
            block_weight: '',
            ft_weight: '',
            max_team_size: '',
            min_league_dur: '',
            min_league_size: '',
            rebound_weight: '',
            three_p_weight: '',
            turnover_weight: '',
            two_p_weight: '',
            steal_weight: '',
        }
        this.state.loggedIn = this.state.loggedIn === "true";
        this.state.registered = this.state.registered === "true";
        this.onCreateAccount = this.onCreateAccount.bind(this)
        this.handleChange = this.handleChange.bind(this)
    }

    handleChange = (e, {name, value}) => this.setState({[name]: value})

    onCreateAccount() {
        if (this.state.registered) {
            console.log("You are already registered!")
        } else {
            console.log(this.state)
            const requestOptions = {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    "email": this.state.registeredEmail,
                    "company_name": this.state.companyName,
                    "client_name": this.state.registeredName,
                    "three_p_weight": this.state.three_p_weight,
                    "two_p_weight": this.state.two_p_weight,
                    "ft_weight": this.state.ft_weight,
                    "rebound_weight": this.state.rebound_weight,
                    "assist_weight": this.state.assist_weight,
                    "block_weight": this.state.block_weight,
                    "turnover_weight": this.state.turnover_weight,
                    "min_league_dur": this.state.min_league_dur,
                    "max_team_size": this.state.max_team_size,
                    "min_league_size": this.state.min_league_size,
                    "steal_weight": this.state.steal_weight,
                    "google_id": this.state.googleId
                })
            };
            fetch('/register', requestOptions)
                .then(response => response.json())
                .then(data => this.setState({postId: data.id}));
        }
    }

    render() {
        if (this.state.registered !== true) {
            return (
                <div>
                    <Header
                        as='h2'
                        content={'Welcome ' + this.state.givenName}
                        inverted
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
                            onChange={this.handleChange}/>
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

    }

}

export default withCookies(Register);