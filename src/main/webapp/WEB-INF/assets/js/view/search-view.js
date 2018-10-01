import DeezerUtil from "../deezer-util.js";

export default class SearchView {
    constructor(playlistView) {
        this.playlistView = playlistView;
        this.searchInput = $('.search-form');
        this.searchButton = $('.fa-search');
        $(this.searchButton).click(() => this.handleSearch($(this.searchInput).val()));
        $(this.playlistView).on('checkPlaylist', () => this.checkPlaylist());
    }

    checkPlaylist() {
        if (this.playlistView.currentPlayList !== this.tracks) {
            this.playlistView.createPlayer(this.tracks, {picture: this.tracks[0].picture, title: "Search"});
        }
    }

    setOptions(data) {
        let ar = SearchView.getDataForOptions(data);
        console.log(ar);
        let clicked = false;
        this.options = {
            data: ar,
            getValue: "title",
            list: {
                maxNumberOfElements: 5,
                match: {
                    enabled: true
                },
                onLoadEvent: () => $('.search-value-play button').click(() => {
                    clicked = true;
                    let itemToSearch = $(this.searchInput).getSelectedItemData();
                    console.log(itemToSearch);
                    $(this).trigger('typeSearch', {
                        type: itemToSearch.type, id: itemToSearch.id
                        , title: itemToSearch.title, picture: itemToSearch.picture
                    });
                }),
                onChooseEvent: () => {
                    if (!clicked) {
                        this.handleSearch($(this.searchInput).val())
                    }
                    clicked = false;
                }
            },
            template: {
                type: "custom",
                method: function (value, item) {
                    return `<div class="search-suggestion">
                                <div class="search-contentWrapper">
                                        <img alt="" class="search-img" src=${item.picture}>
                                        <div class="suggestion-details">
                                            <div class="suggestion-title">${value}</div>
                                            <div class="suggestion-subtitle">${SearchView.handleSearchResult(item)}</div>
                                        </div>
                                        <div class="search-value-play">
                                    <button>
                                    <i class="fas fa-play search-play-icon"></i>
                                    </button>
                                </div>
                                </div>
                </div>`
                }
            },
            highlightPhrase: false
        };
        $(this.searchInput).easyAutocomplete(this.options);
        $(this.searchInput).keypress((e) => {
            if (e.keyCode === 13) {
                this.handleSearch($(this.searchInput).val());
                return false;
            }
        });

    }

    static handleSearchResult(item) {
        return item.type + ((item.type !== 'artist') ? ` by ${item.artist.name}` : '');
    }

    static getDataForOptions(data) {
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

    playSearchResult(type, result, playlist) {
        $(this.searchInput).val('');
        if (!Array.isArray(result)) {
            result = [result];
        }
        this.playlistView.createPlayer(result, playlist);
    }

    handleSearch(str) {
        if (str) {
            $(this).trigger('generalSearch', str);
        }
        $(this.searchInput).val('');
    }

    showSongs(tracks, search) {
        DeezerUtil.createPlaylist(tracks, search);
        if (tracks.length !== 0) {
            this.tracks = tracks;
            DeezerUtil.bindPlay(this.playlistView, this.tracks, 'Search');
        }
    }
}