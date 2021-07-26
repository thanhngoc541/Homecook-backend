package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import daos.OrderDAO;
import daos.OrderMenuDAO;
import dtos.Order;
import dtos.OrderItem;
import dtos.OrderMenu;
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
    private OrderMenuDAO serviceMenu= new OrderMenuDAO();
    private OrderDAO service= new OrderDAO();
    private Gson gson= new GsonBuilder().setPrettyPrinting().create();
    @GET
    @Path("/customer/{id}/{input}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrderByCustomerID(@PathParam("id") String id,@PathParam("input") String input,
                                       @PathParam("page") int page) throws SQLException {
        if (input.equals("all")) input = "";
        ArrayList<Order> orders= service.getOrderByCustomerID(id, page, input);
        String result= gson.toJson(orders);
        return result;
    }
    @GET
    @Path("/orders/{name}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllOrder(@PathParam("name") String input, @PathParam("page") int page) {
        if (input.equals("all")) input="";
        ArrayList<Order> orders= service.getAllOrder(page, input);
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
    @Path("/homecook/{id}/{status}/{name}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrderByHomeCookIDAndStatus(@PathParam("id") String homecookID,
                                                @PathParam("status") String status,
                                                @PathParam("name") String input, @PathParam("page") int page) throws SQLException {
        if (input.equals("all")) input= "";
        Order order= new Order();
        int stat= order.getStatusID(status);
        ArrayList<Order> orders= service.getOrderByHomeCookIDAndStatus(homecookID,input, stat, page);
        String result= gson.toJson(orders);
        return result;
    }
    @GET
    @Path("/customer/{id}/{status}/{name}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrderByCustomerIDAndStatus(@PathParam("id") String customerID, @PathParam("status") String status,
                                                @PathParam("name") String name,@PathParam("page") int page) throws SQLException {
        if(name.equals("all")) name="";
        ArrayList<Order> orders= service.getOrderByCustomerIDAndStatus(customerID, status, name, page);
        String result= gson.toJson(orders);
        return result;
    }
    @GET
    @Path("/homecook/{id}/{name}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrderByHomeCookID(@PathParam("id") String id, @PathParam("name") String input,
                                       @PathParam("page") int page) throws SQLException {
        if (input.equals("all")) input ="";
        ArrayList<Order> orders= service.getOrderByHomeCookID(id, input, page);
        String result= gson.toJson(orders);
        return result;
    }
    @GET
    @Path("/orders/{status}/{name}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllOrderByStatus(@PathParam("status") String status,
                                      @PathParam("page")int page , @PathParam("name") String input) {
        if (input.equals("all")) input="";
        ArrayList<Order> orders= service.getOrderByStatus(status, page, input);
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
    @Path("/count/{name}")
    public String getTotalOrder(@PathParam("name") String input) {
        if (input.equals("all")) input ="";
        int total= service.getTotalOfOrder(input);
        String result= gson.toJson(total);
        return result;
    }
    // @GET
    // @Path("/count/{date}")
    // public String countTodayOrderAdmin(@PathParam("date") String input) {
    //     Instant date= Instant.ofEpochSecond(Long.parseLong(input));
    //     int count = service.countTodayOrderAdmin(date);
    //     String result = gson.toJson(count);
    //     return result;
    // }
    @GET
    @Path("/count/{id}/{date}")
    public String countTodayOrderHomeCook(@PathParam("id") String homecookID , @PathParam("date") String input) {
        Instant date= Instant.ofEpochSecond(Long.parseLong(input));
        int count = service.countTodayOrderHomeCook(homecookID, date);
        String result = gson.toJson(count);
        return result;
    }
    @GET
    @Path("/sales/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrderByWeek(@PathParam("id") String homeCookID) {
        ArrayList total= service.getOrderByWeek(homeCookID);
        String result= gson.toJson(total);
        return result;
    }
    @GET
    @Path("/sales/")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrderByWeek() {
        ArrayList total= service.getOrderByWeekAdmin();
        String result= gson.toJson(total);
        return result;
    }
    @GET
    @Path("/count/{id}/items")
    @Produces(MediaType.APPLICATION_JSON)
    public String countOrderItem(@PathParam("id") String id) {
        int count = service.countOrderItem(id);
        String result= gson.toJson(count);
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
    @Path("/count/customer/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String countCustomerOrder(@PathParam("id") String customerID) {
        int count = service.countCustomerOrder(customerID);
        String result= gson.toJson(count);
        return result;
    }
    @GET
    @Path("/count/orders/{status}/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String countAllOrderByStatus(@PathParam("status")String status, @PathParam("name") String input) {
        if (input.equals("all")) input="";
        int count = service.countAllOrderByStatus(status, input);
        String result= gson.toJson(count);
        return result;
    }
    @GET
    @Path("/count/customer/{id}/{status}/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String countCustomerOrderByIDAndStatus(@PathParam("id") String id, @PathParam("status") String status,
                                                  @PathParam("name") String name) {
        if (name.equals("all")) name="";
        int count= service.countCustomerOrderByIDAndStatus(id, status, name);
        String result = gson.toJson(count);
        return result;
    }
    @GET
    @Path("/count/homecook/{id}/{status}/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String countHomeCookOrderByIDAndStatus(@PathParam("id") String id, @PathParam("status") String status,
                                                  @PathParam("name") String name) {
        if (name.equals("all")) name="";
        int count= service.countHomeCookOrderByIDAndStatus(id, status, name);
        String result = gson.toJson(count);
        return result;
    }
    @GET
    @Path("/count/homecook/{id}")
    @Produces(MediaType.APPLICATION_JSON)
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
        Order order= service.getOrderById(id);
        String result= "";
        if (order.isMenu()) {
            ArrayList<OrderMenu> menus = serviceMenu.getOrderMenuByOrderID(id, 1);
             result= gson.toJson(menus);
        }
        else {
            ArrayList<OrderItem> items= service.getListItemByOrderID(id,1);
             result= gson.toJson(items);
        }
        return result;
    }

    //-----------GetList menu
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(String data, @Context UriInfo uriInfo) throws SQLException, URISyntaxException {
        //lam sao de extract cai order item ra khoi data string
        Order resultID = new Order();
        Order order= gson.fromJson(data, Order.class);
        if (!order.isMenu()) {
             resultID = service.createOrder(order);
            if (resultID.getOrderItems() != null) {
                for (OrderItem item : resultID.getOrderItems()) {
                    item.setOrderID(order.getOrderID());
                    String resultItem= service.insertOrderItems(item);
                }
            }
        }
        if (order.isMenu()) {
             resultID= service.createOrder(order);
            if (resultID.getOrderMenus() != null) {
                for (OrderMenu menu : resultID.getOrderMenus()) {
                    menu.setOrderID(order.getOrderID());
                    String resultItem = serviceMenu.insertOrderMenu(menu);
                }
            }
        }

        URI uri= null;
        if (!resultID.getOrderID().isEmpty()) {
            uri= new URI(uriInfo.getAbsolutePath() +"/"+ resultID.getOrderID());
        }
        return Response.status(Response.Status.OK).entity(uri).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/updateStatus/{id}/{status}")
    public Response updateOrder02(@PathParam("id") String orderID, @PathParam("status") String status) throws SQLException {
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