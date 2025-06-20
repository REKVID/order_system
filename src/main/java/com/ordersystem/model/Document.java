package com.ordersystem.model;

import java.sql.Date;

public class Document {
    private int id;
    private int clientId;
    private Date date;

    public Document() {
    }

    public Document(int id, int clientId, Date date) {
        this.id = id;
        this.clientId = clientId;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
