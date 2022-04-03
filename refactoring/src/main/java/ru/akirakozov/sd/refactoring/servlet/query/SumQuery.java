package ru.akirakozov.sd.refactoring.servlet.query;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Madiyar Nurgazin
 */
public class SumQuery extends Query {

    @Override
    public void executeQuery() throws IOException {
        Optional<Long> sumPrices = productDao.findPricesSum();

        htmlBuilder.addLine("Summary price: ");

        sumPrices.ifPresent(sum -> htmlBuilder.addLine(sum.toString()));
    }
}
