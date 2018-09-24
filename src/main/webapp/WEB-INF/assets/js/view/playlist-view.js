export default class PlayListView {
    constructor() {
        this.volumeBar = $('.volume__bar');
        this.volume = $('.volume');
        this.volumeIconOff = $('.icon-volume-off');
        this.volumeIconOn = $('.icon-volume-on');
        this.genre = $("#genre  button");
        this.artist = $("#artist button");
        //TODO:album handler
        this.albums = $('#albums');
        this.index = 0;
        this.title = $('#title');
        this.play = $('.main-play');
        this.pause = $('.main-pause');
        this.trackPicture = $('.track__picture');
        $(this.trackPicture).hide();
        this.playlist = $('.playlist__section');
        $('.ap__controls--playlist').click(() => this.handlePlaylist());
        $(this.genre).each(i =>
            $(this.genre[i]).click(
                () => this.handleGenreChange($(this.genre[i]).val())
            )
        );

        $(this.artist).each(i =>
            $(this.artist[i]).click(
                () => this.handleArtistChange(($(this.artist[i]).val()))
            )
        );

        $('#btnPrev').click(() => this.playPrevious());
        $('#btnNext').click(() => this.playNext());
        //TODO: wheel handler
        $('.volume-btn').click(() => this.handleVolumeButton());
        $(this.volume).click((event) => this.handleVolume(event));
        let isDraggingVolume = false;
        let startingPosVolume = [];
        $(this.volume)
            .mousedown((evt) => {
                isDraggingVolume = false;
                startingPosVolume = [evt.pageX, evt.pageY];
            })
            .mousemove((evt) => {
                if (!(evt.pageX === startingPosVolume[0] && evt.pageY === startingPosVolume[1])) {
                    isDraggingVolume = true;
                    if (startingPosVolume[1]) {
                        this.handleVolume(evt);
                    }
                }
            })
            .mouseup(() => {
                isDraggingVolume = false;
                startingPosVolume = [];
            });
        this.progress = $('.progress');
        $(this.progress).click((e) => this.handleProgressClick(e))
        let isDraggingProgress = false;
        let startingPosProgress = [];
        $(this.progress)
            .mousedown((evt) => {
                isDraggingProgress = false;
                startingPosProgress = [evt.pageX, evt.pageY];
            })
            .mousemove((evt) => {
                if (!(evt.pageX === startingPosProgress[0] && evt.pageY === startingPosProgress[1])) {
                    isDraggingProgress = true;
                    if (startingPosProgress[0]) {
                        this.handleProgressClick(evt);
                    }
                }
            })
            .mouseup(() => {
                isDraggingProgress = false;
                startingPosProgress = [];
            });
        this.getAudio();
    }

    handlePlaylist() {
        if ($(this.playlist).is(':hidden')) {
            $(this.playlist).show('fast');
            $('.fi-music').css('color', '#ff5722');
        } else {
            $(this.playlist).hide('fast');
            $('.fi-music').css('color', 'black');
        }

    }

    handleProgressClick(event) {
        if (this.audio) {
            const percent = (event.pageX / $(this.progress).width());
            this.audio.currentTime = this.audio.duration * percent;
        }
    }


    handleVolumeButton() {
        if ($(this.volumeIconOff).is(":hidden")) {
            this.previousVolume = parseInt($(this.volumeBar).css('height'));
            this.setVolume(0);
        } else {
            this.setVolume(this.previousVolume);
        }
    }

    handleVolume({pageY}) {
        let currentVolume = parseInt($(this.volumeBar).css('height'));
        let y = currentVolume - (pageY - $(this.volumeBar).offset().top);
        currentVolume = y > 100 ? 100 : (y < 0 ? 0 : y);
        this.setVolume(currentVolume);
    }

    setVolume(currentVolume) {
        $(this.volumeBar).css('height', currentVolume);
        if (this.tracks) {
            this.audio.volume = currentVolume / 100;
        }
        this.setVolumeIcon(currentVolume / 100);
    }

    setVolumeIcon(volume) {
        if (volume === 0) {
            $(this.volumeIconOff).show();
            $(this.volumeIconOn).hide();
        } else {
            $(this.volumeIconOff).hide();
            $(this.volumeIconOn).show();
        }
    }

    handleAudio() {
        if (this.audio.paused) {
            this.audio.play();
        } else {
            this.audio.pause();
        }
    }

    pauseAudio() {
        console.log("pause " + this.currentTrack.id);
        $(`[track=${this.currentTrack.id}pause]`).hide();
        $(`[track=${this.currentTrack.id}play]`).show();
    }

    playAudio() {
        this.audio.volume = parseInt($(this.volumeBar).css('height')) / 100;
        $('#track__title').text(this.currentTrack.title);
        $(this.trackPicture).attr("src", this.currentTrack.picture);
        $(this.trackPicture).show();
        $('#album-artist__title').text(`${this.currentTrack.album.title} - ${this.currentTrack.artist.name}`);
        console.log("play " + this.currentTrack.id);
        $(`[track=${this.currentTrack.id}play]`).hide();
        $(`[track=${this.currentTrack.id}pause]`).show();
    }

    handleProgressBar() {
        const duration = this.audio.duration;
        const current = this.audio.currentTime;
        let percent = (current / duration) * 100;
        $('.progress__bar').width(percent + '%');
    }

    handleTrackTime() {
        let curMins = Math.floor(this.audio.currentTime / 60);
        let curSecs = Math.floor(this.audio.currentTime - curMins * 60);
        let mins = Math.floor(this.audio.duration / 60);
        let secs = Math.floor(this.audio.duration - mins * 60);
        (curSecs < 10) && (curSecs = '0' + curSecs);
        (secs < 10) && (secs = '0' + secs);
        $(`[track=${this.currentTrack.id}track__time--current]`).text(curMins + ':' + curSecs);
        $('.track__time--current').text(curMins + ':' + curSecs);
        isNaN(mins) ? mins = "0" : mins;
        isNaN(secs) ? secs = "00" : secs;
        $('.track__time--duration').text(mins + ':' + secs);
        $(`[track=${this.currentTrack.id}track__time--duration]`).text(mins + ':' + secs);

    }

    handlePlayAlbum(e) {
        $(this).trigger('albumSongs', e.currentTarget.id);
    }

    handleGenreChange(id) {
        $(this).trigger('genre', id);
    }

    handleArtistChange(id) {
        $(this).trigger('artist', id);
    }

    handlePlaySong(playId) {
        if (this.currentTrack.id !== parseInt(playId)) {
            let id = -1;
            this.tracks.forEach((song) => {
                id++;
                if (song.id.toString() === playId) {
                    this.index = id;
                    this.playTrack(this.tracks[id]);
                }
            });
        } else {
            this.handleAudio();
        }
    }

    createPlayer(tracks) {
        this.tracks = tracks;
        this.index = 0;
        let tbody = $("tbody");
        $(tbody).empty();
        $(tracks).each((i) => {
            let track = tracks[i];
            console.log(track);
            let trHtml = `<tr>
                            <td class="btnPlay" id="${track.id}"><i class="fi-play" track="${track.id}play" ></i>
                                <i class="fi-pause" track="${track.id}pause" ></i></td>
                            <td>${track.title}<br>${track.album.title}-${track.artist.name}</td>
                            <td> <span track="${track.id}track__time--current">--</span>
                    <span> / </span>
                    <span track="${track.id}track__time--duration">--</span></td>
                            <td>like</td>
                        </tr>`;
            $(tbody).append(trHtml);

        });
        $('.btnPlay').unbind('click').click((e) => {
            if (e.currentTarget.id) {
                this.pauseAudio();
                this.handlePlaySong(e.currentTarget.id)
            } else {
                this.handleAudio();
            }
        });
        this.playTrack(this.tracks[0]);
    }


    playTrack(track) {
        this.loadTrack(track);
        this.audio.play();
        let volume = $('.volume__bar').css('height');
        this.audio.volume = parseInt(volume) / 100;
    };


    loadTrack(track) {
        this.title.text(track.title);
        this.audio.src = track.url;
    };


    playPrevious() {
        this.pauseAudio();
        if ((this.index - 1) > -1) {
            this.index--;
        } else {
            this.index = 0;
        }
        this.playTrack(this.tracks[this.index]);
    }

    playNext() {
        let trackCount = this.tracks.length;
        this.pauseAudio();
        if ((this.index + 1) < trackCount) {
            this.index++;
        } else {
            this.index = 0;
        }
        this.playTrack(this.tracks[this.index]);
    }

    getAudio() {
        let audio = $('#audio');
        $(audio).on('loadedmetadata', () => {
            $(audio).on('timeupdate', () => {
                this.handleTrackTime();
                this.handleProgressBar();
            })


        });

        $(audio).bind('play', () => {
            this.currentTrack = this.tracks.find(t => t.url === this.audio.src);
            console.log(this.currentTrack);
            $(this.play).attr('track', `${this.currentTrack.id}play`);
            $(this.pause).attr('track', `${this.currentTrack.id}pause`);
            this.playAudio();
        }).bind('pause', () => {
            this.pauseAudio();
        }).bind('ended', () => {
            if ((this.index + 1) < this.tracks.length) {
                this.index++;
            } else {
                this.index = 0;
            }
            this.playTrack(this.tracks[this.index]);
        });
        this.audio = $(audio).get(0);
    }

    createAlbums(albums) {
        this.albums.empty();
        console.log(albums);
        this.albums.append(albums.map((album) => {
            return '<button class="play-album-button" id="' + album.id + '">' + album.title + '</button><br>';
        }).join(''));
        $('.play-album-button').click((e) => this.handlePlayAlbum(e));
    }
}