package com.example.com.localDB;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "user")
public class User {
    @PrimaryKey
    @NonNull
    private String username;
    @NonNull
    private String password;
    private String usernameGoodReads;

    public User(@NonNull String username, @NonNull String password, String usernameGoodReads) {
        this.username = username;
        this.password = password;
        this.usernameGoodReads = usernameGoodReads;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public String getUsernameGoodReads() {
        return usernameGoodReads;
    }

    public void setUsernameGoodReads(String usernameGoodReads) {
        this.usernameGoodReads = usernameGoodReads;
    }
}
