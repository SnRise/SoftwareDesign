package ru.akirakozov.sd.refactoring.servlet.query;

import java.io.IOException;
import java.util.Optional;

import ru.akirakozov.sd.refactoring.dao.ProductDao;

/**
 * @author Madiyar Nurgazin
 */
public class CountQuery extends Query {

    public CountQuery(ProductDao productDao) {
        super(productDao);
    }

    @Override
    public void executeQuery() throws IOException {
        Optional<Long> countPrices = productDao.findProductsCount();

        htmlBuilder.addLine("Number of products: ");

        countPrices.ifPresent(count -> htmlBuilder.addLine(count.toString()));
    }
}
