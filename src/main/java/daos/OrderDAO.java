package daos;

import Utils.DBContext;
import dtos.Dish;
import dtos.Order;
import dtos.OrderItem;
import java.sql.*;
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
    public ArrayList<Order> getOrderByCustomerID(int customerID, int page) throws SQLException {
        ArrayList<Order> list = new ArrayList<>();
        String query = "EXEC getOrderByCustomerID "
        		+ "@CustomerID = ?, "
        		+ "@Page = ?";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                ps.setInt(1, customerID);
                ps.setInt(2, page);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Order ord = new Order();
                    int orderID = rs.getInt("OrderID");
                    //Doi tu sql Timestamp qua Date java
                    java.sql.Timestamp tmpTime = new Timestamp(rs.getTimestamp("TimeStamp").getTime());
                    java.util.Date timeStamp = new Date(tmpTime.getTime());
                    //chuyen tu status id => name
                    int homecookID = rs.getInt("HomeCookID");
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
    public ArrayList<Order> getOrderByHomeCookID(int homecookID, int page) throws SQLException {
        ArrayList<Order> list = new ArrayList<>();
        String query = "EXEC getOrderByHomeCookID "
        		+ "@HomeCookID = ?, "
        		+ "@Page = ?";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                ps.setInt(1, homecookID);
                ps.setInt(2, page);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Order ord = new Order(0, null, null, 0, null);
                    int orderID = rs.getInt("OrderID");
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
                    Order ord = new Order(0, null, null, 0, null);
                    int orderID = rs.getInt("OrderID");
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
    public boolean changeOrderStatus(int orderID, String statusName) throws SQLException {
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
                ps.setInt(2, orderID);
                int n = ps.executeUpdate();
                if (n > 0) {
                    return true;
                }
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
                conn.setAutoCommit(false);
                for (OrderItem item : ord.getOrderItems()) {
                    ps.setInt(1, ord.getOrderID());
                    ps.setInt(2, item.getDish().getDishId());
                    ps.setInt(3, item.getQuantity());
                    ps.setString(4, item.getNote());
                    ps.setDouble(5, item.getTotalPrice());
                }
                int[] count = ps.executeBatch();
                conn.commit();
                if (count != null) {
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean createOrder(Order ord) throws SQLException {
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
                + "@Note = ?) ";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                ps.setInt(1, ord.getHomeCookID());
                ps.setInt(2, ord.getCustomerID());
                ps.setTimestamp(3, timeStamp);
                ps.setInt(4, statusID);
                ps.setString(5, ord.getReceiverPhone());
                ps.setString(6, ord.getReceiverAddress());
                ps.setString(7, ord.getReceiverName());
                ps.setDouble(8, ord.getTotal());
                ps.setString(9, ord.getNote());

                int n = ps.executeUpdate();
                if (n > 0) {

                    return true;
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    //Customer xem item trong ord
    public ArrayList<OrderItem> getListItemByOrderID(int ordID, int page) throws SQLException {
        ArrayList<OrderItem> list = new ArrayList<>();
        String query = "EXEC getListItemByOrderID "
        		+ "@OrderID = ?, "
                + "@Page = ?";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                ps.setInt(1, ordID);
                ps.setInt(2, page);
                rs = ps.executeQuery();
            }
            while (rs.next()) {
                Dish dish = new Dish(rs.getInt("DishID"), rs.getInt("HomeCookID"), rs.getString("DishName"),
                        rs.getDouble("Price"), rs.getString("ImageURL"));
                list.add(new OrderItem(rs.getInt("ItemID"), rs.getInt("OrderID"), dish, rs.getInt("Quantity"),
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
    
    public static void main(String[] args) {
		OrderDAO tempo = new OrderDAO();
		try {
			tempo.changeOrderStatus(4, "Finished");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

