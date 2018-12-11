import React, {Component} from "react"
import {Link} from "react-router-dom";

class GenreList extends Component {
    componentDidMount() {}
    render() {
        const {genres,  play} = this.props;
        console.log("genres", genres);
        console.log("1111111", play);

        return (
            <React.Fragment>
                {genres.map(genre =>
                    <div className="cell" key={genre.id}>
                        <div className="card card-playlist-hover">
                            <Link to={`/genre/${genre.id}`}>
                                <img src={genre.picture}
                                     alt="genre logo"/>
                            </Link>
                            <div className="card-playlist-hover-icons">
                                <button onClick={() => play('genre', genre.id, genre.title)}><i
                                    className="fas fa-play"/>
                                </button>
                            </div>
                            <div className="card-playlist-hover-details">
                                <p className="card-playlist-hover-title">{genre.title}</p>

                            </div>

                        </div>
                    </div>
                )}

            </React.Fragment>
        );
    }



}


export default GenreList;