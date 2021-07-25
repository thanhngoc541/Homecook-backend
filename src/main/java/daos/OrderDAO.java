package daos;

import Utils.DBContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.Dish;
import dtos.Order;
import dtos.OrderItem;
import dtos.OrderMenu;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.Date;

public class OrderDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    private void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (conn != null) {
            conn.close();
        }
    }


    //Lay order cua customer (Customer View)
    public ArrayList<Order> getOrderByCustomerID(String customerID, int page, String searchPhrase) throws SQLException {
        ArrayList<Order> list = new ArrayList<>();
        String query = "EXEC getOrderByCustomerID "
        		+ "@CustomerID = ?, "
        		+ "@Page = ?, " +
                "@searchPhrase = ?";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                ps.setString(1, customerID);
                ps.setInt(2, page);
                ps.setString(3, searchPhrase);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Order ord = new Order();
                    String orderID = rs.getString("OrderID");
                    //Doi tu sql Timestamp qua Date java
                    java.sql.Timestamp tmpTime = new Timestamp(rs.getTimestamp("TimeStamp").getTime());
                    java.util.Date timeStamp = new Date(tmpTime.getTime());

                    java.sql.Timestamp tmpOrderDate= new Timestamp(rs.getTimestamp("OrderDate").getTime());
                    Date orderDate= new Date(tmpOrderDate.getTime());

                    String homecookID = rs.getString("HomeCookID");
                    //chuyen tu status id => name
                    String status = ord.getStatusName(rs.getInt("StatusID"));
                    double total = rs.getDouble("Total");
                    String note = rs.getString("Note");
                    String phone = rs.getString("ReceiverPhone");
                    String address = rs.getString("ReceiverAddress");
                    String name = rs.getString("ReceiverName");

                    ord.setOrderID(orderID);
                    ord.setCustomerID(customerID);
                    ord.setHomeCookID(homecookID);
                    ord.setTimeStamp(timeStamp.toInstant());
                    ord.setOrderDate(orderDate.toInstant());
                    ord.setStatus(status);
                    ord.setTotal(total);
                    ord.setNote(note);
                    ord.setReceiverPhone(phone);
                    ord.setReceiverAddress(address);
                    ord.setReceiverName(name);

                    list.add(ord);

                }
                return list;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }
    public ArrayList<Order> getOrderByCustomerIDAndStatus(String customerID,String status, String input , int page) throws SQLException {
        ArrayList<Order> list = new ArrayList<>();
        Order order= new Order();
        int stat= order.getStatusID(status);
        String query = "EXEC getOrderByCustomerIDAndStatus "
                + "@CustomerID = ?, "
                + "@StatusID= ?, " +
                "@searchPhrase = ? ,"
                + "@Page = ?";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                ps.setString(1, customerID);
                ps.setInt(2, stat);
                ps.setString(3, input);
                ps.setInt(4, page);

                rs = ps.executeQuery();
                while (rs.next()) {
                    Order ord = new Order();
                    String orderID = rs.getString("OrderID");
                    //Doi tu sql Timestamp qua Date java
                    java.sql.Timestamp tmpTime = new Timestamp(rs.getTimestamp("TimeStamp").getTime());
                    java.util.Date timeStamp = new Date(tmpTime.getTime());

                    java.sql.Timestamp tmpOrderDate= new Timestamp(rs.getTimestamp("OrderDate").getTime());
                    Date orderDate= new Date(tmpOrderDate.getTime());

                    String homecookID = rs.getString("HomeCookID");
                    //chuyen tu status id => name
                    String statusName = ord.getStatusName(rs.getInt("StatusID"));
                    double total = rs.getDouble("Total");
                    String note = rs.getString("Note");
                    String phone = rs.getString("ReceiverPhone");
                    String address = rs.getString("ReceiverAddress");
                    String name = rs.getString("ReceiverName");
                    boolean isMenu = rs.getBoolean("IsMenu");
                    ord.setOrderID(orderID);
                    ord.setCustomerID(customerID);
                    ord.setHomeCookID(homecookID);
                    ord.setTimeStamp(timeStamp.toInstant());
                    ord.setOrderDate(orderDate.toInstant());
                    ord.setStatus(statusName);
                    ord.setTotal(total);
                    ord.setNote(note);
                    ord.setReceiverPhone(phone);
                    ord.setReceiverAddress(address);
                    ord.setReceiverName(name);
                    ord.setMenu(isMenu);

                    list.add(ord);

                }
                return list;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }
    public ArrayList<Order> getOrderByHomeCookIDAndStatus(String homecookID, String search, int status, int page) throws SQLException {
        ArrayList<Order> list = new ArrayList<>();
        String query = "EXEC getOrderByHomeCookIDAndStatus "
                + "@HomeCookID = ?, "
                + "@StatusID= ?, " +
                "@searchPhrase = ?, "
                + "@Page = ?";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                ps.setString(1, homecookID);
                ps.setInt(2, status);
                ps.setString(3, search);
                ps.setInt(4, page);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Order ord = new Order("", null, null,null, 0, null);
                    String orderID = rs.getString("OrderID");
                    //Doi tu sql Timestamp qua Date java
                    java.sql.Timestamp tmpTime = new Timestamp(rs.getTimestamp("TimeStamp").getTime());
                    java.util.Date timeStamp = new Date(tmpTime.getTime());
                    //----------
                    java.sql.Timestamp tmpOrderDate= new Timestamp(rs.getTimestamp("OrderDate").getTime());
                    Date orderDate= new Date(tmpOrderDate.getTime());
                    //chuyen tu status id => name
                    String stat = ord.getStatusName(rs.getInt("StatusID"));
                    double total = rs.getDouble("Total");
                    String note = rs.getString("Note");
                    ord.setCustomerID(rs.getString("CustomerID"));
                    ord.setHomeCookID(rs.getString("HomeCookID"));
                    ord.setReceiverName(rs.getString("ReceiverName"));
                    ord.setReceiverPhone(rs.getString("ReceiverPhone"));
                    ord.setReceiverAddress(rs.getString("ReceiverAddress"));
                    ord.setMenu(rs.getBoolean("IsMenu"));
                    ord.setOrderID(orderID);
                    ord.setTimeStamp(timeStamp.toInstant());
                    ord.setOrderDate(orderDate.toInstant());
                    ord.setStatus(stat);
                    ord.setTotal(total);
                    ord.setNote(note);
                    list.add(ord);
                }
                return list;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }
    //lay order cua homecook (HomeCook View)
    public ArrayList<Order> getOrderByHomeCookID(String homecookID, String input, int page) throws SQLException {
        ArrayList<Order> list = new ArrayList<>();
        String query = "EXEC getOrderByHomeCookID "
        		+ "@HomeCookID = ?, "
        		+ "@Page = ?, " +
                "@searchPhrase = ?";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                ps.setString(1, homecookID);
                ps.setInt(2, page);
                ps.setString(3, input);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Order ord = new Order();
                    String orderID = rs.getString("OrderID");
                    //Doi tu sql Timestamp qua Date java
                    java.sql.Timestamp tmpTime = new Timestamp(rs.getTimestamp("TimeStamp").getTime());
                    java.util.Date timeStamp = new Date(tmpTime.getTime());
                    //----------
                    java.sql.Timestamp tmpOrderDate= new Timestamp(rs.getTimestamp("OrderDate").getTime());
                    Date orderDate= new Date(tmpOrderDate.getTime());

                    //chuyen tu status id => name
                    String status = ord.getStatusName(rs.getInt("StatusID"));
                    String address= rs.getString("ReceiverAddress");
                    String phone = rs.getString("ReceiverPhone");
                    String name= rs.getString("ReceiverName");
                    double total = rs.getDouble("Total");
                    String note = rs.getString("Note");
                    ord.setMenu(rs.getBoolean("IsMenu"));
                    ord.setOrderID(orderID);
                    ord.setTimeStamp(timeStamp.toInstant());
                    ord.setOrderDate(orderDate.toInstant());
                    ord.setReceiverPhone(phone);
                    ord.setReceiverName(name);
                    ord.setReceiverAddress(address);
                    ord.setStatus(status);
                    ord.setTotal(total);
                    ord.setNote(note);

                    list.add(ord);
                }
                return list;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }
    //lay AllOrder (admin)
    //-----------
    public ArrayList<Order> getOrderByStatus(String status, int page, String input) {
        ArrayList<Order> orders= new ArrayList<Order>();
        Order order= new Order();
        int stat= order.getStatusID(status);
        String query= "EXEC getOrderByStatus " +
                "@Status = ?, " +
                "@Page = ?, " +
                "@searchPhrase = ?";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps= conn.prepareStatement(query);
                ps.setInt(1, stat);
                ps.setInt(2, page);
                ps.setString(3, input);
                rs= ps.executeQuery();
                while(rs.next()) {
                    Order ord= new Order();
                    String orderID = rs.getString("OrderID");
                    //Doi tu sql Timestamp qua Date java
                    java.sql.Timestamp tmpTime = new Timestamp(rs.getTimestamp("TimeStamp").getTime());
                    java.util.Date timeStamp = new Date(tmpTime.getTime());
                    String customerID= rs.getString("CustomerID");
                    java.sql.Timestamp tmpOrderDate= new Timestamp(rs.getTimestamp("OrderDate").getTime());
                    Date orderDate= new Date(tmpOrderDate.getTime());

                    String homecookID = rs.getString("HomeCookID");
                    //chuyen tu status id => name
                    double total = rs.getDouble("Total");
                    String note = rs.getString("Note");
                    String phone = rs.getString("ReceiverPhone");
                    String address = rs.getString("ReceiverAddress");
                    String name = rs.getString("ReceiverName");

                    ord.setOrderID(orderID);
                    ord.setCustomerID(customerID);
                    ord.setHomeCookID(homecookID);
                    ord.setTimeStamp(timeStamp.toInstant());
                    ord.setOrderDate(orderDate.toInstant());
                    ord.setStatus(status);
                    ord.setTotal(total);
                    ord.setNote(note);
                    ord.setReceiverPhone(phone);
                    ord.setReceiverAddress(address);
                    ord.setReceiverName(name);
                    ord.setMenu(rs.getBoolean("IsMenu"));
                    orders.add(ord);
                }
                return orders;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public Order getOrderById(String orderID) {
        String query= "SELECT * FROM Orders WHERE OrderID = ?";
        Order ord= new Order();
        try {
            conn= DBContext.makeConnection();
            if (conn != null) {
                ps= conn.prepareStatement(query);
                ps.setString(1, orderID);
                rs= ps.executeQuery();
                while (rs.next()) {
                    Instant stamp = rs.getTimestamp("TimeStamp").toInstant();
                    Instant orderDate= rs.getTimestamp("OrderDate").toInstant();
                    String status = ord.getStatusName(rs.getInt("StatusID"));
                   ord= new Order(rs.getString("OrderID"), rs.getString("HomeCookID"), rs.getString("CustomerID"),
                           orderDate, stamp, status, rs.getString("ReceiverName"), rs.getString("ReceiverPhone"),
                           rs.getString("ReceiverAddress"), rs.getDouble("Total"), rs.getString("Note"),
                           rs.getBoolean("IsMenu"));
                }
                return ord;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public int getTotalOfOrder(String input) {
        int count= 0;
        ArrayList<String> orderID= new ArrayList<String>();
        String query="EXEC getTotalOfOrder " +
                "@searchPhrase = ?";
        try{
            conn= DBContext.makeConnection();
            if (conn != null) {
                ps= conn.prepareStatement(query);
                ps.setString(1, input);
                rs= ps.executeQuery();
                while (rs.next()) {
                    String orderId= rs.getString("OrderID");
                    orderID.add(orderId);
                }
                return orderID.size();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }
    public int getTotalHomeCookOrder(String HomeCookID) {
        int count= 0;

        String query = "EXEC getTotalHomeCookOrder "
                + "@HomeCookID = ? ";
        try{
            conn= DBContext.makeConnection();
            if (conn != null) {
                ps= conn.prepareStatement(query);
                ps.setString(1, HomeCookID);
                rs= ps.executeQuery();
                while (rs.next()) {
                    count = rs.getInt("total");

                }
                return count;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }
    public ArrayList getOrderByWeek(String homecookID) {
        ArrayList sales= new ArrayList();
        String query= "EXEC getOrderByWeek " +
                "@HomeCookID = ? ";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps= conn.prepareStatement(query);
                ps.setString(1, homecookID);
                rs= ps.executeQuery();
                while(rs.next()) {
                    sales.add(rs.getInt("Total"));
                }
                return sales;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public ArrayList getOrderByWeekAdmin() {
        ArrayList sales= new ArrayList();
        String query= "EXEC getOrderByWeekAdmin";
        try {
            conn= DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                rs= ps.executeQuery();
                while (rs.next()) {
                    sales.add(rs.getInt("Total"));
                }
                return sales;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public int countOrderItem(String orderID) {
        int count =0;
        String query= "EXEC countOrderItem " +
                "@OrderID = ? ";
        try {
            conn= DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                ps.setString(1, orderID);
                rs= ps.executeQuery();
                if (rs.next()) {
                    count= rs.getInt("Total");
                }
                return count;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
    public int countCustomerOrder(String customerID) {
        int count=0;
        String query = "EXEC getTotalCustomerOrder " +
                "@CustomerID =  ?";
        try {
            conn= DBContext.makeConnection();
            if (conn != null) {
                ps= conn.prepareStatement(query);
                ps.setString(1, customerID);
                rs= ps.executeQuery();
                if (rs.next()) {
                    count= rs.getInt("total");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }
    public int countOrderByDateRangeAndStatus (Instant fromDate, Instant toDate, String status) {
        int count =0;
        Order order= new Order();
        int stat= order.getStatusID(status);

        String query= "EXEC countOrderByDateRangeAndStatus " +
                "@FromDate = ? , " +
                "@ToDate = ? ," +
                "@StatusID = ?";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                Timestamp from= Timestamp.from(fromDate);
                Timestamp to= Timestamp.from(toDate);
                ps = conn.prepareStatement(query);
                ps.setTimestamp(1, from);
                ps.setTimestamp(2, to);
                ps.setInt(3, stat);
                rs= ps.executeQuery();
                if (rs.next()) {
                    count = rs.getInt("Total");
                }
                return count;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
    public int countCustomerOrderByIDAndStatus(String customerID, String status, String input) {
        int count= 0;
        Order order= new Order();
        String query = "EXEC countCustomerOrderByIDAndStatus " +
                "@CustomerID = ?, " +
                "@StatusID = ?, " +
                "@searchPhrase = ?";
        int stat= order.getStatusID(status);
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps= conn.prepareStatement(query);
                ps.setString(1,customerID);
                ps.setInt(2, stat);
                ps.setString(3, input);
                rs= ps.executeQuery();
                while (rs.next()) {
                    count= rs.getInt("Total");
                }
                return count;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }
    public int countHomeCookOrderByIDAndStatus(String homecookID, String status, String input) {
        int count= 0;
        Order order= new Order();
        String query = "EXEC countHomeCookOrderByIDAndStatus " +
                "@HomeCookID = ?, " +
                "@StatusID = ?, " +
                "@searchPhrase = ?";
        int stat= order.getStatusID(status);
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps= conn.prepareStatement(query);
                ps.setString(1,homecookID);
                ps.setInt(2, stat);
                ps.setString(3, input);
                rs= ps.executeQuery();
                while (rs.next()) {
                    count= rs.getInt("Total");
                }
                return count;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }
    public int countAllOrderByStatus(String status, String input) {
        int count =0;
        Order order= new Order();
        String query ="EXEC countAllOrderByStatus " +
                "@StatusID = ?, " +
                "@searchPhrase = ?";
        int stat= order.getStatusID(status);
        try {
            conn= DBContext.makeConnection();
            if (conn!= null ) {
                ps = conn.prepareStatement(query);
                ps.setInt(1, stat);
                ps.setString(2, input);
                rs= ps.executeQuery();
                while(rs.next()) {
                    count= rs.getInt("Total");
                }
                return count;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
    public ArrayList<Order> getSevenOrder() {
        ArrayList<Order> orderList= new ArrayList<Order>();
        String query= "EXEC getFirstSevenOrder";
        try{
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps= conn.prepareStatement(query);
                rs= ps.executeQuery();
                while(rs.next()) {
                    Order ord= new Order();


                    ord.setOrderID(rs.getString("OrderID"));

                    ord.setReceiverAddress(rs.getString("ReceiverAddress"));
                    ord.setReceiverName(rs.getString("ReceiverName"));
                    ord.setReceiverPhone(rs.getString("ReceiverPhone"));
                    ord.setStatus(ord.getStatusName(rs.getInt("StatusID")));
                    ord.setTotal(rs.getDouble("Total"));
                    orderList.add(ord);
                }
                return orderList;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public ArrayList<Order> getAllOrder(int page, String input) {
        ArrayList<Order> list= new ArrayList<>();
        String query= "EXEC getAllOrder " +
                "@searchPhrase = ?, " +
                "@Page = ?";
        try {
            conn= DBContext.makeConnection();
            if (conn != null) {
                ps= conn.prepareStatement(query);
                ps.setString(1, input);
                ps.setInt(2, page);
                rs=ps.executeQuery();
                while (rs.next()) {
                    Order ord = new Order();
                    //chuyen tu status id => name
                    java.sql.Timestamp tmpOrderDate= new Timestamp(rs.getTimestamp("OrderDate").getTime());
                    Date orderDate= new Date(tmpOrderDate.getTime());
                    //-----------
                    java.sql.Timestamp tmpTimeStamp= new Timestamp(rs.getTimestamp("TimeStamp").getTime());
                    Date timeStamp= new Date(tmpOrderDate.getTime());
                    String homecookID = rs.getString("HomeCookID");
                    String orderID= rs.getString("OrderID");
                    String status = ord.getStatusName(rs.getInt("StatusID"));
                    double total = rs.getDouble("Total");
                    String phone = rs.getString("ReceiverPhone");
                    String address = rs.getString("ReceiverAddress");
                    String name = rs.getString("ReceiverName");

                    ord.setOrderID(orderID);
                    ord.setOrderDate(orderDate.toInstant());
                    ord.setTimeStamp(timeStamp.toInstant());
                    ord.setHomeCookID(homecookID);
                    ord.setStatus(status);
                    ord.setTotal(total);
                    ord.setReceiverPhone(phone);
                    ord.setReceiverAddress(address);
                    ord.setReceiverName(name);
                    ord.setMenu(rs.getBoolean("IsMenu"));
                    list.add(ord);
                }
                return list;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public ArrayList<Order> getOrderByTimeRange(Instant fromDate, Instant toDate, int page) {
        ArrayList<Order> orders= new ArrayList<Order>();
        String query= "EXEC getOrderByTimeRange " +
                "@FromDate = ? , " +
                "@ToDate = ? , " +
                "@Page = ?";
        try {
            conn = DBContext.makeConnection();
            if (conn != null ) {
                Timestamp from= Timestamp.from(fromDate);
                Timestamp to= Timestamp.from(toDate);
                ps= conn.prepareStatement(query);
                ps.setTimestamp(1, from);
                ps.setTimestamp(2, to);
                ps.setInt(3,page);
                rs= ps.executeQuery();
                while (rs.next()) {
                    Order ord= new Order();
                    String orderID = rs.getString("OrderID");
                    //Doi tu sql Timestamp qua Date java
                    java.sql.Timestamp tmpTime = new Timestamp(rs.getTimestamp("TimeStamp").getTime());
                    java.util.Date timeStamp = new Date(tmpTime.getTime());
                    String customerID= rs.getString("CustomerID");
                    java.sql.Timestamp tmpOrderDate= new Timestamp(rs.getTimestamp("OrderDate").getTime());
                    Date orderDate= new Date(tmpOrderDate.getTime());
                    int status= rs.getInt("Status");

                    String homecookID = rs.getString("HomeCookID");
                    //chuyen tu status id => name
                    double total = rs.getDouble("Total");
                    String note = rs.getString("Note");
                    String phone = rs.getString("ReceiverPhone");
                    String address = rs.getString("ReceiverAddress");
                    String name = rs.getString("ReceiverName");

                    ord.setOrderID(orderID);
                    ord.setCustomerID(customerID);
                    ord.setHomeCookID(homecookID);
                    ord.setTimeStamp(timeStamp.toInstant());
                    ord.setOrderDate(orderDate.toInstant());
                    ord.setStatus(ord.getStatusName(status));
                    ord.setTotal(total);
                    ord.setNote(note);
                    ord.setReceiverPhone(phone);
                    ord.setReceiverAddress(address);
                    ord.setReceiverName(name);

                    orders.add(ord);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public ArrayList<Order> getOrderByTimeRangeAndStatus(Instant fromDate, Instant toDate,String status, int page) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();
        Order order= new Order();
        int stat= order.getStatusID(status);
        String query = "EXEC getOrderByTimeRangeAndStatus "
        		+ "@FromDate = ?, "
        		+ "@ToDate = ?, " +
                "@StatusID = ? ,"
        		+ "@Page = ?";

        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                Timestamp from= Timestamp.from(fromDate);
                Timestamp to= Timestamp.from(toDate);
                ps = conn.prepareStatement(query);

                ps.setTimestamp(1, from);
                ps.setTimestamp(2, to);
                ps.setInt(3, stat);
                ps.setInt(4, page);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Order ord= new Order();
                    String orderID = rs.getString("OrderID");
                    //Doi tu sql Timestamp qua Date java
                    java.sql.Timestamp tmpTime = new Timestamp(rs.getTimestamp("TimeStamp").getTime());
                    java.util.Date timeStamp = new Date(tmpTime.getTime());
                    String customerID= rs.getString("CustomerID");
                    java.sql.Timestamp tmpOrderDate= new Timestamp(rs.getTimestamp("OrderDate").getTime());
                    Date orderDate= new Date(tmpOrderDate.getTime());

                    String homecookID = rs.getString("HomeCookID");
                    //chuyen tu status id => name
                    double total = rs.getDouble("Total");
                    String note = rs.getString("Note");
                    String phone = rs.getString("ReceiverPhone");
                    String address = rs.getString("ReceiverAddress");
                    String name = rs.getString("ReceiverName");

                    ord.setOrderID(orderID);
                    ord.setCustomerID(customerID);
                    ord.setHomeCookID(homecookID);
                    ord.setTimeStamp(timeStamp.toInstant());
                    ord.setOrderDate(orderDate.toInstant());
                    ord.setStatus(status);
                    ord.setTotal(total);
                    ord.setNote(note);
                    ord.setReceiverPhone(phone);
                    ord.setReceiverAddress(address);
                    ord.setReceiverName(name);

                    orders.add(ord);
                }
                return orders;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }

    //Doi status
    //
    public boolean changeOrderStatus(String orderID, String statusName) throws SQLException {
        Order ord = new Order();
        int statusID = ord.getStatusID(statusName);
        String query = "EXEC changeOrderStatus "
        		+ "@StatusID = ?, "
        		+ "@OrderID = ?";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                ps.setInt(1, statusID);
                ps.setString(2, orderID);
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    public String insertOrderItems(OrderItem item) throws SQLException {
        String query = "EXEC insertOrderItems "
        		+ "@OrderID = ?, "
        		+ "@DishID = ?, "
        		+ "@Quantity = ?, "
        		+ "@Note = ?, "
        		+ "@TotalPrice = ?";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                //when we query, the db will commit automatically => ko dung addBatch duoc
                //Vay thi phai commit db manually
                    ps.setString(1, item.getOrderID());
                    ps.setString(2, item.getDish().getDishId());
                    ps.setInt(3, item.getQuantity());
                    ps.setString(4, item.getNote());
                    ps.setDouble(5, item.getTotalPrice());
                    ps.executeUpdate();
                return item.getDish().getDishId();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }

    public Order createOrder(Order ord) throws SQLException {
        double total =0;
        String query = "EXEC createOrder "
        		+ "@HomeCookID = ?, "
        		+ "@CustomerID = ?,"
        		+ "@TimeStamp = ?, "
                + "@OrderDate = ?, "
                + "@ReceiverPhone = ?, "
                + "@ReceiverAddress = ?, "
                + "@ReceiverName = ?, "
                + "@Total = ?, "
                + "@Note = ?, " +
                "@IsMenu = ?";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                Timestamp timeStamp= Timestamp.from(ord.getTimeStamp());
                Timestamp orderDate= Timestamp.from(ord.getOrderDate());
                ps = conn.prepareStatement(query);
                if (ord.getOrderItems() != null) {
                    for (OrderItem item : ord.getOrderItems()) {
                        total += item.getTotalPrice();
                    }
                    ord.setTotal(total);
                }
                if (ord.getOrderMenus() != null) {
                    for (OrderMenu menu : ord.getOrderMenus()) {
                        total += menu.getTotalPrice();
                    }
                    ord.setTotal(total);
                }
                ps.setString(1, ord.getHomeCookID());
                ps.setString(2, ord.getCustomerID());
                ps.setTimestamp(3, timeStamp);
                ps.setTimestamp(4, orderDate);
                ps.setString(5, ord.getReceiverPhone());
                ps.setString(6, ord.getReceiverAddress());
                ps.setString(7, ord.getReceiverName());
                ps.setDouble(8, ord.getTotal());
                ps.setString(9, ord.getNote());
                ps.setBoolean(10, ord.isMenu());
                rs= ps.executeQuery();
                if(rs.next()) {
                    ord.setOrderID(rs.getString("OrderID"));
                }
                return ord;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }
    public boolean deleteOrder(String OrderID) {
        String query= "EXEC deleteOrder " +
                "@OrderID = ?";
        try {
            conn= DBContext.makeConnection();
            if (conn != null) {
                ps= conn.prepareStatement(query);
                ps.setString(1, OrderID);
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    //Search order by Order ID or Phone
    public int getTotalSearchedOrder (String input, String status) {
        Order order= new Order();
        int stat= order.getStatusID(status);
        int count= 0;
        String query= "EXEC countSearchOrder " +
                "@searchPhrase = ?, " +
                "@StatusID= ?";
        try {
            conn= DBContext.makeConnection();
            if (conn != null) {
                ps= conn.prepareStatement(query);
                ps.setString(1, input);
                ps.setInt(2, stat);
                rs= ps.executeQuery();
                while (rs.next()) {
                    count = rs.getInt("total");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }
    //Customer xem item trong ord
    public ArrayList<OrderItem> getListItemByOrderID(String ordID, int page) throws SQLException {
        ArrayList<OrderItem> list = new ArrayList<>();
        String query = "EXEC getListItemByOrderID "
        		+ "@OrderID = ?, "
                + "@Page = ?";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                ps.setString(1, ordID);
                ps.setInt(2, page);
                rs = ps.executeQuery();
            }
            while (rs.next()) {
                Dish dish = new Dish(rs.getString("DishID"), rs.getString("HomeCookID"), rs.getString("DishName"),
                        rs.getDouble("Price"), rs.getString("ImageURL"));
                list.add(new OrderItem(rs.getString("ItemID"), rs.getString("OrderID"), dish, rs.getInt("Quantity"),
                        rs.getString("Note"), rs.getDouble("TotalPrice")));
            }
            return list;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }

    public static void main(String[] args) throws ParseException, SQLException {
        OrderDAO dao = new OrderDAO();

        Gson gson= new GsonBuilder().setPrettyPrinting().create();

        java.util.Date date= new Date();
        java.sql.Date sqldate= new java.sql.Date(date.getTime());
        Instant ts= Instant.ofEpochSecond(1624726800);
        Instant od= Instant.ofEpochSecond(1626282000);
        System.out.println(ts);
        System.out.println(od);
        Timestamp TS= Timestamp.from(ts);
        Timestamp OD= Timestamp.from(od);
        System.out.println(TS);
        System.out.println(OD);
        System.out.println(dao.getOrderByWeekAdmin());
//
    }
}


