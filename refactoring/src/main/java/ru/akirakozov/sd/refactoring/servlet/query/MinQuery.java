package ru.akirakozov.sd.refactoring.servlet.query;

import java.io.IOException;
import java.util.List;

import ru.akirakozov.sd.refactoring.domain.Product;

/**
 * @author Madiyar Nurgazin
 */
public class MinQuery extends Query {

    @Override
    public void executeQuery() throws IOException {
        List<Product> minProduct = productDao.findMinProduct();

        htmlBuilder.addHeader("Product with min price: ", 1).addProducts(minProduct);
    }
}
