const URI_PREFIX = '/api/v1';

export default class PlaylistController {
    constructor(view){
        this.view = view;

        $(this.view).on('new-playlist', (e, data) => this.createNewPlaylist(data))
    }

    createNewPlaylist(data){
        console.log(data);
        $.ajax({
            type: "POST",
            url: `${URI_PREFIX}/playlist/add`,
            data: {playlistTitle: data.title, access: data.access, song: data.track},
            headers: {
                Accept: 'application/json'
            },
            success: data => {
                console.log(data);
            }
        });
    }
}