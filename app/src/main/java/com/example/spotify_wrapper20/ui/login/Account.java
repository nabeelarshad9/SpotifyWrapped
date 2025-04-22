package com.example.spotify_wrapper20.ui.login;

public class Account {

    private int id;
    private String email;
    private String username;
    private String password;

    public Account(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // ID Getter and Setter Methods:
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Email Getter and Setter Methods:
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Username Getter and Setter Methods:
    public String getUsername() { return username; }
    public void setUsername(String username) {
        this.username = username;
    }

    // Password Getter and Setter Methods:
    public String getPassword() { return password; }
    public void setPassword(String password) {
        this.password = password;
    }

}
