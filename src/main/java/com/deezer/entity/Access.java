package com.deezer.entity;

public enum Access {
    PRIVATE("private"), PUBLIC("public");
    private String id;

    Access(String id) {
        this.id = id;
    }

    public static Access getTypeById(String id) {
        for (Access access : Access.values()) {
            if (access.getId().equals(id)) {
                return access;
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }
}
