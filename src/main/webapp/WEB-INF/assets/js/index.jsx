import ReactDOM from 'react-dom';
import React from 'react';
import {Provider} from "react-redux";
import thunk from "redux-thunk";
import createHistory from "history/createBrowserHistory";
import {createStore, compose, applyMiddleware, combineReducers} from "redux";
import {connectRouter, routerMiddleware} from "connected-react-router";
import {rootReducer} from "./store/reducers/root-reducer";

import App from './App.jsx';

export const history = createHistory();

const reducers = combineReducers({
    rootReducer
});
const middleware = [thunk, routerMiddleware(history)];

const store = createStore(
    connectRouter(history)(reducers),
    compose(applyMiddleware(...middleware))
);

ReactDOM.render(
    <Provider store={store}>
        <App/>
    </Provider>,
    document.getElementById('react'));