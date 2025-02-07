package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class Util {
    private static final Logger log = Logger.getLogger(Util.class.getName());

    private static String url;
    private static String username;
    private static String password;


    public static SessionFactory sessionFactory = null;
    public static SessionFactory getSessionFactory() {
        if(sessionFactory==null) {
            try {
                Configuration configuration = new Configuration();
                configuration.addAnnotatedClass(User.class);
                sessionFactory = configuration.buildSessionFactory();
            } catch (RuntimeException e) {

                log.severe("ошибка при создании." + e);
                throw new ExceptionInInitializerError(e);

            }
        }
        return sessionFactory;
    }

    public static Connection getConnection() throws SQLException, IOException {

        try (FileInputStream fis = new FileInputStream("src\\main\\resources\\db.properties")) {
            Properties properties = new Properties();
            properties.load(fis);
            url = properties.getProperty("db.url");
            username = properties.getProperty("db.username");
            password = properties.getProperty("db.password");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Connection connection = DriverManager.getConnection(url, username, password);
        try {
            connection = DriverManager.getConnection(url, username, password);

            log.info("установлено.");
        } catch (SQLException e) {
            log.info("oшибка при подключении к базе данных: ");
            e.printStackTrace();

        }
        return connection;





    }



    //
    //---Hib
    //

    }



