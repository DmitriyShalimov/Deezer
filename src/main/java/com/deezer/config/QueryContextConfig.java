package com.deezer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryContextConfig {
    @Bean
    public String getAllAlbumsByArtistIdSql() {
        return "SELECT al.id ,al.title, " +
                "al.picture_link,  ar.name as artist_name, ar.id as artist_id " +
                "FROM album al join artist ar on al.artist = ar.id  " +
                "WHERE al.artist=?";
    }

    @Bean
    public String getAlbumsByMaskSql() {
        return "SELECT  al.id ,al.title," +
                "al.picture_link, ar.name as artist_name " +
                ", ar.id as artist_id " +
                "FROM album al join artist ar on al.artist = ar.id" +
                " WHERE lower(title) like lower(?)";
    }

    @Bean
    public String getAlbumByIdSql() {
        return "SELECT  al.id ,al.title,  " +
                " al.picture_link, ar.name as artist_name " +
                ", ar.id as artist_id  " +
                " FROM album al join artist ar on al.artist = ar.id " +
                "WHERE al.id=?";
    }

    @Bean
    public String getArtistsByMaskSql() {
        return "SELECT id,name, picture FROM artist where lower(name) like lower(?)";
    }

    @Bean
    public String getArtistByIdSql() {
        return "SELECT id,name, picture FROM artist where id = ?";
    }

    @Bean
    public String getAllArtistSql() {
        return "SELECT id, name, picture FROM artist";
    }

    @Bean
    public String getAllGenresSql() {
        return "SELECT id, title, picture_link FROM genre";
    }

    @Bean
    public String getGenreByIdSql() {
        return "SELECT id, title, picture_link FROM genre where id = ?";
    }

    @Bean
    public String getSongsForSearchSql() {
        return "select s.id ,s.title, s.picture_link, " +
                "al.title as album_title, art.name as artist_name " +
                "from song  s join album al on s.album = al.id " +
                "join artist  art on al.artist = art.id left join song_user su on su.user = ? and su.song = s.id ";
    }

    @Bean
    public String getAlbumsForSearchSql() {
        return "SELECT  al.id ,al.title, " +
                "al.picture_link, ar.name as artist_name,  ar.id as artist_id " +
                "FROM album al join artist ar on al.artist = ar.id;";
    }

    @Bean
    public String getArtistsForSearchSql() {
        return "SELECT id,name, picture FROM artist;";
    }

    @Bean
    public String addNewUserSql() {
        return "INSERT INTO \"user\" (login, password, salt) VALUES(?, ?, ?);";
    }

    @Bean
    public String getUserByLogin() {
        return "SELECT id,login, password,salt FROM \"user\"  WHERE login=?";
    }

    @Bean
    public String getPlaylistLikeCountSql() {
        return "SELECT COUNT(*) FROM playlist_user WHERE playlist =?";
    }

    @Bean
    public String getUserLikeCountForPlaylistSql() {
        return "SELECT COUNT(*) FROM playlist_user WHERE playlist =? AND \"user\"=?;";
    }

    @Bean
    public String deletePlaylistLikeCountSql() {
        return "DELETE from playlist_user where playlist=? and \"user\"=?";
    }

    @Bean
    public String addPlaylistLikeCountSql() {
        return "INSERT INTO playlist_user (playlist, \"user\") VALUES (?,?)";
    }

    @Bean
    public String getPlaylistsSql() {
        return "SELECT pl.id, pl.title, " +
                "pl.picture, pl.access, pu.id as liked " +
                ", pl_l.ct as likeCount " +
                " FROM playlist AS pl " +
                " left join playlist_user pu on pu.user=? and pu.playlist=pl.id " +
                " left join (select count(playlist) ct, playlist from playlist_user " +
                " group by playlist) pl_l on pl_l.playlist = pl.id  ";
    }

    @Bean
    public String getTopPlaylistSql() {
        return getPlaylistsSql() +
                " WHERE pl.access='public' " +
                " order by pl_l.ct desc nulls last limit ?";
    }

    @Bean
    public String getAllPlaylistOfUserIdSql() {
        return getPlaylistsSql() +
                "WHERE pl.user=?";
    }

    @Bean
    public String getAllPublicPlaylistSql() {
        return getPlaylistsSql() +
                " WHERE pl.access='public';";
    }

    @Bean
    public String addNewUserPlaylistSql() {
        return "insert into playlist(title, \"access\", \"user\") values(?,?,?)";
    }

    @Bean
    public String addSongToPlaylistSql() {
        return "INSERT INTO playlist_song (playlist,song) VALUES(?,?)";
    }

    @Bean
    public String addSongToPlaylistByPlaylistTitleSql() {
        return "INSERT INTO playlist_song (playlist,song) (select max(id) as id, ? from playlist pl where pl.title = ?)";
    }

    @Bean
    public String addPlaylistPictureSql() {
        return "with current_pl as(" +
                "select max(id) as id from playlist p " +
                "where p.title = ? " +
                ") update playlist set picture =" +
                " (select max(picture_link) from song s " +
                "join playlist_song ps on ps.song = s.id " +
                "where ps.playlist = (select id from current_pl))" +
                " where id = (select id from current_pl);";
    }

    @Bean
    public String getPlaylistById() {
        return "SELECT pl.id, pl.title," +
                " pl.picture, pl.access, pu.id as liked " +
                " , -1 as likeCount " +
                "FROM playlist AS pl " +
                "left join playlist_user pu on pu.user=? and pu.playlist=pl.id " +
                "WHERE pl.id = ? and (pl.access='public' or pl.user=?)";
    }

    @Bean
    public String getLikedPlaylistSql() {
        return "SELECT pl.id, pl.title," +
                " pl.picture, pl.access, pu.id as liked " +
                " , pl_l.ct as likeCount " +
                "FROM playlist AS pl " +
                " join playlist_user pu on pu.playlist=pl.id " +
                " left join (select count(playlist) ct, playlist from playlist_user " +
                " group by playlist) pl_l on pl_l.playlist = pl.id  " +
                " WHERE pu.user=?";
    }

    @Bean
    public String getSongByIdSql() {
        return "select s.id ,s.title, s.track_url,s.picture_link,  " +
                "al.title as album_title, al.id as album_id, art.name as artist_name, art.id as artist_id" +
                ", su.id as liked,s.lyrics " +
                "from song s  join album al on s.album = al.id " +
                "join artist art on al.artist = art.id  " +
                "left join song_user su on su.user = ?  and  su.song = s.id " +
                "WHERE s.id = ?";
    }

    @Bean
    public String getAllSongsByGenreSql() {
        return "select s.id ,s.title, s.track_url,s.picture_link, " +
                "al.title as album_title, art.name as artist_name  " +
                ", su.id  as liked, s.lyrics " +
                "from song s join album al on  s.album = al.id " +
                "join artist art on al.artist= art.id " +
                "join song_genre sg on sg.song = s.id " +
                "left join song_user su on su.user = ? and su.song = s.id " +
                "WHERE sg.genre=?";
    }

    @Bean
    public String getAllSongsByArtistSql() {
        return "select s.id ,s.title," +
                "s.track_url, s.picture_link, " +
                "al.title as  album_title, art.name as artist_name  " +
                ", su.id as liked , s.lyrics " +
                "from  song s join album al on s.album = al.id " +
                "join artist art  on al.artist = art.id " +
                "left join song_user su on su.user =?  and su.song = s.id " +
                "WHERE art.id=?";
    }

    @Bean
    public String getAllSongsByAlbumSql() {
        return "select s.id ,s.title,  " +
                "s.track_url,  s.picture_link, " +
                "al.title as album_title,  art.name as artist_name " +
                ", su.id as liked,   s.lyrics " +
                "from song s join album al on  s.album = al.id " +
                "join artist  art on al.artist = art.id " +
                "left join song_user su on su.user  = ?  and su.song = s.id " +
                "WHERE al.id=?";
    }

    @Bean
    public String getAllSongsByMaskSql() {
        return "select s.id ,s.title, s.track_url,s.picture_link,  " +
                " al.title as album_title, art.name as artist_name " +
                ",su.id as liked, s.lyrics " +
                "from song s join album al on s.album =al.id " +
                "join  artist art on al.artist = art.id " +
                "left join song_user su on su.user= ?  and su.song = s.id " +
                "WHERE lower(s.title) like lower(?);";
    }

    @Bean
    public String getRandomSongs() {
        return "select s.id ,s.title,  s.track_url,s.picture_link , " +
                "al.title as  album_title, art.name as artist_name " +
                ",  su.id as liked, s.lyrics " +
                "from song s join album al on s.album =  al.id " +
                "join artist art on al.artist  = art.id " +
                "left join song_user su on  su.user = ?  and su.song = s.id " +
                "order by random() limit 42";
    }

    @Bean
    public String getAllSongsByPlaylistSql() {
        return "select s.id, s.title, " +
                "s.track_url, s.picture_link , " +
                "al.title  as album_title, art.name as artist_name " +
                ", su.id  as liked, s.lyrics " +
                "from song s join album al on s.album = al.id " +
                " join artist art on al.artist = art.id " +
                "join playlist_song pls on pls.song = s.id " +
                "join playlist pl on pls.playlist = pl.id " +
                "left join song_user su  on su.user = ?  and su.song = s.id " +
                "where pls.playlist =? and (pl.access='public' or pl.user=?)";
    }

    @Bean
    public String getSongLikeCountSql() {
        return "SELECT COUNT(*) FROM song_user WHERE song =?";
    }

    @Bean
    public String getAllSongsByGenresSql() {
        return "select s.id ,s.title, s.track_url,s.picture_link, al.title as album_title, art.name as artist_name " +
                ", su.id as liked, s.lyrics " +
                "from song s join album al on s.album = al.id " +
                "join artist art on al.artist = art.id " +
                "join song_genre sg on sg.song = s.id " +
                "left join song_user su on su.user = :userId  and su.song = s.id " +
                "where sg.genre=? or sg.genre=?";
    }

    @Bean
    public String getUserLikedGenresSql() {
        return "select g.id  from genre as g " +
                " join song_genre sg on sg.genre = g.id" +
                " join song s on s.id=sg.song\n" +
                " join song_user as su on su.song=s.id" +
                " where su.user=:userId " +
                " group by g.id" +
                " order by count (g.id) desc limit 2";
    }
}
