import React, {Component} from "react"
import {Link} from "react-router-dom";
import Slider from "react-slick";
import "./css/slick.scss";
import {getItemWidth, getSettings} from "./util/slider-settings";


class ArtistsList extends Component {
    render() {
        const {artists, play} = this.props;
        const width = getItemWidth((window.innerWidth > 0) ? window.innerWidth : screen.width);
        const settings = getSettings(artists.length);
        return (
            <div className="artists-playlists">
                <h3>Artists</h3>
                <div className="outline-pagination">
                    <div className="underline"/>
                </div>
                <Slider {...settings}>
                    {artists.map(artist => <div className="card card-playlist-hover artist slide" key={artist.id}
                                                style={{width: width}}>
                        <Link to={`/artist/${artist.id}`} className="playlist__picture" style={{width: '100%'}}>
                            <img src={artist.picture}
                                 alt="artist photo"/>

                            <div className="card-playlist-hover-icons">
                                <button onClick={(e) => this.play("artist", artist.id, e)}><i className="fas fa-play"/>
                                </button>
                            </div>
                            <div className="card-playlist-hover-details" style={{justifyContent: 'center'}}>
                                <p className="card-playlist-hover-title"
                                   style={{textAlign: 'center', fontSize: '1vw'}}>{artist.name}</p>
                            </div>
                        </Link>
                    </div>)}
                </Slider>
            </div>
        )
    }
    play(type, id, event) {
        event.preventDefault();
        this.props.play(type, id);
    }
}


export default ArtistsList;