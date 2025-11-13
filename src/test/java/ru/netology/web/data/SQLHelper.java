package ru.netology.web.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
    }

    @SneakyThrows
    public static void cleanDatabase() {
        try (var connection = getConnection()) {
            QUERY_RUNNER.execute(connection, "DELETE FROM credit_request_entity");
            QUERY_RUNNER.execute(connection, "DELETE FROM order_entity");
            QUERY_RUNNER.execute(connection, "DELETE FROM payment_entity");
        }
    }

    public static String getPaymentStatus() {
        String query = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
        try (var conn = DriverManager.getConnection(System.getProperty("db.url"), "app", "pass")) {
            var runner = new QueryRunner();
            return runner.query(conn, query, new ScalarHandler<>());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
