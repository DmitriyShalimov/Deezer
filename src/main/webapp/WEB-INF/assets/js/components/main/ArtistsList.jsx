import React, {Component} from "react"
import {Link} from "react-router-dom";

class ArtistsList extends Component {
    render() {
        const {artists, play} = this.props;
        return (
            <div className="artists-playlists">
                <h3>Artists</h3>
                <div className="outline-pagination">
                    <div className="underline"/>
                    <div id="pagination-container-artist"/>
                </div>
                <div className="grid-x grid-padding-x small-up-3 medium-up-5 large-up-6" id="artist">
                    {artists.map(artist => <div className="cell" key={artist.id}>
                        <div className="card card-playlist-hover">
                            <Link to={`/artist/${artist.id}`}>
                                <img src={artist.picture}
                                     alt="artist photo"/>
                            </Link>
                            <div className="card-playlist-hover-icons">
                                <button onClick={() => play("artist", artist.id)}><i className="fas fa-play"/></button>
                            </div>
                            <div className="card-playlist-hover-details">
                                <p className="card-playlist-hover-title">{artist.name}</p>
                            </div>
                        </div>
                    </div>)}
                </div>
            </div>
        );
    }
}


export default ArtistsList;