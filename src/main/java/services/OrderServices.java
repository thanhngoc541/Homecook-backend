package services;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import daos.OrderDAO;
import dtos.Order;
import dtos.OrderItem;

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
import java.util.HashMap;
import java.util.Map;

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
    public String createOrder(String data) throws SQLException, URISyntaxException {
        System.out.println(data);
//        final ObjectMapper mapper = new ObjectMapper();
//        ArrayList<OrderItem> items = null;
//        try {
//            Map<String, Object> map = new HashMap<String, Object>();
//            map = mapper.readValue(data, new TypeReference<HashMap<String, Object>>() {
//            });
//            String itemsStr = map.get("OrderItems").toString();
//            Type orderType = new TypeToken<ArrayList<OrderItem>>() {
//            }.getType();
//            items = gson.fromJson(itemsStr, orderType);
//        } catch (JsonMappingException e) {
//            e.printStackTrace();
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

        //lam sao de extract cai order item ra khoi data string
        Order order= gson.fromJson(data, Order.class);
        Order resultID = service.createOrder(order);
        String resultItem= service.insertOrderItems(order);
        System.out.println(resultID);
        System.out.println(resultItem);
        return resultItem;
//        URI uri = null;
//        if (!resultID.isEmpty()) {
//            uri = new URI(uriInfo.getAbsolutePath() + "/order/" + resultID);
//        }
//        return Response.created(uri).build();
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