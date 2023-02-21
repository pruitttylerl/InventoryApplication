package com.snhu.cs360.inventoryapplication2;

import java.util.Objects;

public class Login {

        private long id;
        private String username;
        private String password;

        //Getters. No setters
        public long getId() {
        return id;
    }

        public String getUsername() {
        return username;
    }

        public String getPassword() {
        return password;
    }

    public Login(long iId, String iUsername, String iPassword){
        id = iId;
        username = iUsername;
        password = iPassword;
    }

    public Login(String username, String password){
        this(-1, username, password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login = (Login) o;
        return id == login.id && Objects.equals(username, login.username) && Objects.equals(password, login.password);
    }

}
