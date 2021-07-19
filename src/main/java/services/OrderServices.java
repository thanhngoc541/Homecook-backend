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
import java.sql.Timestamp;
import java.time.Instant;
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
    @Path("/orders/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllOrder(@PathParam("page") int page) {
        ArrayList<Order> orders= service.getAllOrder(page);
        String result= gson.toJson(orders);
        return result;
    }
    @GET
    @Path("/orders/{status}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllOrderByStatus(@PathParam("status") String status, @PathParam("page")int page) {
        ArrayList<Order> orders= service.getOrderByStatus(status, page);
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
    @Path("/homecook/{id}/{status}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrderByHomeCookIDAndStatus(@PathParam("id") String homecookID,
                                                @PathParam("status") String status, @PathParam("page") int page) throws SQLException {
        Order order= new Order();
        int stat= order.getStatusID(status);
        ArrayList<Order> orders= service.getOrderByHomeCookIDAndStatus(homecookID, stat, page);
        String result= gson.toJson(orders);
        return result;
    }
    @GET
    @Path("/customer/{id}/{status}/{page}/")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrderByCustomerIDAndStatus(@PathParam("id") String customerID, @PathParam("status") String status,
                                                @PathParam("page") int page) throws SQLException {
        Order order= new Order();
        int stat= order.getStatusID(status);
        ArrayList<Order> orders= service.getOrderByCustomerIDAndStatus(customerID, stat, page);
        String result= gson.toJson(orders);
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
    @GET
    @Path("/orders/{fromDate}/{toDate}/{status}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrderByTimeRangeAndStatus(@PathParam("fromDate") String fromDate,
                                              @PathParam("toDate") String toDate,
                                      @PathParam("status") String status, @PathParam("page") int page) throws SQLException {
        Instant from= Instant.ofEpochSecond(Long.parseLong(fromDate));
        Instant to= Instant.ofEpochSecond(Long.parseLong(toDate));
        ArrayList<Order> orders= service.getOrderByTimeRangeAndStatus(from, to, status, page);
        String result= gson.toJson(orders);
        return result;
    }
    @GET
    @Path("/orders/{fromDate}/{toDate}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrderByTimeRange(@PathParam("fromDate") String fromDate, @PathParam("toDate") String toDate,
                                       @PathParam("page") int page) throws SQLException {
        Instant from= Instant.ofEpochSecond(Long.parseLong(fromDate));
        Instant to= Instant.ofEpochSecond(Long.parseLong(toDate));
        ArrayList<Order> orders= service.getOrderByTimeRange(from, to, page);
        String result= gson.toJson(orders);
        return result;
    }
    @GET
    @Path(("/first"))
    @Produces(MediaType.APPLICATION_JSON)
    public String getFirstSevenOrder() {
        ArrayList<Order> orders= service.getSevenOrder();
        String result= gson.toJson(orders);
        return result;
    }
    @GET
    @Path("/count")
    public String getTotalOrder() {
        int total= service.getTotalOfOrder();
        String result= gson.toJson(total);
        return result;
    }
    @GET
    @Path("/count/orders/{fromDate}/{toDate}/{status}")
    @Produces(MediaType.APPLICATION_JSON)
    public String countOrderByDateRangeAndStatus(@PathParam("fromDate")  String fromDate,
                                                 @PathParam("toDate") String toDate,
                                                 @PathParam("status") String status) {
        Instant from= Instant.ofEpochSecond(Long.parseLong(fromDate));
        Instant to= Instant.ofEpochSecond(Long.parseLong(toDate));
        int count= service.countOrderByDateRangeAndStatus(from, to, status);
        String result= gson.toJson(count);
        return result;
    }
    @GET
    @Path("/count/orders/{status}")
    @Produces(MediaType.APPLICATION_JSON)
    public String countAllOrderByStatus(@PathParam("status")String status) {
        int count = service.countAllOrderByStatus(status);
        String result= gson.toJson(count);
        return result;
    }
    @GET
    @Path("/count/customer/{id}/{status}")
    public String countCustomerOrderByIDAndStatus(@PathParam("id") String id, @PathParam("status") String status) {
        int count= service.countCustomerOrderByIDAndStatus(id, status);
        String result = gson.toJson(count);
        return result;
    }
    @GET
    @Path("/count/homecook/{id}/{status}")
    public String countHomeCookOrderByIDAndStatus(@PathParam("id") String id, @PathParam("status") String status) {
        int count= service.countHomeCookOrderByIDAndStatus(id, status);
        String result = gson.toJson(count);
        return result;
    }
    @GET
    @Path("/count/homecook/{id}")
    public String getTotalHomeCookOrder(@PathParam("id") String id) {
        int total= service.getTotalHomeCookOrder(id);
        String result= gson.toJson(total);
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
    public Response updateOrder02(@PathParam("id") String orderID, @PathParam("status") String status) throws SQLException {
//        Order order= gson.fromJson(data, Order.class);
//        Order order= new Order();
//        int stat=order.getStatusID(status);
        boolean result= service.changeOrderStatus(orderID, status);
        return result ? Response.ok().build() : Response.notModified().build();
    }
    @DELETE
    @Path("/{id}")
    public Response deleteOrder(@PathParam("id") String id) {
        if (service.deleteOrder(id)) {
            return Response.ok().build();
        }
        else return Response.notModified().build();
    }
}