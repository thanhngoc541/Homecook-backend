package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import daos.DishDAO;
import dtos.Dish;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

@Path("/dishes")
public class DishServices {
    private DishDAO service = new DishDAO();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/cook/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllDishesByHomeCook(@PathParam("id") int id) throws SQLException {
        List<Dish> dishes = service.getAllDishesByHomeCook(id,1);
        String result = gson.toJson(dishes);
        return result;
    }

    @GET
    @Path("/dish/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDishByID(@PathParam("id")int id) throws SQLException {
        Dish d = service.getDishByID(id);
        String result = gson.toJson(d);
        return result;
    }

    @GET
    @Path("/status/{status}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllDishesByStatus(@PathParam("status")boolean status) throws SQLException {
        List<Dish> d = service.getAllDishesByStatus(status,1);
        String result = gson.toJson(d);
        return result;
    }

    @POST
    @Path("/dish")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean createDish(Dish dish) throws SQLException {
        boolean result = service.createDish(dish);
        return result;
    }

    @DELETE
    @Path("/dish/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean deleteDish(@PathParam("id")int id) throws SQLException {
        boolean result = service.deleteDish(id);
        return result;
    }

    @PUT
    @Path("/dish/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean updateDish(Dish dish) throws SQLException {
        boolean result = service.updateDish(dish);
        return result;
    }

}
