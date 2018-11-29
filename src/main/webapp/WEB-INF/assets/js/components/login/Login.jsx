import React, {Component} from "react";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import CreateAccount from "./CreateAccount.jsx";
import './css/index.css'
import LoginHeader from "./LoginHeader.jsx";
import SocialButtons from "./SocialButtons.jsx";
import {signIn} from "../../store/actions/login.js";

class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {login: "", password: ""};
    }

    render() {
        const {signIn, errorMessage} = this.props;
        const {login, password} = this.state;
        return (
            <React.Fragment>
                <LoginHeader/>
                <div className="card login-box">
                    <div className="cell">
                        <div className="login-box-form-section">
                            <h1 className="login-box-title">Sign in</h1>
                            <p id="error">{errorMessage}</p>
                            <input className="login-box-input" type="text" name="login" id="login"
                                   placeholder="Username" value={login}
                                   onChange={e => this.updateLogin(e.target.value)}/>
                            <input className="login-box-input" type="password" name="password" id="password"
                                   placeholder="Password" value={password}
                                   onChange={e => this.updatePassword(e.target.value)}/>
                            <button className="login-box-submit-button" id="login-btn" type="submit"
                                    name="signin_submit" onClick={() => signIn(login, password)}>Sign me in
                            </button>
                            <SocialButtons/>
                            <CreateAccount question="New to Deezer?" link="/registration"
                                           message="Create your Deezer account"/>
                        </div>
                    </div>
                </div>
            </React.Fragment>)
    }

    updateLogin(login) {
        this.setState({
            login
        })
    }

    updatePassword(password) {
        this.setState({
            password
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
        signIn: bindActionCreators(signIn, dispatch)
    };
};
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(Login);
