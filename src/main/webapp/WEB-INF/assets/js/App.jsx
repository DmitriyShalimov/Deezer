import React, {Component} from "react";
import {Switch, Route} from "react-router-dom";
import Login from './components/login/Login.jsx';
import Registration from './components/login/Registration.jsx';
import {history} from "./index.jsx";
import {ConnectedRouter} from "connected-react-router";


class App extends Component {
    render() {
        return (
            <div className="app">
                <ConnectedRouter history={history}>
                        <Switch>
                            <Route exact path="/login" component={Login}/>
                            <Route exact path="/registration" component={Registration}/>
                        </Switch>
                </ConnectedRouter>
            </div>
        );
    }
}

export default App;