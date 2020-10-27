package mysqlbase.api;

import java.sql.*;

public class Connector {
  private static String serverIP;
  private static String dbName;
  private static String userLogin;
  private static String userPassword;
  private static final String DRIVER_CLASS = "org.gjt.mm.mysql.Driver";
  private static Connection connection;

  public static void setParameters(String server_ip, String dbname, String login,
                   String password) throws Exception {
    serverIP = server_ip;
    dbName = dbname;
    userLogin = login;
    userPassword = password;
    Class.forName(DRIVER_CLASS);
  }

  public static Statement getStatement() throws SQLException {
    if (connection == null || connection.isClosed()) {
      connection = DriverManager.getConnection("jdbc:mysql://" + serverIP + "/" +
                                               dbName, userLogin, userPassword);
    }
    return connection.createStatement();
  }

  public static void close() throws SQLException  {
    connection.close();
  }

  public static void setTransaction() throws SQLException {
    connection.setAutoCommit(false);
  }

  public static void finalizeTransaction() throws SQLException {
      connection.commit();
      connection.setAutoCommit(true);
  }

  public static void rollBackTransaction() throws SQLException {
    connection.rollback();
    connection.setAutoCommit(true);
  }

  public static boolean isReady() {
    try {
      if (connection == null || connection.isClosed()) {
        return false;
      }
      return true;
    }
    catch (SQLException e) {
      e.printStackTrace();
      return false;
    }

  }
}
