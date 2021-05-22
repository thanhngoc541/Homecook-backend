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
        String sql = "INSERT INTO Menus (HomeCookID, MenuName, IsServing,HomeCookName) VALUES ( ?, ?, ?,?)";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setInt(1, menu.getMenuID());
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

    public boolean addDishToMenu(int menuID,int dishID) throws  SQLException{
        String sql ="INSERT INTO DishIn (MenuID, DishID) VALUES (?,?)";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setInt(1, menuID);
                pm.setInt(2, dishID);
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

    public boolean deleteDishFromMenu(int menuID,int dishID) throws  SQLException{
        String sql ="DELETE  FROM DishIn WHERE MenuID= ? AND DishID=?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setInt(1, menuID);
                pm.setInt(2, dishID);
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

    public boolean changeMenuName(String menuName, int menuId) throws  SQLException{
        String sql ="UPDATE Menus SET MenuName= ? WHERE MenuID= ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, menuName);
                pm.setInt(2, menuId);
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

    public boolean changeMenuStatus(boolean isServing, int menuId) throws  SQLException{
        String sql ="UPDATE Menus SET IsServing= ? WHERE MenuID= ?";
        try {
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setBoolean(1, isServing);
                pm.setInt(2, menuId);
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

    public boolean deleteMenu( int menuId) throws  SQLException{
        String sql ="DELETE  FROM Menus WHERE MenuID= ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setInt(1, menuId);
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

    public Menu getMenuByID(int ID) throws SQLException {
        String sql = "SELECT MenuName, IsServing,HomeCookName FROM Menus WHERE MenuID= ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setInt(1, ID);
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

    public List<Menu> getAllMenusByHomeCookID(int ID) throws SQLException {
        List list = new ArrayList<Menu>();
        String sql = "SELECT MenuID , MenuName, IsServing,HomeCookName FROM Menus WHERE HomeCookID= ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setInt(1, ID);
                rs = pm.executeQuery();
                while(rs.next()) list.add(new Menu(rs.getInt("MenuID"),
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
    public List<Menu> getAllMenusByHomeCookIDAndStatus(int ID,boolean isServing) throws SQLException {
        List list = new ArrayList<Menu>();
        String sql = "SELECT MenuID , MenuName,HomeCookName FROM Menus WHERE HomeCookID= ? AND IsServing=?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setInt(1, ID);
                pm.setBoolean(2, isServing);
                rs = pm.executeQuery();
                while(rs.next()) list.add(new Menu(rs.getInt("MenuID"),
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

    public List<Dish> getAllDishesInMenu(int menuID) throws SQLException {
        List list = new ArrayList<Dish>();
        String sql = "SELECT DishID FROM DishIn WHERE MenuID=?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setInt(1, menuID);
                rs = pm.executeQuery();
                DishDAO dishDAO=new DishDAO();
                while(rs.next()) list.add(dishDAO.getDishByID(rs.getInt("DishID")));
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
