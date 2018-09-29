export default class PlaylistView {
    constructor() {
        this.user = $('main').attr('value');
        this.playlistMenu = $('.playlist-menu');
        this.playlistMainBtn = $('.main-add-to-playlist');
        $('.new-playlist__create').click(() => this.handleCreateNewPlaylist())
    }

    handleCreateNewPlaylist() {
        const newPlaylistName = $('#new-playlist__input')[0].val();
        const newPlaylistAccess = $("#new-playlist-access__checkbox").is(":checked");
        console.log(newPlaylistAccess);
        console.log(newPlaylistName);
        if (newPlaylistName) {
            $(this).trigger('new-playlist', {
                plTitle: newPlaylistName,
                user: this.user,
                track: $(this.playlistMainBtn).attr('track'),
                access: newPlaylistAccess ? 'public' : 'private'
            });
        }
    }

}