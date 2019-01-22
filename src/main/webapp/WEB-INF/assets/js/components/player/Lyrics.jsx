import React from "react"

const Lyrics = (props) => {
    const {lyrics} = props;
    return (
        <section className="track-lyrics-dropdown dropdown-pane top" id="lyricsPane" data-dropdown data-position="top"
                 data-alignment="right" data-v-offset="60"
                 data-close-on-click="true">
            <p className={getClass(lyrics)}>{lyrics ? lyrics : "We are doing our best to find the lyrics for this song."}</p>
        </section>
    );
};
const getClass = (lyrics) => {
    return lyrics ? "lyrics-text" : " empty-message";
};
export default Lyrics;