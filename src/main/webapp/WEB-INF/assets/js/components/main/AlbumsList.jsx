import React, {Component} from "react"
import {Link} from "react-router-dom";

class AlbumsList extends Component {

    render() {
        const {albums, play} = this.props;
        console.log(albums);
        return (
            <div className="artists-playlists">
                <h3>Albums</h3>
                <div className="outline-pagination">
                    <div className="underline"/>
                    <div id="pagination-container-artist"/>
                </div>
                <div className="grid-x grid-padding-x small-up-3 medium-up-5 large-up-6" id="album">
                    {albums.map(album => <div className="cell" key={album.id}>
                        <div className="card card-playlist-hover">
                            <Link to={`/album/${album.id}`}>
                                <img src={album.picture}
                                     alt="artist photo"/>
                            </Link>
                            <div className="card-playlist-hover-icons">
                                <button onClick={() => play("album", album.id)}><i className="fas fa-play"/>
                                </button>
                            </div>
                            <div className="card-playlist-hover-details">
                                <p className="card-playlist-hover-title">{album.title}</p>
                                <p className="card-playlist-hover-subtitle">
                                    <Link to={`/artist/${album.artist.id}`}
                                          className="underline-link">{album.artist.name}</Link>
                                </p>
                            </div>
                        </div>
                    </div>)}
                </div>
            </div>
        );
    }
}


export default AlbumsList;