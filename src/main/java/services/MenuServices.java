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
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String createMenu(String data) throws URISyntaxException ,SQLException{
        Menu menu = gson.fromJson(data, Menu.class);
        String result = service.createMenu(menu);
        return result;
      //  URI uri = new URI("/menu/"+menuID);
       // return Response.created(uri).build();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/adddishtomenu/{menuid}/{dishid}")
    public Response addDishToMenu(@PathParam("menuid") String menuid, @PathParam("dishid") String dishid) throws SQLException {
        DishIn dishIn = new DishIn(menuid,dishid);//gson.fromJson(data,DishIn.class);
            if (dao.addDishToMenu(dishIn)) {
            return Response.ok().build();
        } else {
            return Response.notModified().build();
        }
    }

    @GET
        @Path("/removedishfrommenu/{menuid}/{dishid}")
    public Response removedishfrommenu(@PathParam("menuid") String menuid, @PathParam("dishid") String dishid) throws SQLException {
        DishIn dishIn = new DishIn(menuid,dishid);
        if (dao.deleteDishFromMenu(dishIn)) {
            return Response.ok().build();
        } else {
            return Response.notModified().build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/changename/{id}/{name}")
    public Response changename(@PathParam("id") String id, @PathParam("name") String name) throws SQLException {
        if (service.changeMenuName(name,id)) {
            return Response.ok().build();
        } else {
            return Response.notModified().build();
        }
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/changestatus/{id}/{status}")
    public Response changstatus(@PathParam("id") String id, @PathParam("status") boolean status) throws SQLException {
        if (service.changeMenuStatus(status,id)) {
            return Response.ok().build();
        } else {
            return Response.notModified().build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String list() throws  SQLException{
        List<Menu> items= service.getAllActiveMenus(1);
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
        String result= gson.toJson(items);
        return result;
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMenu(@PathParam("id") String id) throws SQLException {
        if (service.deleteMenu(id)) {
            return Response.ok().build();
        } else {
            return Response.notModified().build();
        }
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



}
