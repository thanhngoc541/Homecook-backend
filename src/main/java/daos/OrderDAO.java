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
                    //chuyen tu status id => name
                    String homecookID = rs.getString("HomeCookID");
                    String status = ord.getStatusName(rs.getInt("StatusID"));
                    double total = rs.getDouble("Total");
                    String note = rs.getString("Note");
                    String phone = rs.getString("ReceiverPhone");
                    String address = rs.getString("ReceiverAddress");
                    String name = rs.getString("ReceiverName");

                    ord.setOrderID(orderID);
                    ord.setCustomerID(customerID);
                    ord.setHomeCookID(homecookID);
                    ord.setTimeStamp(timeStamp);
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
                    Order ord = new Order("", null, null, 0, null);
                    String orderID = rs.getString("OrderID");
                    //Doi tu sql Timestamp qua Date java
                    java.sql.Timestamp tmpTime = new Timestamp(rs.getTimestamp("TimeStamp").getTime());
                    java.util.Date timeStamp = new Date(tmpTime.getTime());
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
                    java.sql.Timestamp tmpTime = new Timestamp(rs.getTimestamp("TimeStamp").getTime());
                    java.util.Date timeStamp = new Date(tmpTime.getTime());
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
                    ord.setTimeStamp(timeStamp);
                    ord.setStatus(status);
                    ord.setTotal(total);
                    ord.setNote(note);
                    ord.setReceiverPhone(phone);
                    ord.setReceiverAddress(address);
                    ord.setReceiverName(name);

                    System.out.println(ord.getOrderItems());
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
                    Order ord = new Order("", null, null, 0, null);
                    String orderID = rs.getString("OrderID");
                    //Doi tu sql Timestamp qua Date java
                    java.sql.Timestamp tmpTime = new Timestamp(rs.getTimestamp("TimeStamp").getTime());
                    java.util.Date timeStamp = new Date(tmpTime.getTime());
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

    public boolean insertOrderItems(Order ord) throws SQLException {
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
                    ps.setString(1, item.getOrderID());
                    ps.setString(2, item.getDish().getDishId());
                    ps.setInt(3, item.getQuantity());
                    ps.setString(4, item.getNote());
                    ps.setDouble(5, item.getTotalPrice());
                    ps.executeUpdate();
                }
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    public String createOrder(Order ord) throws SQLException {
        int statusID = ord.getStatusID(ord.getStatus());
        java.util.Date objDate = new java.util.Date(ord.getTimeStamp().getTime());
        java.sql.Timestamp timeStamp = new java.sql.Timestamp(objDate.getTime());
        String query = "EXEC createOrder "
        		+ "@HomeCookID = ?, "
        		+ "@CustomerID = ?,"
        		+ "@TimeStamp = ?, "
        		+ "@StatusID = ?, " 
                + "@ReceiverPhone = ?, "
                + "@ReceiverAddress = ?, "
                + "@ReceiverName = ?, "
                + "@Total = ?, "
                + "@Note = ? ";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                ps.setString(1, ord.getHomeCookID());
                ps.setString(2, ord.getCustomerID());
                ps.setTimestamp(3, timeStamp);
                ps.setInt(4, statusID);
                ps.setString(5, ord.getReceiverPhone());
                ps.setString(6, ord.getReceiverAddress());
                ps.setString(7, ord.getReceiverName());
                ps.setDouble(8, ord.getTotal());
                ps.setString(9, ord.getNote());
                rs= ps.executeQuery();
                while (rs.next()) {
                    ord.setOrderID(rs.getString("OrderID"));
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
    public boolean deleteOrder(int OrderID) {
        String query= "EXEC deleteOrder" +
                "@OrderID = ?";
        try {
            conn= DBContext.makeConnection();
            if (conn != null) {
                ps= conn.prepareStatement(query);
                ps.setInt(1, OrderID);
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
        OrderDAO tempo = new OrderDAO();
//		try {
//			tempo.changeOrderStatus(4, "Finished");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        String strDate = "2022-01-01 15:00:06.000";
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = formatter.parse(strDate);
//        Order ord = new Order("B489E4B9-9ABC-41B9-88FC-380579FB3CC6", "535340B1-8053-4819-8772-488577A10639", date, "Pending",
//                "0901517531", "NTMK", "Ngokku", 500, "Ok Test", null);
//        System.out.println(tempo.createOrder(ord));

//        System.out.println(tempo.changeOrderStatus("BD89AAF7-6364-4C9E-897E-159E40DAF7B1", "Accept"));
        System.out.println(tempo.getListItemByOrderID("D0B05EAC-8C40-416E-9283-F13B787FB908", 1));
//        System.out.println(tempo.getAllOrder());
//        ArrayList<Order> ord= tempo.getOrderByCustomerID(7, 1);
//        Gson gson= new GsonBuilder().setPrettyPrinting().create();
//        String json= gson.toJson(ord);
//        System.out.println(json);
    }
}


