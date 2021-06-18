package daos;
import com.google.gson.Gson;
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
    public Menu getMenuByID(String ID) throws SQLException {
        String sql = "EXEC getMenuByID "
                + "@MenuID = ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, ID);
                rs = pm.executeQuery();
                DishInDAO dao = new DishInDAO();
                if (rs.next()) return
                        new Menu(rs.getString("MenuName"),
                                ID,
                                rs.getString("HomeCookID"),
                                rs.getString("HomeCookName"),
                                rs.getBoolean("IsServing"),
                                dao.getAllDishesInMenu(ID));
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

    public List<Menu> getAllMenusByHomeCookID(String ID) throws SQLException {
        List<Menu> list = new ArrayList<Menu>();
        String sql = "EXEC getMenuByHomeCookID "
                + "@HomeCookID = ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, ID);
                rs = pm.executeQuery();
                DishInDAO dao= new DishInDAO();
                while(rs.next())
                    list.add(new Menu(rs.getString("MenuName"),
                            rs.getString("MenuID"),
                            ID,
                            rs.getString("HomeCookName"),
                            rs.getBoolean("IsServing"),
                            dao.getAllDishesInMenu(ID)));
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
    public List<Menu> getAllActiveMenus(int page) throws SQLException {
        List list = new ArrayList<Menu>();
        String sql = "EXEC getMenuByStatus "
                + "@IsServing= ?, "
                + "@Page = ?";
        String menuId=null;
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setBoolean(1,true);
                pm.setInt(2, page);


                rs = pm.executeQuery();

                DishInDAO dao = new DishInDAO();

                while(rs.next()){
                    menuId=rs.getString("MenuID");
                    System.out.println(menuId);
                    list.add(new Menu(rs.getString("MenuName"),
                            menuId,
                            rs.getString("HomeCookID"),
                            rs.getString("HomeCookName"),
                            true,
                            dao.getAllDishesInMenu(menuId)));}
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

    public boolean createMenu(Menu menu) throws SQLException {
        System.out.println(menu.getMenuName());
        String sql = "EXEC createMenu "
        		+ "@HomeCookID = ?, "
        		+ "@MenuName = ?, "
        		+ "@IsServing = ?, "
        		+ "@HomeCookName = ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, menu.getHomeCookID());
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
                 pm.executeUpdate();

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


    public static void main(String[] args) throws SQLException {
    MenuDAO dao=new MenuDAO();
//    dao.createMenu(new Menu("asdasdasdas",
//            null,
//            "B489E4B9-9ABC-41B9-88FC-380579FB3CC6",
//            "Ngoc",
//            true,
//             null));
   dao.deleteMenu("fee2cb76-89aa-4ccd-ab52-03c619b3366c");
   // List<Menu> menus = dao.getAllMenusByHomeCookID("B489E4B9-9ABC-41B9-88FC-380579FB3CC6");
    //String data = new Gson().toJson(menus);
    //    System.out.println(data);
    }
}
