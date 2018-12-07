import React, {Component} from "react";
import CreateNewPlaylist from "./CreateNewPlaylist.jsx";

class AddToPlaylist extends Component {
    componentDidMount() {
        $('#createPlaylistModal').on('open.zf.reveal', () =>
            $('#addToPlaylistMenu').foundation('close'));
    }

    render() {
        const {playlists, track, createPlaylist} = this.props;
        return (
            <section className="playlist-menu dropdown-pane top" id="addToPlaylistMenu" data-dropdown>
                <div className="create-playlist" data-open="createPlaylistModal">Create new playlist</div>
                <div className="menu-separator"/>
                <ul className="user-playlist-list">
                    {playlists.length > 0 && playlists.map(playlist =>
                        <li className="user-playlist" key={playlist.id}
                            onClick={() => this.handleAddToPlaylist(playlist.id, track.id)}>{playlist.title}<span
                            className="user-playlist-subtitle"> - {playlist.access}</span></li>
                    )}
                </ul>
                <CreateNewPlaylist track={track} createPlaylist={createPlaylist}/>
            </section>
        )
    }

    handleAddToPlaylist(playlistId, trackId) {
        this.props.addTrackToPlaylist(playlistId, trackId);
        $('#addToPlaylistMenu').foundation('close');
    }
}

export default AddToPlaylist;