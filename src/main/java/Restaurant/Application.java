package com.svalero.books;

import java.sql.SQLException;

public class Application {
    public static void main(String args[]) throws SQLException {
        Menu menu = new Menu();
        menu.showMenu();
    }
}