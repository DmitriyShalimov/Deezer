import React, {Component} from "react";

import Player from "../player/Player.jsx";
import Header from "../header/Header.jsx";
import {connect} from "react-redux";
import {redirect} from "../../store/actions/main.js";
import {validateToken, logout} from "../../store/actions/login.js";
import {bindActionCreators} from "redux";


class MainPage extends Component {
    componentWillMount() {
        const token = localStorage.getItem("user-token");
        if (token && !this.props.isAuth) {
            this.props.validateToken(token);
        } else if (!this.props.isAuth) {
            this.props.redirect("/login");
        }
    }

    render() {
        const {isAuth, redirect, logout} = this.props;
        if (isAuth === false) {
            redirect("/login");
        }
        return (
            <React.Fragment>
                {isAuth &&
                <React.Fragment>
                    <Header logout={logout}/>
                    <Player/>
                </React.Fragment>
                }
            </React.Fragment>
        )
    };
}

const mapStateToProps = state => {
    return {
        isAuth: state.rootReducer.isAuth
    };
};

const mapDispatchToProps = dispatch => {
    return {
        redirect: (redirectUrl) =>
            dispatch(redirect(redirectUrl)),
        validateToken: (token) => dispatch(validateToken(token)),
        logout: bindActionCreators(logout, dispatch)
    };
};
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(MainPage);


