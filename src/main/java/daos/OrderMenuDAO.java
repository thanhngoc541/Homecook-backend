package daos;

import Utils.DBContext;
import dtos.Menu;
import dtos.Order;
import dtos.OrderMenu;

import java.sql.*;
import java.util.ArrayList;

public class OrderMenuDAO {
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

    public ArrayList<OrderMenu> getOrderMenuByOrderID (String orderID, int page) {
        MenuDAO menuDao= new MenuDAO();
        ArrayList<OrderMenu> list= new ArrayList<>();
        String query= "EXEC getOrderMenuByOrderID " +
                "@OrderID= ?, " +
                "@Page= ? ";
        try {
            conn= DBContext.makeConnection();
            if (conn != null) {
                ps= conn.prepareStatement(query);
                ps.setString(1, orderID);
                ps.setInt(2, page);
                rs= ps.executeQuery();
                while(rs.next()) {
                    Menu menu= menuDao.getMenuByID(rs.getString("MenuID"));
                    list.add(new OrderMenu(rs.getString("ItemID"), rs.getString("OrderID"), menu, rs.getInt("Quantity"), rs.getString("Note"), rs.getDouble("TotalPrice")));

                }
                return list;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public String insertOrderMenu(OrderMenu menu) {
        double total =0;
        String query= "EXEC insertOrderMenu " +
                "@OrderID = ?, " +
                "@MenuID = ?," +
                "@Quantity = ?, " +
                "@Note = ?, " +
                "@TotalPrice = ?";
        try {
            conn = DBContext.makeConnection();
            if (conn != null) {
                ps = conn.prepareStatement(query);
                //when we query, the db will commit automatically => ko dung addBatch duoc
                //Vay thi phai commit db manually
                ps.setString(1, menu.getOrderID());
                ps.setString(2, menu.getMenu().getMenuID());
                ps.setInt(3, menu.getQuantity());
                ps.setString(4, menu.getNote());
                ps.setDouble(5, menu.getTotalPrice());
                return menu.getMenu().getMenuID();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
