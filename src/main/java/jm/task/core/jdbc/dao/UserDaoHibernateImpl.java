package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS usertab (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), lastname VARCHAR(20), age INT(20))";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            System.out.println("ошибка createUsersTable");
        }


    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            String sql = "DROP TABLE IF EXISTS usertab";

            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            System.out.println("ошибка dropUsersTable");
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();

        } catch (Exception e) {
            System.out.println("ошибка saveUser");
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (transaction != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {

            User user = session.get(User.class, id);
            user.getName();
        }catch (Exception e) {
            System.out.println("ошибка removeUserById");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Session session = Util.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM User ");
            userList = query.getResultList();
        } catch (Exception e) {
            System.out.println("ошибка getAllUsers");
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tr = null;
        String sql = "TRUNCATE TABLE usertab";
        try (Session session = Util.getSessionFactory().openSession()) {
            tr = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            tr.commit();
        } catch (Exception e) {
            System.out.println("cleanUsersTable");
            if (tr != null) {
                tr.rollback();
            }
        }
    }
}
