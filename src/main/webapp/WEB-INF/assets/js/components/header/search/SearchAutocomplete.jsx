import React, {Component} from "react";
import '../css/index.scss';
import Autosuggest from 'react-autosuggest';
import SearchForm from "./SearchForm.jsx";
import {bindActionCreators} from "redux";
import {getSearchOptions, typeSearch, redirect} from "../../../store/actions/main.js";
import {connect} from "react-redux";
import {history} from "../../../index.jsx";

class SearchAutocomplete extends Component {
    state = {value: '', suggestions: []};

    componentWillMount() {
        this.props.getSearchOptions();
    }

    onSuggestionsFetchRequested = ({value}) => {
        this.setState({
            suggestions: this.getSuggestions(value)
        });
    };

    onSuggestionsClearRequested = () => {
        this.setState({
            suggestions: []
        });
    };

    onChange = (event, {newValue}) => {
        this.setState({
            value: newValue
        });
    };

    onKeyPressed = (event) => {
        if (event.key === 'Enter') {
            this.generalSearch(this.state.value);
        }
    };

    generalSearch(mask) {
        history.push(`/search/${mask}`);
    };

    getSuggestions = (value) => {
        const inputValue = value.trim().toLowerCase();
        const inputLength = inputValue.length;
        let options = SearchAutocomplete.adjustOptions(this.props.searchOptions).filter(option =>
            option.title.toLowerCase().slice(0, inputLength) === inputValue
        ).slice(0, 5);
        return inputLength === 0 ? [] : (options.length === 0 ? [{
            title: '',
            notFound: `No results for '${value}'`
        }] : options);
    };

    static adjustOptions(data) {
        return data.map(d => {
            !d.title ? d.title = d.name : d.title;
            if (d.hasOwnProperty('artist')) {
                if (d.hasOwnProperty('album')) {
                    d.type = 'song'
                } else {
                    d.type = 'album';
                }
            } else {
                d.type = 'artist';
            }
            return d
        });
    }


    render() {
        const {value, suggestions} = this.state;
        const inputProps = {
            placeholder: "Search",
            value,
            onChange: this.onChange,
            onKeyPress: this.onKeyPressed
        };
        return (
            <div className="search-icon-form">
                <i className="fas fa-search" onClick={() => this.generalSearch(this.state.value)}/>
                <Autosuggest
                    suggestions={suggestions}
                    onSuggestionsFetchRequested={this.onSuggestionsFetchRequested}
                    onSuggestionsClearRequested={this.onSuggestionsClearRequested}
                    getSuggestionValue={getSuggestionValue}
                    renderSuggestion={this.renderSuggestion.bind(this)}
                    inputProps={inputProps}
                />
            </div>
        );
    }

    renderSuggestion(suggestion) {
        if (suggestion.notFound) {
            return <div className="empty-message" style={{fontSize:"16px"}}>{suggestion.notFound}</div>
        } else
            return <SearchForm picture={suggestion.picture} title={suggestion.title} subtitle={getSubtitle(suggestion)}
                               type={suggestion.type} id={suggestion.id}
                               typeSearch={this.props.typeSearch}/>
    };

}

const getSuggestionValue = suggestion => suggestion.title;


const getSubtitle = (item) => {
    return item.type + ((item.type !== 'artist') ? ` by ${item.artist.name}` : '');
};


const mapStateToProps = state => {
    return {
        searchOptions: state.rootReducer.searchOptions
    };
};

const mapDispatchToProps = dispatch => {
    return {
        getSearchOptions: bindActionCreators(getSearchOptions, dispatch),
        typeSearch: (type, id) => dispatch(typeSearch(type, id)),
        redirect: (url) => dispatch(redirect(url))
    };
};
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(SearchAutocomplete);



