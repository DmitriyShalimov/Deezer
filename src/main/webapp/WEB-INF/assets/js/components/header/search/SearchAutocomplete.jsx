import React, {Component} from "react";
import '../css/index.scss';
import Autosuggest from 'react-autosuggest';
import SearchForm from "./SearchForm.jsx";
import {bindActionCreators} from "redux";
import {getSearchOptions, typeSearch} from "../../../store/actions/main.js";
import {connect} from "react-redux";


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


    getSuggestions = (value) => {
        const inputValue = value.trim().toLowerCase();
        const inputLength = inputValue.length;
        let options = SearchAutocomplete.adjustOptions(this.props.searchOptions).filter(option =>
            option.title.toLowerCase().slice(0, inputLength) === inputValue
        ).slice(0, 5);
        return inputLength === 0 ? [] : options;
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

    handleTypeSearch = (event, {suggestion}) => {
        this.props.typeSearch(suggestion.type, suggestion.id);
    };


    render() {
        const {value, suggestions} = this.state;
        const inputProps = {
            placeholder: "Search",
            value,
            onChange: this.onChange
        };
        return (
            <Autosuggest
                suggestions={suggestions}
                onSuggestionsFetchRequested={this.onSuggestionsFetchRequested}
                onSuggestionsClearRequested={this.onSuggestionsClearRequested}
                getSuggestionValue={getSuggestionValue}
                renderSuggestion={renderSuggestion}
                inputProps={inputProps}
                onSuggestionSelected={this.handleTypeSearch}
            />
        );
    }
}

const getSuggestionValue = suggestion => suggestion.title;

const renderSuggestion = suggestion => (
    <SearchForm picture={suggestion.picture} title={suggestion.title} subtitle={getSubtitle(suggestion)}/>
);

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
        typeSearch: (type, id) => dispatch(typeSearch(type, id))
    };
};
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(SearchAutocomplete);



