import './App.css';
import {Component} from "react";
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import Home from "./screens/Home";
import AppNavbar from "./utils/AppNavbar";
import Welcome from "./screens/Welcome";
import Leagues from "./screens/Leagues";
import LeagueInfo from "./screens/LeagueInfo";
import TeamInfo from "./screens/TeamInfo";

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
                            <Route path='/leagues' exact={true} component={Leagues}/>
                            <Route path='/league_info' exact={true} component={LeagueInfo}/>
                            <Route path='/team_info' exact={true} component={TeamInfo}/>
                        </Switch>
                    </Router>
                </div>
                <div className='footer'>

                </div>
            </div>
        )
    }
}

export default App;
