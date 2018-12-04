import React, {Component} from "react";
import './css/index.scss';
import {connect} from "react-redux";

//TODO:split!!, remove autoplay, mouse drag on process and volume
class Player extends Component {
    state = {
        track: null,
        playing: false,
        trackIndex: 0,
        currentTime: '0:00',
        duration: '0:00',
        progress: 0,
        volume: 0.8,
        prevVolume: 0.8
    };

    constructor(props) {
        super(props);
        this.progress = React.createRef();
    }

    componentDidMount() {
        this.audio = document.getElementById('audio');
        this.audio.volume = this.state.volume;
    }

    componentWillReceiveProps(props) {
        this.setState({track: this.getTrack(props.currentPlaylist)});
    };

    render() {
        const {track, playing, currentTime, duration, progress, volume} = this.state;
        return (
            <footer>
                <audio preload="metadata" id="audio" controls="controls" src={track ? track.url : ""}
                       onTimeUpdate={() => this.handleTimeUpdate()}
                       onLoadedMetadata={() => this.handleLoadedTrack()}
                       onPause={() => this.setState({playing: false})}
                       onPlay={() => this.setState({playing: true})}
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
                                    onClick={() => this.playTrack(track)}>
                                {!playing && <i className="fas fa-play main-play top has-tip"
                                                data-tooltip aria-haspopup="true" tabIndex="1" title="play"/>}
                                {playing && <i className="fas fa-pause main-pause"/>}
                            </button>
                            <button className="ap__controls ap__controls--next" id="btnNext"
                                    onClick={() => this.playNextTrack()}>
                                <i className="fas fa-step-forward top has-tip"
                                   data-tooltip aria-haspopup="true" tabIndex="1" title="next track"/>
                            </button>
                        </div>

                        <div className="ap__item ap__item--settings">
                            <button className="ap__controls ap__controls--playlist" id="addLike">
                                {track ? track.liked && <i className="far fa-heart top main-like"
                                                           data-tooltip aria-haspopup="true" tabIndex="1"
                                                           title="Add to favourites"/> : ""}
                                {track ? !track.liked && <i className="fas fa-heart top main-dislike"
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
                            <button className="ap__controls ap__controls--playlist" id="showPlaylist">
                                <i className="fas fa-music top has-tip"
                                   data-tooltip aria-haspopup="true" tabIndex="1"
                                   title="Current queue"/>
                            </button>

                        </div>
                    </div>
                </div>
            </footer>
        );

    }


    getTrack(currentPlaylist) {
        if (!currentPlaylist) return;
        if (!Array.isArray(currentPlaylist)) {
            currentPlaylist = [currentPlaylist];
        }
        if (currentPlaylist.length === 0) return;
        return currentPlaylist[this.state.trackIndex];
    }

    playTrack(track) {
        if (!track) return;
        if (this.state.playing) {
            this.audio.pause();
        } else {
            this.audio.play();
        }

    }

    handleProgressClick({pageX}) {
        if (!this.state.track) return;
        const progressWidth = this.progress.current.offsetWidth;
        const percent = (pageX / progressWidth);
        console.log(percent);
        const currentTime = this.audio.duration * percent;
        this.audio.currentTime = currentTime;
        console.log("time", currentTime);
        this.setState({currentTime: this.getTrackTime(currentTime)});
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
        this.setState({currentTime: this.getTrackTime(this.audio.currentTime), progress: percent})
    }

    handleLoadedTrack() {
        this.setState({duration: this.getTrackTime(this.audio.duration)});
    }

    handleEndedTrack(){
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
        let index = this.state.trackIndex;
        if ((index + 1) < trackCount) {
            index++;
        } else {
            index = 0;
        }
        const track = playlist[index];
        console.log("next track", track);
        this.setState({track: track, trackIndex: index});
    }

    playPrevTrack() {
        this.audio.pause();
        let index = this.state.trackIndex;
        if ((index - 1) > -1) {
            index--;
        } else {
            index = 0;
        }
        const track = this.props.currentPlaylist[index];
        console.log("prev track", track);
        this.setState({track: track, trackIndex: index});
    }

}

const mapStateToProps = state => {
    return {
        currentPlaylist: state.rootReducer.currentPlaylist
    };
};

// const mapDispatchToProps = dispatch => {
//     return {
//         getSearchOptions: bindActionCreators(getSearchOptions, dispatch),
//         typeSearch: (type, id) => dispatch(typeSearch(type, id))
//     };
// };
export default connect(
    mapStateToProps
)(Player);
