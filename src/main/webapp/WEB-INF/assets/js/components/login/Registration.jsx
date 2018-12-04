import React, {Component} from "react";
import './css/index.css'
import LoginHeader from "./LoginHeader.jsx";
import SocialButtons from "./SocialButtons.jsx";
import CreateAccount from "./CreateAccount.jsx";
import {register} from "../../store/actions/login.js";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";

class Registration extends Component {
    constructor(props) {
        super(props);
        this.state = {login: "", password: "", password2: ""};
    }
    componentWillMount(){
        window.fbAsyncInit = function () {
            FB.init({
                appId: '330979217471281',
                oauth: true,
                status: true, // check login status
                cookie: true, // enable cookies to allow the server to access the session
                xfbml: true // parse XFBML
            });

        };
    }

    render() {
        const {signUp, errorMessage} = this.props;
        const {login, password, password2} = this.state;
        return (
            <React.Fragment>
                <LoginHeader/>
                <div className="card login-box">
                    <div className="cell">
                        <div className="login-box-form-section">
                            <h1 className="login-box-title">Sign up</h1>
                            <p id="error">{errorMessage}</p>
                            <input className="login-box-input" type="text" name="login" id="login"
                                   placeholder="Username" value={login}
                                   onChange={e => this.updateState("login", e.target.value)}/>
                            <input className="login-box-input" type="password" name="password" id="password"
                                   placeholder="Password" value={password}
                                   onChange={e => this.updateState("password", e.target.value)}/>
                            <input className="login-box-input" type="password" name="password2" id="password2"
                                   placeholder="Retype password" value={password2}
                                   onChange={e => this.updateState("password2", e.target.value)}/>
                            <button className="login-box-submit-button" id="register" type="submit"
                                    name="signup_submit" onClick={() => signUp(login, password, password2)}>Sign me up
                            </button>
                        </div>
                        <SocialButtons/>
                        <CreateAccount question="Already have an account?" link="/login" message="Sign in"/>
                    </div>
                </div>
            </React.Fragment>
        )
    }

    updateState(name, value) {
        this.setState({
            [name]: value
        })
    }
}
const mapStateToProps = state => {
    return {
        user: state.rootReducer.user,
        errorMessage: state.rootReducer.errorMessage
    };
};

const mapDispatchToProps = dispatch => {
    return {
        signUp: bindActionCreators(register, dispatch)
    };
};
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(Registration);

