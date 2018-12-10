import React, {Component} from "react";

import "./css/index.scss";
import "./css/playlist.scss";
import Player from "../player/Player.jsx";
import Header from "../header/Header.jsx";
import {connect} from "react-redux";
import {redirect, likeTrack, setCurrentPlaylist, getRandom, likePlaylist} from "../../store/actions/main.js";
import {validateToken, logout} from "../../store/actions/login.js";
import {setTrack, setTrackIndex} from "../../store/actions/track-actions.js";
import {bindActionCreators} from "redux";
import {Route, withRouter} from "react-router-dom";
import Genre from "./Genre.jsx";
import Album from "./Album.jsx";
import Song from "./Song.jsx";
import Artist from "./Artist.jsx";
import SearchPage from "./SearchPage.jsx";
import Playlist from "./Playlist.jsx";
import NotFound from "./NotFound.jsx";
import RandomSong from "./RandomSong.jsx";
import MusicLibrary from "./MusicLibrary.jsx";
import MusicLibraryPage from "./MusicLibraryPage.jsx";
import DeezerMain from "./DeezerMain.jsx";
import AllPlaylists from "./AllPlaylists.jsx";

//TODO:style 404
class MainPage extends Component {
    componentWillMount() {
        const token = localStorage.getItem("user-token");
        if (token && !this.props.isAuth) {
            let redirectOnSuccess = window.location.pathname;
            if (redirectOnSuccess === '/login' && redirectOnSuccess === '/registration') {
                redirectOnSuccess = '/';
            }
            console.log(redirectOnSuccess);
            this.props.validateToken(token, redirectOnSuccess);
        } else if (!this.props.isAuth) {
            this.props.redirect("/login");
        }
    }

    render() {
        const {isAuth, logout, notFound, getRandom} = this.props;
        if (isAuth) {
            return (
                <React.Fragment>
                    <Header logout={logout}/>
                    <main>
                        <div className="grid-container">
                            <RandomSong playRandomSong={getRandom}/>
                            <MusicLibrary/>
                            {notFound ? <NotFound/> :
                                <React.Fragment>
                                    <Route exact path="/genre/:id(\d+)"
                                           render={props => <Genre {...props.match}
                                                                   handleLike={this.handleLike.bind(this)}
                                                                   playTrack={this.playTrack.bind(this)}/>}/>
                                    < Route exact path="/album/:id(\d+)"
                                            render={props => <Album {...props.match}
                                                                    handleLike={this.handleLike.bind(this)}
                                                                    playTrack={this.playTrack.bind(this)}/>}/>
                                    <Route exact path="/song/:id(\d+)"
                                           render={props => <Song {...props.match}
                                                                  handleLike={this.handleLike.bind(this)}
                                                                  playTrack={this.playTrack.bind(this)}/>}/>
                                    <Route exact path="/artist/:id(\d+)"
                                           render={props => <Artist {...props.match}
                                                                    handleLike={this.handleLike.bind(this)}
                                                                    playTrack={this.playTrack.bind(this)}/>}/>
                                    <Route exact path="/search/:mask"
                                           render={props => <SearchPage {...props.match} key={this.props.location.key}
                                                                        handleLike={this.handleLike.bind(this)}
                                                                        playTrack={this.playTrack.bind(this)}/>}/>
                                    <Route exact path="/playlist/:id(\d+)"
                                           render={props => <Playlist {...props.match}
                                                                      handleLike={this.handleLike.bind(this)}
                                                                      playTrack={this.playTrack.bind(this)}/>}/>
                                    <Route exact path="/music-library"
                                           render={() => <MusicLibraryPage key={this.props.location.key}
                                               likePlaylist={this.likePlaylist.bind(this)}/>}/>
                                    <Route exact path="/all-playlists"
                                           render={() => <AllPlaylists key={this.props.location.key}
                                                                           likePlaylist={this.likePlaylist.bind(this)}/>}/>
                                    <Route exact path="/"
                                           render={() => <DeezerMain key={this.props.location.key} likePlaylist={this.likePlaylist.bind(this)}/>}/>
                                </React.Fragment>
                            }
                        </div>
                    </main>
                    <Player handleLike={this.handleLike.bind(this)} playTrack={this.playTrack.bind(this)}/>
                </React.Fragment>
            )
        } else {
            return null;
        }
    };

    playTrack(trackToPlay, playlist, title) {
        if (!trackToPlay) return;
        const {track, setTrack, setTrackIndex, playing, audio, setCurrentPlaylist} = this.props;
        let {currentPlaylist} = this.props;
        if (playlist && playlist !== currentPlaylist) {
            setCurrentPlaylist(playlist, title);
            currentPlaylist = playlist;
        }
        if (trackToPlay !== track) {
            setTrack(trackToPlay);
            setTrackIndex(currentPlaylist.indexOf(trackToPlay));
        }
        if (playing) {
            audio.pause();
        } else {
            audio.play();
        }
    }


    handleLike(track) {
        let {likeTrack, currentPlaylist, pagePlaylist} = this.props;
        likeTrack(track.id);
        currentPlaylist = toArray(currentPlaylist);
        const like = !track.liked;
        if (currentPlaylist.length > 0) {
            this.setLike(currentPlaylist, track, like);
        }
        toArray(pagePlaylist);
        if (pagePlaylist.length > 0) {
            this.setLike(pagePlaylist, track, like);
        }
        this.forceUpdate();
    }

    setLike(playlist, track, like) {
        let trackInPlaylist = playlist.filter(tr => tr.id === track.id)[0];
        if (trackInPlaylist) {
            trackInPlaylist.liked = like;
        }
    }

    likePlaylist(playlist) {
        this.props.likePlaylist(playlist.id);
        playlist.liked = !playlist.liked;
        playlist.liked ? playlist.likeCount++ : playlist.likeCount--;
        this.forceUpdate();
    }
}

export const toArray = (playlist) => {
    if (!Array.isArray(playlist)) {
        playlist = [playlist];
    }
    return playlist;
};

const mapStateToProps = state => {
    return {
        isAuth: state.rootReducer.isAuth,
        notFound: state.rootReducer.notFound,
        currentPlaylist: state.rootReducer.currentPlaylist,
        pagePlaylist: state.rootReducer.pagePlaylist,
        track: state.trackReducer.track,
        playing: state.trackReducer.playing,
        audio: state.trackReducer.audio
    };
};

const mapDispatchToProps = dispatch => {
    return {
        redirect: (redirectUrl) =>
            dispatch(redirect(redirectUrl)),
        validateToken: (token, redirectOnSuccess) => dispatch(validateToken(token, redirectOnSuccess)),
        logout: bindActionCreators(logout, dispatch),
        likeTrack: (id) => dispatch(likeTrack(id)),
        setTrackIndex: (idx) => dispatch(setTrackIndex(idx)),
        setTrack: (track) => dispatch(setTrack(track)),
        setCurrentPlaylist: (playlist, title) => dispatch(setCurrentPlaylist(playlist, title)),
        getRandom: bindActionCreators(getRandom, dispatch),
        likePlaylist: (playlist) => dispatch(likePlaylist(playlist))
    };
};
export default withRouter(connect(
    mapStateToProps,
    mapDispatchToProps
)(MainPage));


