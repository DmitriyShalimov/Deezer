import React, {Component} from "react";
import {getTopPlaylists, typeSearch, getRecommendedPlaylists, getGenrePlylists} from "../../store/actions/main.js";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import PlaylistsList from "./PlaylistsList.jsx";
import {withRouter} from "react-router-dom";
import GenresList from "./GenresList.jsx";

class DeezerMain extends Component {
    componentWillMount() {
        this.props.getTopPlaylists();
        this.props.getRecommendedPlaylists();
        this.props.getGenrePlylists();
    }

    render() {
        const {topPlaylists, typeSearch, likePlaylist, recommendedPlaylists, genrePlaylists} = this.props;
        return (
            <section id="playlist-section">
                <div className="genres-playlists">
                    <h3>Explore by genre</h3>
                    <div className="outline-pagination">
                        <div className="underline"/>
                    </div>
                    {genrePlaylists.length > 0 &&
                    <GenresList genres={genrePlaylists} play={typeSearch}/>}
                </div>
                <div className="recommended-playlists">
                    <h3>Recommendations for you</h3>
                    <div className="underline"/>
                    <div className="grid-x grid-padding-x small-up-3 medium-up-4 large-up-5 artist-page"
                         id="recommendedPlaylist">
                        {recommendedPlaylists.length > 0 &&
                        <PlaylistsList playlists={recommendedPlaylists} play={typeSearch}/>}
                    </div>
                </div>
                <div className="top-public-playlists">
                    <h3>Deezer Top 10</h3>
                    <div className="underline"/>
                    <div className="grid-x grid-padding-x small-up-2 medium-up-4 large-up-5" id="topPlaylist">
                        {topPlaylists.length > 0 &&
                        <PlaylistsList playlists={topPlaylists} play={typeSearch} like={likePlaylist} showlike/>}
                    </div>
                </div>
            </section>
        );
    }
}

const mapStateToProps = state => {
    return {
        topPlaylists: state.rootReducer.topPlaylists,
        recommendedPlaylists: state.rootReducer.recommendedPlaylists,
        genrePlaylists: state.rootReducer.genrePlaylists
    };
};

const mapDispatchToProps = dispatch => {
    return {
        getTopPlaylists: bindActionCreators(getTopPlaylists, dispatch),
        getRecommendedPlaylists: bindActionCreators(getRecommendedPlaylists, dispatch),
        getGenrePlylists: bindActionCreators(getGenrePlylists, dispatch),
        typeSearch: (type, id, title) => dispatch(typeSearch(type, id, title)),
    };
};
export default withRouter(connect(
    mapStateToProps,
    mapDispatchToProps
)(DeezerMain));

