import React, {Component} from 'react';
import './App.css';
import Welcome from "./screens/Welcome";
import Home from "./screens/Home";
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import AppNavbar from "./utils/AppNavbar";
import Register from "./screens/Register";
import 'semantic-ui-css/semantic.min.css'
import Customize from "./screens/Customize";
import Memes from "./screens/Memes";
import Data from "./screens/Data";

class App extends Component {

    render() {
        return (
            <div className="box">
                <AppNavbar/>
                <div className="row content">
                    <Router>
                        <Switch>
                            <Route path='/' exact={true} component={Welcome}/>
                            <Route path='/home' exact={true} component={Home}/>
                            <Route path='/register' exact={true} component={Register}/>
                            <Route path='/update' exact={true} component={Customize}/>
                            <Route path='/memes' exact={true} component={Memes}/>
                            <Route path='/data' exact={true} component={Data}/>
                        </Switch>
                    </Router>
                </div>
            </div>
        )
    }
}

export default App;