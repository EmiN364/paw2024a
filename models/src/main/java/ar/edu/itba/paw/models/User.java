package ar.edu.itba.paw.models;

public class User {
    private long userId;
    private String username;
    private String password;

    public User(long userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public long getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return username;
    }
}
