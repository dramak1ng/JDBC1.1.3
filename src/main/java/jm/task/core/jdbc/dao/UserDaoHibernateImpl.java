package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import javax.persistence.Query;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.createSQLQuery("CREATE TABLE IF NOT EXISTS users(id SERIAL PRIMARY KEY, name VARCHAR(64) NOT NULL ,lastname VARCHAR(64) NOT NULL, age BIGINT NOT NULL)").executeUpdate();
                tx.commit();
                logger.info("Была создана таблица");

            } catch (HibernateException e) {
                logger.info("Ошибка при создании");
                tx.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession();) {
            Transaction tx = session.beginTransaction();
            try {
                session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
                tx.commit();
                logger.info("Таблица была удалена");

            } catch (HibernateException e) {
                logger.info("Произошла ошибка при удалении таблицы");
                tx.rollback();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession();) {
            Transaction tx = session.beginTransaction();
            try {
                session.save(new User(name, lastName, age));
                logger.info("был создан пользовательн: " + name);
                tx.commit();

            } catch (HibernateException e) {
                logger.info("ошибка при создании ");
                tx.rollback();
            }


        }
    }


    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession();) {
            Transaction tx = session.beginTransaction();
            try {
                User user = session.get(User.class, id);
                    session.delete(user);
                logger.info("Пользователь удален:  " + id);
                tx.commit();

            } catch (HibernateException e) {
                logger.info("Произошла ошибка при удалении пользователя");
                tx.rollback();
            }

        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                users = session.createQuery("FROM User", User.class).list();
            } catch (HibernateException e) {
                logger.info("Произошла ошибка");
                tx.rollback();
            }
        }
        return users;









    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession();) {
            Transaction tx = session.beginTransaction();
            try {
                session.createSQLQuery("Truncate table users").executeUpdate();
                tx.commit();
            } catch (HibernateException e) {
                logger.info("Произошла ошибка");
                tx.rollback();
            }
        }
    }

}


