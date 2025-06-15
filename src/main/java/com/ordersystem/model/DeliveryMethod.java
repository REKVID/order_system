package com.ordersystem.model;

public class DeliveryMethod {
    private int id;
    private String name;
    private int speedDays;

    public DeliveryMethod() {
    }

    public DeliveryMethod(int id, String name, int speedDays) {
        this.id = id;
        this.name = name;
        this.speedDays = speedDays;
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

    public int getSpeedDays() {
        return speedDays;
    }

    public void setSpeedDays(int speedDays) {
        this.speedDays = speedDays;
    }

    @Override
    public String toString() {
        return "DeliveryMethod{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", speedDays=" + speedDays +
                '}';
    }
}
