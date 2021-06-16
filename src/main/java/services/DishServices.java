package services;

import com.google.gson.Gson;
import daos.DishDAO;
import dtos.Dish;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

@Path("/dishes")
public class DishServices {
    private DishDAO service = new DishDAO();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllDishesByHomeCook(@PathParam("id") String id) throws SQLException {
        List<Dish> dishes = service.getAllDishesByHomeCook(id,1);
        String result = new Gson().toJson(dishes);
        return result;
    }
}
