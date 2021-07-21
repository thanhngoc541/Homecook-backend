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
        if (con != null) con.close();
    }
    public int countAllDishesByStatus(boolean status) throws SQLException {
        String sql ="EXEC countAllDishesByStatus" +
                " @Status = ?";
        try {
            con = DBContext.makeConnection();
            if (con != null){
                pm = con.prepareStatement(sql);
                pm.setBoolean(1, status);
                rs = pm.executeQuery();
                if (rs.next()) return rs.getInt("totalDishes");
            }
        }finally {
            closeConnection();
        }
        return -1;
    }

    public int getTotalHomeCookDish(String HomeCookID) {
        int count= 0;

        String query = "EXEC getTotalHomeCookDish "
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



    public List<Dish> getAllDishesByHomeCook(String homeCookID, int page) throws SQLException {
        ArrayList<Dish> list = new ArrayList<>();
        String sql = "EXEC getAllDishesByHomeCook "

        		+ "@HomeCookID = ?, "
        		+ "@Page = ?";
        try{
            con = DBContext.makeConnection();
            if (con != null) {
                pm = con.prepareStatement(sql);
                pm.setString(1, homeCookID);
                pm.setInt(2, page);
                rs = pm.executeQuery();
                while (rs.next()) {
                    list.add(new Dish(rs.getString("DishID"),
                            rs.getString("HomeCookID"),
                            rs.getString("DishName"),
                            rs.getDouble("Price"),
                            rs.getBoolean("IsAvailable"),
                            rs.getString("Description"),
                            rs.getString("ImageURL"),
                            rs.getFloat("Rating"),
                            rs.getString("Servings")));
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public List<Dish> getAllDishesByStatus(boolean status, int page) throws SQLException {
        ArrayList<Dish> list = new ArrayList<>();
        String sql = "EXEC getAllDishesByStatus "
                + "@Status = ?, "
                + "@Page = ?";
        try {
            con = DBContext.makeConnection();
            if (con != null) {
                pm = con.prepareStatement(sql);
                pm.setBoolean(1, status);
                pm.setInt(2, page);
                rs = pm.executeQuery();
                while (rs.next()) {
                    list.add(new Dish(rs.getString("DishID"),
                            rs.getString("HomeCookID"),
                            rs.getString("DishName"),
                            rs.getDouble("Price"),
                            status,
                            rs.getString("Description"),
                            rs.getString("ImageURL"),
                            rs.getFloat("Rating"),
                            rs.getString("Servings")));
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }


    public Dish getDishByID(String DishID) throws SQLException {
        Dish result = null;
        String sql = "EXEC getDishByID "
                + "@DishID= ?";
        try {
            con = DBContext.makeConnection();
            if (con != null) {
                pm = con.prepareStatement(sql);
                pm.setString(1, DishID);
                rs = pm.executeQuery();
                if (rs.next()) return new Dish(DishID,
                        rs.getString("HomeCookID"),
                        rs.getString("DishName"),
                        rs.getDouble("Price"),
                        rs.getBoolean("IsAvailable"),
                        rs.getString("Description"),
                        rs.getString("ImageURL"),
                        rs.getFloat("Rating"),
                        rs.getString("Servings"));
            }
        } finally {
            closeConnection();
        }
        return null;
    }

    public Dish createDish(Dish dish) throws SQLException {
        String sql = "EXEC createDish "
                + "@HomeCookID = ?, "
                + "@DishName = ?, "
                + "@Price = ?, "
                + "@IsAvailable = ?, "
                + "@Description = ?, "
                + "@Servings = ?, "
                + "@ImageURL = ?";
        try {
            con = DBContext.makeConnection();
            if (con != null) {
                pm = con.prepareStatement(sql);
                pm.setString(1, dish.getHomeCookID());
                pm.setString(2, dish.getDishName());
                pm.setFloat(3, (float) dish.getPrice());
                pm.setBoolean(4, dish.isAvailable());
                pm.setString(5, dish.getDescription());
                pm.setString(6, dish.getServings());
                pm.setString(7, dish.getImageURL());
                rs = pm.executeQuery();
                if(rs.next())
                {
                    dish.setDishId(rs.getString("DishID"));
                    return dish;
                }
            }
        } finally {
            closeConnection();
        }
        return null;
    }


    public boolean updateDish(Dish dish) throws  SQLException{
        String sql ="EXEC updateDish "
        		+ "@DishName= ?, "
        		+ "@Price = ?, "
        		+ "@IsAvailable = ?, "
        		+ "@Description = ?, "
        		+ "@ImageURL = ? ,"
                + "@Servings = ?, "
        		+ "@DishID= ?";
        try{

            con = DBContext.makeConnection();
            if (con != null) {
                pm = con.prepareStatement(sql);
                pm.setString(1, dish.getDishName());
                pm.setFloat(2, (float) dish.getPrice());
                pm.setBoolean(3, dish.isAvailable());
                pm.setString(4, dish.getDescription());
                pm.setString(5, dish.getImageURL());
                pm.setString(6, dish.getServings());
                pm.setString(7, dish.getImageURL());

                pm.executeUpdate();
                return true;

            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean deleteDish(String DishID) throws SQLException {
        String sql = "EXEC deleteDish "
                + "@DishID = ?";
        try {
            con = DBContext.makeConnection();
            if (con != null) {
                pm = con.prepareStatement(sql);
                pm.setString(1, DishID);

                pm.executeUpdate();
                return true;

            }
        } finally {
            closeConnection();
        }

        return false;
    }


    public boolean changeDishStatus(String DishID, boolean input) throws SQLException {
        String sql = "EXEC changeDishStatus "
                + "@Status = ?, "
                + "@DishID = ?";
        try {
            con = DBContext.makeConnection();
            if (con != null) {
                pm = con.prepareStatement(sql);
                pm.setBoolean(1, input);
                pm.setString(2, DishID);

                pm.executeUpdate();
                return true;
            }
        } finally {
            closeConnection();
        }
        return false;
    }


     public static void main(String[] args) throws SQLException {
         DishDAO dishdao = new DishDAO();
         System.out.println(dishdao.changeDishStatus("469E31C4-1365-4EF3-8DCD-81A357C553F1",true));
     }


}
