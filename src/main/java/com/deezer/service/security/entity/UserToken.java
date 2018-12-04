package com.deezer.service.security.entity;

import com.deezer.entity.User;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserToken {
    private final String uuid;
    private final User user;
    private final LocalDateTime creationDate;

    public UserToken(String uuid, User user, LocalDateTime creationDate) {
        this.uuid = uuid;
        this.user = user;
        this.creationDate = creationDate;
    }

    public String getUuid() {
        return uuid;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserToken userToken = (UserToken) o;
        return Objects.equals(uuid, userToken.uuid) &&
                Objects.equals(user, userToken.user) &&
                Objects.equals(creationDate, userToken.creationDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid, user, creationDate);
    }

    @Override
    public String toString() {
        return "UserToken{" +
                "uuid='" + uuid + '\'' +
                ", user=" + user +
                ", creationDate=" + creationDate +
                '}';
    }
}
