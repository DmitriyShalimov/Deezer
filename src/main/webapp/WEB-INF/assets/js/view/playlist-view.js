import DeezerUtil from "../deezer-util.js";

export default class PlaylistView {
    constructor(mainView) {
        this.mainView = mainView;
        this.playlistMenu = $('#addToPlaylistMenu');
        this.playlistMainBtn = $('.main-add-to-playlist');
        this.newPlaylistModal = $('#createPlaylistModal');
        $(this.newPlaylistModal).on('open.zf.reveal', () =>
            $(this.playlistMenu).foundation('close'));
        $('.new-playlist__create').click(() => this.handleCreateNewPlaylist());
        $('.music-library').click(() => this.showUserPlaylists());
    }

    handleCreateNewPlaylist() {
        const newPlaylistName = $('#new-playlist__input').val();
        const newPlaylistAccess = $("#new-playlist-access__checkbox").is(":checked");
        if (newPlaylistName) {
            $(this).trigger('new-playlist', {
                plTitle: newPlaylistName,
                track: $(this.playlistMainBtn).attr('track'),
                access: newPlaylistAccess ? 'public' : 'private'
            });
        }
    }

    closeModal() {
        $(this.newPlaylistModal).foundation('close');

    }

    closePlaylistMenu() {
        $(this.playlistMenu).foundation('close');
    }

    showPlaylistsNames(data) {
        this.playlists = data;
        let list = $(".user-playlist-list");
        $(list).empty();
        data.forEach(playlist => {
            $(list).append(`<li class="user-playlist" playlist="${playlist.id}">${playlist.title}<span class="user-playlist-subtitle"> - ${playlist.access}</span></li>`)
        });
        let playlists = $('.user-playlist');
        $(playlists).each(i => {
            $(playlists[i]).click((e) => {
                let id = $(e.currentTarget).attr('playlist');
                $(this).trigger('add-song', {playlist: id, song: $(this.playlistMainBtn).attr('track')})
            });

        })
    }

    showUserPlaylists(playlists) {
        if (playlists) this.playlists = playlists;
        history.pushState(this.playlists, 'Library', '/music-library');
        DeezerUtil.hideMainPlaylists();
        $('#playlist-section').append(`
        <div class="user-library">
                <ul class="tabs" data-active-collapse="true" data-tabs id="collapsing-tabs">
                    <li class="tabs-title is-active"><a href="#privatePl" aria-selected="true">Private</a></li>
                    <li class="tabs-title"><a href="#publicPl">Public</a></li>
                    <li class="tabs-title"><a href="#favouritesPl">Favourites</a></li>
                </ul>
            <div class="tabs-content artist-page" data-tabs-content="collapsing-tabs">
                <div class=" tabs-panel is-active grid-x grid-padding-x small-up-3 medium-up-5 large-up-7" id="privatePl">
                    <p class="empty-message">Nothing to show. Create you first private playlist and it will be here.</p>
                </div>
                <div class="tabs-panel grid-x grid-padding-x small-up-3 medium-up-5 large-up-7" id="publicPl">
                <p class="empty-message">Nothing to show. Create you first public playlist and it will be here.</p>
                </div>
                <div class=" tabs-panel grid-x grid-padding-x small-up-3 medium-up-5 large-up-7" id="favouritesPl">
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                </div>
            </div>
        </div>`);

        $(document).foundation();

        let privatePlalists = this.playlists.filter(playlist => playlist.access === 'PRIVATE');
        if (privatePlalists.length > 0) {
            DeezerUtil.showPlaylists('privatePl', privatePlalists, this.mainView);
        }
        let publicPlaylists = this.playlists.filter(playlist => playlist.access === 'PUBLIC');
        if (publicPlaylists.length > 0) {
            DeezerUtil.showPlaylists('publicPl', publicPlaylists, this.mainView);
        }
    }


}