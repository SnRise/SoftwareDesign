package ru.akirakozov.sd.refactoring.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

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
public class GetProductServletTest extends ServletTestBase {

    @Test
    public void doGetTest() throws IOException {
        HttpServletRequest request = getRequest();
        HttpServletResponse response = getResponse();
        ProductDao productDao = getProductDao();
        PrintWriter printWriter = getPrintWriter();

        GetProductsServlet getProductsServlet = new GetProductsServlet(productDao);

        Mockito.when(productDao.findAll()).thenReturn(
                Arrays.asList(
                        new Product("iphone6", 300),
                        new Product("iPad", 500)
                )
        );
        Mockito.when(response.getWriter()).thenReturn(printWriter);

        getProductsServlet.doGet(request, response);

        Mockito.verify(productDao).findAll();

        String expectedHtml = "<html><body>\n"
                + "iphone6\t300</br>\n"
                + "iPad\t500</br>\n"
                + "</body></html>";

        assertEquals(expectedHtml, getResponseText());
    }
}
