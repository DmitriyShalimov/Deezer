import React, {Component} from "react";
import '../css/index.scss';
import {Link} from "react-router-dom";

class SearchForm extends Component {
    render() {
        const {picture, title, subtitle, type, id, typeSearch} = this.props;
        return (
            <div className="search-suggestion">
                <div className="search-contentWrapper">
                    <Link to={`/${type}/${id}`} className="search-contentWrapper">
                        <img alt="" className="search-img" src={picture}/>
                        <div className="suggestion-details">
                            <div className="suggestion-title">{title}</div>
                            <div className="suggestion-subtitle">{subtitle}</div>
                        </div>
                    </Link>
                    <div className="search-value-play">
                        <button onClick={()=> typeSearch(type, id)}>
                            <i className="fas fa-play search-play-icon"/>
                        </button>
                    </div>
                </div>
            </div>
        );
    }
}

export default SearchForm;
