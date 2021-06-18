package  Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {

    public static Connection makeConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            //2. Tao object ket noi CSDL gom 3 param: url, username, pwd
            String url = "jdbc:sqlserver://homecook.database.windows.net:1433;" +
                    "database=homecook;" +
                    "user=azureAdmin@homecook;" +
                    "password=home@dmin391;" +
                    "encrypt=true;" +
                    "trustServerCertificate=false;" +
                    "hostNameInCertificate=*.database.windows.net;" +
                    "loginTimeout=30;";
            Connection con = DriverManager.getConnection(url);
            return con;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

  public static void main(String[] agrs){
      Connection con = makeConnection();
      if(con == null) System.out.println("ket noi db eo dc");
      else System.out.println("ket noi database thanh cong");
  }
}