import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pojo.*;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.List;

public class testing {
    public static List<Product> timKiemTheoTen(String name){
        SessionFactory f = HibernateUtils.getSessionFactory();

        try (Session session = f.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = builder.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            query.select(root);

            if (!name.isEmpty()) {
                Predicate p = builder.like(root.get("name").as(String.class), String.format("%%%s%%", name));
                query = query.where(p);

                List<Product> prods = session.createQuery(query)
                        .getResultList();
                return prods;
            }
        }
        return null;
    }

    public static List<Product> timKiemTheoKhoangGia(BigDecimal from, BigDecimal to){
        SessionFactory f = HibernateUtils.getSessionFactory();

        try (Session session = f.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = builder.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            query.select(root);

            Predicate p = builder.between(root.get("price").as(BigDecimal.class), from, to);
            query = query.where(p);

            List<Product> prods = session.createQuery(query).getResultList();
            return prods;
        }
    }

    public static List<Product> timKiemTheoDanhMuc(int cateID){
        SessionFactory f = HibernateUtils.getSessionFactory();

        try (Session session = f.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = builder.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            query.select(root);

            Predicate p = builder.equal(root.get("category"), cateID);
            query = query.where(p);

            List<Product> prods = session.createQuery(query).getResultList();
            return prods;
        }
    }

    public static void main(String[] args) {
        String name = "iphone";
        List<Product> result = timKiemTheoTen(name);
        System.out.printf("\nname = %s\n", name);
        result.forEach(product -> System.out.printf("%d %s %.1f\n", product.getId(), product.getName(), product.getPrice()));

        BigDecimal from = new BigDecimal(20000000);
        BigDecimal to = new BigDecimal(30000000);
        List<Product> result1 = timKiemTheoKhoangGia(from, to);
        System.out.printf("\n%.1f - %.1f\n", from, to);
        result1.forEach(product -> System.out.printf("%d %s %.1f\n", product.getId(), product.getName(), product.getPrice()));

        int cateID = 2;
        List<Product> result2 = timKiemTheoDanhMuc(cateID);
        System.out.printf("\nCate ID = %d\n", cateID);
        result2.forEach(product -> System.out.printf("%d %s %.1f %d\n", product.getId(), product.getName(), product.getPrice(), product.getCategory().getId()));
    }
}
