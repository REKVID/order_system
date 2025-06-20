package com.ordersystem.model;

public class User {
    private int id;
    private String username;
    private String password;
    private int roleId;
    private Integer clientId;

    public User() {
    }

    public User(int id, String username, String password, int roleId, Integer clientId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
}
