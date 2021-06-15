package daos;
import dtos.Dish;
import dtos.Menu;
import Utils.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {
    private Connection con = null;
    private PreparedStatement pm = null;
    private ResultSet rs = null;

    private void closeConnection() throws SQLException {
        if (rs != null) rs.close();
        if (pm != null) pm.close();
        if (con !=null) con.close();
    }

    public boolean createMenu(Menu menu) throws SQLException {
        String sql = "EXEC createMenu "
        		+ "@HomeCookID = ?, "
        		+ "@MenuName = ?, "
        		+ "@IsServing = ?, "
        		+ "@HomeCookName = ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, menu.getMenuID());
                pm.setString(2,menu.getMenuName());
                pm.setBoolean(3, menu.isServing());
                pm.setString(4,menu.getHomeCookName());
                int n = pm.executeUpdate();
                if (n > 0) return true;
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

    public boolean changeMenuName(String menuName, String menuId) throws  SQLException{
        String sql ="EXEC changeMenuName "
        		+ "@MenuName = ?, "
        		+ "@MenuID= ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, menuName);
                pm.setString(2, menuId);
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

    public boolean changeMenuStatus(boolean isServing, String menuId) throws  SQLException{
        String sql ="EXEC changeMenuStatus "
        		+ "@IsServing = ?, "
        		+ "@MenuID = ?";
        try {
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setBoolean(1, isServing);
                pm.setString(2, menuId);
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

    public boolean deleteMenu( String menuId) throws  SQLException{
        String sql ="EXEC deleteMenu "
        		+ "@MenuID = ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, menuId);
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

    public Menu getMenuByID(String ID, int page) throws SQLException {
        String sql = "EXEC getMenuByID "
        		+ "@MenuID = ?, "
        		+ "@Page = ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, ID);
                pm.setInt(2, page);
                rs = pm.executeQuery();
                if (rs.next()) return new Menu(ID,
                        rs.getString("MenuName"),
                        rs.getBoolean("IsServing"),
                        rs.getString("HomeCookName"),
                        null);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }
        return null;
    }

    public List<Menu> getAllMenusByHomeCookID(String ID, int page) throws SQLException {
        List<Menu> list = new ArrayList<Menu>();
        String sql = "EXEC getMenuByHomeCookID "
        		+ "@HomeCookID = ?, "
        		+ "@Page = ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, ID);
                pm.setInt(2, page);
                rs = pm.executeQuery();
                while(rs.next()) 
                	list.add(new Menu(rs.getString("MenuID"),
                        rs.getString("MenuName"),
                        rs.getBoolean("IsServing"),
                        rs.getString("HomeCookName"),
                        null));
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
    public List<Menu> getAllMenusByHomeCookIDAndStatus(String ID, boolean isServing, int page) throws SQLException {
        List<Menu> list = new ArrayList<Menu>();
        String sql = "EXEC getMenuByHomeCookIDAndStatus "
        		+ "@HomeCookID = ?, "
        		+ "@IsServing= ?, "
        		+ "@Page = ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, ID);
                pm.setBoolean(2, isServing);
                pm.setInt(3, page);
                rs = pm.executeQuery();
                while(rs.next()) 
                	list.add(new Menu(rs.getString("MenuID"),
                        rs.getString("MenuName"),
                        isServing,
                        rs.getString("HomeCookName"),
                        null));
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

    public List<Dish> getAllDishesInMenu(String menuID, int page) throws SQLException {
        List<Dish> list = new ArrayList<Dish>();
        String sql = "EXEC getAllDishesInMenu "
        		+ "@MenuID = ?, "
        		+ "@Page = ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, menuID);
                pm.setInt(2, page);
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

    }
}
