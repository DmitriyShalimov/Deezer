import React from "react";
import './css/index.css'

const CreateAccount = props => {
    const {question, message, link} = props;
    return (
        <React.Fragment>
            <div className="divider"><h5>{question}</h5></div>
            <div className="registration">
                <a className="login-box-submit-button register-btn" href={link}>{message}</a>
            </div>
        </React.Fragment>
    )
};
export default CreateAccount;