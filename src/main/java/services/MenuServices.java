package services;

import com.google.gson.Gson;
import daos.DishInDAO;
import daos.MenuDAO;
import dtos.Menu;
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
import java.util.List;

@Path("/menu")
public class MenuServices {
    private MenuDAO service = new MenuDAO();
    private DishInDAO dao = new DishInDAO();
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMenu(Menu menu) throws URISyntaxException ,SQLException{
        service.createMenu(menu);
        URI uri = new URI("/createmenu/");
        return Response.created(uri).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/adddishtomenu/{menuid}/{dishid}")
    public Response addDishToMenu(@PathParam("menuid") String menuid, @PathParam("dishid") String dishid) throws SQLException {
        if (dao.addDishToMenu(menuid,dishid)) {
            return Response.ok().build();
        } else {
            return Response.notModified().build();
        }
    }

    @DELETE
    @Path("/removedishfrommenu/{menuid}/{dishid}")
    public Response removedishfrommenu(@PathParam("menuid") String menuid, @PathParam("dishid") String dishid) throws SQLException {
        if (dao.deleteDishFromMenu(menuid,dishid)) {
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
    public List<Menu> list() throws  SQLException{
        return service.getAllActiveMenus(1);
    }

    @DELETE
    @Path("/delete/{id}")
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
    public Response getMenuByID(@PathParam("id") String id) throws SQLException {
        Menu menu = service.getMenuByID(id);
        if (menu != null) {
            return Response.ok(menu, MediaType.APPLICATION_JSON).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("homecook/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Menu> getMenuByHomeCookID(@PathParam("id") String id) throws SQLException {
        return service.getAllMenusByHomeCookID(id);
    }



}
