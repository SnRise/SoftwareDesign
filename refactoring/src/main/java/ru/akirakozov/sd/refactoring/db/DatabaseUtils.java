package ru.akirakozov.sd.refactoring.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Function;

import ru.akirakozov.sd.refactoring.util.ResourceLoader;

/**
 * @author Madiyar Nurgazin
 */
public class DatabaseUtils {

    private final static String DB_URL = "jdbc:sqlite:test.db";

    public static void init() throws Exception {
        try (Connection c = DriverManager.getConnection(DB_URL)) {
            String sql = ResourceLoader.load("sql/init.sql");
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    public static void executeSqlInsert(String name, long price) throws IOException {
        String sql = ResourceLoader.load("sql/insert.sql");
        try (Connection c = DriverManager.getConnection(DB_URL)) {
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setLong(2, price);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T executeSqlQuery(String sql, Function<ResultSet, T> resultCollector) {
        T result;
        try (Connection c = DriverManager.getConnection(DB_URL)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            result = resultCollector.apply(rs);
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
