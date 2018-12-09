import React from "react";
import {Link} from "react-router-dom";

const MusicLibrary = () => {
    return (
        <aside className="music-library right">
            <Link to={"/music-library"} className="music-library">
                <i className="fas fa-clone"/>
                <p className="music-library-title">Music Library</p>
            </Link>
        </aside>
    )

};

export default MusicLibrary;