import React from "react";
import {Link} from "react-router-dom";

const MusicLibrary = (props) => {
    return (
        <aside className="right">
            <Link to={"/"} className={"music-library home " + isActive(props.isHome)}>
                <div className="aside-nav">
                    <i className="fas fa-home"/>
                    <p className="music-library-title">Home</p>
                </div>
            </Link>
            <Link to={"/music-library"} className={"music-library " + isActive(props.isMusicLibrary)}>
                <div className="aside-nav">
                    <i className="fas fa-clone"/>
                    <p className="music-library-title">Music Library</p>
                </div>
            </Link>
            <Link to={"/all-playlists"} className={"all-playlists " + isActive(props.isAllPlaylists)}>
                <div className="aside-nav">
                    <i className="fas fa-music"/>
                    <p className="music-library-title">All Playlists</p>
                </div>
            </Link>
        </aside>
    )

};

const isActive = (isActive) => {
    return isActive ? "active" : "";
};

export default MusicLibrary;