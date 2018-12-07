import React, {Component} from "react";

class TrackRow extends Component {
    render() {
        const {track, playTrack, handleLike, currentTrack, playlistTitle, playlist} = this.props;
        return (
            <tr>
                <td className="btnPlay" onClick={() => playTrack(track, playlist, playlistTitle)}>
                    {this.renderPlayButton(track === currentTrack)}
                </td>
                <td>
                    <div className="search-pl-info"><p
                        className="pl-track__title">{track.title}</p>
                        <p className="pl-album-artist__title">{track.album.title} - {track.artist.name}</p>
                    </div>
                </td>
                <td>
                    {this.renderTrackTime(track === currentTrack)}
                </td>
                <td className="btnLike" onClick={() => handleLike(track)}>
                    {!track.liked && <i className="far fa-heart top like"/>}
                    {track.liked && <i className="fas fa-heart top dislike"/>}
                </td>
            </tr>
        )
    }

    renderTrackTime(isTrackCurrent) {
        const {currentTime, duration} = this.props;
        return (
            <div className="pl-track__time">
                <span>{isTrackCurrent ? currentTime : "0:00"}</span>
                <span> / </span>
                <span>{isTrackCurrent ? duration : "0:00"}</span>
            </div>
        )
    };

    renderPlayButton(isTrackCurrent) {
        if (isTrackCurrent && this.props.playing) {
            return <i className="fas fa-pause"/>
        } else {
            return <i className="fas fa-play"/>;
        }
    };
}

export default TrackRow;