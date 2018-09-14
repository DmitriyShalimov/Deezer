create table "user" (
  id         serial primary key
  ,login      text unique
  ,password   text not null
  ,salt       text not null
  ,photo_link text
);

create table genre (
  id    serial primary key
  ,title text not null
);

create table artist (
  id   serial primary key
  ,name text not null
);

create table album (
  id           serial primary key
  ,title        text not null
  ,artist       int references artist (id)
  ,picture_link text
);

create table song (
  id           serial primary key
  ,title        text not null
  ,track_url    text not null
  ,album        int references album (id)
  ,picture_link text
  ,like_count   int
);

create table song_genre (
  id    serial primary key
  ,song  int references song (id)
  ,genre int references genre (id)
);


create table playlist (
  id         serial primary key
  ,title      text not null
  ,access     text check (access = 'private' or access = 'public')
  ,like_count int
);

create table playlist_user (
  id       serial primary key
  ,"user"   int references "user" (id)
  ,playlist int references playlist (id)
);

create table playlist_song (
  id       serial primary key
  ,song     int references song (id)
  ,playlist int references playlist (id)
);

create table song_artist (
  id     serial primary key
  ,song   int references song (id)
  ,artist int references artist (id)
);
