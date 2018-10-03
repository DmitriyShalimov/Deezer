export default class DeezerUtil {
    static adjustUI() {
        let header = $('header');
        let footer = $('.ap');
        let contentPlacementTop = $(header).position().top + $(header).height();
        let main = $('main');
        $(main).css('margin-top', contentPlacementTop);
        let contentPlacementBottom = $(footer).height();
        $(main).css('margin-bottom', contentPlacementBottom);
    }

    static createMiniPlaylist(tracks, playlist, view) {
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
            let trHtml = DeezerUtil.getSongHtml(tracks[i]);
            $("#playlistBody").append(trHtml);
            DeezerUtil.toggleLike(tracks[i]);
            DeezerUtil.bindLike(view);
        });
    }

    static createPlaylist(tracks, search, view) {
        const body = $('#songsBody');
        view.pageTrackList = tracks;
        $(body).empty();
        if (tracks.length === 0) return;
        $(tracks).each((i) => {
            let trHtml = DeezerUtil.getSongHtml(tracks[i]);
            $(body).append(trHtml);
            if (search) $("td.btnPlay").attr("search", search);
            DeezerUtil.toggleLike(tracks[i]);
            DeezerUtil.bindLike(view);
        });
        $('.songs-playlist').show();
    }

    static getSongHtml(track) {
        return `<tr>
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
                             <td class="btnLike" trackid="${track.id}">
                                <i class="far fa-heart top like"></i>
                                <i class="fas fa-heart top dislike"></i>
                            </td>
                        </tr>`;
    }

    static toggleLike(track) {
        let btnLike = $(`.btnLike[trackid=${track.id}]`);
        if (track.liked) {
            $(btnLike).find('.dislike').addClass('active-like-state');
            $(btnLike).find('.like').removeClass('active-like-state');
        } else {
            $(btnLike).find('.like').addClass('active-like-state');
            $(btnLike).find('.dislike').removeClass('active-like-state');
        }
    }

    static getItemHtml(id, picture, title, subtitle) {
        return `<div class="cell" value="${id}">
                    <div class="card card-playlist-hover">
                        <img src="${picture}"
                             alt="artist photo">
                        <div class="card-playlist-hover-icons">
                            <button value="${id}"><i class="fas fa-play"></i></button>

                        </div>
                        <div class="card-playlist-hover-details">
                            <p class="card-playlist-hover-title">${title}</p>
                            <p class="card-playlist-hover-subtitle">${subtitle || ''}</p>
                        </div>
                    </div>
                </div>`;
    }

    static getArtistsHtml(artists) {
        let html = '';
        $(artists).each((i) => {
            let artist = artists[i];
            html += DeezerUtil.getItemHtml(artist.id, artist.picture, artist.name);
        });
        return html;
    }

    static getAlbumsHtml(albums) {
        let html = '';
        $(albums).each((i) => {
            let album = albums[i];
            html += DeezerUtil.getItemHtml(album.id, album.picture, album.title, album.artist.name);
        });
        return html;
    }

    static getGenresHtml(genres) {
        let html = '';
        $(genres).each((i) => {
            let genre = genres[i];
            html += DeezerUtil.getItemHtml(genre.id, genre.picture, genre.title);
        });

        return html;
    }

    static getTopPlaylistsHtml(playlists, like) {
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
                            ${like ? `<div class="card-playlist-like" playlist="${playlist.id}">
                                <i class="far fa-heart top pl-like ${playlist.liked ? '' : 'active-like-state'}"></i>
                                <i class="fas fa-heart top pl-dislike ${playlist.liked ? 'active-like-state' : ''}"></i>
                            </div>` : ''}
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

    static bindBtnPlay(type, button, picture, title, view) {
        $(button).each(i => {
                const pictureSrc = $(picture[i]).attr('src');
                const titleText = $(title[i]).text();
                const id = $(button[i]).val();
                $(button[i]).unbind('click').click(
                    (e) => {
                        e.stopPropagation();
                        view.handleItemChange({id, title: titleText, picture: pictureSrc}, type)
                    }
                )
            }
        );
    }

    static bindGenres(view) {
        DeezerUtil.bindBtnPlay('genre', $("#genre button"), $("#genre img"), $("#genre p.card-playlist-hover-title"), view);
        DeezerUtil.bindCard('genre', view);
    }

    static showArtists(artists, view) {
        let artistsPlaylist = $('#artist');
        $(artistsPlaylist).empty();
        if (artists.length === 0) return;
        DeezerUtil.setPagination($('#pagination-container-artist'), artists, DeezerUtil.getArtistsHtml, artistsPlaylist, DeezerUtil.bindArtists, view);
        $('.artists-playlists').show();
    }

    static bindArtists(view) {
        DeezerUtil.bindBtnPlay('artist', $("#artist button"), $("#artist img"), $("#artist p.card-playlist-hover-title"), view);
        DeezerUtil.bindCard('artist', view);
    }

    static showAlbums(albums, view, artistDefined) {
        let albumsPlaylist = $('#album');
        $(albumsPlaylist).empty();
        if (albums.length === 0) return;
        let albumPl = $('.album-playlists');
        if (artistDefined) {
            albumPl.find('.artist-page').remove();
            $(albumPl).addClass('artist-page');
        }
        DeezerUtil.setPagination($('#pagination-container-album'), albums, DeezerUtil.getAlbumsHtml, albumsPlaylist, DeezerUtil.bindAlbums, view);
        $(albumPl).show();
    }

    static bindAlbums(view) {
        DeezerUtil.bindBtnPlay('album', $("#album button"), $("#album img"), $("#album p.card-playlist-hover-title"), view);
        DeezerUtil.bindCard('album', view);
    }

    static bindCard(item, view) {
        let albumCards = $(`#${item} .cell`);
        $(albumCards).each(i => {
            let albumCard = albumCards[i];
            $(albumCard).click((e) => {
                let selectedItem = {
                    id: $(albumCard).attr('value'),
                    type: item,
                    title: $(albumCard).find('.card-playlist-hover-title').text(),
                    subtitle: $(albumCard).find('.card-playlist-hover-subtitle').text(),
                    picture: $(albumCard).find('img').attr('src')
                };
                console.log(selectedItem);
                view.handleShowItem(selectedItem);
            });
        });
    }

    static bindPlLike(view) {
        let playlistLike = $('.card-playlist-like');
        $(playlistLike).each(i => {
            $(playlistLike[i]).unbind('click').click((e) => {
                e.stopPropagation();
                console.log(view);
                view.handlePlaylistLike($(playlistLike[i]).attr('playlist'));
            })
        });
    }

    static showTopPlaylists(playlists, view) {
        if (playlists.length === 0) return;
        let playlistsPlaylist = $('#topPlaylist');
        $('.top-public-playlists').show();
        $(playlistsPlaylist).empty();
        let html = DeezerUtil.getTopPlaylistsHtml(playlists, true);
        $(playlistsPlaylist).append(html);
        DeezerUtil.bindBtnPlay('playlist', $("#topPlaylist button"), $("#topPlaylist img"), $("#topPlaylist p.card-playlist-hover-title"), view);
        DeezerUtil.bindPlLike(view);
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

    static showPlaylists(itemId, playlists, view, isLiked) {
        if (playlists.length === 0) return;
        let playlistsPlaylist = $(`#${itemId}`);
        $(playlistsPlaylist).empty();
        let html = DeezerUtil.getTopPlaylistsHtml(playlists, isLiked);
        $(playlistsPlaylist).append(html);
        DeezerUtil.bindBtnPlay('playlist', $(`#${itemId} button`), $(`#${itemId} img`), $(`#${itemId} p.card-playlist-hover-title`), view);
        DeezerUtil.bindPlLike(view);
        let playlistCards = $(`#${itemId} .cell`);
        $(playlistCards).each(i => {
            let playlistCard = playlistCards[i];
            $(playlistCard).click((e) => {
                e.stopPropagation();
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
        DeezerUtil.hideMainPlaylists();
        DeezerUtil.createPlaylist(data, true, view);
        DeezerUtil.createHeader(item);
        console.log(item);
        if (item.albums) {
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
                            <h4 class="playlist__title">${item.title}</h4>
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

    static bindLike(view) {
        $('.btnLike').unbind('click').click((e) => {
            let likeId = $(e.currentTarget).attr('trackId');
            view.addLike(likeId);
        });

    }

    static hideMainPlaylists() {
        $('.songs-playlist').hide();
        $('.playlist__header').remove();
        $('.genres-playlists').hide();
        $('.artists-playlists').hide();
        $('.top-public-playlists').hide();
        $('.user-library').remove();
        $('.all-public-playlists').hide();
        let album = $('.album-playlists');
        $(album).removeClass('artist-page');
        $(album).hide();

    }
}