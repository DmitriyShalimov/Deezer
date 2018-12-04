import React, {Component} from "react";
import './css/index.scss';
import SearchAutocomplete from "./search/SearchAutocomplete.jsx";

class Header extends Component {
    render() {
        return (
            <header className="top-bar-container">
                <div className="top-bar-section">
                    <div className="top-bar">
                        <div className="top-bar-left">
                            <div className="logo">
                                <img className="menu-text" src="/assets/img/logo.png" alt=""/>
                                <p className="menu-text">Deezer</p>
                            </div>
                        </div>
                        <div className="top-bar-center">
                            <div className="search-icon-form"><i className="fas fa-search"/>
                                <SearchAutocomplete/>
                            </div>
                        </div>
                        <div className="top-bar-right">
                            <i className="logout fas fa-sign-out-alt top has-tip"
                               data-tooltip aria-haspopup="true" tabIndex="1" title="Logout" onClick={()=> this.props.logout()}/>
                        </div>
                    </div>
                </div>
            </header>
        )
    };




}

export default Header