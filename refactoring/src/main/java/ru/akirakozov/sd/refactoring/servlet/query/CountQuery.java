package ru.akirakozov.sd.refactoring.servlet.query;

import java.io.IOException;
import java.util.Optional;

import ru.akirakozov.sd.refactoring.collector.ResultSetCollector;
import ru.akirakozov.sd.refactoring.db.DatabaseUtils;
import ru.akirakozov.sd.refactoring.util.ResourceLoader;

/**
 * @author Madiyar Nurgazin
 */
public class CountQuery extends Query {

    @Override
    public void executeQuery() throws IOException {
        String sql = ResourceLoader.load("sql/count.sql");
        Optional<Long> price = DatabaseUtils.executeSqlQuery(sql, ResultSetCollector::collectPrice);

        htmlBuilder.addLine("Number of products: ");

        price.ifPresent(p -> htmlBuilder.addLine(p.toString()));
    }
}
