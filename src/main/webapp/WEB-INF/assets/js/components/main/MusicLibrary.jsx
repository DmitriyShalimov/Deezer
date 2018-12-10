import React from "react";
import {Link} from "react-router-dom";

const MusicLibrary = () => {
    return (
        <aside className="right">
            <Link to={"/music-library"} className="music-library">
                <i className="fas fa-clone"/>
                <p className="music-library-title">Music Library</p>
            </Link>
            <Link to={"/all-playlists"} className="all-playlists">
                <i className="fas fa-music"/>
                <p className="music-library-title">All Playlists</p>
            </Link>
        </aside>
    )

};

export default MusicLibrary;