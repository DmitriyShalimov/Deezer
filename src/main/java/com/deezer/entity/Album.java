package com.deezer.entity;

import java.util.Objects;

public class Album implements SearchResult{
    private int id;
    private Artist artist;
    private String title;
    private String picture;

    public Album() {
    }

    public Album(String title) {
        this.title = title;
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

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
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
        Album album = (Album) o;
        return id == album.id &&
                Objects.equals(artist, album.artist) &&
                Objects.equals(title, album.title) &&
                Objects.equals(picture, album.picture);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, artist, title, picture);
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", artist=" + artist +
                ", title='" + title + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
