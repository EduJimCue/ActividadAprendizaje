package com.svalero.books.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Dish {

    private int id;
    private float price;
    private String name;

    private Restaurant restaurant;

    private List<Order> orders;

    public Dish() {
        orders = new ArrayList<>();
    }

    public Dish(float price, String name, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.restaurant = restaurant;
        orders = new ArrayList<>();
    }



    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public float getPrice() {return price;}

    public void setPrice(float price) {this.price = price;}

}
