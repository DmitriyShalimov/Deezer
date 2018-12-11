import React, {Component} from "react"
import {Link} from "react-router-dom";
import Slider from "react-slick";
import {getItemRectangleWidth, getSettings} from "./util/slider-settings";

class GenresList extends Component {

    render() {
        const {genres, play} = this.props;
        const width = getItemRectangleWidth((window.innerWidth > 0) ? window.innerWidth : screen.width);
        const settings = getSettings(genres.length, 4);
        return (
            <Slider {...settings} variableWidth={false}>
                {genres.map(genre => <div className="card card-playlist-hover genre slide" key={genre.id}
                                          style={{width: width}}>
                    <Link to={`/genre/${genre.id}`}>
                        <img src={genre.picture}
                             alt="genre photo"/>
                    </Link>
                    <div className="card-playlist-hover-icons">
                        <button onClick={() => play("genre", genre.id)}><i className="fas fa-play"/>
                        </button>
                    </div>
                    <div className="card-playlist-hover-details">
                        <p className="card-playlist-hover-title" style={{fontSize: '1vw'}}>{genre.title}</p>
                    </div>
                </div>)}
            </Slider>
        );
    }
}


export default GenresList;