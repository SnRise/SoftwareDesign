package ru.akirakozov.sd.refactoring.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.db.Database;
import ru.akirakozov.sd.refactoring.domain.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Madiyar Nurgazin
 */
public class ProductDaoTest {

    private final static String TEST_DB_URL = "jdbc:sqlite:productDaoTest.db";

    private Database db;
    private ProductDao productDao;
    private List<Product> products;

    @BeforeEach
    public void init() throws Exception {
        db = new Database(TEST_DB_URL);
        db.init();

        productDao = new ProductDao(db);

        products = Arrays.asList(
                new Product("product1", 10),
                new Product("product2", 20),
                new Product("product3", 30)
        );
    }

    @AfterEach
    public void clean() throws Exception {
        db.clean();
    }

    @Test
    public void findAllEmptyTest() throws IOException {
        assertTrue(productDao.findAll().isEmpty());
    }

    @Test
    public void findMaxEmptyTest() throws IOException {
        assertTrue(productDao.findMaxProduct().isEmpty());
    }

    @Test
    public void findMinEmptyTest() throws IOException {
        assertTrue(productDao.findMinProduct().isEmpty());
    }

    @Test
    public void findCountEmptyTest() throws IOException {
        assertEquals(0, productDao.findProductsCount().get());
    }

    @Test
    public void findSumEmptyTest() throws IOException {
        assertEquals(0, productDao.findPricesSum().get());
    }

    @Test
    public void saveProductTest() throws IOException {
        Product product = new Product("iphone6", 300);

        productDao.save(product);

        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(product);

        assertEquals(expectedProducts, productDao.findAll());
    }

    @Test
    public void findMaxTest() throws IOException {
        fillDb();

        List<Product> expectedMaxProduct = new ArrayList<>();
        expectedMaxProduct.add(new Product("product3", 30));

        assertEquals(expectedMaxProduct, productDao.findMaxProduct());
    }

    @Test
    public void findMinTest() throws IOException {
        fillDb();

        List<Product> expectedMaxProduct = new ArrayList<>();
        expectedMaxProduct.add(new Product("product1", 10));

        assertEquals(expectedMaxProduct, productDao.findMinProduct());
    }

    @Test
    public void findCountTest() throws IOException {
        fillDb();

        assertEquals(3, productDao.findProductsCount().get());
    }

    @Test
    public void findSumTest() throws IOException {
        fillDb();

        assertEquals(60, productDao.findPricesSum().get());
    }

    private void fillDb() throws IOException {
        for (Product product : products) {
            productDao.save(product);
        }
    }
}
