package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import daos.DishDAO;
import dtos.Dish;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

@Path("/dishes")
public class DishServices {
    private DishDAO service = new DishDAO();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/cook/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllDishesByHomeCook(@PathParam("id") String id) throws SQLException {
        List<Dish> dishes = service.getAllDishesByHomeCook(id,1);
        String result = gson.toJson(dishes);
        return result;
    }

    @GET
    @Path("/dish/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDishByID(@PathParam("id")String id) throws SQLException {
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
    public Response createDish(Dish dish) throws SQLException, URISyntaxException {
        boolean result = service.createDish(dish);
        URI uri = result ? new URI("/dishes/dish/"+dish.getDishId()) : null;

        return Response.created(uri).build();
    }

    @PUT
    @Path("/dish/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDish(Dish dish) throws SQLException {
        boolean result = service.updateDish(dish);

        return result ? Response.ok().build() : Response.notModified().build();
    }

    @DELETE
    @Path("/dish/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDish(@PathParam("id")String id) throws SQLException {
        boolean result = service.deleteDish(id);

        return result ? Response.ok().build() : Response.notModified().build();
    }



}
