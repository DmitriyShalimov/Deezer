import React, {Component} from "react"
import {Link} from "react-router-dom";

class PlaylistsList extends Component {

    render() {
        const {playlists, showlike, isRectangular} = this.props;
        return (
            <React.Fragment>
                {playlists.map(playlist =>
                    <div className="cell" key={playlist.id}>
                        <div className={"card card-playlist-hover" + (isRectangular ? " rectangular" : "")}>
                            <Link to={`/playlist/${playlist.id}`}>
                                {isRectangular ?
                                    <div className="img-container">
                                        <img src={playlist.picture}
                                             alt="playlist logo"/>
                                        <img src={playlist.picture}
                                             alt="playlist logo" className="bw-image"/>
                                    </div>
                                    : <img src={playlist.picture}
                                           alt="playlist logo"/>}
                                <div className="card-playlist-hover-icons">
                                    <button onClick={(e) => this.play('playlist', playlist.id, playlist.title, e)}><i
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
                                        <i className="far fa-heart top pl-like"
                                           onClick={(e) => this.like(playlist, e)}/>}
                                        {playlist.liked &&
                                        <i className="fas fa-heart top pl-dislike"
                                           onClick={(e) => this.like(playlist, e)}/>}
                                        <div className="followers-count suggestion-subtitle">
                                <span
                                    className="like-count">{playlist.likeCount}</span> {this.getFollowerWord(playlist.likeCount)}
                                        </div>
                                    </div>

                                </React.Fragment>}
                            </Link>
                        </div>
                    </div>
                )}

            </React.Fragment>
        );
    }

    play(type, id, title, event) {
        event.preventDefault();
        this.props.play(type, id, title);
    }

    like(playlist, e) {
        e.preventDefault();
        this.props.like(playlist);
    }

    getFollowerWord = (likeCount) => likeCount === 1 ? 'follower' : 'followers';

}


export default PlaylistsList;