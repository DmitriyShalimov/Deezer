jQuery(function ($) {
    var index = 0;
    playing = false;
    songsJson = $.ajax({
        type: "GET",
        url: "/artist/" + 1,
        async: false
    }).responseText;
    createPlayer();


    $("#genre").change(function () {
        songsJson = $.ajax({
            type: "GET",
            url: "/genre/" + this.value,
            async: false
        }).responseText;
        createPlayer()
    });
    $("#artist").change(function () {
        albumsJson = $.ajax({
            type: "GET",
            url: "/album/" + this.value,
            async: false
        }).responseText;

        listAlbum = albumsJson.substring(1, albumsJson.length - 2).split(';,');
        albums = listAlbum.map(function (album) {
            return JSON.parse(album);
        });
        $('#albums').empty();
        $('#albums').append(albums.map(function (album) {
            return '<button class="play-album-button" id="' + album.id + '">' + album.title + '</button><br>';
        }).join(''));
        songsJson = $.ajax({
            type: "GET",
            url: "/artist/" + this.value,
            async: false
        }).responseText;
        createPlayer()
    });

    $("#genre").change(function () {
        songsJson = $.ajax({
            type: "GET",
            url: "/genre/" + this.value,
            async: false
        }).responseText;
        createPlayer()
    });

    function createPlayer() {
        $('.play-album-button').click(function (e) {
            songsJson = $.ajax({
                type: "GET",
                url: "/albumsongs/" + e.currentTarget.id.toString(),
                async: false
            }).responseText;
            createPlayer()
        });
        listSong = songsJson.substring(1, songsJson.length - 2).split(';,');
        tracks = listSong.map(function (song) {
            return qq = JSON.parse(song);
        });
        $('#playlist').empty();
        $('#playlist').append(tracks.map(function (tune) {
            return '<button class="play-song-button" id="' + tune.id + '">' + tune.title + '</button><br>';
        }).join(''));
        trackCount = tracks.length;
        action = $('#action');
        title = $('#title');
        audio = $('#audio').bind('play', function () {
            playing = true;
            action.text('Now Playing...');
        }).bind('pause', function () {
            playing = false;
            action.text('Paused...');
        }).bind('ended', function () {
            action.text('Paused...');
            if ((index + 1) < trackCount) {
                index++;
                loadTrack(index);
                audio.play();
            } else {
                audio.pause();
                index = 0;
                loadTrack(index);
            }
        }).get(0);
        $('#btnPrev').click(function () {
            if ((index - 1) > -1) {
                index--;
                loadTrack(index);
                if (playing) {
                    audio.play();
                }
            } else {
                audio.pause();
                index = 0;
                loadTrack(index);
            }
        });
        $('#btnNext').click(function () {
            if ((index + 1) < trackCount) {
                index++;
                loadTrack(index);
                if (playing) {
                    audio.play();
                }
            } else {
                audio.pause();
                index = 0;
                loadTrack(index);
            }
        });
        $('.play-song-button').click(function (e) {
            id = -1;
            tracks.forEach(function (song) {
                id++;
                if (song.id.toString() === e.currentTarget.id.toString()) {
                    playTrack(id);
                }
            });
        });

        loadTrack = function (id) {
            $('.plSel').removeClass('plSel');
            $('#plList li:eq(' + id + ')').addClass('plSel');
            title.text(tracks[id].title);
            index = id;
            audio.src = tracks[id].url;
        };
        playTrack = function (id) {
            loadTrack(id);
            audio.play();
        };
    }
});