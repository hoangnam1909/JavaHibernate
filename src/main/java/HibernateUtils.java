import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import pojo.*;
import java.util.Properties;

public class HibernateUtils {
    private static final SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    static {
        Configuration conf = new Configuration();
        Properties props = new Properties();

        props.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        props.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        props.put(Environment.URL, "jdbc:mysql://localhost:3306/saledb");
        props.put(Environment.USER, "root");
        props.put(Environment.PASS, "12345678");
        props.put(Environment.SHOW_SQL, "true");

        conf.setProperties(props);
        conf.addAnnotatedClass(Category.class);
        conf.addAnnotatedClass(Product.class);

        ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        sessionFactory = conf.buildSessionFactory(registry);
    }
}
