import React, {Component} from "react"
import TrackRow from "./TrackRow.jsx";
import TrackHeader from "./TrackHeader.jsx";
import {connect} from "react-redux";
import {getPagePlaylistMeta, getPagePlaylist, getAlbumsByArtist, typeSearch} from "../../store/actions/main.js";
import AlbumsList from "./AlbumsList.jsx";
import {withRouter} from "react-router-dom";

class Artist extends Component {
    componentWillMount() {
        this.props.getPagePlaylistMeta("artist", this.props.params.id);
        this.props.getPagePlaylist("artist", this.props.params.id);
        this.props.getAlbumsByArtist(this.props.params.id);
    }

    render() {
        const {pagePlaylistMeta, typeSearch,pagePlaylist, handleLike, playTrack, playing, track, currentTime, duration, albums} = this.props;
        return (
            <React.Fragment>
                {pagePlaylistMeta && Array.isArray(pagePlaylist) &&
                <section>

                    <div className="playlist__header card artist">
                        <div className="playlist__picture">
                            <img src={pagePlaylistMeta.picture}/>
                        </div>
                        <div className="playlist__details">
                            <h4 className="playlist__title">{pagePlaylistMeta.name}</h4>
                        </div>
                    </div>
                    <AlbumsList albums={albums} play={typeSearch} hideArtist/>
                    <div className="songs-playlist">
                        <table className="hover unstriped">
                            <TrackHeader/>
                            <tbody id="songsBody">
                            {pagePlaylist.map(rowTrack => <TrackRow key={rowTrack.id} track={rowTrack}
                                                                    currentTrack={track}
                                                                    handleLike={handleLike} playTrack={playTrack}
                                                                    playlist={pagePlaylist}
                                                                    playlistTitle={pagePlaylistMeta.name}
                                                                    playing={playing} currentTime={currentTime}
                                                                    duration={duration}/>)}
                            </tbody>
                        </table>
                    </div>
                </section>
                }
            </React.Fragment>
        )
    }
}

const mapStateToProps = state => {
    return {
        pagePlaylistMeta: state.rootReducer.pagePlaylistMeta,
        pagePlaylist: state.rootReducer.pagePlaylist,
        albums: state.rootReducer.albums,
        playing: state.trackReducer.playing,
        track: state.trackReducer.track,
        currentTime: state.trackReducer.currentTime,
        duration: state.trackReducer.duration
    };
};

const mapDispatchToProps = dispatch => {
    return {
        getPagePlaylistMeta: (type, id) =>
            dispatch(getPagePlaylistMeta(type, id)),
        getPagePlaylist: (type, id) => dispatch(getPagePlaylist(type, id)),
        typeSearch: (type, id) => dispatch(typeSearch(type, id)),
        getAlbumsByArtist: (id) => dispatch(getAlbumsByArtist(id))

    };
};
export default withRouter(connect(
    mapStateToProps,
    mapDispatchToProps
)(Artist));

