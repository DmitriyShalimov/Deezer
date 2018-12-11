import ReactDOM from 'react-dom';
import React from 'react';
import {Provider} from "react-redux";
import thunk from "redux-thunk";
import createHistory from "history/createBrowserHistory";
import {createStore, applyMiddleware, combineReducers} from "redux";
import { composeWithDevTools } from 'redux-devtools-extension';
import {connectRouter, routerMiddleware} from "connected-react-router";
import {rootReducer} from "./store/reducers/root-reducer.js";

import App from './App.jsx';
import {trackReducer} from "./store/reducers/track-reducer";

export const history = createHistory();

const reducers = combineReducers({
    rootReducer, trackReducer
});
const middleware = [thunk, routerMiddleware(history)];

const store = createStore(
    connectRouter(history)(reducers),
    composeWithDevTools(applyMiddleware(...middleware))
);

ReactDOM.render(
    <Provider store={store}>
        <App/>
    </Provider>,
    document.getElementById('react'));