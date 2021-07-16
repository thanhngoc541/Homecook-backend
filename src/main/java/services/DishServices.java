package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import daos.DishDAO;
import dtos.Dish;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

@Path("/dishes")
public class DishServices {
    private DishDAO service = new DishDAO();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @GET
    @Path("/count/homecook/{id}")
    public String getTotalHomeCookDish(@PathParam("id") String id) {
        int total= service.getTotalHomeCookDish(id);
        String result= gson.toJson(total);
        return result;
    }


    @GET
    @Path("/count/{status}")
    public int countAllDishes(@PathParam("status") boolean status) throws SQLException {
        int count = service.countAllDishesByStatus(status);
        if (count > 0) return count;
        return -1;
    }

    @GET
    @Path("/homecook/{id}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDishesByHomeCook(@PathParam("id") String id,@PathParam("page") int page) throws SQLException {
        List<Dish> dishes = service.getAllDishesByHomeCook(id,page);
        if (dishes.size()>0){
            return Response.status(Response.Status.OK).entity(gson.toJson(dishes)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDishByID(@PathParam("id")String id) throws SQLException {
        Dish d = service.getDishByID(id);
        if (d != null){
            return Response.status(Response.Status.OK).entity(gson.toJson(d)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/status/{status}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDishesByStatus(@PathParam("status")boolean status,@PathParam("page") int page) throws SQLException {
        List<Dish> d = service.getAllDishesByStatus(status,page);
        if (d.size()>0){
            return Response.status(Response.Status.OK).entity(gson.toJson(d)).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createDish(String data) throws SQLException, URISyntaxException {
        Dish dish = gson.fromJson(data, Dish.class);
        return gson.toJson(service.createDish(dish));
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDish(String data) throws SQLException {
        Dish dish = gson.fromJson(data, Dish.class);
        boolean result = service.updateDish(dish);
        return result ? Response.status(Response.Status.OK).entity(dish.getDishId()).build() : Response.notModified().build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDish(@PathParam("id")String id) throws SQLException {
        boolean result = service.deleteDish(id);
        return result ? Response.status(Response.Status.OK).entity(id).build() : Response.notModified().build();
    }
}
