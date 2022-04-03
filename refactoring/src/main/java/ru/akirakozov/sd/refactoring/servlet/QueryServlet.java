package ru.akirakozov.sd.refactoring.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.akirakozov.sd.refactoring.servlet.query.Query;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        Query query = Query.choose(command);
        String queryResponse = query.execute();

        response.getWriter().println(queryResponse);
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
