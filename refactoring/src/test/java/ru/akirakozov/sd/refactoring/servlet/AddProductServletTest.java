package ru.akirakozov.sd.refactoring.servlet;

import java.io.IOException;
import java.io.PrintWriter;

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
public class AddProductServletTest extends ServletTestBase {

    @Test
    public void doGetTest() throws IOException {
        HttpServletRequest request = getRequest();
        HttpServletResponse response = getResponse();
        ProductDao productDao = getProductDao();
        PrintWriter printWriter = getPrintWriter();

        AddProductServlet addProductServlet = new AddProductServlet(productDao);
        Product product = new Product("iPad", 500);

        Mockito.when(request.getParameter("name")).thenReturn(product.getName());
        Mockito.when(request.getParameter("price")).thenReturn(String.valueOf(product.getPrice()));
        Mockito.when(response.getWriter()).thenReturn(printWriter);

        addProductServlet.doGet(request, response);

        Mockito.verify(productDao).save(product);

        assertEquals("OK\n", getResponseText());
    }
}
