package daos;

import Utils.DBContext;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import dtos.Dish;
import dtos.Order;
import dtos.OrderItem;
import java.io.*;
import java.lang.reflect.Type;
import java.sql.*;
import java.text.ParseException;
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
    public ArrayList<Order> getOrderByCustomerID(int customerID) throws SQLException {
        ArrayList<Order> list = new ArrayList<>();
        String query = "SELECT OrderID, CustomerID, HomeCookID, TimeStamp, StatusID, Note, Total, ReceiverPhone, " +
                "ReceiverAddress, " +
                "ReceiverName  FROM Orders WHERE CustomerID=?";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                ps.setInt(1, customerID);
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

    //ham nay de test Json
    public ArrayList<Order> getAllOrder() {
        ArrayList<Order> list = new ArrayList<>();
        String query = "SELECT * FROM Orders";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                rs = ps.executeQuery();
                while (rs.next()) {
                    java.sql.Timestamp tmpTime = new Timestamp(rs.getTimestamp("TimeStamp").getTime());
                    java.util.Date timeStamp = new Date(tmpTime.getTime());
                    Order ord = new Order(rs.getInt("OrderID"),
                            rs.getInt("HomeCookID"),
                            rs.getInt("CustomerID"),
                            timeStamp,
                            null, rs.getString("ReceiverPhone"), rs.getString("ReceiverAddress"), rs.getString(
                            "ReceiverName"), rs.getDouble("Total"), rs.getString("Note"), null);
                    ord.setStatus(ord.getStatusName(rs.getInt("StatusID")));
                    list.add(ord);
                }
                return list;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    //lay order cua homecook (HomeCook View)
    public ArrayList<Order> getOrderByHomeCookID(int homecookID) throws SQLException {
        ArrayList<Order> list = new ArrayList<>();
        String query = "SELECT OrderID, TimeStamp, StatusID, Note, Total FROM Orders WHERE HomeCookID=?";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                ps.setInt(1, homecookID);
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
    public ArrayList<Order> getOrderByTimeRange(String fromDate, String toDate) throws SQLException {
        ArrayList<Order> list = new ArrayList<>();
        String query = "SELECT OrderID, TimeStamp, StatusID, Total, Note FROM Orders WHERE TimeStamp BETWEEN ? AND ?";

        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                ps.setString(1, fromDate);
                ps.setString(2, toDate);
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
                    return list;
                }
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
        String query = "UPDATE Orders SET StatusID=? WHERE OrderID=?";
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

    public boolean insertOrderItem(Order ord) throws SQLException {
        String query = "INSERT INTO OrderItems (OrderID, DishID, Quantity, Note, TotalPrice) VALUES (?,?,?,?,?)";
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
                    ps.addBatch();
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
        String query = "INSERT INTO Orders (HomeCookID, CustomerID, TimeStamp, StatusID," +
                " ReceiverPhone, ReceiverAddress, ReceiverName, Total, Note) " +
                "VALUES (?,?,?,?,?,?,?,?,?) ";
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
    public ArrayList<OrderItem> getListItemByOrderID(int ordID) throws SQLException {
        ArrayList<OrderItem> list = new ArrayList<>();
        String query = "SELECT i.ItemID, i.DishID, i.OrderID, d.DishID, i.Quantity, i.Note, i.TotalPrice, d" +
                ".HomeCookID, d" +
                ".DishName, d" +
                ".Price, d.ImageURL\n" +
                "FROM OrderItems i, Dishes d \n" +
                "WHERE i.DishID= d.DishID AND i.OrderID= ?";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                ps.setInt(1, ordID);
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
//    public String objectsToJSon (Object obj) {
//        Gson gson= new GsonBuilder().setPrettyPrinting().create();
//        String jsonString= gson.toJson(obj);
//        if ( jsonString.isEmpty()) {
//            return jsonString;
//        }
//        return null;
//    }
//    public List jsonToObject (String json, ) {
//        Type listOfMyClassObject = new TypeToken<ArrayList<>>() {}.getType();
//        Gson gson= new Gson();
//        List<obj> outputList= gson.fromJson(json, listOfMyClassObject);
//        if (!outputList.isEmpty()) {
//            return outputList;
//        }
//        return null;
//    }
    //HomeCook xem item trong ord
//    public ArrayList<OrderItem> getOrderDetailsBy (Order ord) throws SQLException {
//        ArrayList<OrderItem> list= new ArrayList<>();
//        String query= "SELECT i.ItemID, i.DishID, i.OrderID, d.DishID, i.Quantity, i.Note, i.TotalPrice, d.HomeCookID, d.DishName, d.Price, d.ImageURL\n" +
//                "from OrderItems i, Dishes d\n" +
//                "where i.DishID= d.DishID and HomeCookID=? and i.OrderID=?";
//        try {
//            conn= new DBContext().makeConnection();
//            if (conn != null) {
//                ps= conn.prepareStatement(query);
//                ps.setInt(1, ord.getHomeCookID());
//                ps.setInt(2, ord.getOrderID());
//                rs= ps.executeQuery();
//                while (rs.next()) {
//                    OrderItem item= new OrderItem();
//                    Dish dish= new Dish(rs.getInt("DishID"), rs.getInt("HomeCookID"), rs.getString("DishName"),
//                            rs.getDouble("Price"), rs.getString("ImageURL"));
//                    int quantity= rs.getInt("Quantity");
//                    String note= rs.getString("Note");
//                    double totalPrice= rs.getDouble("TotalPrice");
//                    item.setQuantity(quantity);
//                    item.setNote(note);
//                    item.setTotalPrice(totalPrice);
//                    list.add(item);
//                }
//                return list;
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        finally {
//            closeConnection();
//        }
//        return null;
//    }
    public static void main(String[] args) throws SQLException, ParseException, IOException {

        OrderDAO dao = new OrderDAO();
        //Test ham create order (Chua co oderItemlist)
//        String date= "1900-01-01 00:00:06.790";
//        Date date1=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse(date);
//        Order ord= new Order(6, 3, date1, "Delivering", "3","3","3",100, null, null);
//        System.out.println(dao.createOrder(ord));

//        System.out.println(dao.getAllOrder());

        //Done convert list to json object
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
        ArrayList<Order> list= dao.getOrderByCustomerID(7);
        System.out.println(list);
        String json= gson.toJson(list);
        System.out.println(json);

        //Deserialzi
        Type orderType= new TypeToken<ArrayList<Order>>(){}.getType();

        List<Order> orders= new Gson().fromJson(json, orderType);
        System.out.println(orders);

//        JsonParser parser = new JsonParser();
//        Object obj = parser.parse(new FileReader("OrderList.json"));
//        JsonObject jsonObject = (JsonObject) obj;
//        JsonArray msg = (JsonArray) jsonObject.get("Order");
//
//
//        Gson gson = new Gson();
//        String json = gson.toJson(ord);
//
//        FileWriter file = new FileWriter("D:\\FPTU\\2021.Summer\\SWP391\\Project\\homecook-frontend\\src" +
//                "\\json\\OrderList.json", false);
//        JsonWriter jw = new JsonWriter(file);
//        Iterator<JsonElement> iterator= msg.iterator();
//        Order ords= new Order();
//        while (iterator.hasNext()) {
//            ords.create
//        }
//        file.close();
//        if (gson != null) {
//            System.out.println("Add successfully");


        }
    }


