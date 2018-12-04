import {ERROR, SET_IS_AUTHENTICATED} from "./actionTypes.js";
import {push} from 'react-router-redux';
import 'babel-polyfill';

const error = message => {
    return {
        type: ERROR,
        payload: message
    }
};

const isAuth = res => {
    return {
        type: SET_IS_AUTHENTICATED,
        payload: res.auth
    }
};

export const signIn = (login, password, signUpOnError) => {
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
                    return res.json();
                } else {
                    if (signUpOnError) {
                        dispatch(register(login, password, password));
                    } else {
                        return dispatch(error("Invalid Login/Password"));
                    }
                }
            }).then(res => {
                if (res) {
                    localStorage.setItem('user-token', res.uuid);
                    dispatch(push("/"))
                }
            });
    };
};

export const register = (login, password, password2) => {
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
                        return res.json()
                    } else {
                        dispatch(error(`User with login '${login}' already exists`))
                    }
                }).then(res => {
                    localStorage.setItem('user-token', res.uuid);
                    dispatch(push("/"))
                });
    };
};

export const logout = () => {
    return dispatch => {
        const token = localStorage.getItem('user-token');
        return fetch(`/logout`, {
            headers: {
                'User-Token':
                token
            }
        })
            .then(res => {
                    if (res.status == 200) {
                        dispatch(isAuth({auth: false}));
                    }
                }
            );
    }
};

export const validateToken = (token) => {
    return dispatch => {
        return fetch(`validate/${token}`)
            .then(res => res.json())
            .then(res => {
                    if (res.auth) {
                        dispatch(push('/'));
                    }
                    dispatch(isAuth(res));
                }
            );
    }
};