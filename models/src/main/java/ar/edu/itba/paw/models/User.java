package ar.edu.itba.paw.models;

public class User {
    private long userId;
    private String username;

    public User(long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return username;
    }
}
