package com.rain.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

/**
 * @author rain.z
 * @description DBUtil
 * @date 2022/05/10
 */
public class DBUtil {
    private static Connection connection = null;
    private static Statement statement = null;

    public static ResultSet select(String sql) {
        return (ResultSet) execute(sql, true);
    }

    public static int insert(String sql) {
        return (int) execute(sql, false);
    }

    public static int delete(String sql) {
        return (int) execute(sql, false);
    }

    public static int update(String sql) {
        return (int) execute(sql, false);
    }

    public static void closeResource(ResultSet rs) {
        try {
            if (null != rs) {
                rs.close();
                rs = null;
            }

            if (null != statement) {
                statement.close();
                statement = null;
            }

            if (null != connection) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object execute(String sql, boolean isQuerySql) {
        try {
            connection = getConnection();
            assert connection != null;
            statement = connection.createStatement();
            if (isQuerySql) {
                return statement.executeQuery(sql);
            } else {
                return statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection getConnection() {
        try {

            boolean needCreateTable = !Files.exists(Paths.get("test.db"));

            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db");

            initTable(connection, needCreateTable);

            return connection;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    private static void initTable(Connection connection, boolean flag) throws SQLException {
        if (flag) {
            System.out.println("Sqlite 文件不存在，创建表。。。");
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE R_CONNECTION_INFO " + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," + " NAME           TEXT    NOT NULL, "
                    + " PARAMS            TEXT     NOT NULL) ";
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }
}
