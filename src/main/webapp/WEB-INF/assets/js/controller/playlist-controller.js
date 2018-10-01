const URI_PREFIX = '/api/v1';

export default class PlaylistController {
    constructor(view) {
        this.view = view;
        this.getUserPlaylists(this.view.showPlaylistsNames.bind(this.view));
        $(this.view).on('new-playlist', (e, data) => this.createNewPlaylist(data));
        $(this.view).on('add-song', (e, data) => this.addSongToPlaylist(data));
        $(this.view).on('refresh', (e, success) => this.getUserPlaylists(success));

    }

    createNewPlaylist(data) {
        console.log(data);
        $.ajax({
            type: "POST",
            url: `${URI_PREFIX}/playlist`,
            data: { title: data.plTitle, access: data.access, song: data.track},
            success: () => {
                this.getUserPlaylists(this.view.showPlaylistsNames.bind(this.view));
                this.view.closeModal();
            }
        });
    }

    getUserPlaylists(success){
        $.ajax({
            type: "GET",
            url: `${URI_PREFIX}/playlist/user`,
            success: data => {
                success(data);
            }
        });
    }

    addSongToPlaylist(data){
        console.log(data);
        $.ajax({
            type: "POST",
            url: `${URI_PREFIX}/playlist/${data.playlist}/song/${data.song}`,
            success: data => {
                this.view.closePlaylistMenu();
            }
        });
    }
}