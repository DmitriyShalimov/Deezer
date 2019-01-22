import React, {Component} from "react"
import {Link} from "react-router-dom";
import {getItemRectangleWidth, getSettings} from "./util/slider-settings";
import Slider from "react-slick";

class GenreList extends Component {

    render() {
        const {genres} = this.props;
        const width = getItemRectangleWidth((window.innerWidth > 0) ? window.innerWidth : screen.width);
        const settings = getSettings(genres.length, 4);
        return (
            <Slider {...settings} variableWidth={false}>
                {genres.map(genre => <div className="card card-playlist-hover genre slide" key={genre.id}
                                          style={{width: width}}>
                    <Link to={`/genre/${genre.id}`}>
                        <img src={genre.picture}
                             alt="genre photo"/>

                        <div className="card-playlist-hover-icons">
                            <button onClick={(e) => this.play("genre", genre.id, genre.title, e)}><i
                                className="fas fa-play"/>
                            </button>
                        </div>
                        <div className="card-playlist-hover-details">
                            <p className="card-playlist-hover-title" style={{fontSize: '1vw'}}>{genre.title}</p>
                        </div>
                    </Link>
                </div>)}
            </Slider>
        );
    }

    play(type, id, title, event) {
        event.preventDefault();
        this.props.play(type, id, title);
    }


}


export default GenreList;