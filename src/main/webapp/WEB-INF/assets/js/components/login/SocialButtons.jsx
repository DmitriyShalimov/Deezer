import React, {Component} from "react";
import './css/index.css'
import {fbLogin, initGoogleLogin, initFbLogin} from "./util/social-login-functions.js"
import {bindActionCreators} from "redux";
import {signIn, register} from "../../store/actions/login.js";
import {connect} from "react-redux";

class SocialButtons extends Component {
    constructor(props) {
        super(props);
        this.state = {login: "", password: ""};
    }

    componentDidMount() {
        window.fbAsyncInit = () => {
            FB.init({
                appId: '330979217471281',
                oauth: true,
                status: true, // check login status
                cookie: true, // enable cookies to allow the server to access the session
                xfbml: true // parse XFBML
            });

        };
        initGoogleLogin(this.handleGoogleLogin.bind(this));
        initFbLogin();
    }

    render() {
        return (
            <div className="login-box-social-section-inner">
                <a className="login-box-social-button-facebook " id="fb-login"
                   onClick={() => this.handleFbLogin()}><i
                    className="fab fa-facebook-square"/></a>
                <a id="google-login" className="login-box-social-button-google">
                    <i className="fab fa-google-plus-g"/>
                </a>
            </div>
        )
    }

    handleFbLogin() {
        const {signIn} = this.props;
        let user = fbLogin();
        console.log("user from component", user);
        if (user) {
            this.setState({login: user.login, password: user.password});
            signIn(user.login, user.password, true);
        }
    }

    handleGoogleLogin(login, password) {
        const {signIn} = this.props;
        this.setState({login, password});
        signIn(login, password, true);
    }
}

const mapStateToProps = state => {
    return {
        errorMessage: state.rootReducer.errorMessage
    };
};

const mapDispatchToProps = dispatch => {
    return {
        signIn: bindActionCreators(signIn, dispatch),
        signUp: bindActionCreators(register, dispatch),
    };
};
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(SocialButtons);
