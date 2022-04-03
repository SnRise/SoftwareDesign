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
import ru.akirakozov.sd.refactoring.util.ResourceLoader;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        String sql;

        if ("max".equals(command)) {
            sql = ResourceLoader.load("sql/max.sql");
            List<Product> products = DatabaseUtils.executeSqlQuery(sql, ResultSetCollector::collectProducts);

            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Product with max price: </h1>");

            for (Product product : products) {
                response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
            }
            response.getWriter().println("</body></html>");
        } else if ("min".equals(command)) {
            sql = ResourceLoader.load("sql/min.sql");
            List<Product> products = DatabaseUtils.executeSqlQuery(sql, ResultSetCollector::collectProducts);

            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Product with min price: </h1>");

            for (Product product : products) {
                response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
            }
            response.getWriter().println("</body></html>");
        } else if ("sum".equals(command)) {
            sql = ResourceLoader.load("sql/sum.sql");
            Optional<Long> price = DatabaseUtils.executeSqlQuery(sql, ResultSetCollector::collectPrice);

            response.getWriter().println("<html><body>");
            response.getWriter().println("Summary price: ");

            if (price.isPresent()) {
                response.getWriter().println(price.get());
            }
            response.getWriter().println("</body></html>");
        } else if ("count".equals(command)) {
            sql = ResourceLoader.load("sql/count.sql");
            Optional<Long> price = DatabaseUtils.executeSqlQuery(sql, ResultSetCollector::collectPrice);

            response.getWriter().println("<html><body>");
            response.getWriter().println("Number of products: ");

            if (price.isPresent()) {
                response.getWriter().println(price.get());
            }
            response.getWriter().println("</body></html>");
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
