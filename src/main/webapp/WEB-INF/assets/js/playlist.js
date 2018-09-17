jQuery(function ($) {
    var index = 0;
    playing = false;
    divValue = $('#first').get(0).attributes.item(1).value;
    listSong = divValue.substring(1, divValue.length - 2).split(';,');
    tracks = listSong.map(function (song) {
        return qq = JSON.parse(song);
    });
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
    btnPrev = $('#btnPrev').click(function () {
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
    btnNext = $('#btnNext').click(function () {
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

        tracks.forEach(function (song) {
            if (song.id.toString() === e.currentTarget.id.toString()) {
                playTrack(song.id - 1);
            }
        });


    });
    loadTrack = function (id) {
        $('.plSel').removeClass('plSel');
        $('#plList li:eq(' + id + ')').addClass('plSel');
        title.text(tracks[id].title);
        index = id;
        audio.src = tracks[id].url;
    },
        playTrack = function (id) {
            loadTrack(id);
            audio.play();
        };

});