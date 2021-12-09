import {withCookies} from "react-cookie";
import {Redirect} from "react-router-dom";
import {Button, Confirm, Message} from "semantic-ui-react";

const {Component} = require("react");
const {Dimmer, Loader, Header, Form, Input} = require("semantic-ui-react");
const React = require("react");

class Customize extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loggedIn: this.props.cookies.get("loggedIn") || false,
            givenName: this.props.cookies.get("givenName") || null,
            familyName: this.props.cookies.get("familyName") || null,
            googleId: this.props.cookies.get("googleId") || null,
            email: this.props.cookies.get("email") || null,
            registered: this.props.cookies.get("registered") || null,
            defaultCompanyName: null,
            defaultName: null,
            defaultEmail: null,
            loading: false
        }
        this.state.loggedIn = this.state.loggedIn === "true";
        this.state.registered = this.state.registered === "true";
        this.handleChange = this.handleChange.bind(this)
        this.updateClient = this.updateClient.bind(this)
        this.show = this.show.bind(this)
        this.handleConfirm = this.handleConfirm.bind(this)
        this.handleCancel = this.handleConfirm.bind(this)
        this.deleteClient = this.deleteClient.bind(this)
    }

    async componentWillMount() {
        const input = '/getClient?google_id=' + this.state.googleId
        const response = await fetch(input);
        const body = await response.json();
        const {cookies} = this.props;
        if (body.length > 0) {
            const client = body[0]
            console.log(body[0])
            cookies.set("registered", true, {path: "/"})
            this.setState({
                stipe_name: client.client_name,
                stipe_email: client.email,
                defaultName: client.name,
                defaultEmail: client.email,
                company_name: client.company_name,
                defaultCompanyName: client.company_name,
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
        }
    }

    handleChange = (e, {name, value}) => this.setState({[name]: value})

    async updateClient() {
        this.setState({
            isLoading: true
        })
        await new Promise(r => setTimeout(r, 500));

        const {cookies} = this.props;

        if (!this.state.registered) {
            console.log("You are not registered!")
            cookies.set("registered", false, {path: "/"})
            this.setState({
                requestFailed: false,
                registered: false,
                isLoading: false
            })
        } else {
            const requestOptions = {
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    "email": this.state.stipe_email || this.state.defaultEmail,
                    "company_name": this.state.company_name || this.state.defaultCompanyName,
                    "client_name": this.state.stipe_name || this.state.defaultName,
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
            fetch('/customize', requestOptions)
                .then(response => {
                    return response.json()
                })
                .then(data => {
                    console.log("Successfully Updated Client: ")
                    window.alert("Successfully updated client")
                    this.setState({
                        postId: data.id,
                        requestFailed: false,
                        isLoading: false,
                    })
                    this.props.history.push('/home');
                })
                .catch(error => {
                    console.log("Error Updating Client: " + error)
                    window.alert("Error updating account, check your values and try again")
                    this.setState({
                        requestFailed: true,
                        isLoading: false
                    })
                })
        }
    }

    show = () => this.setState({open: true})
    handleConfirm = () => this.setState({open: false})
    handleCancel = () => this.setState({open: false})

    async deleteClient() {
        this.setState({
            isLoading: true
        })
        const input = '/terminate_account?google_id=' + this.state.googleId

        const {cookies} = this.props

        await fetch(input, {method: 'DELETE'})
            .then(async response => {
                console.log(response)
                this.setState({
                    loggedIn: false,
                    registered: false,
                    isLoading: false,
                    givenName: null,
                    familyName: null,
                    googleId: null,
                    email: null,
                })
                cookies.set("loggedIn", false, {path: "/"})
                cookies.set("givenName", null, {path: "/"})
                cookies.set("familyName", null, {path: "/"})
                cookies.set("googleId", null, {path: "/"})
                cookies.set("email", null, {path: "/"})
                cookies.set("registered", false, {path: "/"})
                window.alert("Successfully deleted account, we are sorry to see you go!")
                window.location.reload()
                this.props.history.push('/');
            })
            .catch(error => {
                console.log('There was an error deleting the account: ' + error);
                window.alert("Error deleting account, try again")
                this.setState({
                    isLoading: false,
                })
            });
    }

    render() {
        if (this.state.loggedIn) {
            if (this.state.registered) {
                return (
                    <div>
                        <Dimmer active={this.state.isLoading}>
                            <Loader size='big'>Updating your account...</Loader>
                        </Dimmer>
                        <Header
                            as='h2'
                            content={'Need a change, ' + this.state.givenName + '?'}
                            inverted
                            className='update-title'
                            style={{
                                fontSize: '2.5em',
                                fontWeight: 'normal'
                            }}
                        />
                        <p style={{color: 'white', paddingLeft: '10px'}}>
                            No worries! You can always change your settings here:
                        </p>
                        <Message
                            warning
                            header='Warning!'
                            list={['Leaving values in blank will reset them to their default settings!']}
                            className='warning-box'
                        />
                        <Form inverted className="register-form">
                            <Form.Group widths='equal'>
                                <Form.Field
                                    id='form-input-control-first-name'
                                    control={Input}
                                    label="Full Name"
                                    name="stipe_name"
                                    value={this.state.stipe_name}
                                    placeholder={this.state.stipe_name}
                                    onChange={this.handleChange}/>
                                <Form.Field
                                    id='form-input-error-email'
                                    control={Input}
                                    label="Email"
                                    name="stipe_email"
                                    value={this.state.stipe_email}
                                    placeholder={this.state.stipe_email}
                                    onChange={this.handleChange}/>
                            </Form.Group>
                            <Form.Field
                                id='form-input-control-company'
                                control={Input}
                                label="Company Name"
                                name="company_name"
                                value={this.state.company_name}
                                placeholder="Company Name"
                                onChange={this.handleChange}
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
                            <Button onClick={this.updateClient}>Update</Button>
                            <Button floated='right' onClick={this.deleteClient}
                                    className='delete-button'>Delete</Button>
                            <Confirm
                                open={this.state.open}
                                header='This is a custom header'
                                onCancel={this.handleCancel}
                                onConfirm={this.handleConfirm}
                            />
                        </Form>
                    </div>
                )
            } else {
                return <Redirect to='/register'/>
            }
        } else {
            return <Redirect to='/'/>
        }
    }
}

export default withCookies(Customize)