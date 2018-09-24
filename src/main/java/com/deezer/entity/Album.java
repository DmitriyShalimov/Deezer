package com.deezer.entity;

import java.util.Objects;

public class Album {
    private int id;
    private String title;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return id == album.id &&
                Objects.equals(title, album.title);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"title\":\"" + title + '\"' +
                "};";
    }
}
