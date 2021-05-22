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

    public List<Dish> getAllDishesByHomeCook(int ID) throws SQLException {
        ArrayList<Dish> list = new ArrayList<>();
        String sql = "SELECT DishID ,DishName, Price, IsAvailable, Description, ImageURL  FROM Dishes WHERE HomeCookID= ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setInt(1, ID);
                rs = pm.executeQuery();
                while(rs.next()){
                    list.add(new Dish(rs.getInt("DishID"),
                            ID,
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

    public List<Dish> getAllDishesByStatus(boolean status) throws SQLException {
        ArrayList<Dish> list = new ArrayList<>();
        String sql ="SELECT DishID, HomeCookID, DishName, Price, Description, ImageURL FROM Dishes WHERE IsAvailable = ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setBoolean(1, status);
                rs = pm.executeQuery();
                while(rs.next()){
                    list.add(new Dish(rs.getInt("DishID"),
                            rs.getInt("HomeCookID"),
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


    public Dish getDishByID(int DishID) throws  SQLException{
        Dish result = null;
        String sql = "SELECT HomeCookID, DishName, Price, IsAvailable, Description, ImageURL  FROM Dishes WHERE DishID= ?";
        try{
            con = DBContext.makeConnection();
                if (con != null){
                    pm = con.prepareStatement(sql);
                    pm.setInt(1, DishID);
                    rs = pm.executeQuery();
                    if (rs.next()) return new Dish(DishID,
                            rs.getInt("HomeCookID"),
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
        String sql = "INSERT INTO Dishes (HomeCookID, DishName, Price, IsAvailable, Description, ImageURL) VALUES (?, ?, ?, ?, ?, ?)";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setInt(1, dish.getHomeCookID());
                pm.setString(2,dish.getDishName());
                pm.setFloat(3, (float) dish.getPrice());
                pm.setBoolean(4, dish.isAvailable());
                pm.setString(5,dish.getDescription());
                pm.setString(6, dish.getImageURL());
                int n = pm.executeUpdate();
                if (n > 0) return true;
            }
        }finally {
            closeConnection();
        }
        return false;
    }

    public boolean updateDish(Dish dish) throws  SQLException{
        String sql ="UPDATE Dishes SET DishName= ?, Price = ?, IsAvailable = ?, Description = ?, ImageURL = ? WHERE DishID= ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setString(1, dish.getDishName());
                pm.setFloat(2, (float) dish.getPrice());
                pm.setBoolean(3, dish.isAvailable());
                pm.setString(4, dish.getDescription());
                pm.setString(5 , dish.getImageURL());
                pm.setInt(6, dish.getDishId());
                int n = pm.executeUpdate();
                if ( n > 0) return true;
            }
        }finally {
            closeConnection();
        }
        return false;
    }

    public boolean deleteDish(int DishID) throws SQLException{
        String sql ="DELETE from Dishes WHERE DishID =?";
        try{
            con = DBContext.makeConnection();
            if(con != null){
                pm = con.prepareStatement(sql);
                pm.setInt(1, DishID);
                int n = pm.executeUpdate();
                if( n > 0) return true;
            }
        }finally{
            closeConnection();
        }

        return false;
    }


    public boolean changeDishStatus(int DishID, boolean input) throws SQLException {
        String sql = "UPDATE Dishes SET IsAvailable = ? WHERE DishID= ?";
        try{
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setBoolean(1,input);
                pm.setInt(2, DishID);
                int n = pm.executeUpdate();
                if (n > 0 ) return true;
            }
        }finally {
            closeConnection();
        }
        return false;
    }

    public static void main(String[] args) throws SQLException {
        DishDAO dishdao = new DishDAO();
//        for (Dish d : dishdao.getAllDishesByHomeCook(2)){
//            System.out.println(d);
//        }
//        for (Dish d : dishdao.getAllDishesByStatus(true)){
//            System.out.println(d);
//        }
        for (Dish d : dishdao.getAllDishesByOrderID(4)){
            System.out.println(d);
        }
        //HomeCookID, DishName, Price, IsAvailable Description, ImageURL
//        boolean flag = dishdao.createDish(new Dish(2,"Bun Bo",50.1,false,"Ngon nhat Sai Gon", "abc"));
//        Dish dish = dishdao.getDishByID(2);
//        dish.setDishName("Bun Cha Ha Noi");
//        boolean flag = dishdao.updateDish(dish);
//        boolean flag = dishdao.changeDishStatus(2,false);
//        System.out.println(flag);

    }
}
