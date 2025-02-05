package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(" CREATE TABLE IF NOT EXISTS users ("
                    + "id SERIAL PRIMARY KEY ," +
                    "name VARCHAR(64) NOT NULL," +
                    "lastName VARCHAR(64) NOT NULL," +
                    "age BIGINT NOT NULL)");
            logger.info("Таблица создана.");
        } catch (SQLException e) {
            logger.info("Ошибка при создании: " + e.getMessage());
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }

    }

    @Override
    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS users");
            logger.info("Таблица удалена");
        } catch (SQLException | IOException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users ( name, lastName, age) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            logger.info("Пользователь:  " + name + " добавлен");
        } catch (SQLException | IOException e) {
            logger.severe(e.getMessage());
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            logger.info("Пользователь добавлен: " + id);
        } catch (SQLException | IOException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id,name, lastname, age FROM users")) {

            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                int age = resultSet.getInt("age");
                User user = new User(id, name, lastname, (byte) age);
                users.add(user);
            }
        } catch (SQLException | IOException e) {
            logger.severe(e.getMessage());
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM users";
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            logger.info("Таблица очищена");

        } catch (SQLException | IOException e) {
            logger.severe(e.getMessage());
        }
    }
}
