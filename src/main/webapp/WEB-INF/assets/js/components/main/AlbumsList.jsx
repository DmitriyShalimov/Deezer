import React, {Component} from "react"
import {Link} from "react-router-dom";
import Slider from "react-slick";
import {getItemWidth, getSettings} from "./util/slider-settings";

class AlbumsList extends Component {

    render() {
        const {albums, play, hideArtist} = this.props;
        const width = getItemWidth((window.innerWidth > 0) ? window.innerWidth : screen.width);
        const settings = getSettings(albums.length);
        return (
            <div className="artists-playlists">
                <h3>Albums</h3>
                <div className="outline-pagination">
                    <div className="underline"/>
                </div>
                <Slider {...settings}>
                    {albums.map(album => <div className="card card-playlist-hover slide" key={album.id}
                                              style={{width: width}}>
                        <Link to={`/album/${album.id}`}>
                            <img src={album.picture}
                                 alt="artist photo"/>

                        <div className="card-playlist-hover-icons">
                            <button onClick={(e) => this.play("album", album.id, e)}><i className="fas fa-play"/>
                            </button>
                        </div>
                        <div className="card-playlist-hover-details" style={{flexDirection: 'column'}}>
                            <p className="card-playlist-hover-title" style={{fontSize: '1vw'}}>{album.title}</p>
                            {!hideArtist && <p className="card-playlist-hover-subtitle">
                                <Link to={`/artist/${album.artist.id}`}
                                      className="underline-link" style={{fontSize: '0.9vw'}}>{album.artist.name}</Link>
                            </p>}
                        </div>
                        </Link>
                    </div>)}
                </Slider>
            </div>
        );
    }
    play(type, id, event) {
        event.preventDefault();
        this.props.play(type, id);
    }
}


export default AlbumsList;