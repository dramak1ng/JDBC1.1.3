package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserDaoJDBCImpl user = new UserDaoJDBCImpl();
        user.createUsersTable();
        user.saveUser("da","da", (byte) 12);
        user.saveUser("qw","wq", (byte) 22);
        user.saveUser("ew","re", (byte) 32);
        user.saveUser("re","re", (byte) 42);
        System.out.println(user.getAllUsers());
        user.cleanUsersTable();
        user.dropUsersTable();


    }
}
