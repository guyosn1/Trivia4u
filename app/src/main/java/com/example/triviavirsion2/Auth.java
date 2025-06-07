package com.example.triviavirsion2;

public class Auth {
    String email;
    String password;

    public Auth() {

    }
    public Auth(String email, String password) {

        this.email = email;
        this.password = password;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
