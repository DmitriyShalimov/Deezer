import React from "react";

const RandomSong = (props) => {
    const {playRandomSong} = props;
    return (
        <aside className="random__song left has-tip"
               data-tooltip aria-haspopup="true" tabIndex="1" title="Play something"><i
            className="fas fa-dice-three" onClick={() => playRandomSong()}/></aside>
    )
};

export default RandomSong;