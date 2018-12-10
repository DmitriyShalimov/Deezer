import React, {Component} from "react"
import {Link} from "react-router-dom";

class PlaylistsList extends Component {

    render() {
        const {playlists, showlike, play, like} = this.props;
        return (
            <React.Fragment>
                {playlists.map(playlist =>
                    <div className="cell" key={playlist.id}>
                        <div className="card card-playlist-hover">
                            <Link to={`/playlist/${playlist.id}`}>
                                <img src={playlist.picture}
                                     alt="playlist logo"/>
                            </Link>
                            <div className="card-playlist-hover-icons">
                                <button onClick={() => play('playlist', playlist.id, playlist.title)}><i
                                    className="fas fa-play"/>
                                </button>
                            </div>
                            <div className="card-playlist-hover-details">
                                <p className="card-playlist-hover-title">{playlist.title}</p>

                            </div>
                            {showlike &&
                            <React.Fragment>
                                <div className="card-playlist-like">
                                    {!playlist.liked &&
                                    <i className="far fa-heart top pl-like" onClick={() => like(playlist)}/>}
                                    {playlist.liked &&
                                    <i className="fas fa-heart top pl-dislike" onClick={() => like(playlist)}/>}
                                    <div className="followers-count suggestion-subtitle">
                                <span
                                    className="like-count">{playlist.likeCount}</span> {this.getFollowerWord(playlist.likeCount)}
                                    </div>
                                </div>

                            </React.Fragment>}
                        </div>
                    </div>
                )}

            </React.Fragment>
        );
    }

    getFollowerWord = (likeCount) => likeCount === 1 ? 'follower' : 'followers';

}


export default PlaylistsList;