import React, {Component} from "react";
import {getTopPlaylists, getGenres, typeSearch, getRecommendedPlaylists} from "../../store/actions/main.js";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import PlaylistsList from "./PlaylistsList.jsx";
import {withRouter} from "react-router-dom";
import GenreList from "./GenreList.jsx";

class DeezerMain extends Component {
    componentWillMount() {
        this.props.getTopPlaylists();
        this.props.getRecommendedPlaylists();
        this.props.getGenres();
    }

    render() {
        const {topPlaylists, typeSearch, likePlaylist, recommendedPlaylists, genres} = this.props;
        return (
            <section id="playlist-section">
                <div className="genres-playlists">
                    <h3>Explore by genre</h3>
                    <div className="outline-pagination">
                        <div className="underline"/>
                        <div id="pagination-container-genre"/>
                    </div>
                    <div className="grid-x grid-padding-x small-up-2 medium-up-4 large-up-5" id="genre">
                        {genres.length > 0 &&
                        <GenreList genres={genres} play={typeSearch}/>}
                    </div>
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
        genres: state.rootReducer.genres,
        topPlaylists: state.rootReducer.topPlaylists,
        recommendedPlaylists: state.rootReducer.recommendedPlaylists
    };
};

const mapDispatchToProps = dispatch => {
    return {
        getGenres: bindActionCreators(getGenres, dispatch),
        getTopPlaylists: bindActionCreators(getTopPlaylists, dispatch),
        getRecommendedPlaylists: bindActionCreators(getRecommendedPlaylists, dispatch),
        typeSearch: (type, id, title) => dispatch(typeSearch(type, id, title)),
    };
};
export default withRouter(connect(
    mapStateToProps,
    mapDispatchToProps
)(DeezerMain));

