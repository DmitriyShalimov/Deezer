import React, {Component} from "react"
import "./css/music-library.scss";
import {connect} from "react-redux";
import PlaylistsList from "./PlaylistsList.jsx";
import {bindActionCreators} from "redux";
import {getFavouritePlaylists,typeSearch} from "../../store/actions/main.js";
import {withRouter} from "react-router-dom";

class MusicLibraryPage extends Component {
    componentWillMount() {
        this.props.getFavouritePlaylists();
    }

    componentDidMount() {
        $(document).foundation();
    }

    render() {
        const {userPlaylists, favouritePlaylists, typeSearch, likePlaylist} = this.props;
        console.log(userPlaylists);
        const privatePl = userPlaylists.filter(playlist => playlist.access==='PRIVATE');
        const publicPl = userPlaylists.filter(playlist => playlist.access==='PUBLIC');
        return (
            <section>
                <div className="user-library">
                    <ul className="tabs" data-active-collapse="true" data-tabs id="collapsing-tabs">
                        <li className="tabs-title is-active"><a href="#privatePl" aria-selected="true">Private</a>
                        </li>
                        <li className="tabs-title"><a href="#publicPl">Public</a></li>
                        <li className="tabs-title"><a href="#favouritesPl">Playlists you liked</a></li>
                    </ul>
                    <div className="tabs-content artist-page" data-tabs-content="collapsing-tabs">
                        <div
                            className=" tabs-panel is-active grid-x grid-padding-x small-up-3 medium-up-5 large-up-6"
                            id="privatePl">
                            {privatePl.length > 0 ?
                                <PlaylistsList playlists={privatePl} play={typeSearch}/> :
                                <p className="empty-message">Nothing to show. Create you first private playlist and it
                                    will be here.</p>}
                        </div>
                        <div className="tabs-panel grid-x grid-padding-x small-up-3 medium-up-5 large-up-6"
                             id="publicPl">
                            {publicPl.length > 0 ?
                                <PlaylistsList playlists={publicPl} play={typeSearch}  like={likePlaylist} showlike/> :
                                <p className="empty-message">Nothing to show. Create you first public playlist and it
                                    will be here.</p>}
                        </div>
                        <div className=" tabs-panel grid-x grid-padding-x small-up-3 medium-up-5 large-up-6"
                             id="favouritesPl">
                            {favouritePlaylists.length > 0 ?
                                <PlaylistsList playlists={favouritePlaylists} play={typeSearch}  like={likePlaylist} showlike/> :
                                <p className="empty-message">Nothing to show. Click 'like' on any playlist and it will
                                    be here.</p>
                            }

                        </div>
                    </div>
                </div>
            </section>
        )
    }
}

const mapStateToProps = state => {
    return {
        userPlaylists: state.rootReducer.userPlaylists,
        favouritePlaylists: state.rootReducer.favouritePlaylists
    };
};

const mapDispatchToProps = dispatch => {
    return {
        getFavouritePlaylists: bindActionCreators(getFavouritePlaylists, dispatch),
        typeSearch: (type, id, title) => dispatch(typeSearch(type, id, title)),
    };
};
export default withRouter(connect(
    mapStateToProps,
    mapDispatchToProps
)(MusicLibraryPage));

