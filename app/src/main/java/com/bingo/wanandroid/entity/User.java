package com.bingo.wanandroid.entity;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -194128673367761299L;
    /**
     * chapterTops : []
     * collectIds : [8118,8115]
     * email :
     * icon :
     * id : 17227
     * password :
     * token :
     * type : 0
     * username : ziqing
     */

    private String email;
    private String icon;
    private long id;
    private String password;
    private String token;
    private int type;
    private String username;
    private boolean isLogin;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
