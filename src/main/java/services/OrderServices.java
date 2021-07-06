package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import daos.OrderDAO;
import dtos.Order;
import dtos.OrderItem;
import org.glassfish.jersey.server.Uri;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/order")
public class OrderServices {
    private OrderDAO service= new OrderDAO();
    private Gson gson= new GsonBuilder().setPrettyPrinting().create();
    @GET
    @Path("/customer/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrderByCustomerID(@PathParam("id") String id) throws SQLException {
        ArrayList<Order> orders= service.getOrderByCustomerID(id, 1);
        String result= gson.toJson(orders);
        return result;
    }
    @GET
    @Path("/orders")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllOrder() {
        ArrayList<Order> orders= service.getAllOrder();
        String result= gson.toJson(orders);
        return result;
    }
    @GET
    @Path("/byId/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrderByID(@PathParam("id") String id) {
        Order order= service.getOrderById(id);
        String result= gson.toJson(order);
        return result;
    }

    @GET
    @Path("/homecook/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrderByHomeCookID(@PathParam("id") String id) throws SQLException {
        ArrayList<Order> orders= service.getOrderByHomeCookID(id, 1);
        String result= gson.toJson(orders);
        return result;
    }

    //ORDER ITEMs
    @GET
    @Path("/item/{orderID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getListItems(@PathParam("orderID") String id) throws SQLException {
        ArrayList<OrderItem> items= service.getListItemByOrderID(id,1);
        String result= gson.toJson(items);
        return result;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(String data, @Context UriInfo uriInfo) throws SQLException, URISyntaxException {
        //lam sao de extract cai order item ra khoi data string
        Order order= gson.fromJson(data, Order.class);
        Order resultID = service.createOrder(order);
        System.out.println(resultID);
        if (resultID.getOrderItems() != null) {
            for (OrderItem item : resultID.getOrderItems()) {
                item.setOrderID(order.getOrderID());
                String resultItem= service.insertOrderItems(item);
                System.out.println(resultItem);
            }
        }
        URI uri= null;
        if (!resultID.getOrderID().isEmpty()) {
            uri= new URI(uriInfo.getAbsolutePath() +"/"+ resultID.getOrderID());
        }
        System.out.println("uri: " + uri);
        return Response.created(uri).build();

    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/updateStatus/{id}/{status}")
    public Response updateOrder(@PathParam("id") String id, @PathParam("status") String status) throws SQLException {
        if (service.changeOrderStatus(id, status))
            return Response.ok().build();
        else return Response.notModified().build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteOrder(@PathParam("id") String id) {
        if (service.deleteOrder(id)) {
            return Response.ok().build();
        }
        else return Response.notModified().build();
    }
}