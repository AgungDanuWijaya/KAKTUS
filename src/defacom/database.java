package defacom;

import function.main_function;
import java.sql.*;

public class database {

    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;
    ResultSetMetaData metadata = null;
    String className;
    public static int local = 0;

    public database(int server, main_function kernel) {
        className = database.class.getName();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading driver: " + e.getMessage());
        }
        try {
            if (server == database.local) {
                con = DriverManager.getConnection(kernel.a.url_db.getText(), kernel.a.user.getText(),
                        kernel.a.pass.getText());
            }
            stmt = con.createStatement();
        } catch (SQLException e) {
            System.err.println("MYSQL error");
            System.exit(1);
        }
    }

    public void closingDatabase() {
        // Lepaskan resource statement
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("Error SQL: " + e.getMessage());
            }
        }
        // Lepaskan koneksi database
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println("Error SQL: " + e.getMessage());
            }
        }

    }

    public ResultSet getResultData(String query) throws SQLException {
        rs = stmt.executeQuery(query);
        return rs;
    }

    public void setResultData(String query) {
        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
        }
    }

    public Statement getStatement() {
        return stmt;
    }

    public void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Error SQL: " + e.getMessage());
            }
        }
    }

    public Connection getConnection() {
        return con;
    }
}
