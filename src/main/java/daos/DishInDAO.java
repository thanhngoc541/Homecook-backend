package daos;

import Utils.DBContext;
import dtos.Dish;
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
    public boolean addDishToMenu(String menuID, String dishID) throws  SQLException{
        String sql ="EXEC addDishToMenu "
                + "@MenuID = ?, "
                + "@DishID = ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, menuID);
                pm.setString(2, dishID);
                int n = pm.executeUpdate();
                if ( n > 0) return true;
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

    public boolean deleteDishFromMenu(String menuID, String dishID) throws  SQLException{
        String sql = "EXEC deleteDishFromMenu "
                + "@MenuID = ?, "
                + "@DishID = ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, menuID);
                pm.setString(2, dishID);
                int n = pm.executeUpdate();
                if ( n > 0) return true;
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
        //dao.addDishToMenu("fee2cb76-89aa-4ccd-ab52-03c619b3366c","a8a7ee2f-b586-482b-9cfe-0342d99c8102");

    }
}
