package com.deezer.entity;

import java.util.List;
import java.util.Objects;

public class PlayList {
    private int id;
    private String title;
    private int likeCount;
    private Access access;
    private List<Song> songs;
    private String picture;

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

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

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayList playList = (PlayList) o;
        return id == playList.id &&
                likeCount == playList.likeCount &&
                Objects.equals(title, playList.title) &&
                access == playList.access &&
                Objects.equals(songs, playList.songs) &&
                Objects.equals(picture, playList.picture);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, likeCount, access, songs, picture);
    }

    @Override
    public String toString() {
        return "PlayList{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", likeCount=" + likeCount +
                ", access=" + access +
                ", songs=" + songs +
                ", picture='" + picture + '\'' +
                '}';
    }
}
