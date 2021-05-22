package daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Utils.DBContext;
import dtos.Account;

public class UserDAO {
	public Account getUserByID(int ID) {
		try {
			Account result = new Account();
			Connection conn = DBContext.makeConnection();
			String query = "SELECT UserID, Username, Password, RoleID, Email, FullName, DoB, Address, PhoneNumber, IsActive "
					+ "FROM Accounts "
					+ "WHERE UserID = ?";
			PreparedStatement prstm = conn.prepareStatement(query);
			prstm.setInt(1, ID);
			ResultSet rs = prstm.executeQuery();
			
			while (rs.next()) {
				result.setUserID(rs.getInt("UserID"));
				result.setUsername(rs.getString("Username"));
				result.setPassword(rs.getString("Password"));
				result.setFullName(rs.getString("FullName"));
				result.setEmail(rs.getString("Email"));
				result.setDoB(
						new java.util.Date(rs.getDate("DoB").getTime()));
				result.setAddress(rs.getString("Address"));
				result.setPhoneNumber(rs.getString("PhoneNumber"));
				result.setActive(rs.getBoolean("IsActive"));
				result.setRole(result.getRoleName(rs.getInt("RoleID")));
			}
			conn.close();
			return result;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Account getAccountByUNameAndPassword(String username, String password) {
		try {
			Account result = new Account();
			Connection conn = DBContext.makeConnection();
			String query = 
					"SELECT UsesrID, Username, Password, RoleID, Email, FullName, DoB, Address, PhoneNumber, IsActive "
					+ "FROM Accounts "
					+ "WHERE UserName = ? AND Password = ?";
			PreparedStatement prstm = conn.prepareStatement(query);
			prstm.setString(1, username);
			prstm.setString(2,  password);
			ResultSet rs = prstm.executeQuery();
			
			while (rs.next()) {
				result.setUserID(rs.getInt("UserID"));
				result.setUsername(rs.getString("Username"));
				result.setPassword(rs.getString("Password"));
				result.setFullName(rs.getString("FullName"));
				result.setEmail(rs.getString("Email"));
				result.setDoB(
						new java.util.Date(rs.getDate("DoB").getTime()));
				result.setAddress(rs.getString("Address"));
				result.setPhoneNumber(rs.getString("PhoneNumber"));
				result.setActive(rs.getBoolean("IsActive"));
				result.setRole(result.getRoleName(rs.getInt("RoleID")));
			}
			conn.close();
			return result;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean createUserAccount(Account input) {
		try {
			Connection conn = DBContext.makeConnection();
			String query =
					"INSERT INTO Accounts (Username, Password, RoleID, Email, FullName, DoB, Address, PhoneNumber)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement prstm = conn.prepareStatement(query);
			
			prstm.setString(1, input.getUsername());
			prstm.setString(2, input.getPassword());
			prstm.setInt(3,  input.getRoleID("Customer"));
			prstm.setString(4, input.getEmail());
			prstm.setString(5, input.getFullName());
			prstm.setDate(6, java.sql.Date.valueOf(input.getDoB()));
			prstm.setString(7,  input.getAddress());
			prstm.setString(8, input.getPhoneNumber());
			
			return prstm.executeUpdate() == 1;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateUserInfo(Account input) {
		try {
			Connection conn = DBContext.makeConnection();
			String query =
					"UPDATE Accounts"
					+ "SET Username = ?, Password = ?, Email = ?, "
					+ "FullName = ?, DoB = ?, Address = ?, "
					+ "PhoneNumber = ? "
					+ "WHERE UserID= ?";
			PreparedStatement prstm = conn.prepareStatement(query);
			
			prstm.setString(1, input.getUsername());
			prstm.setString(2, input.getPassword());
			prstm.setString(3, input.getEmail());
			prstm.setString(4, input.getFullName());
			prstm.setDate(5, java.sql.Date.valueOf(input.getDoB()));
			prstm.setString(6,  input.getAddress());
			prstm.setString(7, input.getPhoneNumber());
			prstm.setInt(8, input.getUserID());
			
			return prstm.executeUpdate() == 1;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<Account> getAllCustomers(){
		try {
			ArrayList<Account> result = new ArrayList<Account>();
			Account tempoResult = new Account();
			Connection conn = DBContext.makeConnection();
		
			String query = "SELECT UserID, Username, Password, RoleID, Email, FullName, DoB, Address, PhoneNumber, IsActive "
					+ "FROM Accounts "
					+ "WHERE RoleID = ?";
		
			PreparedStatement prstm = conn.prepareStatement(query);
			prstm.setInt(1, tempoResult.getRoleID("Customer"));
			ResultSet rs = prstm.executeQuery();
		
			while (rs.next()) {
				tempoResult.setUserID(rs.getInt("UserID"));
				tempoResult.setUsername(rs.getString("Username"));
				tempoResult.setPassword(rs.getString("Password"));
				tempoResult.setFullName(rs.getString("FullName"));
				tempoResult.setEmail(rs.getString("Email"));
				tempoResult.setDoB(
					new java.util.Date(rs.getDate("DoB").getTime()));
				tempoResult.setAddress(rs.getString("Address"));
				tempoResult.setPhoneNumber(rs.getString("PhoneNumber"));
				tempoResult.setActive(rs.getBoolean("IsActive"));
				tempoResult.setRole(tempoResult.getRoleName(rs.getInt("RoleID")));
			
				result.add(tempoResult);
			}
			conn.close();
			return result;
		}		
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
