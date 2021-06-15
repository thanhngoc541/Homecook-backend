package services;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import daos.OrderDAO;
import dtos.Order;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/orders")
public class OrderServices {
    private OrderDAO service= new OrderDAO();

    @GET
    @Path("/customer/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrderByCustomerID(@PathParam("id") int id) throws SQLException {
        ArrayList<Order> orders= service.getOrderByCustomerID(id, 1);
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
        String result= gson.toJson(orders);
        return result;
    }

    @GET
    @Path("/homecook/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrderByHomeCookID(@PathParam("id") int id) throws SQLException {
        ArrayList<Order> orders= service.getOrderByHomeCookID(id, 1);
        Gson gson= new GsonBuilder().setPrettyPrinting().create();
        String result= gson.toJson(orders);
        return result;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(Order ord) throws SQLException, URISyntaxException {
        int newOrderId= service.createOrder(ord);
        URI uri= new URI("/customer/" + newOrderId);
        return Response.created(uri).build();
    }

    //Loi ham put
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response updateOrder(@PathParam("id") int id, Order ord, String status) throws SQLException {
        ord.setOrderID(id);
        if (service.changeOrderStatus(ord.getOrderID(), status))
            return Response.ok().build();
        else return Response.notModified().build();
    }
//
    @DELETE
    @Path("{id}")
    public Response deleteOrder(@PathParam("id") int id) {
        if (service.deleteOrder(id)) {
            return Response.ok().build();
        }
        else return Response.notModified().build();
    }


//    @POST
//    @Path("customer")
//    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    public Order createOrder(Order ord) throws SQLException {
//        System.out.println(ord);
//        service.createOrder(ord);
//        return ord;
//    }
//
//    @PUT
//    @Path("customer")
//    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    public Order updateOrder (Order ord, String status) throws SQLException {
//        System.out.println(ord);
//        if (service.changeOrderStatus(ord.getOrderID(), "Finished") == true) return ord;
//        return null;
//    }
}