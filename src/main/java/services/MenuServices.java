package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import daos.DishInDAO;
import daos.MenuDAO;
import dtos.Dish;
import dtos.DishIn;
import dtos.Menu;
import dtos.OrderItem;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("/menu")
public class MenuServices {
    private MenuDAO service = new MenuDAO();
    private DishInDAO dao = new DishInDAO();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @GET
    @Path("/count/homecook/{id}")
    public String getTotalHomeCookMenu(@PathParam("id") String id) {
        int total= service.getTotalHomeCookMenu(id);
        String result= gson.toJson(total);
        return result;
    }
    @GET
    @Path("/count/{name}")
    public String getTotalSearchedMenu(@PathParam("name") String name) {
        if (name.equals("all")) name="";
        int total= service.getTotalSearchedMenu(name);
        String result= gson.toJson(total);
        return result;
    }
    @GET
    @Path("/top")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTopMenu() throws  SQLException{
        List<Menu> items= service.getTopMenu();
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
        String result= gson.toJson(items);
        return result;
    }
    @GET
    @Path("/{name}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSearchedMenu(@PathParam("name") String name,@PathParam("page") int page) throws  SQLException{
        if (name.equals("all")) name="";
        List<Menu> items= service.getSearchedMenu(name,page);
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
        String result= gson.toJson(items);
        return result;
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMenuByID(@PathParam("id") String id) throws SQLException {
        Menu menu= service.getMenuByID(id);
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
        String result= gson.toJson(menu);
        return result;
    }
    @GET
    @Path("homecook/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMenuByHomeCookID(@PathParam("id") String id) throws SQLException {
        List<Menu> items= service.getAllMenusByHomeCookID(id);
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
        String result= gson.toJson(items);
        return result;
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createMenu(String data) throws URISyntaxException ,SQLException{
        Menu menu = gson.fromJson(data, Menu.class);
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
        String result = gson.toJson(service.createMenu(menu));
        return result;
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMenu(String data) throws URISyntaxException ,SQLException{
        Menu menu = gson.fromJson(data, Menu.class);
        if (service.updateMenu(menu)) {
            return Response.ok().build();
        } else {
            return Response.notModified().build();
        }
    }
    @PUT
    @Path("/{MenuID}/{Status}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeStatus(@PathParam("MenuID") String menuId,@PathParam("Status") String Status) throws URISyntaxException ,SQLException{
        if (service.changeMenuStatus(Status.equals("true"),menuId)) {
            return Response.ok().build();
        } else {
            return Response.notModified().build();
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/dish")
    public Response addDishToMenu(String data) throws SQLException {
        DishIn dishIn = gson.fromJson(data,DishIn.class);
        if (dao.addDishToMenu(dishIn)) {
            return Response.ok().build();
        } else {
            return Response.notModified().build();
        }
    }


    @DELETE
    @Path("/{id}")
    public boolean deleteMenu(@PathParam("id") String id) throws SQLException {
        return service.deleteMenu(id);

    }
    @DELETE
    @Path("/dish")
    public Response removeDishFromMenu(String data) throws SQLException {
        DishIn dishIn = gson.fromJson(data,DishIn.class);
        if (dao.deleteDishFromMenu(dishIn)) {
            return Response.ok().build();
        } else {
            return Response.notModified().build();
        }
    }







}
