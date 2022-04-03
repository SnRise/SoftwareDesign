package ru.akirakozov.sd.refactoring.servlet.query;

import java.io.IOException;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.util.HtmlBuilder;

/**
 * @author Madiyar Nurgazin
 */
public abstract class Query {
    protected final HtmlBuilder htmlBuilder;
    protected final ProductDao productDao;

    protected Query(ProductDao productDao) {
        this.htmlBuilder = new HtmlBuilder();
        this.productDao = productDao;
    }

    public static Query choose(String command, ProductDao productDao) {
        switch (command) {
            case "min":
                return new MinQuery(productDao);
            case "max":
                return new MaxQuery(productDao);
            case "sum":
                return new SumQuery(productDao);
            case "count":
                return new CountQuery(productDao);
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
