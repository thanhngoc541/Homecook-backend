package daos;

import Utils.DBContext;
import dtos.Dish;
import dtos.DishIn;
import dtos.Menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DishInDAO {
    private Connection con = null;
    private PreparedStatement pm = null;
    private ResultSet rs = null;

    private void closeConnection() throws SQLException {
        if (rs != null) rs.close();
        if (pm != null) pm.close();
        if (con !=null) con.close();
    }
    public boolean addDishToMenu(DishIn dishIn) throws  SQLException{
        String sql ="EXEC addDishToMenu "
                + "@MenuID = ?, "
                + "@DishID = ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, dishIn.getMenuID());
                pm.setString(2, dishIn.getDishId());
                pm.executeUpdate();
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }
        return false;
    }

    public boolean deleteDishFromMenu(DishIn dishIn) throws  SQLException{
        String sql = "EXEC deleteDishFromMenu "
                + "@MenuID = ?, "
                + "@DishID = ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, dishIn.getMenuID());
                pm.setString(2, dishIn.getDishId());
               pm.executeUpdate();
            return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }
        return false;
    }
    public boolean removeAllDishesFromMenu(String MenuID) throws  SQLException{
        String sql = "EXEC removeAllDishesFromMenu "
                + "@MenuID = ? ";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, MenuID);
                pm.executeUpdate();
                return true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }
        return false;
    }


    public List<Dish> getAllDishesInMenu(String menuID) throws SQLException {
        List<Dish> list = new ArrayList<Dish>();
        String sql = "EXEC getAllDishesInMenu "
                + "@MenuID = ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, menuID);
                rs = pm.executeQuery();
                DishDAO dishDAO=new DishDAO();
                while(rs.next())
                    list.add(dishDAO.getDishByID((rs.getString("DishID"))));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }
        return list;
    }
    public static void main(String[] args) throws SQLException {
        DishInDAO dao = new DishInDAO();
        System.out.println(dao.deleteDishFromMenu(new DishIn("D0EE1BF5-5C7F-4CC2-A95C-61C73FB366B9","821F056A-A896-4A6E-AB08-67583BF5A27E")));

    }
}
