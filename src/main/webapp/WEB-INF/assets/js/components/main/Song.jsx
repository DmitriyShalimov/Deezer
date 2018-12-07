import React, {Component} from "react"
import TrackRow from "./TrackRow.jsx";
import TrackHeader from "./TrackHeader.jsx";
import {connect} from "react-redux";
import {getPagePlaylistMeta, getPagePlaylist} from "../../store/actions/main.js";
import {Link} from "react-router-dom";

class Song extends Component {
    componentWillMount() {
        this.props.getPagePlaylist("", this.props.params.id);
    }

    render() {
        const {pagePlaylist, handleLike, playTrack, playing, track, currentTime, duration} = this.props;
        console.log(pagePlaylist);
        return (
            <React.Fragment>
                {!Array.isArray(pagePlaylist) &&
                <section>

                    <div className="playlist__header card ${item.type}">
                        <div className="playlist__picture">
                            <img src={pagePlaylist.picture}/>
                        </div>
                        <div className="playlist__details">
                            <h4 className="playlist__title">{pagePlaylist.title}</h4>
                            {pagePlaylist.artist &&
                            <p className="playlist__subtitle">
                                <span>from </span>
                                <Link to={`/album/${pagePlaylist.album.id}`} className="underline-link">{pagePlaylist.album.title}</Link>
                                <span> by </span>
                                <Link to={`/artist/${pagePlaylist.artist.id}`} className="underline-link">{pagePlaylist.artist.name}</Link></p>}
                        </div>
                    </div>
                    <div className="songs-playlist">
                        <table className="hover unstriped">
                            <TrackHeader/>
                            <tbody id="songsBody">
                            <TrackRow track={pagePlaylist}
                                      currentTrack={track}
                                      handleLike={handleLike} playTrack={playTrack}
                                      playlist={[pagePlaylist]}
                                      playlistTitle={pagePlaylist.title}
                                      playing={playing} currentTime={currentTime}
                                      duration={duration}/>
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
        getPagePlaylist: (type, id) => dispatch(getPagePlaylist(type, id))
    };
};
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(Song);

