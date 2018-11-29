import {ERROR} from "./actionTypes.js";
import {push} from 'react-router-redux';

const error = message => {
    return {
        type: ERROR,
        payload: message
    }
};

export const signIn = (login, password) => {
    return dispatch => {
        return fetch(`login`, {
            method: 'POST',
            headers: new Headers({
                'Content-Type': 'application/x-www-form-urlencoded',
            }),
            body: `login=${login}&password=${password}`
        })
            .then(res => {
                if (res.status == 200) {
                    dispatch(push('/'))
                } else {
                    dispatch(error("Invalid Login/Password"))
                }
            });
    };
};

export const signUp = (login, password, password2) => {
    return dispatch => {
        if (password !== password2) {
            dispatch(error("Passwords should match"))
        } else
            return fetch(`registration`, {
                method: 'POST',
                headers: new Headers({
                    'Content-Type': 'application/x-www-form-urlencoded',
                }),
                body: `login=${login}&password=${password}`
            })
                .then(res => {
                    if (res.status == 200) {
                        dispatch(push('/'))
                    } else {
                        dispatch(error(`User with login '${login}' already exists`))
                    }
                });
    };
};
