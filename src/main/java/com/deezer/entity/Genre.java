package com.deezer.entity;

import java.util.Objects;

public class Genre {
    private int id;
    private String title;
    private String picture;

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
        Genre genre = (Genre) o;
        return id == genre.id &&
                Objects.equals(title, genre.title) &&
                Objects.equals(picture, genre.picture);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, picture);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
