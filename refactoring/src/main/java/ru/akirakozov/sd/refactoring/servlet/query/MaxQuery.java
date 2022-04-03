package ru.akirakozov.sd.refactoring.servlet.query;

import java.io.IOException;
import java.util.List;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.domain.Product;

/**
 * @author Madiyar Nurgazin
 */
public class MaxQuery extends Query {

    public MaxQuery(ProductDao productDao) {
        super(productDao);
    }

    @Override
    public void executeQuery() throws IOException {
        List<Product> maxProduct = productDao.findMaxProduct();

        htmlBuilder.addHeader("Product with max price: ", 1).addProducts(maxProduct);
    }
}
