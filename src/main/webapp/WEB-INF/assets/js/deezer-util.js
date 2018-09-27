export default class DeezerUtil {
    static adjustUI() {
        let header = $('header');
        let footer = $('.ap');
        let contentPlacementTop = $(header).position().top + $(header).height() + 10;
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
            let genreHtml =
                `<div class="cell">
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
            html += genreHtml;
        });
        return html;
    }
    static setPagination(paginationContainer, data, htmlFunction, paginationData){
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
            }
        });

    }
    static showGenres(genres, view) {
        if (genres.length === 0) return;
        $('.genres-playlists').show();
        let genrePlaylist = $('#genre');
        $(genrePlaylist).empty();
        DeezerUtil.setPagination($('#pagination-container-genre'), genres, DeezerUtil.getGenresHtml, genrePlaylist);
        let genreBtn = $("#genre button");
        let genrePicture = $("#genre img");
        let genreTitle = $("#genre p.card-playlist-hover-title");
        $(genreBtn).each(i => {
                const picture = $(genrePicture[i]).attr('src');
                const title = $(genreTitle[i]).text();
                const id = $(genreBtn[i]).val();
                $(genreBtn[i]).unbind('click').click(
                    () => view.handleGenreChange({id, title, picture})
                )
            }
        );

    }

    static showArtists(artists, view) {
        if (artists.length === 0) return;
        let artistsPlaylist = $('#artist');
        $(artistsPlaylist).empty();
        DeezerUtil.setPagination($('#pagination-container-artist'), artists, DeezerUtil.getArtistsHtml, artistsPlaylist);
        $('.artists-playlists').show();
        let artist = $("#artist button");
        let artistPicture = $("#artist img");
        let artistTitle = $("#artist p.card-playlist-hover-title");
        $(artist).each(i => {
                const picture = $(artistPicture[i]).attr('src');
                const title = $(artistTitle[i]).text();
                const id = $(artist[i]).val();
                $(artist[i]).unbind('click').click(
                    () => view.handleArtistChange({id, title, picture})
                )
            }
        );

    }

    static showAlbums(albums, view) {
        if (albums.length === 0) return;
        let albumsPlaylist = $('#album');
        $(albumsPlaylist).empty();
        DeezerUtil.setPagination($('#pagination-container-album'), albums, DeezerUtil.getAlbumsHtml, albumsPlaylist);
        $('.album-playlists').show();
        let album = $("#album button");
        let albumPicture = $("#album img");
        let albumTitle = $("#album p.card-playlist-hover-title");
        $(album).each(i => {
            const picture = $(albumPicture[i]).attr('src');
            const title = $(albumTitle[i]).text();
            const id = $(album[i]).val();
            $(album[i]).click(
                () => view.handleAlbumChange({id, title, picture})
            )
        });
    }
}