export default class DeezerUtil {
    static adjustUI() {
        let header = $('header');
        let footer = $('.ap');
        let contentPlacementTop = $(header).position().top + $(header).height() + 20;
        let main = $('main');
        $(main).css('margin-top', contentPlacementTop);
        let contentPlacementBottom = $(footer).height();
        $(main).css('margin-bottom', contentPlacementBottom);
    }

    static createMiniPlaylist(tracks, playlist) {
        console.log(playlist);
        $('.playlist__section').empty().prepend(`
            <div class="pl-img">   
                <img src=${playlist.picture}>
            </div>
            <div class="pl-info">
                <p class="pl-title">Current playlist: ${playlist.title}</p>
            </div>
            <div class="mini-playlist">
            <table class="hover unstriped">
            <thead>
            <tr>
                <th></th>
                <th>Track</th>
                <th><i class="far fa-clock"></i></th>
                <th><i class="far fa-heart"></i></th>
            </tr>
            </thead>
            <tbody id="playlistBody">
            </tbody>
        </table>
        </div>
        `);
        $(tracks).each((i) => {
            let track = tracks[i];
            let trHtml = `<tr>
                            <td class="btnPlay" trackid="${track.id}"><i class="fas fa-play" track="${track.id}play" ></i>
                                <i class="fas fa-pause" track="${track.id}pause" ></i></td>
                            <td>
                                <div><p class="pl-track__title">${track.title}</p>
                                    <p class="pl-album-artist__title">${track.album.title} - ${track.artist.name}</p>
                                </div>
                            </td>
                            <td> 
                            <div class="pl-track__time">
                                <span track="${track.id}track__time--current">0:00</span>
                                <span> / </span>
                                <span track="${track.id}track__time--duration">0:00</span>
                            </div>
                                               
                    </td>
                            <td>like</td>
                        </tr>`;
            $("#playlistBody").append(trHtml);
        });
    }

    static createPlaylist(tracks, search) {
        const body = $('#songsBody');
        $(body).empty();
        if (tracks.length === 0) return;
        $(tracks).each((i) => {
            let track = tracks[i];
            let trHtml = `<tr>
                            <td class="btnPlay" trackid="${track.id}"><i class="fas fa-play" track="${track.id}play" ></i>
                                <i class="fas fa-pause" track="${track.id}pause" ></i></td>
                            <td>
                                <div class="search-pl-info"><p class="pl-track__title">${track.title}</p>
                                    <p class="pl-album-artist__title">${track.album.title} - ${track.artist.name}</p>
                                </div>
                            </td>
                            <td> 
                            <div class="pl-track__time">
                                <span track="${track.id}track__time--current">0:00</span>
                                <span> / </span>
                                <span track="${track.id}track__time--duration">0:00</span>
                            </div>
                                               
                    </td>
                            <td>like</td>
                        </tr>`;
            $(body).append(trHtml);
            if (search) $("td.btnPlay").attr("search", search);
        });
        $('.songs-playlist').show();
    }

    static getArtistsHtml(artists) {
        let html = '';
        $(artists).each((i) => {
            let artist = artists[i];
            html += `<div class="cell" value="${artist.id}">
                    <div class="card card-playlist-hover">
                        <img src="${artist.picture}"
                             alt="artist photo">
                        <div class="card-playlist-hover-icons">
                            <button value="${artist.id}"><i class="fas fa-play"></i></button>

                        </div>
                        <div class="card-playlist-hover-details">
                            <p class="card-playlist-hover-title">${artist.name}</p>
                        </div>
                    </div>
                </div>`;
        });
        return html;
    }

    static getAlbumsHtml(albums) {
        let html = '';
        $(albums).each((i) => {
            let album = albums[i];
            html += `<div class="cell" value="${album.id}">
                    <div class="card card-playlist-hover">
                        <img src="${album.picture}"
                             alt="album photo">
                        <div class="card-playlist-hover-icons">
                            <button value="${album.id}"><i class="fas fa-play"></i></button>

                        </div>
                        <div class="card-playlist-hover-details">
                            <p class="card-playlist-hover-title">${album.title}</p>
                            <p class="card-playlist-hover-subtitle">${album.artist.name}</p>
                        </div>
                    </div>
                </div>`;
        })
        return html;
    }

    static getGenresHtml(genres) {
        let html = '';
        $(genres).each((i) => {
            let genre = genres[i];
            html += `<div class="cell" value="${genre.id}">
                    <div class="card card-playlist-hover">
                        <img src="${genre.picture}"
                             alt="genre logo">
                        <div class="card-playlist-hover-icons">
                            <button value="${genre.id}"><i class="fas fa-play"></i></button>

                        </div>
                        <div class="card-playlist-hover-details">
                            <p class="card-playlist-hover-title">${genre.title}</p>
                        </div>
                    </div>
                </div>`;
        });

        return html;
    }

    static getTopPlaylistsHtml(playlists) {
        let html = '';
        $(playlists).each((i) => {
            let playlist = playlists[i];
            html += `<div class="cell" value="${playlist.id}">
                    <div class="card card-playlist-hover">
                        <img src="${playlist.picture}"
                             alt="playlist logo">
                        <div class="card-playlist-hover-icons">
                            <button value="${playlist.id}"><i class="fas fa-play"></i></button>
                        </div>
                        <div class="card-playlist-hover-details">
                            <p class="card-playlist-hover-title">${playlist.title}</p>
                        </div>
                    </div>
                </div>`;
        });
        return html;
    }

    static setPagination(paginationContainer, data, htmlFunction, paginationData, bindFunction, view) {
        $(paginationContainer).pagination({
            dataSource: data,
            pageSize: 5,
            showPageNumbers: false,
            showNavigator: false,
            position: 'top',
            prevText: '<i class="fas fa-arrow-left"></i>',
            nextText: '<i class="fas fa-arrow-right"></i>',
            callback: (data, pagination) => {
                let html = htmlFunction(data);
                $(paginationData).html(html);
                bindFunction(view);
            }
        });

    }

    static showGenres(genres, view) {
        if (genres.length === 0) return;
        $('.genres-playlists').show();
        let genrePlaylist = $('#genre');
        $(genrePlaylist).empty();
        DeezerUtil.setPagination($('#pagination-container-genre'), genres, DeezerUtil.getGenresHtml, genrePlaylist, DeezerUtil.bindGenres, view);
    }

    static bindGenres(view) {
        let genreBtn = $("#genre button");
        let genrePicture = $("#genre img");
        let genreTitle = $("#genre p.card-playlist-hover-title");
        $(genreBtn).each(i => {
                const picture = $(genrePicture[i]).attr('src');
                const title = $(genreTitle[i]).text();
                const id = $(genreBtn[i]).val();
                $(genreBtn[i]).unbind('click').click(
                    (e) => {
                        e.stopPropagation();
                        view.handleGenreChange({id, title, picture})
                    }
                )
            }
        );
        let genreCards = $("#genre .cell");
        $(genreCards).each(i => {
            let genreCard = genreCards[i];
            $(genreCard).click((e) => {
                let selectedItem = {
                    id: $(genreCard).attr('value'),
                    type: 'genre',
                    title: $(genreCard).find('.card-playlist-hover-title').text(),
                    picture: $(genreCard).find('img').attr('src')
                };
                console.log(selectedItem);
                view.handleShowItem(selectedItem);
            });
        });
    }

    static showArtists(artists, view) {
        let artistsPlaylist = $('#artist');
        $(artistsPlaylist).empty();
        if (artists.length === 0) return;
        DeezerUtil.setPagination($('#pagination-container-artist'), artists, DeezerUtil.getArtistsHtml, artistsPlaylist, DeezerUtil.bindArtists, view);
        $('.artists-playlists').show();
    }

    static bindArtists(view) {
        let artist = $("#artist button");
        let artistPicture = $("#artist img");
        let artistTitle = $("#artist p.card-playlist-hover-title");
        $(artist).each(i => {
                const picture = $(artistPicture[i]).attr('src');
                const title = $(artistTitle[i]).text();
                const id = $(artist[i]).val();
                $(artist[i]).unbind('click').click(
                    (e) => {
                        e.stopPropagation();
                        view.handleArtistChange({id, title, picture})
                    }
                )
            }
        );
        let artistCards = $("#artist .cell");
        $(artistCards).each(i => {
            let artistCard = artistCards[i];
            $(artistCard).click((e) => {
                let selectedItem = {
                    id: $(artistCard).attr('value'),
                    type: 'artist',
                    title: $(artistCard).find('.card-playlist-hover-title').text(),
                    picture: $(artistCard).find('img').attr('src')
                };
                view.handleShowItem(selectedItem);
            });
        });
    }

    static showAlbums(albums, view, artistDefined) {
        let albumsPlaylist = $('#album');
        $(albumsPlaylist).empty();
        if (albums.length === 0) return;
        let albumPl = $('.album-playlists');
        if (artistDefined) {
            // let html = DeezerUtil.getAlbumsHtml(albums);
            albumPl.find('.artist-page').remove();
            //$(albumPl).find('#album').prepend(html);
            $(albumPl).addClass('artist-page');
            // DeezerUtil.bindAlbums(view);
        }
        DeezerUtil.setPagination($('#pagination-container-album'), albums, DeezerUtil.getAlbumsHtml, albumsPlaylist, DeezerUtil.bindAlbums, view);
        //}
        $(albumPl).show();
    }

    static bindAlbums(view) {
        let album = $("#album button");
        let albumPicture = $("#album img");
        let albumTitle = $("#album p.card-playlist-hover-title");
        $(album).each(i => {
            const picture = $(albumPicture[i]).attr('src');
            const title = $(albumTitle[i]).text();
            const id = $(album[i]).val();
            $(album[i]).click(
                (e) => {
                    e.stopPropagation();
                    view.handleAlbumChange({id, title, picture})
                }
            )
        });
        DeezerUtil.bindCard('album', view);
    }

    static bindCard(item, view){
        let albumCards = $(`#${item} .cell`);
        $(albumCards).each(i => {
            let albumCard = albumCards[i];
            $(albumCard).click((e) => {
                let selectedItem = {
                    id: $(albumCard).attr('value'),
                    type: 'album',
                    title: $(albumCard).find('.card-playlist-hover-title').text(),
                    subtitle: $(albumCard).find('.card-playlist-hover-subtitle').text(),
                    picture: $(albumCard).find('img').attr('src')
                };
                console.log(selectedItem);
                view.handleShowItem(selectedItem);
            });
        });
    }

    static showTopPlaylists(playlists, view) {
        if (playlists.length === 0) return;
        let playlistsPlaylist = $('#topPlaylist');
        $('.top-public-playlists').show();
        $(playlistsPlaylist).empty();
        let html = DeezerUtil.getTopPlaylistsHtml(playlists);
        $(playlistsPlaylist).append(html);
        let playlist = $("#topPlaylist button");
        let playlistPicture = $("#topPlaylist img");
        let playlistTitle = $("#topPlaylist p.card-playlist-hover-title");
        $(playlist).each(i => {
            const picture = $(playlistPicture[i]).attr('src');
            const title = $(playlistTitle[i]).text();
            const id = $(playlist[i]).val();
            $(playlist[i]).unbind('click').click(
                (e) => {
                    e.stopPropagation();
                    view.handlePlaylistChange({id, title, picture})
                }
            )
        });
        let playlistCards = $("#topPlaylist .cell");
        $(playlistCards).each(i => {
            let playlistCard = playlistCards[i];
            $(playlistCard).click((e) => {
                let selectedItem = {
                    id: $(playlistCard).attr('value'),
                    type: 'playlist',
                    title: $(playlistCard).find('.card-playlist-hover-title').text(),
                    picture: $(playlistCard).find('img').attr('src')
                };
                console.log(selectedItem);
                view.handleShowItem(selectedItem);
            });
        });
    }

    static showPlaylists(itemId, playlists, view){
        if (playlists.length === 0) return;
        let playlistsPlaylist = $(`#${itemId}`);
        $(playlistsPlaylist).empty();
        let html = DeezerUtil.getTopPlaylistsHtml(playlists);
        $(playlistsPlaylist).append(html);
        let playlist = $(`#${itemId} button`);
        let playlistPicture = $(`#${itemId} img`);
        let playlistTitle = $(`#${itemId} p.card-playlist-hover-title`);
        $(playlist).each(i => {
            const picture = $(playlistPicture[i]).attr('src');
            const title = $(playlistTitle[i]).text();
            const id = $(playlist[i]).val();
            $(playlist[i]).unbind('click').click(
                (e) => {
                    e.stopPropagation();
                    view.handlePlaylistChange({id, title, picture})
                }
            )
        });
        let playlistCards = $(`#${itemId} .cell`);
        $(playlistCards).each(i => {
            let playlistCard = playlistCards[i];
            $(playlistCard).click((e) => {
                let selectedItem = {
                    id: $(playlistCard).attr('value'),
                    type: 'playlist',
                    title: $(playlistCard).find('.card-playlist-hover-title').text(),
                    picture: $(playlistCard).find('img').attr('src')
                };
                console.log(selectedItem);
                view.handleShowItem(selectedItem);
            });
        });
    }

    static showItem(data, item, view) {
        history.pushState(data, item.type, `/${item.type}/${item.id}`);
        DeezerUtil.hideMainPlaylists();
        DeezerUtil.createPlaylist(data, true);
        DeezerUtil.createHeader(item);
        console.log(item);
        if (item.albums) {
            console.log(item.albums);
            DeezerUtil.showAlbums(item.albums, view, true);
        }
        DeezerUtil.bindPlay(view, data, item.title);
    }

    static createHeader(item) {
        $('.playlist__header').remove();
        let html = `<div class="playlist__header card ${item.type}">
                        <div class="playlist__picture">
                            <img  src=${item.picture}>
                        </div>
                        <div class="playlist__details">
                            <h3 class="playlist__title">${item.title}</h3>
                            <p class="playlist__subtitle">${item.subtitle || ''}</p>
                        </div>
                    </div>`;
        let songs = $('.songs-playlist');
        $(songs).parent().prepend(html);

    }

    static bindPlay(view, tracks, playlistTitle) {
        $('.btnPlay').unbind('click').click((e) => {
            if (!view.tracks) {
                view.tracks = tracks;
            }

            if ($(e.currentTarget).attr("search")
                && view.currentPlayList !== tracks) {
                view.createPlayer(tracks, {title: playlistTitle, picture: tracks[0].picture});
            }
            let playId = $(e.currentTarget).attr('trackId');
            if (playId) {
                view.pauseAudio();
                view.handlePlaySong(playId)
            } else {
                view.handleAudio();
            }
        });

    }

    static hideMainPlaylists() {
        $('.songs-playlist').hide();
        $('.playlist__header').remove();
        $('.genres-playlists').hide();
        $('.artists-playlists').hide();
        $('.top-public-playlists').hide();
        $('.user-library').remove();
        let album = $('.album-playlists');
        $(album).removeClass('artist-page');
        $(album).hide();

    }
}