package com.deezer.service.impl;

import com.deezer.dao.GenreDao;
import com.deezer.dao.PlayListDao;
import com.deezer.dao.SongDao;
import com.deezer.entity.Access;
import com.deezer.entity.PlayList;
import com.deezer.entity.Song;
import com.deezer.service.PlayListService;
import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DefaultPlayListService implements PlayListService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final PlayListDao playListDao;
    private final SongDao songDao;
    private final GenreDao genreDao;
    private Map<Integer, Map<Integer, List<Song>>> recommendedPlaylistsForUser = new HashMap<>();
    private final List<PlayList> recommendedPlaylist;

    @Autowired
    public DefaultPlayListService(PlayListDao playListDao, SongDao songDao, GenreDao genreDao) {
        this.playListDao = playListDao;
        this.songDao = songDao;
        this.genreDao = genreDao;
        this.recommendedPlaylist = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        PlayList firstPlayList = new PlayList();
        firstPlayList.setId(-1);
        firstPlayList.setTitle("Hits only");
        firstPlayList.setPicture("https://apsuanews.com/wp-content/uploads/2018/07/d187d0b8d181d182d0bed0b5" +
                "-d0bcd0bed180d0b5-d0b8-d0b3d180d18fd0b7d0bdd18bd0b5-d180d0b5d0bad0b8-d181d18dd181-d0b0d0b1d185d0b0d0b7d0b8d0b8-1-250x250.jpg");
        PlayList secondPlayList = new PlayList();
        secondPlayList.setId(-2);
        secondPlayList.setTitle("Best tracks for you");
        secondPlayList.setPicture("http://www.worlds.ru//photo/italy_180720061452_21.jpg");
        PlayList thirdPlayList = new PlayList();
        thirdPlayList.setId(-3);
        thirdPlayList.setTitle("Feel good");
        thirdPlayList.setPicture("http://illustrators.ru/uploads/illustration/image/1193972/square_%D1%87%D1%82%D0%BE%D1%82%D0%BE12.png");
        PlayList fourthPlayList = new PlayList();
        fourthPlayList.setId(-4);
        fourthPlayList.setTitle("Come away");
        fourthPlayList.setPicture("https://ki.ill.in.ua/m/670x450/12111065.jpg");
        PlayList fifthPlayList = new PlayList();
        fifthPlayList.setId(-5);
        fifthPlayList.setTitle("Day and Night");
        fifthPlayList.setPicture("http://www.stroygrad.kr.ua/images/joomgallery/thumbnails/__2/_6/117448097_20170802_1276306513.jpg");
        recommendedPlaylist.add(firstPlayList);
        recommendedPlaylist.add(secondPlayList);
        recommendedPlaylist.add(thirdPlayList);
        recommendedPlaylist.add(fourthPlayList);
        recommendedPlaylist.add(fifthPlayList);
    }

    @Override
    public void addPlaylist(String playlistTitle, Access access, int userId, int songId) {
        playListDao.addPlaylist(playlistTitle, access, userId, songId);
    }

    @Override
    public void addSongToPlaylist(int playlistId, int songId) {
        playListDao.addSongToPlaylist(playlistId, songId);
    }

    @Override
    public List<PlayList> getUserPlaylist(int id) {
        return playListDao.getUserPlaylist(id);
    }

    @Override

    public void likePlaylist(int playlistId, int userId) {
        playListDao.likePlaylist(playlistId, userId);
    }

    @Override
    public Integer getPlaylistLikeCount(int id) {
        return playListDao.getPlaylistLikeCount(id);
    }

    @Override
    public List<PlayList> getTopPlaylists(int userId) {
        return playListDao.getTopPlaylists(userId);
    }

    @Override
    public PlayList getById(int id, int userId) {
        if(id < 0){
            return recommendedPlaylist.stream().filter(playList -> playList.getId() == id).findFirst().orElseThrow(() -> new EmptyResultDataAccessException(1));
        }
        return playListDao.getById(id, userId);

    }

    @Override
    public List<PlayList> getLikedPlaylists(int userId) {
        return playListDao.getLikedPlayLists(userId);
    }

    @Override
    public List<PlayList> getAllPublicPlaylists(int userId) {
        return playListDao.getAllPublicPlaylists(userId);
    }

    @Override
    public List<PlayList> getRecommendedPlayList(int userId) {
        List<Integer> genres = genreDao.getUserLikedGenres(userId);
            initSongsForRecommendedPlayList(genres, userId);
            return recommendedPlaylist;
    }

    @Override
    public List<Song> getSongsFromRecommendedPlayList(int playListId, int userId) {
        logger.info("Getting songs from playlist {} for user {}", playListId, userId);
        Map<Integer, List<Song>> userPlaylists = recommendedPlaylistsForUser.get(userId);
        logger.info("User playlists are {}", userPlaylists.values());
        return userPlaylists.get(playListId);
    }


    private void initSongsForRecommendedPlayList(List<Integer> genres, int userId) {
        recommendedPlaylistsForUser.remove(userId);
        List<Song> songs;
        songs = songDao.getRecommendedSongForGenre(userId, genres);
        int songsInPlaylist = songs.size() / 5 + 1;
        List<List<Song>> songForRecommendedPlayList = ListUtils.partition(songs, songsInPlaylist);
        Map<Integer, List<Song>> map = new HashMap<>();
        for (int i = 0; i < songForRecommendedPlayList.size(); i++) {
            map.put(i - 5, songForRecommendedPlayList.get(i));
        }
        recommendedPlaylistsForUser.put(userId, map);
    }
}
