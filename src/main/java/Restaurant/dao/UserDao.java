package Restaurant.dao;

import Restaurant.domain.User;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao {

    private Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    // TODO Controlar las mismas excepciones que para el caso de BookDao.add()
    public void add(User user) throws SQLException {
        String sql = "INSERT INTO USSR (USUARIO, PSSWORD, USERNAME) VALUES (?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getName());
        statement.setString(2, user.getUsername());
        statement.setString(3, user.getPassword());
        statement.executeUpdate();
    }

    public Optional<User> getUser(String username, String password) throws SQLException {

        String sql = "SELECT * FROM USSR WHERE USERNAME = ? AND PSSWORD = ?";
        User user = null;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("ID_USER"));
            user.setName(resultSet.getString("USUARIO"));
            user.setUsername(resultSet.getString("USERNAME"));
            user.setPassword(resultSet.getString("PSSWORD"));
        }


        return Optional.ofNullable(user);
    }

    // TODO Terminar de hacer el resto de métodos de este DAO: modifyUser, deleteUser, getUsers, . . . .
}
