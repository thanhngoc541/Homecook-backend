package services;

import com.google.gson.Gson;
import daos.MenuDAO;
import dtos.Menu;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("/menu")
public class MenuServices {
    private MenuDAO service = new MenuDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Menu> list() throws  SQLException{
        return service.getAllMenusByStatus(true,1);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMenuByID(@PathParam("id") int id) throws SQLException {
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
    public List<Menu> getMenuByHomeCookID(@PathParam("id") int id) throws SQLException {
        return service.getAllMenusByHomeCookID(1,1);
    }

}
