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
    public int getTotalSearchedMenu(String name) {
        int count= 0;

        String query = "EXEC countSearchMenu "
                + "@searchPhrase = ? ";
        try{
            con= DBContext.makeConnection();
            if (con != null) {
                pm= con.prepareStatement(query);
                pm.setString(1, name);
                rs= pm.executeQuery();
                while (rs.next()) {
                    count = rs.getInt("total");

                }
                return count;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }

    public int getTotalHomeCookMenu(String HomeCookID) {
        int count= 0;

        String query = "EXEC getTotalHomeCookMenu "
                + "@HomeCookID = ? ";
        try{
            con= DBContext.makeConnection();
            if (con != null) {
                pm= con.prepareStatement(query);
                pm.setString(1, HomeCookID);
                rs= pm.executeQuery();
                while (rs.next()) {
                    count = rs.getInt("total");

                }
                return count;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
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
                                dao.getAllDishesInMenu(ID),
                                rs.getString("MenuURL"),
                                rs.getString("MenuDescription"),
                                rs.getFloat("Rating"),
                                rs.getString("Servings"),
                                rs.getFloat("Price"));
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

    public List<Menu> getTopMenu() throws SQLException {
        List<Menu> list = new ArrayList<>();
        String sql = "EXEC getTopMenu ";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                rs = pm.executeQuery();
                DishInDAO dao= new DishInDAO();
                while(rs.next())
                    list.add(new Menu(rs.getString("MenuName"),
                            rs.getString("MenuID"),
                            rs.getString("HomeCookID"),
                            rs.getString("HomeCookName"),
                            rs.getBoolean("IsServing"),
                            null,
                            rs.getString("MenuURL"),
                            rs.getString("MenuDescription"),
                            rs.getFloat("Rating"),
                            rs.getString("Servings"),
                            rs.getFloat("Price")));
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
                DishInDAO dao = new DishInDAO();
                String menuID="";
                while(rs.next()){
                    menuID=rs.getString("MenuID");
                    list.add(new Menu(rs.getString("MenuName"),
                            menuID,
                            ID,
                            rs.getString("HomeCookName"),
                            rs.getBoolean("IsServing"),
                            null,
                            rs.getString("MenuURL"),
                            rs.getString("MenuDescription"),
                            rs.getFloat("Rating"),
                            rs.getString("Servings"),
                            rs.getFloat("Price")));}
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
    public List<Menu> getSearchedMenu(String name, int page) throws SQLException {

        List list = new ArrayList<Menu>();
        String sql = "EXEC searchMenu "
                + "@searchPhrase = ? ,"
                + "@Page = ?";

        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1,name);
                pm.setInt(2, page);


                rs = pm.executeQuery();

                DishInDAO dao = new DishInDAO();

                while(rs.next()){
                    list.add(new Menu(rs.getString("MenuName"),
                            rs.getString("MenuID"),
                            rs.getString("HomeCookID"),
                            rs.getString("HomeCookName"),
                            true,
                            null,
                            rs.getString("MenuURL"),
                            rs.getString("MenuDescription"),
                            rs.getFloat("Rating"),
                            rs.getString("Servings"),
                            rs.getFloat("Price")));}
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

    public Menu createMenu(Menu menu) throws SQLException {
        System.out.println(menu.getMenuName());
        String sql = "EXEC createMenu "
                + "@HomeCookID = ? , "
                + "@MenuName = ? , "
                + "@IsServing = ? , "
                + "@HomeCookName = ? , "
                + "@Servings = ? , "
                + "@Price = ? , "
                + "@MenuURL = ? , "
                + "@MenuDescription = ? ";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, menu.getHomeCookID());
                pm.setString(2,menu.getMenuName());
                pm.setBoolean(3, menu.isServing());
                pm.setString(4,menu.getHomeCookName());
                pm.setString(5,menu.getServings());
                pm.setFloat(6,menu.getPrice());
                pm.setString(7,menu.getMenuURL());
                pm.setString(8,menu.getMenuDescription());
                rs= pm.executeQuery();
                if (rs.next()) {
                    menu.setMenuID(rs.getString("MenuID"));
                    return menu;
                }

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
    public boolean  updateMenu(Menu menu) throws SQLException {

        String sql = "EXEC updateMenu "
                + "@MenuID = ? , "
                + "@MenuName = ? , "
                + "@IsServing = ? , "
                + "@MenuURL = ? , "
                + "@MenuDescription = ? ,"
                + "@Servings = ? , "
                + "@Price = ?  ";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, menu.getMenuID());
                pm.setString(2,menu.getMenuName());
                pm.setBoolean(3, menu.isServing());
                pm.setString(4,menu.getMenuURL());
                pm.setString(5,menu.getMenuDescription());
                pm.setString(6, menu.getServings());
                pm.setFloat(7,menu.getPrice());
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


    public static void main(String[] args) throws SQLException {
   MenuDAO dao=new MenuDAO();
//        System.out.println(dao.getSearchedMenu("so",1));
//        String menu = dao.createMenu(new Menu("test",
//                null,
//                "B489E4B9-9ABC-41B9-88FC-380579FB3CC6",
//                "Ngoc",
//                false,
//                null
//                , null
//                , null));
//        System.out.println(menu);
        //dao.deleteMenu("fee2cb76-89aa-4ccd-ab52-03c619b3366c");
        System.out.println(dao.getSearchedMenu("247",1 ));
    }
}
