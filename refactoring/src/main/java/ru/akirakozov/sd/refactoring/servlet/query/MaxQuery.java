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
public class MaxQuery extends Query {

    @Override
    public void executeQuery() throws IOException {
        String sql = ResourceLoader.load("sql/max.sql");
        List<Product> products = DatabaseUtils.executeSqlQuery(sql, ResultSetCollector::collectProducts);

        htmlBuilder.addHeader("Product with max price: ", 1);

        for (Product product : products) {
            htmlBuilder.addLine(product.getName() + "\t" + product.getPrice() + "</br>");
        }
    }
}
