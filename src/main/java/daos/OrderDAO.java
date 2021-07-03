package daos;

import Utils.DBContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.Dish;
import dtos.Order;
import dtos.OrderItem;
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
    public ArrayList<Order> getOrderByCustomerID(String customerID, int page) throws SQLException {
        ArrayList<Order> list = new ArrayList<>();
        String query = "EXEC getOrderByCustomerID "
        		+ "@CustomerID = ?, "
        		+ "@Page = ?";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                ps.setString(1, customerID);
                ps.setInt(2, page);
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
    //lay order cua homecook (HomeCook View)
    public ArrayList<Order> getOrderByHomeCookID(String homecookID, int page) throws SQLException {
        ArrayList<Order> list = new ArrayList<>();
        String query = "EXEC getOrderByHomeCookID "
        		+ "@HomeCookID = ?, "
        		+ "@Page = ?";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                ps.setString(1, homecookID);
                ps.setInt(2, page);
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
                    String status = ord.getStatusName(rs.getInt("StatusID"));
                    double total = rs.getDouble("Total");
                    String note = rs.getString("Note");

                    ord.setOrderID(orderID);
                    ord.setTimeStamp(timeStamp.toInstant());
                    ord.setOrderDate(orderDate.toInstant());
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
    public ArrayList<Order> getAllOrder() {
        ArrayList<Order> list= new ArrayList<>();
        String query= "EXEC getAllOrder";
        try {
            conn= DBContext.makeConnection();
            if (conn != null) {
                ps= conn.prepareStatement(query);
                rs=ps.executeQuery();
                while (rs.next()) {
                    Order ord = new Order();
                    String orderID = rs.getString("OrderID");
                    //Doi tu sql Timestamp qua Date java
                    Instant stamp = rs.getTimestamp("TimeStamp").toInstant();
                    Instant orderDate= rs.getTimestamp("OrderDate").toInstant();
                    //chuyen tu status id => name
                    String homecookID = rs.getString("HomeCookID");
                    String customerID= rs.getString("CustomerID");
                    String status = ord.getStatusName(rs.getInt("StatusID"));
                    double total = rs.getDouble("Total");
                    String note = rs.getString("Note");
                    String phone = rs.getString("ReceiverPhone");
                    String address = rs.getString("ReceiverAddress");
                    String name = rs.getString("ReceiverName");

                    ord.setOrderID(orderID);
                    ord.setCustomerID(customerID);
                    ord.setHomeCookID(homecookID);
                    ord.setTimeStamp(stamp);
                    ord.setOrderDate(orderDate);
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
        }
        return null;
    }
    //Khi lay date tu object ve thi phai convert sang string de goi ham nay
    public ArrayList<Order> getOrderByTimeRange(String fromDate, String toDate, int page) throws SQLException {
        ArrayList<Order> list = new ArrayList<>();
        String query = "EXEC getOrderByTimeRange "
        		+ "@FromDate = ?,"
        		+ "@ToDate = ?, "
        		+ "@Page = ?";

        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                ps.setString(1, fromDate);
                ps.setString(2, toDate);
                ps.setInt(3, page);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Order ord = new Order("", null, null, null, 0, null);
                    String orderID = rs.getString("OrderID");
                    //Doi tu sql Timestamp qua Date java
                    Instant timeStamp = rs.getTime("TimeStamp").toInstant();
                    //chuyen tu status id => name
                    String status = ord.getStatusName(rs.getInt("StatusID"));
                    double total = rs.getDouble("Total");
                    String note = rs.getString("Note");

                    ord.setOrderID(orderID);
                    ord.setTimeStamp(timeStamp);
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

    public String insertOrderItems(Order ord) throws SQLException {
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
                for (OrderItem item : ord.getOrderItems()) {
                    item.setOrderID(ord.getOrderID());
                    ps.setString(1, item.getOrderID());
                    ps.setString(2, item.getDish().getDishId());
                    ps.setInt(3, item.getQuantity());
                    ps.setString(4, item.getNote());
                    ps.setDouble(5, item.getTotalPrice());
                    ps.executeUpdate();
                    item.setItemID(rs.getString("ItemID"));
                }
                return ord.getOrderID();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }

    public Order createOrder(Order ord) throws SQLException {
        String query = "EXEC createOrder "
        		+ "@HomeCookID = ?, "
        		+ "@CustomerID = ?,"
        		+ "@TimeStamp = ?, "
                + "@OrderDate = ?, "
                + "@ReceiverPhone = ?, "
                + "@ReceiverAddress = ?, "
                + "@ReceiverName = ?, "
                + "@Total = ?, "
                + "@Note = ? ";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                Timestamp timeStamp= Timestamp.from(ord.getTimeStamp());
                Timestamp orderDate= Timestamp.from(ord.getOrderDate());
                ps = conn.prepareStatement(query);
                ps.setString(1, ord.getHomeCookID());
                ps.setString(2, ord.getCustomerID());
                ps.setTimestamp(3, timeStamp);
                ps.setTimestamp(4, orderDate);
                ps.setString(5, ord.getReceiverPhone());
                ps.setString(6, ord.getReceiverAddress());
                ps.setString(7, ord.getReceiverName());
                ps.setDouble(8, ord.getTotal());
                ps.setString(9, ord.getNote());
                rs= ps.executeQuery();
                if(rs.next()) {

                    ord.setOrderID(rs.getString("OrderID"));
                    return ord;
                }
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
        Instant ts= Instant.ofEpochSecond(1625450400);
        Instant od= Instant.ofEpochSecond(1625282813);
        System.out.println(ts);
        System.out.println(od);
        Timestamp TS= Timestamp.from(ts);
        Timestamp OD= Timestamp.from(od);

        System.out.println(dao.deleteOrder("c4781043-71e9-4fb5-93c2-482cae9782e8"));
//        System.out.println(TS);
//        System.out.println(OD);
        //---------------------
//        ArrayList<Order> list= dao.getOrderByCustomerID("535340B1-8053-4819-8772-488577A10639", 1);
////        System.out.println(list);
//        Gson gson= new GsonBuilder().setPrettyPrinting().create();
//        System.out.println(gson.toJson(list));
        //-----------------------
//        Order ord= new Order("6ABE8D62-72D2-4F13-B790-C35EA529365B", "6BB74684-993E-4286-B4BE-7E723BBA1614",
//                ts,  od, "0909889029", "06 Tan Son Street, Ward 11, Go Vap District","Nguyen Le Kieu Tram", 7.0,
//                "Test create order DAO" );

//
//        System.out.println(dao.insertOrderItems(ord));
        //---------------------------
//        Timestamp TS= Timestamp.from(ts);
//        Timestamp OD= Timestamp.from(od);
////        Time OD= new Time(od.toEpochMilli());
//
//        System.out.println(ts);
//        System.out.println(od);
//        System.out.println(TS);
//        System.out.println(OD);
//        ps.setTime(3, new Time(ord.getTimeStamp().toEpochMilli()));
//        ps.setTime(4, new Time(ord.getOrderDate().toEpochMilli()));
        //-------
//        String data= "{\n" +
//                "        \"HomeCookID\": \"6ABE8D62-72D2-4F13-B790-C35EA529365B\",\n" +
//                "        \"CustomerID\": \"6BB74684-993E-4286-B4BE-7E723BBA1614\",\n" +
//                "        \"TimeStamp\": {\n" +
//                "            \"seconds\": 1621530000,\n" +
//                "            \"nanos\": 0\n" +
//                "        },\n" +
//                "        \"OrderDate\": {\n" +
//                "            \"seconds\": 1624294800,\n" +
//                "            \"nanos\": 0\n" +
//                "        },\n" +
//                "        \"ReceiverPhone\": \"0909889029\",\n" +
//                "        \"ReceiverAddress\": \"06 Tan Son Street, Ward 11, Go Vap District, Ho Chi Minh City\",\n" +
//                "        \"ReceiverName\": \"Nguyen Le Kieu Tram\",\n" +
//                "        \"Total\": 13.5,\n" +
//                "        \"Note\": \"Test API lan 1\",\n" +
//                "        \"OrderItems\":\n" +
//                "            [\n" +
//                "              {\n" +
//                "                \"Quantity\": 1,\n" +
//                "                \"Note\":\"Item2\",\n" +
//                "                \"TotalPrice\": 3.5,\n" +
//                "                \"Dish\": {\n" +
//                "                    \"DishName\": \"Brown Rice Yogurt\",\n" +
//                "                    \"DishId\": \"9C61505A-82CE-4EC7-8BD6-B16004685E57\",\n" +
//                "                    \"HomeCookID\": \"6ABE8D62-72D2-4F13-B790-C35EA529365B\",\n" +
//                "                    \"Price\": 3.5,\n" +
//                "                    \"IsAvailable\": false,\n" +
//                "                    \"ImageURL\": \"https://www.tasteofhome.com/wp-content/uploads/2021/01/tasty-butter-chicken-curry-dish-from-indian-cuisine-1277362334.jpg\"\n" +
//                "                }\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"Quantity\": 3,\n" +
//                "                \"Note\":\"Item2\",\n" +
//                "                \"TotalPrice\": 3.5,\n" +
//                "                \"Dish\": {\n" +
//                "                    \"DishName\": \"Hu Tieu Chay\",\n" +
//                "                    \"DishId\": \"427DEE88-94C5-4872-B046-6815D6A6C552\",\n" +
//                "                    \"HomeCookID\": \"B489E4B9-9ABC-41B9-88FC-380579FB3CC6\",\n" +
//                "                    \"Price\": 3.5,\n" +
//                "                    \"IsAvailable\": true,\n" +
//                "                    \"ImageURL\": \"https://images.immediate.co.uk/production/volatile/sites/30/2020/08/mexican-chicken-burger_1-b5cca6f.jpg?quality=90&resize=440%2C400\"\n" +
//                "                }\n" +
//                "            }\n" +
//                "            ]   \n" +
//                "}\n";
//        Order order= gson.fromJson(data, Order.class);
//        System.out.println(dao.createOrder(order));
//        System.out.println(order.getOrderItems());
//        System.out.println(dao.insertOrderItems(order));
    }
}


