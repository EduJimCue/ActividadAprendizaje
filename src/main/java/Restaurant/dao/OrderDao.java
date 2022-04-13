package Restaurant.dao;



import Restaurant.domain.Dish;
import Restaurant.domain.Order;
import Restaurant.domain.User;
import Restaurant.util.DateUtils;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDao {

    private Connection connection;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    public void addOrder(User user, List<Optional<Dish>> dishes) throws SQLException {
        String orderSql = "INSERT INTO ORDERS (ORDER_DATE, PAID, ID_USER) VALUES (?, ?, ?)";

        PreparedStatement orderStatement = connection.prepareStatement(orderSql);
        orderStatement.setDate(1, Date.valueOf(LocalDate.now()));
        orderStatement.setInt(2, 0);
        orderStatement.setInt(3,user.getId() );
        orderStatement.executeUpdate();

        String sql = "SELECT MAX(ID_ORDER) FROM orders";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            int orderId = resultSet.getInt(1);

        for (Optional<Dish> dish : dishes) {
            String bookSql = "INSERT INTO ORDERS_DISH (ID_DISH, ID_ORDER) VALUES (?, ?)";
            PreparedStatement bookStatement = connection.prepareStatement(bookSql);
            bookStatement.setInt(2, orderId);
            bookStatement.setInt(1, dish.get().getId());
            bookStatement.executeUpdate();
        }
        }

    }

    public Order getOrder() {
        return null;
    }

    public void payOrder() {

    }

    public List<Order> getOrdersBetweenDates(LocalDate fromDate, LocalDate toDate) throws SQLException {
        String sql = "SELECT * FROM orders WHERE date BETWEEN ? AND ?";
        List<Order> orders = new ArrayList<>();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, DateUtils.toSqlDate(fromDate));
        statement.setDate(2, DateUtils.toSqlDate(toDate));
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Order order = new Order();
            order.setId(resultSet.getInt(1));
            order.setDate(DateUtils.toLocalDate(resultSet.getDate(2)));
            order.setPaid(resultSet.getBoolean(3));
            orders.add(order);
        }

        return orders;
    }

    // TODO Terminar de hacer el resto de métodos de este DAO: modifyUser, deleteUser, getUsers, . . . .
}
