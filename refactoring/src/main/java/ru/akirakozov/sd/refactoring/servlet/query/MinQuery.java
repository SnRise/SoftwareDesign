package ru.akirakozov.sd.refactoring.servlet.query;

import java.io.IOException;
import java.util.List;

import ru.akirakozov.sd.refactoring.collector.ResultSetCollector;
import ru.akirakozov.sd.refactoring.db.DatabaseUtils;
import ru.akirakozov.sd.refactoring.domain.Product;
import ru.akirakozov.sd.refactoring.util.ResourceLoader;

/**
 * @author Madiyar Nurgazin
 */
public class MinQuery extends Query {

    @Override
    public void executeQuery() throws IOException {
        String sql = ResourceLoader.load("sql/min.sql");
        List<Product> products = DatabaseUtils.executeSqlQuery(sql, ResultSetCollector::collectProducts);

        htmlBuilder.addHeader("Product with min price: ", 1).addProducts(products);
    }
}
