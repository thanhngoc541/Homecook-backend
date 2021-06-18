package daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dtos.Account;

import Utils.DBContext;


public class AccountDAO {

	public ArrayList<Account> getAllAccountByRole(String role, int page){
		try {
			ArrayList<Account> result = new ArrayList<Account>();
			Account tempoResult = new Account();
			Connection conn = DBContext.makeConnection();

			String query = "getAccountByRoleID @page=?, @RoleID = ?";

			PreparedStatement prstm = conn.prepareCall(query);
			prstm.setInt(1, page);
			prstm.setInt(2,  tempoResult.getRoleID(role));
			ResultSet rs = prstm.executeQuery();

			while (rs.next()) {
				Account input = new Account (rs.getString("AccountID"), rs.getString("Username"),
						rs.getString("Password"), tempoResult.getRoleName(rs.getInt("RoleID")),
						rs.getString("Email"), rs.getString("FullName"),
						 new java.util.Date(rs.getDate("DoB").getTime()),
						rs.getString("Address"), rs.getString("PhoneNumber"),
						rs.getBoolean("IsActive"));

				result.add(input);
			}
			conn.close();
			return result;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param input
	 * @return
	 */
	public boolean createAccount(Account input) {
		try {
			Connection conn = DBContext.makeConnection();
			String query = "EXEC createAccount @Username = ?, "
					+ "@Password = ?, "
					+ "@RoleID = ?, "
					+ "@Email= ?, "
					+ "@FullName = ?, "
					+ "@Date = ?, "
					+ "@Address = ?, "
					+ "@PhoneNumber = ?";
			PreparedStatement prstm = conn.prepareCall(query);

			prstm.setString(1, input.getUsername());
			prstm.setString(2, input.getPassword());
			prstm.setInt(3,  input.getRoleID(input.getRole()));
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
	public boolean changeHomeCookStatus(String userID, boolean status) {
		try {
		Connection conn = DBContext.makeConnection();

		String query =	"EXEC changeHomeCookStatus "
				+ "@IsActive = ?, "
				+ "@UserID = ?";

		PreparedStatement prstm = conn.prepareStatement(query);

		prstm.setBoolean(1, status);
		prstm.setString(2, userID);

		return prstm.executeUpdate() == 1;
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	return false;
	}
	public Account getAccountByID(String ID) {
		try {
			Account result = new Account();
			Connection conn = DBContext.makeConnection();
			String query = "EXEC getAccountByID @UserID = ?";
			PreparedStatement prstm = conn.prepareStatement(query);
			prstm.setString(1, ID);
			ResultSet rs = prstm.executeQuery();

			while (rs.next()) {
				result.setUserID(rs.getString("AccountID"));
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

	public Account getAccountByUName(String username) {
		try {
			Account result = new Account();
			Connection conn = DBContext.makeConnection();
			String query =
					"EXEC getAccountByUName @Username = ?";
			PreparedStatement prstm = conn.prepareStatement(query);
			prstm.setString(1, username);
			ResultSet rs = prstm.executeQuery();

			while (rs.next()) {
				result.setUserID(rs.getString("AccountID"));
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


	public boolean updateAccountInfo(Account input) {
		try {
			Connection conn = DBContext.makeConnection();
			String query ="EXEC updateAccountInfo "
					+ "@Username = ?,"
					+ "@Password = ?,"
					+ "@Email = ?,"
					+ "@FullName = ?,"
					+ "@Date = ?,"
					+ "@Address = ?,"
					+ "@PhoneNumber = ?,"
					+ "@UserID = ?";
			PreparedStatement prstm = conn.prepareStatement(query);

			prstm.setString(1, input.getUsername());
			prstm.setString(2, input.getPassword());
			prstm.setString(3, input.getEmail());
			prstm.setString(4, input.getFullName());
			prstm.setDate(5, new java.sql.Date(input.DoBForDA()));
			prstm.setString(6,  input.getAddress());
			prstm.setString(7, input.getPhoneNumber());
			prstm.setString(8, input.getUserID());
			return prstm.executeUpdate() == 1;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
//		AccountDAO tempo = new AccountDAO();
//		tempo.getAllAccountByRole("Customer", 2);
//		for (Account account : tempo.getAllAccountByRole("Customer",1)) {
//			System.out.println(account.toString());
//		}
//
//		tempo.changeHomeCookStatus(2, false);
//
//		System.out.println(tempo.getAccountByID(1));
//
//		tempo.updateAccountInfo(tempo.getAccountByID(10));
	}
}
