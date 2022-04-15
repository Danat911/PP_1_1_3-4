package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class Util {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

                Map<String, String> settings = new HashMap<>();
                settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/userbd");
                settings.put("hibernate.connection.username", "root");
                settings.put("hibernate.connection.password", "root");
                settings.put("hibernate.show_sql", "true");
                settings.put("hibernate.hbm2ddl.auto", "update");

                registryBuilder.applySettings(settings);
                registry = registryBuilder.build();
                MetadataSources sources = new MetadataSources(registry).addAnnotatedClass(User.class);

                sessionFactory = sources.buildMetadata().buildSessionFactory();

            } catch (Exception e) {
                System.out.println("Ошибка создания SessionFactory");
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }


    //---------------------------------------------------------------------
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection () {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("Соединение с БД установленно");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // реализуйте настройку соеденения с БД
}
