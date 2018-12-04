import React, {Component} from "react";
import '../css/index.scss';

class SearchForm extends Component {
    render() {
        const {picture, title, subtitle} = this.props;
        return (<div className="search-suggestion">
            <div className="search-contentWrapper">
                    <img alt="" className="search-img" src={picture}/>
                    <div className="suggestion-details">
                        <div className="suggestion-title">{title}</div>
                        <div className="suggestion-subtitle">{subtitle}</div>
                    </div>
                <div className="search-value-play">
                    <button>
                        <i className="fas fa-play search-play-icon"/>
                    </button>
                </div>
            </div>
        </div>);
    }
}

export default SearchForm;
