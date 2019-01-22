import React, {Component} from "react";
import './css/index.scss';
import {connect} from "react-redux";
import MiniPlaylist from "./MiniPlaylist.jsx";
import {getUserPlaylists, createPlaylist, addTrackToPlaylist} from "../../store/actions/main.js";
import {bindActionCreators} from "redux";
import AddToPlaylist from "./AddToPlaylist.jsx";
import Lyrics from "./Lyrics.jsx";
import {
    setTrackIndex,
    setTrack,
    setPlaying,
    setAudio,
    setTrackTime,
    setTrackDuration
} from "../../store/actions/track-actions.js";
import {toArray} from "../main/MainPage.jsx";
import {Link} from "react-router-dom";


class Player extends Component {
    state = {
        progress: 0,
        volume: 0.8,
        prevVolume: 0.8,
        showMiniPlaylist: false,
        showVolume: false
    };

    constructor(props) {
        super(props);
        this.progress = React.createRef();
    }

    componentDidMount() {
        this.audio = document.getElementById('audio');
        this.props.setAudio(this.audio);
        this.audio.volume = this.state.volume;
        this.props.getUserPlaylists();
        $(document).foundation();
    }

    render() {
        const {progress, volume, showMiniPlaylist} = this.state;
        const {currentTime, duration, playing, track, currentPlaylistTitle, currentPlaylist, userPlaylists, createPlaylist, addTrackToPlaylist, handleLike, playTrack, setPlaying} = this.props;
        return (
            <footer>
                <audio preload="metadata" id="audio" controls="controls" src={track ? track.url : ""}
                       onTimeUpdate={() => this.handleTimeUpdate()}
                       onLoadedMetadata={() => this.handleLoadedTrack()}
                       onPause={() => setPlaying(false)}
                       onPlay={() => setPlaying(true)}
                       onEnded={() => this.handleEndedTrack()}
                       autoPlay
                />
                <div className={"ap" + (track ? " active" : "")} id="ap">
                    <div className="ap__item ap__item--track">
                        <div className="progress-container">
                            <div className="progress" onClick={(e) => this.handleProgressClick(e)} ref={this.progress}>
                                <div className="progress__bar" style={{width: `${progress}%`}}/>
                            </div>
                        </div>
                        <div className="track">
                            <div className="track__time">
                                <span
                                    className="track__time--current">{currentTime}</span>
                                <span> / </span>
                                <span
                                    className="track__time--duration">{duration}</span>
                            </div>
                        </div>
                    </div>
                    <div className="ap__inner">
                        <div className="ap__item ap__item--title">
                            <div className="flex">
                                {track && <img className="track__picture" src={track ? track.picture : ""} alt=""/>}
                                <div className="track__info">
                                    <p className="track__title">{track ? track.title : ""}</p>
                                    {track && <p className="album-artist__title">
                                        <Link to={`/album/${track.album.id}`}
                                              className="underline-link">{track.album.title}
                                        </Link> - <Link to={`/artist/${track.artist.id}`}
                                                        className="underline-link">{track.artist.name}</Link></p>}
                                </div>
                            </div>
                            <div className="flex">
                                <button className="ap__controls ap__controls--playlist" id="addLike"
                                        onClick={() => handleLike(track)}>
                                    {track ? !track.liked && <i className="far fa-heart top main-like"
                                                                data-tooltip aria-haspopup="true" tabIndex="1"
                                                                title="Add to favourite tracks"/> : ""}
                                    {track ? track.liked && <i className="fas fa-heart top main-dislike"
                                                               data-tooltip aria-haspopup="true" tabIndex="1"
                                                               title="Remove from favourite tracks"/> : ""}
                                </button>
                                <button className="ap__controls ap__controls--playlist main-add-to-playlist"
                                        data-toggle="addToPlaylistMenu">
                                    <i className="fas fa-plus top has-tip"
                                       data-tooltip aria-haspopup="true" tabIndex="1"
                                       title="Add to playlist"/>
                                </button>
                            </div>
                        </div>
                        <div className="ap__item ap__item--playback">
                            <button className="ap__controls ap__controls--prev" id="btnPrev"
                                    onClick={() => this.playPrevTrack()}>
                                <i className="fas fa-step-backward  top "
                                   data-tooltip aria-haspopup="true" tabIndex="5"
                                   title="previous track"/>
                            </button>
                            <button className="ap__controls ap__controls--toggle btnPlay"
                                    onClick={() => playTrack(track)}>
                                {!playing && <i className="fas fa-play main-play top has-tip"
                                                data-tooltip aria-haspopup="true" title="play"/>}
                                {playing && <i className="fas fa-pause main-pause"/>}
                            </button>
                            <button className="ap__controls ap__controls--next" id="btnNext"
                                    onClick={() => this.playNextTrack()}>
                                <i className="fas fa-step-forward top has-tip"
                                   data-tooltip aria-haspopup="true" tabIndex="1" title="next track"/>
                            </button>
                        </div>

                        <div className="ap__item ap__item--settings">
                            <div className="track__lyrics" data-toggle="lyricsPane">
                                {track &&
                                <img src="/assets/img/lyrics.svg" alt="" data-tooltip aria-haspopup="true" tabIndex="1"
                                     title="Lyrics"
                                     className="lyrics-icon"/>}
                            </div>
                            <div className="ap__controls volume-container">
                                <button className="volume-btn" onDoubleClick={() => this.handleVolumeBtn()}
                                        onClick={() => this.showVolume()}>
                                    {volume !== 0 && <i className="fas fa-volume-up icon-volume-on"/>}
                                    {volume === 0 && <i className="fas fa-volume-off icon-volume-off"/>}
                                </button>
                                <div className={"volume " + this.isVolumeActive()}
                                     onClick={(event) => this.handleVolume(event)}>
                                    <div className="volume__track">
                                        <div className="volume__bar" style={{height: volume * 100}}/>
                                    </div>
                                </div>
                            </div>
                            <button className="ap__controls ap__controls--playlist queue" id="showPlaylist"
                                    onClick={() => this.handleMiniPlaylist()}>
                                <div className="queue-button">
                                    {currentPlaylist[0] ?
                                        <img className="queue-img" src={currentPlaylist[0].picture} alt=""
                                             style={{boxShadow: "0 1px 6px rgba(25,25,34,.16)"}}/> :
                                        <img className="queue-img" src="/assets/img/equalizer.png" alt=""/>}
                                    <div
                                        className="queue-title">{currentPlaylistTitle ? currentPlaylistTitle : 'Queue'}  </div>
                                </div>
                            </button>

                        </div>
                    </div>
                </div>
                {showMiniPlaylist &&
                <MiniPlaylist currentTrack={track} playlist={toArray(currentPlaylist)}
                              title={currentPlaylistTitle}
                              playing={playing}
                              currentTime={currentTime}
                              duration={duration}
                              playTrack={playTrack}
                              handleLike={handleLike}/>}
                <AddToPlaylist playlists={userPlaylists} track={track}
                               createPlaylist={createPlaylist} addTrackToPlaylist={addTrackToPlaylist}/>
                <Lyrics lyrics={track ? track.lyrics : ""}/>
            </footer>

        )
            ;

    }

    getMiniPlaylistButtonColor = () => this.state.showMiniPlaylist ? {color: '#ff5722'} : {color: 'black'};

    handleMiniPlaylist() {
        const isShown = this.state.showMiniPlaylist;
        this.setState({showMiniPlaylist: !isShown});
    }


    handleProgressClick({pageX}) {
        if (!this.props.track) return;
        const progressWidth = this.progress.current.offsetWidth;
        const percent = (pageX / progressWidth);
        console.log(percent);
        const currentTime = this.audio.duration * percent;
        this.audio.currentTime = currentTime;
        console.log("time", currentTime);
        this.props.setTrackTime(this.getTrackTime(currentTime));
    }

    showVolume() {
        const isVolumeOpen = this.state.showVolume;
        this.setState({showVolume: !isVolumeOpen})
    }

    isVolumeActive() {
        return this.state.showVolume ? "active" : "";
    }

    handleVolumeBtn() {
        const volume = this.state.volume;
        if (volume > 0) {
            this.setState({volume: 0, prevVolume: volume});
            this.audio.volume = 0;
        } else {
            let prevVolume = this.state.prevVolume;
            this.setState({volume: prevVolume});
            this.audio.volume = prevVolume;
        }
    }

    handleVolume({pageY}) {
        let currentVolume = this.state.volume * 100;
        let y = currentVolume - (pageY - document.getElementsByClassName('volume__bar')[0].getBoundingClientRect().top);
        currentVolume = y > 100 ? 100 : (y < 0 ? 0 : y);
        this.setVolume(currentVolume / 100);
    }

    setVolume(currentVolume) {
        this.setState({volume: currentVolume});
        if (this.props.currentPlaylist) {
            this.audio.volume = currentVolume;
        }
    }


    handleTimeUpdate() {
        const duration = this.audio.duration;
        const current = this.audio.currentTime;
        let percent = (current / duration) * 100;
        this.props.setTrackTime(this.getTrackTime(this.audio.currentTime));
        this.setState({progress: percent});
    }

    handleLoadedTrack() {
        this.props.setTrackDuration(this.getTrackTime(this.audio.duration));
    }

    handleEndedTrack() {
        this.playNextTrack();
    }

    getTrackTime(time) {
        if (!time)
            return '0:00';
        let min = Math.floor(time / 60);
        let sec = Math.floor(time - min * 60);
        (sec < 10) && (sec = '0' + sec);
        return `${min}:${sec}`;
    }


    playNextTrack() {
        this.audio.pause();
        const playlist = this.props.currentPlaylist;
        const trackCount = playlist.length;
        let index = this.props.trackIndex;
        if ((index + 1) < trackCount) {
            index++;
        } else {
            index = 0;
        }
        const track = playlist[index];
        console.log("next track", track);
        this.props.setTrack(track);
        this.props.setTrackIndex(index);
    }

    playPrevTrack() {
        this.audio.pause();
        let index = this.props.trackIndex;
        if ((index - 1) > -1) {
            index--;
        } else {
            index = 0;
        }
        const track = this.props.currentPlaylist[index];
        console.log("prev track", track);
        this.props.setTrack(track);
        this.props.setTrackIndex(index);
    }

}

const mapStateToProps = state => {
    return {
        currentPlaylist: state.rootReducer.currentPlaylist,
        currentPlaylistTitle: state.rootReducer.currentPlaylistTitle,
        userPlaylists: state.rootReducer.userPlaylists,
        trackIndex: state.trackReducer.trackIndex,
        track: state.trackReducer.track,
        playing: state.trackReducer.playing,
        currentTime: state.trackReducer.currentTime,
        duration: state.trackReducer.duration
    };
};

const mapDispatchToProps = dispatch => {
    return {
        getUserPlaylists: bindActionCreators(getUserPlaylists, dispatch),
        createPlaylist: (playlistName, accessModifier, trackId) => dispatch(createPlaylist(playlistName, accessModifier, trackId)),
        addTrackToPlaylist: (playlistId, trackId) => dispatch(addTrackToPlaylist(playlistId, trackId)),
        setTrackIndex: (idx) => dispatch(setTrackIndex(idx)),
        setTrack: (track) => dispatch(setTrack(track)),
        setPlaying: (playing) => dispatch(setPlaying(playing)),
        setAudio: (audio) => dispatch(setAudio(audio)),
        setTrackTime: (time) => dispatch(setTrackTime(time)),
        setTrackDuration: (duration) => dispatch(setTrackDuration(duration))
    };
};
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(Player);
