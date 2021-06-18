package daos;

import Utils.DBContext;
import dtos.Dish;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DishDAO {
    private Connection con = null;
    private PreparedStatement pm = null;
    private ResultSet rs = null;

    public DishDAO() {
    }

    private void closeConnection() throws SQLException {
        if (rs != null) rs.close();
        if (pm != null) pm.close();
        if (con !=null) con.close();
    }

    public List<Dish> getAllDishesByHomeCook(String homeCookID, int page) throws SQLException {
        ArrayList<Dish> list = new ArrayList<>();
        String sql = "EXEC getAllDishesByHomeCook "
        		+ "@HomeCookID = ?, "
        		+ "@HomeCookID = ?, "
        		+ "@Page = ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, homeCookID);
                pm.setInt(2, page);
                rs = pm.executeQuery();
                while(rs.next()){
                    list.add(new Dish(rs.getString("DishID"),

                            rs.getString("DishName"),
                            rs.getDouble("Price"),
                            rs.getBoolean("IsAvailable"),
                            rs.getString("Description"),
                            rs.getString("ImageURL")));
                }
            }
        }finally {
            closeConnection();
        }
        return list;
    }

    public List<Dish> getAllDishesByStatus(boolean status, int page) throws SQLException {
        ArrayList<Dish> list = new ArrayList<>();
        String sql ="EXEC getAllDishesByStatus "
        		+ "@Status = ?, "
        		+ "@Page = ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setBoolean(1, status);
                pm.setInt(2, page);
                rs = pm.executeQuery();
                while(rs.next()){
                    list.add(new Dish(rs.getString("DishID"),
                            rs.getString("HomeCookID"),
                            rs.getString("DishName"),
                            rs.getDouble("Price"),
                            status,
                            rs.getString("Description"),
                            rs.getString("ImageURL")));
                }
            }
        }finally {
            closeConnection();
        }
        return list;
    }


    public Dish getDishByID(String DishID) throws  SQLException{
        Dish result = null;
        String sql = "EXEC getDishByID "
        		+ "@DishID= ?";
        try{
            con = DBContext.makeConnection();
                if (con != null){
                    pm = con.prepareStatement(sql);
                    pm.setString(1, DishID);
                    rs = pm.executeQuery();
                    if (rs.next()) return new Dish(DishID,
                            rs.getString("HomeCookID"),
                            rs.getString("DishName"),
                            rs.getDouble("Price"),
                            rs.getBoolean("IsAvailable"),
                            rs.getString("Description"),
                            rs.getString("ImageURL"));
            }
        }finally {
            closeConnection();
        }
        return null;
    }

    public boolean createDish(Dish dish) throws SQLException {
        String sql = "EXEC createDish "
        		+ "@HomeCookID = ?, "
        		+ "@DishName = ?, "
        		+ "@Price = ?, "
        		+ "@IsAvailable = ?, "
        		+ "@Description = ?, "
        		+ "@ImageURL = ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, dish.getHomeCookID());
                pm.setString(2,dish.getDishName());
                pm.setFloat(3, (float) dish.getPrice());
                pm.setBoolean(4, dish.isAvailable());
                pm.setString(5,dish.getDescription());
                pm.setString(6, dish.getImageURL());
                int n = pm.executeUpdate();
                if (n == 1) return true;
            }
        }finally {
            closeConnection();
        }
        return false;
    }

    public boolean updateDish(Dish dish) throws  SQLException{
        String sql ="EXEC updateDish "
        		+ "@DishName= ?, "
        		+ "@Price = ?, "
        		+ "@IsAvailable = ?, "
        		+ "@Description = ?, "
        		+ "@ImageURL = ? "
        		+ "@DishID= ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, dish.getDishName());
                pm.setFloat(2, (float) dish.getPrice());
                pm.setBoolean(3, dish.isAvailable());
                pm.setString(4, dish.getDescription());
                pm.setString(5 , dish.getImageURL());
                pm.setString(6, dish.getDishId());
                int n = pm.executeUpdate();
                if ( n > 0) return true;
            }
        }finally {
            closeConnection();
        }
        return false;
    }

    public boolean deleteDish(String DishID) throws SQLException{
        String sql ="EXEC deleteDish "
        		+ "@DishID = ?";
        try{
            con = DBContext.makeConnection();
            if(con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, DishID);
                int n = pm.executeUpdate();
                if( n == 1) return true;
            }
        }finally{
            closeConnection();
        }

        return false;
    }


    public boolean changeDishStatus(String DishID, boolean input) throws SQLException {
        String sql = "EXEC changeDishStatus"
        		+ "@IsAvailable = ?, "
        		+ "@DishID = ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setBoolean(1,input);
                pm.setString(2, DishID);
                int n = pm.executeUpdate();
                if (n > 0 ) return true;
            }
        }finally {
            closeConnection();
        }
        return false;
    }

    // public static void main(String[] args) throws SQLException {
    //     DishDAO dishdao = new DishDAO();
    //     for (Dish d : dishdao.getAllDishesByHomeCook(2,1)){
    //         System.out.println(d);
    //     }
    // }

//        for (Dish d : dishdao.getAllDishesByStatus(true)){
//            System.out.println(d);
//        }
//        for (Dish d : dishdao.getAllDishesByOrderID(4)){
//            System.out.println(d);

		/*
		 * for (Dish d : dishdao.getAllDishesByOrderID(4)){ System.out.println(d); }
		 */
        //HomeCookID, DishName, Price, IsAvailable Description, ImageURL
//        boolean flag = dishdao.createDish(new Dish(2,"Bun Bo",50.1,false,"Ngon nhat Sai Gon", "abc"));
//        Dish dish = dishdao.getDishByID(2);
//        dish.setDishName("Bun Cha Ha Noi");
//        boolean flag = dishdao.updateDish(dish);
//        boolean flag = dishdao.changeDishStatus(2,false);
//        System.out.println(flag);

//    }
}
