package ru.akirakozov.sd.refactoring.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        String sql;

        HtmlBuilder htmlBuilder = new HtmlBuilder();

        if ("max".equals(command)) {
            sql = ResourceLoader.load("sql/max.sql");
            List<Product> products = DatabaseUtils.executeSqlQuery(sql, ResultSetCollector::collectProducts);

            htmlBuilder.addHeader("Product with max price: ", 1);

            for (Product product : products) {
                htmlBuilder.addLine(product.getName() + "\t" + product.getPrice() + "</br>");
            }
        } else if ("min".equals(command)) {
            sql = ResourceLoader.load("sql/min.sql");
            List<Product> products = DatabaseUtils.executeSqlQuery(sql, ResultSetCollector::collectProducts);

            htmlBuilder.addHeader("Product with min price: ", 1);

            for (Product product : products) {
                htmlBuilder.addLine(product.getName() + "\t" + product.getPrice() + "</br>");
            }
        } else if ("sum".equals(command)) {
            sql = ResourceLoader.load("sql/sum.sql");
            Optional<Long> price = DatabaseUtils.executeSqlQuery(sql, ResultSetCollector::collectPrice);

            htmlBuilder.addLine("Summary price: ");

            price.ifPresent(p -> htmlBuilder.addLine(p.toString()));
        } else if ("count".equals(command)) {
            sql = ResourceLoader.load("sql/count.sql");
            Optional<Long> price = DatabaseUtils.executeSqlQuery(sql, ResultSetCollector::collectPrice);

            htmlBuilder.addLine("Number of products: ");

            price.ifPresent(p -> htmlBuilder.addLine(p.toString()));
        } else {
            htmlBuilder.addLine("Unknown command: " + command);
        }

        response.getWriter().println(htmlBuilder.build());
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
