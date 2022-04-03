package ru.akirakozov.sd.refactoring.servlet;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import ru.akirakozov.sd.refactoring.dao.ProductDao;

/**
 * @author Madiyar Nurgazin
 */
public class ServletTestBase {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Connection connection;
    private Statement statement;
    private PrintWriter printWriter;
    private StringWriter stringWriter;
    private ProductDao productDao;


    @BeforeEach
    public void prepareMocks() {
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        connection = Mockito.mock(Connection.class);
        statement = Mockito.mock(Statement.class);
        productDao = Mockito.mock(ProductDao.class);

        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
    }

    protected HttpServletRequest getRequest() {
        return request;
    }

    protected HttpServletResponse getResponse() {
        return response;
    }

    protected Connection getConnection() {
        return connection;
    }

    protected Statement getStatement() {
        return statement;
    }

    protected PrintWriter getPrintWriter() {
        return printWriter;
    }

    protected String getResponseText() {
        return stringWriter.toString().trim();
    }

    protected ProductDao getProductDao() {
        return productDao;
    }
}
