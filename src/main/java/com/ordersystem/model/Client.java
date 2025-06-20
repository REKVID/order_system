package com.ordersystem.model;

public class Client {
    private int id;
    private String name;
    private String phone;
    private String address;
    private String contactPerson;

    public Client() {
    }

    public Client(int id, String name, String phone, String address, String contactPerson) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.contactPerson = contactPerson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
}
