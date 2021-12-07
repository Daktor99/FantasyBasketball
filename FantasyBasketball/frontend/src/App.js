import React, {Component} from 'react';
import './App.css';
import Welcome from "./screens/Welcome";
import Home from "./screens/Home";
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import AppNavbar from "./utils/AppNavbar";

class App extends Component {

  render() {
    return (
        <div className="box">
            <AppNavbar/>
            <div className="row content">
                <Router>
                  <Switch>
                      <Route path='/' exact={true} component={Welcome}/>
                      <Route path='/home' exact={true} component = {Home}/>
                      {/*<Route path='/data'*/}
                  </Switch>
                </Router>
            </div>
        </div>
    )
  }
}

export default App;