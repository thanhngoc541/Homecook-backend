package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import daos.AccountDAO;
import dtos.Account;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;


@Path("/accounts")
public class AccountServices {
    final private AccountDAO service = new AccountDAO();
    final private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/role/{role}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccountByRole(@PathParam("role") String role) throws SQLException {
        List<Account> accounts = service.getAllAccountByRole(role.toLowerCase(),1);
        if (accounts.size()>0){
            return Response.status(Response.Status.OK).entity(gson.toJson(accounts)).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @GET
    @Path("/{role}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public String countByRole(@PathParam("role") String role) {
        int count= service.countByRole(role);
        String result= gson.toJson(count);
        return result;
    }
    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public  Response getAccountByID(@PathParam("id")String id) throws SQLException {
        Account account = service.getAccountByID(id);
        if (account != null){
            return Response.status(Response.Status.OK).entity(gson.toJson(account)).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/username/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountByUName(@PathParam("username")String username)throws SQLException{
        Account account  = service.getAccountByUName(username);
        if (account != null){
            return Response.status(Response.Status.OK).entity(gson.toJson(account)).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccount(String data)throws SQLException{
        Account account = gson.fromJson(data, Account.class);
        service.createAccount(account);
        Account user = service.getAccountByUName(account.getUsername());
        return user !=null ? Response.status(Response.Status.OK).entity(user.getUserID()).build() : Response.serverError().build();
    }

    @POST
    @Path("/login")
     public Response login(String data)throws SQLException{
        Account account = gson.fromJson(data, Account.class);

        System.out.println(account.hashPasswords());

        Account user = service.login(account.getUsername(), account.hashPasswords());
        return user != null ? Response.status(Response.Status.OK).entity(user).build() : Response.status(Response.Status.NO_CONTENT).build();
    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAccountInfo(String data)throws SQLException{
        Account account = gson.fromJson(data, Account.class);
        var result = service.updateAccountInfo(account);
        return result ? Response.status(Response.Status.OK).entity(account.getUserID()).build() : Response.notModified().build();
    }

    @PUT
    @Path("/{userID}/{status}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeHomeCookStatus(@PathParam("userID")String userID, @PathParam("status")boolean status)throws SQLException{
        var result = service.changeHomeCookStatus(userID,status);
        return result ? Response.status(Response.Status.OK).entity(userID).build() : Response.notModified().build();
    }
}
