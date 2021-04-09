package com.example.xcargomobile.user;

import java.util.ArrayList;
import java.util.List;

public class Users {

    List<User> User;

    public Users() {
        this.User = new ArrayList();
    }

    public Users(List<User> User) {
        this.User = User;
    }

    public List<User> getUser() {
        return User;
    }

    public void setUser(List<User> User) {
        this.User = User;
    }

}