package services;

import Utils.encryption;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import daos.AccountDAO;
import dtos.Account;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
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
    @Path("/{role}/{name}/{page}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSearchedAccount(@PathParam("role") String role, @PathParam("name") String name,
                                     @PathParam("page") int page) {
        if (name.equals("all")) name="";
        ArrayList<Account> accounts= service.getSearchedAccount(name, page, role);

        Gson gson= new GsonBuilder().setPrettyPrinting().create();
        String result= gson.toJson(accounts);
        return result;
    }
    @GET
    @Path("/count/{role}/{username}")
    public String getTotalSearchedAccount(@PathParam("role") String role ,@PathParam("username") String username) {
        if (username.equals("all")) username="";
        int total= service.getTotalSearchedAccount(role, username);
        String result= gson.toJson(total);
        return result;
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
    @GET
    @Path("/count/{role}")
    public String getTotalAccount(@PathParam("role") String role) {
        int total= service.countByRole(role);
        String result= gson.toJson(total);
        return result;
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccount(String data)throws SQLException{
        Account account = gson.fromJson(data, Account.class);
        account.setSaltKey(encryption.saltKeyGenerate(account.getFullName().trim()));
        System.out.println(account);
        
        boolean rs = service.createAccount(account);

        return rs ? Response.status(Response.Status.OK).entity(gson.toJson(account)).build() : Response.serverError().build();
    }

    @POST
    @Path("/login")
     public Response login(String data)throws SQLException{
        System.out.println(data);
        Account account = gson.fromJson(data, Account.class);
        Account user = service.login(account.getUsername(), account.getPassword());
        user.setPassword("");
        return user != null ? Response.status(Response.Status.OK).entity(gson.toJson(user)).build() : Response.status(Response.Status.NO_CONTENT).build();
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
    public Response changeAccountStatus(@PathParam("userID")String userID, @PathParam("status")boolean status)throws SQLException{
        var result = service.changeAccountStatus(userID,status);
        return result ? Response.status(Response.Status.OK).entity(userID).build() : Response.notModified().build();
    }
}
