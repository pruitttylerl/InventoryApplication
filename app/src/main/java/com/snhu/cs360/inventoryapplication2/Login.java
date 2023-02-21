package com.snhu.cs360.inventoryapplication2;

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
}
