package com.deezer.entity;

import java.util.Objects;

public class Song {
    private int id;
    private String title;
    private Artist artist;
    private Album album;
    private String url;
    private String picture;
    private int likeCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return id == song.id &&
                likeCount == song.likeCount &&
                Objects.equals(title, song.title) &&
                Objects.equals(artist, song.artist) &&
                Objects.equals(album, song.album) &&
                Objects.equals(url, song.url) &&
                Objects.equals(picture, song.picture);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, artist, album, url, picture, likeCount);
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artist=" + artist +
                ", album=" + album +
                ", url='" + url + '\'' +
                ", picture='" + picture + '\'' +
                ", likeCount=" + likeCount +
                '}';
    }
}
