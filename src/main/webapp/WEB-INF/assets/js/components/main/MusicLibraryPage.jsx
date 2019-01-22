import React, {Component} from "react"
import "./css/music-library.scss";
import {connect} from "react-redux";
import PlaylistsList from "./PlaylistsList.jsx";
import {bindActionCreators} from "redux";
import {getFavouritePlaylists, typeSearch, setIsMusicLibrary, getPagePlaylist} from "../../store/actions/main.js";
import {withRouter} from "react-router-dom";
import TrackHeader from "./TrackHeader.jsx";
import TrackRow from "./TrackRow.jsx";

class MusicLibraryPage extends Component {
    componentWillMount() {
        this.props.getFavouritePlaylists();
        this.props.setIsMusicLibrary(true);
        const id = this.props.favouriteTracksPlId;
        if (id) {
            this.props.getPagePlaylist('playlist', id)
        }
    }

    componentDidMount() {
        $(document).foundation();
    }

    render() {
        const {
            userPlaylists, favouritePlaylists, typeSearch, likePlaylist,
            pagePlaylist, playing, track, currentTime, duration,
            handleLike, playTrack
        } = this.props;
        const privatePl = userPlaylists.filter(playlist => playlist.access === 'PRIVATE' && playlist.title !== 'Favourites');
        const publicPl = userPlaylists.filter(playlist => playlist.access === 'PUBLIC');
        return (
            <section>
                <div className="user-library">
                    <ul className="tabs" data-active-collapse="true" data-tabs id="collapsing-tabs">
                        <li className="tabs-title is-active"><a href="#privatePl" aria-selected="true">Private</a>
                        </li>
                        <li className="tabs-title"><a href="#publicPl">Public</a></li>
                        <li className="tabs-title"><a href="#favouritesPl">Playlists you liked</a></li>
                        <li className="tabs-title"><a
                            href="#favouriteTracksPl">Favourite Tracks</a></li>
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
                                <PlaylistsList playlists={publicPl} play={typeSearch} like={likePlaylist} showlike/> :
                                <p className="empty-message">Nothing to show. Create you first public playlist and it
                                    will be here.</p>}
                        </div>
                        <div className=" tabs-panel grid-x grid-padding-x small-up-3 medium-up-5 large-up-6"
                             id="favouritesPl">
                            {favouritePlaylists.length > 0 ?
                                <PlaylistsList playlists={favouritePlaylists} play={typeSearch} like={likePlaylist}
                                               showlike/> :
                                <p className="empty-message">Nothing to show. Click 'like' on any playlist and it will
                                    be here.</p>
                            }

                        </div>
                        <div className=" tabs-panel grid-x grid-padding-x small-up-3 medium-up-5 large-up-6"
                             id="favouriteTracksPl">
                            {pagePlaylist.length > 0 ?
                                <table className="hover unstriped">
                                    <TrackHeader/>
                                    <tbody id="songsBody">
                                    {pagePlaylist.map(rowTrack => <TrackRow key={rowTrack.id} track={rowTrack}
                                                                            currentTrack={track}
                                                                            handleLike={handleLike}
                                                                            playTrack={playTrack}
                                                                            playlist={pagePlaylist}
                                                                            playlistTitle={"Favourites"}
                                                                            playing={playing}
                                                                            currentTime={currentTime}
                                                                            duration={duration}/>)}
                                    </tbody>
                                </table>
                                :
                                <p className="empty-message">Nothing to show. Click 'like' on any song and it will
                                    be here.</p>
                            }

                        </div>
                    </div>
                </div>
            </section>
        )
    }

    componentWillUnmount() {
        this.props.setIsMusicLibrary(false);
    }

    getFavouriteTracks(playlist) {
        console.log("pl", playlist);
        playlist && this.props.getPagePlaylist('playlist', playlist.id);
    }
}

const mapStateToProps = state => {
    return {
        userPlaylists: state.rootReducer.userPlaylists,
        favouritePlaylists: state.rootReducer.favouritePlaylists,
        favouriteTracksPlId: state.rootReducer.favouriteTracksPlId,
        pagePlaylist: state.rootReducer.pagePlaylist,
        playing: state.trackReducer.playing,
        track: state.trackReducer.track,
        currentTime: state.trackReducer.currentTime,
        duration: state.trackReducer.duration
    }
        ;
};

const mapDispatchToProps = dispatch => {
    return {
        getFavouritePlaylists: bindActionCreators(getFavouritePlaylists, dispatch),
        typeSearch: (type, id, title) => dispatch(typeSearch(type, id, title)),
        setIsMusicLibrary: (isMusicLibrary) => dispatch(setIsMusicLibrary(isMusicLibrary)),
        getPagePlaylist: (type, id) => dispatch(getPagePlaylist(type, id))
    };
};
export default withRouter(connect(
    mapStateToProps,
    mapDispatchToProps
)(MusicLibraryPage));

