package ru.akirakozov.sd.refactoring.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.akirakozov.sd.refactoring.collector.ResultSetCollector;
import ru.akirakozov.sd.refactoring.db.DatabaseUtils;
import ru.akirakozov.sd.refactoring.domain.Product;
import ru.akirakozov.sd.refactoring.util.HtmlBuilder;
import ru.akirakozov.sd.refactoring.util.ResourceLoader;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sql = ResourceLoader.load("sql/select.sql");
        List<Product> products = DatabaseUtils.executeSqlQuery(sql, ResultSetCollector::collectProducts);

        HtmlBuilder htmlBuilder = new HtmlBuilder().addProducts(products);

        response.getWriter().println(htmlBuilder.build());

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
