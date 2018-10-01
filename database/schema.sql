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

CREATE OR REPLACE FUNCTION like_song(userId int, songId int)
  RETURNS void AS $$
DECLARE
  likeCount int;
  favouritesPl int;
BEGIN
  SELECT COUNT(*) into likeCount FROM song_user WHERE song = songId AND "user"= userId ;
  select id into favouritesPl from playlist where "user" = userId and title = 'Favourites';
  if likeCount = 0 then
    INSERT INTO song_user (song, "user") VALUES (songId,userId);
    if favouritesPl is null then
      insert into playlist(title, "access", "user", picture) values('Favourites', 'private', userId, '/assets/img/cat.png') returning id into favouritesPl;
    end if;
    INSERT INTO playlist_song (playlist,song) VALUES(favouritesPl, songId);
  else
    DELETE from song_user where song = songId and "user"= userId;
    DELETE FROM playlist_song where playlist = favouritesPl and song = songId;
  end if;
END;
$$ LANGUAGE plpgsql;
