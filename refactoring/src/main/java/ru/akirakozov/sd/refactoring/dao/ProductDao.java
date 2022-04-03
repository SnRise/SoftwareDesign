package ru.akirakozov.sd.refactoring.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import ru.akirakozov.sd.refactoring.collector.ResultSetCollector;
import ru.akirakozov.sd.refactoring.db.Database;
import ru.akirakozov.sd.refactoring.domain.Product;
import ru.akirakozov.sd.refactoring.util.ResourceLoader;

/**
 * @author Madiyar Nurgazin
 */
public class ProductDao {

    private final Database db;

    public ProductDao(Database db) {
        this.db = db;
    }

    public void save(Product product) throws IOException {
        db.executeSqlInsert(product.getName(), product.getPrice());
    }

    public List<Product> findAll() throws IOException {
        return find("sql/select.sql", ResultSetCollector::collectProducts);
    }

    public List<Product> findMaxProduct() throws IOException {
        return find("sql/max.sql", ResultSetCollector::collectProducts);
    }

    public List<Product> findMinProduct() throws IOException {
        return find("sql/min.sql", ResultSetCollector::collectProducts);
    }

    public Optional<Long> findPricesSum() throws IOException {
        return find("sql/sum.sql", ResultSetCollector::collectOne);
    }

    public Optional<Long> findProductsCount() throws IOException {
        return find("sql/count.sql", ResultSetCollector::collectOne);
    }

    private <T> T find(String sqlPath, Function<ResultSet, T> collector) throws IOException {
        String sql = ResourceLoader.load(sqlPath);

        return db.executeSqlQuery(sql, collector);
    }
}
