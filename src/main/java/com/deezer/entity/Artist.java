package com.deezer.entity;

import java.util.Objects;

public class Artist {
    private int id;
    private String name;
    private Album album;
    private String picture;

    public Artist() {
    }

    public Artist(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
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
        Artist artist = (Artist) o;
        return id == artist.id &&
                Objects.equals(name, artist.name) &&
                Objects.equals(album, artist.album) &&
                Objects.equals(picture, artist.picture);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, album, picture);
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", album=" + album +
                ", picture='" + picture + '\'' +
                '}';
    }
}
