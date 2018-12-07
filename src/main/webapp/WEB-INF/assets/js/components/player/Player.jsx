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


//TODO:split!!, remove autoplay, mouse drag on progress and volume
class Player extends Component {
    state = {
        progress: 0,
        volume: 0.8,
        prevVolume: 0.8,
        showMiniPlaylist: false
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
                                <div className="progress__preload"/>
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
                            {track && <img className="track__picture" src={track ? track.picture : ""} alt=""/>}
                            <div className="track__info">
                                <p className="track__title">{track ? track.title : ""}</p>
                                <p className="album-artist__title">{track ? `${track.album.title} - ${track.artist.name}` : ""}</p>
                            </div>
                            <div className="track__lyrics" data-toggle="lyricsPane">
                                {track && <i className="fas fa-microphone"
                                             data-tooltip aria-haspopup="true" tabIndex="1"
                                             title="Lyrics"/>}
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
                            <button className="ap__controls ap__controls--playlist" id="addLike"
                                    onClick={() => handleLike(track)}>
                                {track ? !track.liked && <i className="far fa-heart top main-like"
                                                            data-tooltip aria-haspopup="true" tabIndex="1"
                                                            title="Add to favourites"/> : ""}
                                {track ? track.liked && <i className="fas fa-heart top main-dislike"
                                                           data-tooltip aria-haspopup="true" tabIndex="1"
                                                           title="Cancel like"/> : ""}
                            </button>
                            <button className="ap__controls ap__controls--playlist main-add-to-playlist"
                                    data-toggle="addToPlaylistMenu">
                                <i className="fas fa-plus top has-tip"
                                   data-tooltip aria-haspopup="true" tabIndex="1"
                                   title="Add to playlist"/>
                            </button>
                            <div className="ap__controls volume-container">
                                <button className="volume-btn" onClick={() => this.handleVolumeBtn()}>
                                    {volume !== 0 && <i className="fas fa-volume-up icon-volume-on"/>}
                                    {volume === 0 && <i className="fas fa-volume-off icon-volume-off"/>}
                                </button>
                                <div className="volume" onClick={(event) => this.handleVolume(event)}>
                                    <div className="volume__track">
                                        <div className="volume__bar" style={{height: volume * 100}}/>
                                    </div>
                                </div>
                            </div>
                            <button className="ap__controls ap__controls--playlist" id="showPlaylist"
                                    onClick={() => this.handleMiniPlaylist()}>
                                <i className="fas fa-music top has-tip"
                                   data-tooltip aria-haspopup="true" tabIndex="1"
                                   title="Current queue" style={this.getMiniPlaylistButtonColor()}/>
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
