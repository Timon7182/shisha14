package Db;


import Domain.Item;
import Domain.Order;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtil {
    //session factory - hibernate. and always null

    private static SessionFactory sessionFactory;




    public synchronized static SessionFactory getSessionFactory() throws Exception {
        if(sessionFactory==null){





            String driver = "org.postgresql.Driver";
            String url = "jdbc:postgresql://rogue.db.elephantsql.com/yzxsfumo";
            String user="yzxsfumo";
            String pas="XmsGyby0iBdMkozplQgiH6HMNpt4tf2h";
            String dialect = "org.hibernate.dialect.PostgreSQLDialect";

            Configuration configuration = new Configuration();
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, driver);
            properties.put(Environment.URL, url);
            properties.put(Environment.USER, user);
            properties.put(Environment.PASS,pas);
            properties.put(Environment.DIALECT,dialect);
            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS,"thread");
//            properties.put(Environment.HBM2DDL_AUTO,"create");
            properties.put(Environment.ENABLE_LAZY_LOAD_NO_TRANS,"true");
            properties.put(Environment.ALLOW_UPDATE_OUTSIDE_TRANSACTION, "true");




            configuration.setProperties(properties);
            configuration.addAnnotatedClass(Order.class);
            configuration.addAnnotatedClass(Item.class);


            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        }
        return sessionFactory;
    }






}
