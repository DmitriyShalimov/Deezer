import React, {Component} from "react";
import EmptyQueue from "./EmptyQueue.jsx";
import TrackRow from "../main/TrackRow.jsx";
import TrackHeader from "../main/TrackHeader.jsx";

class MiniPlaylist extends Component {
    render() {
        const {playlist, title, playTrack, handleLike, currentTrack, currentTime, duration, playing} = this.props;
        return (
            <section className="playlist__section">
                {playlist.length > 0 ?
                    <React.Fragment>
                        <div className="pl-img">
                            <img alt="" src={playlist[0].picture}/>
                        </div>
                        <div className="pl-info">
                            <p className="pl-title">Current playlist: <span>{title}</span></p>
                        </div>
                        <div className="mini-playlist">
                            <table className="hover unstriped">
                                <TrackHeader/>
                                <tbody id="playlistBody">
                                {playlist.map(track =>
                                    <TrackRow key={track.id} track={track} playTrack={playTrack} handleLike={handleLike}
                                              currentTrack={currentTrack} currentTime={currentTime}
                                              duration={duration} playing={playing}/>
                                )}
                                </tbody>
                            </table>
                        </div>
                    </React.Fragment>
                    : <EmptyQueue/>}
            </section>
        );
    }

}

export default MiniPlaylist;
