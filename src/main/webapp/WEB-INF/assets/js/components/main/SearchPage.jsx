import React, {Component} from "react"
import TrackRow from "./TrackRow.jsx";
import TrackHeader from "./TrackHeader.jsx";
import {connect} from "react-redux";
import {getSearchResult, typeSearch} from "../../store/actions/main.js";
import ArtistsList from "./ArtistsList.jsx";
import AlbumsList from "./AlbumsList.jsx";
import {withRouter} from "react-router-dom";

class SearchPage extends Component {
    componentWillMount() {
        const mask = this.props.params.mask;
        this.props.getSearchResult("song", mask);
        this.props.getSearchResult("album", mask);
        this.props.getSearchResult("artist", mask);
    }

    render() {
        const {pagePlaylist, artists, albums, handleLike, playTrack, playing, track, currentTime, duration, typeSearch} = this.props;
        return (
            <React.Fragment>
                {artists.length > 0 && <ArtistsList artists={artists} play={typeSearch}/>}
                {albums.length > 0 && <AlbumsList albums={albums} play={typeSearch}/>}
                {pagePlaylist.length > 0 &&
                <section>
                    <div className="songs-playlist">
                        <table className="hover unstriped">
                            <TrackHeader/>
                            <tbody id="songsBody">
                            {pagePlaylist.map(rowTrack => <TrackRow key={rowTrack.id} track={rowTrack}
                                                                    currentTrack={track}
                                                                    handleLike={handleLike} playTrack={playTrack}
                                                                    playlist={pagePlaylist}
                                                                    playlistTitle={"Search"}
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
        pagePlaylist: state.rootReducer.pagePlaylist,
        playing: state.trackReducer.playing,
        track: state.trackReducer.track,
        currentTime: state.trackReducer.currentTime,
        duration: state.trackReducer.duration,
        artists: state.rootReducer.artists,
        albums: state.rootReducer.albums
    };
};

const mapDispatchToProps = dispatch => {
    return {
        getSearchResult: (type, mask) => dispatch(getSearchResult(type, mask)),
        typeSearch: (type, id) => dispatch(typeSearch(type, id))
    };
};
export default withRouter(connect(
    mapStateToProps,
    mapDispatchToProps
)(SearchPage));

