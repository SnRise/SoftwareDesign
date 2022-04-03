package ru.akirakozov.sd.refactoring.collector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ru.akirakozov.sd.refactoring.domain.Product;

/**
 * @author Madiyar Nurgazin
 */
public class ResultSetCollector {

    public static List<Product> collectProducts(ResultSet rs) {
        List<Product> products = new ArrayList<>();

        try {
            while (rs.next()) {
                products.add(new Product(rs.getString("name"), rs.getLong("price")));
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Optional<Long> collectOne(ResultSet rs) {
        try {
            if (rs.next()) {
                return Optional.of(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }

        return Optional.empty();
    }


}
