package com.movierental.model.user;

public class User {
    private String userId;
    private String username;
    private String password;
    private String email;
    private String fullName;


    public User(String userId, String username, String password, String email, String fullName) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
    }


    public User() {
        this.userId = "";
        this.username = "";
        this.password = "";
        this.email = "";
        this.fullName = "";
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public boolean authenticate(String inputPassword) {
        return this.password.equals(inputPassword);
    }


    public int getRentalLimit() {
        return 3; // Default rental limit
    }


    public String toFileString() {
        return userId + "," + username + "," + password + "," + email + "," + fullName;
    }


    public static User fromFileString(String fileString) {
        String[] parts = fileString.split(",");
        if (parts.length >= 5) {
            return new User(parts[0], parts[1], parts[2], parts[3], parts[4]);
        }
        return null;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}