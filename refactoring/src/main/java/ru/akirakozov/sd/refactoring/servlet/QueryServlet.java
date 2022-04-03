package ru.akirakozov.sd.refactoring.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.servlet.query.Query;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractServlet {

    public QueryServlet(ProductDao productDao) {
        super(productDao);
    }

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        Query query = Query.choose(command, productDao);
        String queryResponse = query.execute();

        response.getWriter().println(queryResponse);
    }

}
