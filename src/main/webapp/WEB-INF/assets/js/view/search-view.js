import DeezerUtil from "../deezer-util.js";

export default class SearchView {
    constructor(playlistView) {
        this.playlistView = playlistView;
        this.searchInput = $('.search-form');
        this.searchButton = $('.fi-magnifying-glass');

        $(this.searchButton).click(() => this.handleSearch($(this.searchInput).val()));
        $(this.playlistView).on('checkPlaylist', () => this.checkPlaylist());
    }
    checkPlaylist(){
        if (this.playlistView.currentPlayList !== this.tracks) {
            this.playlistView.createPlayer(this.tracks, false);
        }
    }
    setOptions(data) {
        let ar = this.getDataForOptions(data);
        this.options = {
            data: ar,
            getValue: "title",
            list: {
                maxNumberOfElements: 8,
                match: {
                    enabled: true
                },
                sort: {
                    enabled: true
                },
                onClickEvent: () => {
                    let itemToSearch = $(this.searchInput).getSelectedItemData();
                    $(this).trigger('typeSearch', {type: itemToSearch.type, id: itemToSearch.id});
                }
            },
            template: {
                type: "description",
                fields: {
                    description: "type"
                }
            }
        };
        $(this.searchInput).easyAutocomplete(this.options);
    }

    getDataForOptions(data) {
        return data.map(d => {
            !d.title ? d.title = d.name : d.title;
            if (d.hasOwnProperty('artist')) {
                if (d.hasOwnProperty('album')) {
                    d.type = 'song'
                } else {
                    d.type = 'album';
                }
            } else {
                d.type = 'artist';
            }
            return d
        });
    }

    playSearchResult(type, result) {
        if (!Array.isArray(result)) {
            result = [result];
        }
        this.playlistView.createPlayer(result);
    }

    handleSearch(str) {
        if (str) $(this).trigger('generalSearch', str);
    }

    hideMainPlaylists() {
        $('.genres-playlists').hide();
        $('.artists-playlists').hide();
    }

    showSongs(tracks) {
        if (tracks.length === 0) return;
        this.tracks = tracks;
        DeezerUtil.createPlaylistTable(tracks, $('#songsBody'), true);
        $('.songs-playlist').show();

        $('.btnPlay').unbind('click').click((e) => {
            if ($(e.currentTarget).attr("search")
                && this.playlistView.currentPlayList !== this.tracks) {
                this.playlistView.createPlayer(this.tracks, false);
            }
            let playId = $(e.currentTarget).attr('trackId');
            if (playId) {
                this.playlistView.pauseAudio();
                this.playlistView.handlePlaySong(playId)
            } else {
                this.playlistView.handleAudio();
            }
        });

    }
}