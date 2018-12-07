import React, {Component} from "react";

class CreateNewPlaylist extends Component {
    state = {playlistName: '', isPublic: false};

    render() {
        return (
            <div className="reveal" id="createPlaylistModal" data-reveal>
                <h2 className="title">New Playlist</h2>
                <div className="new-playlist__name">
                    <label htmlFor="new-playlist__input">Title</label>
                    <input id="new-playlist__input" autoFocus="autofocus"
                           onChange={e => this.updatePlaylistName(e.target.value)}/>
                </div>
                <div className="new-playlist__access" data-tooltip aria-haspopup="true" data-position="bottom"
                     data-alignment="left"
                     tabIndex="1" title="All users can listen this playlist">
                    <input className="new-playlist__access__input" type="checkbox" id="new-playlist-access__checkbox"
                           onChange={() => this.updateAccess()}/>
                    <label className="new-playlist__access__label" htmlFor="new-playlist-access__checkbox">Make
                        public</label>
                </div>
                <div className="buttons">
                    <button className="new-playlist__cancel" type="button" onClick={()=> this.closeModal()}>Cancel
                    </button>
                    <button className="new-playlist__create" type="button" onClick={() => this.createPlaylist()}
                            >Create
                        Playlist
                    </button>
                </div>
                <button className="close-button" onClick={()=> this.closeModal()} type="button">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        )
    }

    updatePlaylistName(title) {
        this.setState({playlistName: title});
    }

    updateAccess() {
        const {isPublic} = this.state;
        this.setState({isPublic: !isPublic})
    }

    createPlaylist() {
        const {track, createPlaylist} = this.props;
        const {playlistName, isPublic} = this.state;
        const accessModifier = isPublic ? "public" : "private";
        createPlaylist(playlistName, accessModifier, track.id);
        this.closeModal();
    }

    closeModal(){
        $('#createPlaylistModal').foundation('close');
    }

}

export default CreateNewPlaylist;
