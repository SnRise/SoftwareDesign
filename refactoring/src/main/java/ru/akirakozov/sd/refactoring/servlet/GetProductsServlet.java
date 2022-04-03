package ru.akirakozov.sd.refactoring.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.domain.Product;
import ru.akirakozov.sd.refactoring.util.HtmlBuilder;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    private final ProductDao productDao = new ProductDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> products = productDao.findAll();

        HtmlBuilder htmlBuilder = new HtmlBuilder().addProducts(products);

        response.getWriter().println(htmlBuilder.build());

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
