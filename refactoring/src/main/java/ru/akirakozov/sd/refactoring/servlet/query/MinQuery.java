package ru.akirakozov.sd.refactoring.servlet.query;

import java.io.IOException;
import java.util.List;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.domain.Product;

/**
 * @author Madiyar Nurgazin
 */
public class MinQuery extends Query {

    public MinQuery(ProductDao productDao) {
        super(productDao);
    }

    @Override
    public void executeQuery() throws IOException {
        List<Product> minProduct = productDao.findMinProduct();

        htmlBuilder.addHeader("Product with min price: ", 1).addProducts(minProduct);
    }
}
