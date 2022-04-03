package ru.akirakozov.sd.refactoring.servlet.query;

import java.io.IOException;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.util.HtmlBuilder;

/**
 * @author Madiyar Nurgazin
 */
public abstract class Query {
    protected final HtmlBuilder htmlBuilder = new HtmlBuilder();
    protected final ProductDao productDao = new ProductDao();

    public static Query choose(String command) {
        switch (command) {
            case "min":
                return new MinQuery();
            case "max":
                return new MaxQuery();
            case "sum":
                return new SumQuery();
            case "count":
                return new CountQuery();
            default:
                return new UnknownCommandQuery(command);
        }
    }

    protected abstract void executeQuery() throws IOException;

    public String execute() throws IOException {
        executeQuery();
        return htmlBuilder.build();
    }

}
