package Restaurant.dao;




import Restaurant.domain.Dish;
import Restaurant.domain.Restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class DishDao {

    private Connection connection;

    public DishDao(Connection connection) {
        this.connection = connection;
    }

    public void add(Dish dish, Restaurant restaurant) throws SQLException {
        String sql = "INSERT INTO DISH (DISH_NAME, PRICE,ID_RESTAURANT) VALUES (?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, dish.getName());
        statement.setFloat(2, dish.getPrice());
        statement.setFloat(3, restaurant.getId());
        statement.executeUpdate();
    }


    public boolean delete(String name) throws SQLException {
        String sql = "DELETE FROM DISH WHERE DISH_NAME = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        int rows = statement.executeUpdate();

        return rows == 1;
    }

    public boolean modify(String name, Dish dish, Restaurant restaurant) throws SQLException {
        String sql = "UPDATE DISH SET DISH_NAME = ?, PRICE = ?, ID_RESTAURANT = ? WHERE DISH_NAME = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, dish.getName());
        statement.setFloat(2, dish.getPrice());
        statement.setFloat(3, restaurant.getId());
        statement.setString(4, name);
        int rows = statement.executeUpdate();
        return rows == 1;
    }

    public ArrayList<Dish> findAll() throws SQLException {
        String sql = "SELECT * FROM DISH ORDER BY DISH_NAME";
        ArrayList<Dish> dishes = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Dish dish = new Dish();
            dish.setId(resultSet.getInt("ID_DISH"));
            dish.setName(resultSet.getString("DISH_NAME"));
            dish.setPrice(resultSet.getInt("author"));
            dishes.add(dish);
        }

        return dishes;
    }
    public ArrayList<Dish> findbyRestaurant(String name) throws SQLException {
        String sql =  "SELECT * FROM DISH JOIN RESTAURANT ON RESTAURANT.ID_RESTAURANT = DISH.ID_RESTAURANT WHERE RESTAURANT_NAME = ?";

        ArrayList<Dish> dishes = new ArrayList<>();


        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Dish dish = new Dish();
            dish.setId(resultSet.getInt("ID_DISH"));
            dish.setName(resultSet.getString("DISH_NAME"));
            dish.setPrice(resultSet.getFloat("PRICE"));

            dishes.add(dish);
        }

        return dishes;
    }


    public Optional<Dish> findByName(String name) throws SQLException {
        String sql = "SELECT * FROM DISH WHERE DISH_NAME = ?";
        Dish dish = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            dish = new Dish();
            dish.setId(resultSet.getInt("ID_DISH"));
            dish.setPrice(resultSet.getInt("PRICE"));
            dish.setName(resultSet.getString("DISH_NAME"));
        }

        return Optional.ofNullable(dish);
    }
    public Optional<Dish> findByNameAndRestaurant(String name, String restaurant) throws SQLException {
        String sql = "SELECT * FROM DISH JOIN RESTAURANT ON RESTAURANT.ID_RESTAURANT = DISH.ID_RESTAURANT WHERE DISH_NAME = ? AND RESTAURANT_NAME = ? ";
        Dish dish = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setString(2, restaurant);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            dish = new Dish();
            dish.setId(resultSet.getInt("ID_DISH"));
            dish.setPrice(resultSet.getInt("PRICE"));
            dish.setName(resultSet.getString("DISH_NAME"));
        }

        return Optional.ofNullable(dish);
    }

}
