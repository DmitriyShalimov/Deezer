export default class DeezerUtil {
    static createPlaylistTable(tracks, tbody, search) {
        $(tbody).empty();
        $(tracks).each((i) => {
            let track = tracks[i];
            console.log(track);
            let trHtml = `<tr>
                            <td class="btnPlay" trackid="${track.id}"><i class="fi-play" track="${track.id}play" ></i>
                                <i class="fi-pause" track="${track.id}pause" ></i></td>
                            <td>${track.title}<br>${track.album.title}-${track.artist.name}</td>
                            <td> <span track="${track.id}track__time--current">--</span>
                    <span> / </span>
                    <span track="${track.id}track__time--duration">--</span></td>
                            <td>like</td>
                        </tr>`;
            $(tbody).append(trHtml);
            if (search) $("td.btnPlay").attr("search", search);

        });
    }

    static createArtistsCards(artists) {
        let artistsPlaylist = $('#artist');
        $(artistsPlaylist).empty();
        $(artists).each((i) => {
            let artist = artists[i];
            let artistHtml =
                `<div class="cell" value="${artist.id}">
                    <div class="card card-playlist-hover">
                        <img src="${artist.picture}"
                             alt="artist photo">
                        <div class="card-playlist-hover-icons">
                            <button value="${artist.id}"><i class="fi-play-circle"></i></button>

                        </div>
                        <div class="card-playlist-hover-details">
                            <h4 class="card-playlist-hover-title">${artist.name}</h4>
                        </div>
                    </div>
                </div>`;
            $(artistsPlaylist).append(artistHtml);
        });
    }

    static createAlbumsCards(albums) {
        let albumPlaylist = $('#album');
        $(albumPlaylist).empty();
        $(albums).each((i) => {
            let album = albums[i];
            let albumHtml =
                `<div class="cell" value="${album.id}">
                    <div class="card card-playlist-hover">
                        <img src="${album.picture}"
                             alt="album photo">
                        <div class="card-playlist-hover-icons">
                            <button value="${album.id}"><i class="fi-play-circle"></i></button>

                        </div>
                        <div class="card-playlist-hover-details">
                            <h4 class="card-playlist-hover-title">${album.title}</h4>
                            <h5 class="card-playlist-hover-title">${album.artist.name}</h5>
                        </div>
                    </div>
                </div>`;
            $(albumPlaylist).append(albumHtml);
        });
    }

    static createGenresCards(genres) {
        let genrePlaylist = $('#genre');
        $(genrePlaylist).empty();
        $(genres).each((i) => {
            let genre = genres[i];
            let genreHtml =
                `<div class="cell"">
                    <div class="card card-playlist-hover">
                        <img src="${genre.picture}"
                             alt="genre logo">
                        <div class="card-playlist-hover-icons">
                            <button value="${genre.id}"><i class="fi-play-circle"></i></button>

                        </div>
                        <div class="card-playlist-hover-details">
                            <h4 class="card-playlist-hover-title">${genre.title}</h4>
                        </div>
                    </div>
                </div>`
            $(genrePlaylist).append(genreHtml);
        });
    }
}