package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Utils.DBContext;
import dtos.Account;

public class HomeCookDAO {
	public ArrayList<Account> getAllHomeCook(){
		try {
			ArrayList<Account> result = new ArrayList<Account>();
			Account tempoResult = new Account();
			Connection conn = DBContext.makeConnection();
			
			String query = "SELECT UserID, Username, Password, RoleID, Email, FullName, DoB, Address, PhoneNumber, IsActive "
					+ "FROM Accounts "
					+ "WHERE RoleID = ?";
			
			PreparedStatement prstm = conn.prepareStatement(query);
			prstm.setInt(1, tempoResult.getRoleID("HomeCook"));
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
	
	public boolean createHomeCookAccount(Account input) {
		try {
			Connection conn = DBContext.makeConnection();
			String query =
					"INSERT INTO Accounts (Username, Password, RoleID, Email, FullName, DoB, Address, PhoneNumber)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement prstm = conn.prepareStatement(query);
			
			prstm.setString(1, input.getUsername());
			prstm.setString(2, input.getPassword());
			prstm.setInt(3,  input.getRoleID("HomeCook"));
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
	
	public boolean changeHomeCookStatus(Account input) {
		try {
		Connection conn = DBContext.makeConnection();
		
		String query =
				"UPDATE Accounts"
				+ "SET IsActive = ?"
				+ "WHERE UserID= ?";
		
		PreparedStatement prstm = conn.prepareStatement(query);
		
		prstm.setBoolean(1, input.isActive());
		prstm.setInt(8, input.getUserID());
		
		return prstm.executeUpdate() == 1;
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	return false;
	}
}
