CREATE TABLE GENRE
(
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(50) NOT NULL UNIQUE,
  picture_link VARCHAR(500)
);

CREATE TABLE ARTIST
(
  id      INTEGER PRIMARY KEY AUTO_INCREMENT,
  name    VARCHAR(50) NOT NULL UNIQUE,
  picture VARCHAR(500)
);

CREATE TABLE ALBUM
(
  id           INTEGER PRIMARY KEY AUTO_INCREMENT,
  title        VARCHAR(50) NOT NULL,
  artist       INTEGER,
  picture_link VARCHAR(500),
  foreign key (artist) REFERENCES ARTIST (id),
  CONSTRAINT unique_artist_album UNIQUE (title, artist)
);

CREATE TABLE SONG
(
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(50) NOT NULL,
  track_url VARCHAR(500) NOT NULL,
  album integer,
  picture_link VARCHAR(500),
  like_count integer,
  lyrics text,
  foreign key (album) REFERENCES ALBUM (id),
  CONSTRAINT unique_song_album UNIQUE (title, album)
);

CREATE TABLE SONG_GENRE
(
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  song integer NOT NULL,
  genre integer NOT NULL,
  foreign key (song) REFERENCES SONG (id),
  foreign key (genre) REFERENCES GENRE (id),
  CONSTRAINT unique_song_genre UNIQUE (song, genre)
);

CREATE TABLE SONG_USER
(
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  song integer NOT NULL,
  user integer NOT NULL
)