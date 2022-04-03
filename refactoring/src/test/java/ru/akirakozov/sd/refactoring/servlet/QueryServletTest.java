package ru.akirakozov.sd.refactoring.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.domain.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Madiyar Nurgazin
 */
public class QueryServletTest extends ServletTestBase {

    @Test
    public void doGetMaxTest() throws IOException {
        HttpServletRequest request = getRequest();
        HttpServletResponse response = getResponse();
        ProductDao productDao = getProductDao();
        PrintWriter printWriter = getPrintWriter();

        QueryServlet queryServlet = new QueryServlet(productDao);

        List<Product> maxProduct = new ArrayList<>();
        maxProduct.add(new Product("iphone6", 300));

        Mockito.when(request.getParameter("command")).thenReturn("max");
        Mockito.when(productDao.findMaxProduct()).thenReturn(maxProduct);
        Mockito.when(response.getWriter()).thenReturn(printWriter);

        queryServlet.doGet(request, response);

        Mockito.verify(productDao).findMaxProduct();

        String expectedHtml = "<html><body>\n"
                + "<h1>Product with max price: </h1>\n"
                + "iphone6\t300</br>\n"
                + "</body></html>";

        assertEquals(expectedHtml, getResponseText());
    }
    
    @Test
    public void doGetMinTest() throws IOException {
        HttpServletRequest request = getRequest();
        HttpServletResponse response = getResponse();
        ProductDao productDao = getProductDao();
        PrintWriter printWriter = getPrintWriter();

        QueryServlet queryServlet = new QueryServlet(productDao);

        List<Product> maxProduct = new ArrayList<>();
        maxProduct.add(new Product("iphone6", 300));

        Mockito.when(request.getParameter("command")).thenReturn("min");
        Mockito.when(productDao.findMinProduct()).thenReturn(maxProduct);
        Mockito.when(response.getWriter()).thenReturn(printWriter);

        queryServlet.doGet(request, response);

        Mockito.verify(productDao).findMinProduct();

        String expectedHtml = "<html><body>\n"
                + "<h1>Product with min price: </h1>\n"
                + "iphone6\t300</br>\n"
                + "</body></html>";

        assertEquals(expectedHtml, getResponseText());
    }
    
    @Test
    public void doGetSumTest() throws IOException {
        HttpServletRequest request = getRequest();
        HttpServletResponse response = getResponse();
        ProductDao productDao = getProductDao();
        PrintWriter printWriter = getPrintWriter();

        QueryServlet queryServlet = new QueryServlet(productDao);

        Mockito.when(request.getParameter("command")).thenReturn("sum");
        Mockito.when(productDao.findPricesSum()).thenReturn(Optional.of(300L));
        Mockito.when(response.getWriter()).thenReturn(printWriter);

        queryServlet.doGet(request, response);

        Mockito.verify(productDao).findPricesSum();

        String expectedHtml = "<html><body>\n"
                + "Summary price: \n"
                + "300\n"
                + "</body></html>";

        assertEquals(expectedHtml, getResponseText());
    }
    
    @Test
    public void doGetCountTest() throws IOException {
        HttpServletRequest request = getRequest();
        HttpServletResponse response = getResponse();
        ProductDao productDao = getProductDao();
        PrintWriter printWriter = getPrintWriter();

        QueryServlet queryServlet = new QueryServlet(productDao);

        Mockito.when(request.getParameter("command")).thenReturn("count");
        Mockito.when(productDao.findProductsCount()).thenReturn(Optional.of(1L));
        Mockito.when(response.getWriter()).thenReturn(printWriter);

        queryServlet.doGet(request, response);

        Mockito.verify(productDao).findProductsCount();

        String expectedHtml = "<html><body>\n"
                + "Number of products: \n"
                + "1\n"
                + "</body></html>";

        assertEquals(expectedHtml, getResponseText());
    }
}
