package com.svalero.books.domain;

import com.svalero.books.util.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private int id;
    private LocalDate date;
    private boolean paid;

    private User user;
    private List<Dish> dishes;

    public Order() {
        dishes = new ArrayList<>();
    }

    public Order(boolean paid, LocalDate date, User user) {
        this.paid = paid;
        this.date = date;
        this.user = user;
        dishes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Pagado: " + paid + "\n" +
                "Fecha: " + DateUtils.formatLocalDate(date);
    }
}
