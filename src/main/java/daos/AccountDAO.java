package daos;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import dtos.Account;

import Utils.DBContext;


public class AccountDAO {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	private void closeConnection() throws SQLException {
		if (rs != null) {
			rs.close();
		}
		if (ps != null) {
			ps.close();
		}
		if (conn != null) {
			conn.close();
		}
	}
	public int countByRole(String roleName) {
		int count=0;
		String query= "EXEC countByRole " +
				"@RoleID= ?";
		Account account= new Account();
		try {
			conn= DBContext.makeConnection();
			if (conn != null) {
				ps= conn.prepareStatement(query);
				ps.setInt(1, account.getRoleID(roleName));
				rs = ps.executeQuery();
				while(rs.next()) {
					count = rs.getInt("Total");
				}
				return count;
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return 0;
	}

	public ArrayList<Account> getAllAccountByRole(String role, int page) throws SQLException  {
		try {
			ArrayList<Account> result = new ArrayList<Account>();
			Account tempoResult = new Account();
			conn = DBContext.makeConnection();

			String query = "getAccountByRoleID @page=?, @RoleID = ?";

			 ps = conn.prepareCall(query);
			ps.setInt(1, page);
			ps.setInt(2,  tempoResult.getRoleID(role));
			 rs = ps.executeQuery();

			while (rs.next()) {
				Account input = new Account (rs.getString("AccountID"), rs.getString("Username"),
						rs.getString("Password"), tempoResult.getRoleName(rs.getInt("RoleID")),
						rs.getString("Email"), rs.getString("FullName"),
						 new java.util.Date(rs.getDate("DoB").getTime()),
						rs.getString("Address"), rs.getString("PhoneNumber"),
						rs.getBoolean("IsActive"));

				result.add(input);
			}
			return result;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			closeConnection();
		}
		return null;
	}

	public Account getAccountByID(String ID) throws SQLException {
		try {
			Account result = new Account();
			 conn = DBContext.makeConnection();
			String query = "EXEC getAccountByID @UserID = ?";
			if (conn != null) {
				ps = conn.prepareStatement(query);
				ps.setString(1, ID);
				rs = ps.executeQuery();

				while (rs.next()) {
					result.setUserID(rs.getString("AccountID"));
					result.setUsername(rs.getString("Username"));
					result.setPassword(rs.getString("Password"));
					result.setFullName(rs.getString("FullName").trim());
					result.setEmail(rs.getString("Email"));
					result.setDoB(
							new java.util.Date(rs.getDate("DoB").getTime()));
					result.setAddress(rs.getString("Address"));
					result.setPhoneNumber(rs.getString("PhoneNumber"));
					result.setActive(rs.getBoolean("IsActive"));
					result.setRole(result.getRoleName(rs.getInt("RoleID")));
					result.setSaltKey(rs.getString("SaltKey"));
					result.setToken(rs.getString("token"));
				}
				return result;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return null;
	}

	public Account getAccountByUName(String username) throws SQLException{
		try {
			Account result = new Account();
			 conn = DBContext.makeConnection();
			String query =
					"EXEC getAccountByUName @Username = ?";
			assert conn != null;
			 ps = conn.prepareStatement(query);
			ps.setString(1, username);
			 rs = ps.executeQuery();

			while (rs.next()) {
				result.setUserID(rs.getString("AccountID"));
				result.setUsername(rs.getString("Username"));
				result.setPassword(rs.getString("Password"));
				result.setFullName(rs.getString("FullName").trim());
				result.setEmail(rs.getString("Email"));
				result.setDoB(
						new java.util.Date(rs.getDate("DoB").getTime()));
				result.setAddress(rs.getString("Address"));
				result.setPhoneNumber(rs.getString("PhoneNumber"));
				result.setActive(rs.getBoolean("IsActive"));
				result.setRole(result.getRoleName(rs.getInt("RoleID")));
				result.setSaltKey(rs.getString("SaltKey"));
			}
			conn.close();
			return result;
		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return null;
	}

	public int getTotalSearchedAccount(String role, String username) {
		int count= 0;
		Account  account= new Account();
		int roleID= account.getRoleID(role);
		String query= "EXEC countSearchAccount " +
				"@searchPhrase = ?, " +
				"@Role = ?";
		try {
			conn = DBContext.makeConnection();
			if (conn != null) {
				ps= conn.prepareStatement(query);
				 ps.setString(1, username);
				 ps.setInt(2, roleID);
				 rs= ps.executeQuery();
				 while (rs.next()) {
				 	count= rs.getInt("total");
				 }
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return count;
	}
	public ArrayList<Account> getSearchedAccount(String name, int page, String role) {
		Account account= new Account();
		int roleID= account.getRoleID(role);
		ArrayList<Account> list = new ArrayList<>();
		String query= "EXEC searchAccount " +
				"@searchPhrase = ? , " +
				"@Page = ?, " +
				"@Role = ?";
		try {
			conn = DBContext.makeConnection();
			if (conn != null) {
				ps= conn.prepareStatement(query);
				ps.setString(1, name);
				ps.setInt(2, page);
				ps.setInt(3, roleID);
				rs = ps.executeQuery();
				while (rs.next()) {
					Account result = new Account();
					result.setUserID(rs.getString("AccountID"));
					result.setUsername(rs.getString("Username"));
					result.setPassword(rs.getString("Password"));
					result.setFullName(rs.getString("FullName").trim());
					result.setEmail(rs.getString("Email"));
					result.setDoB(
							new java.util.Date(rs.getDate("DoB").getTime()));
					result.setAddress(rs.getString("Address"));
					result.setPhoneNumber(rs.getString("PhoneNumber"));
					result.setActive(rs.getBoolean("IsActive"));
					result.setRole(result.getRoleName(rs.getInt("RoleID")));
					result.setSaltKey(rs.getString("SaltKey"));
					list.add(result);
				}
				return list;
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}
	public boolean createAccount(Account input) throws SQLException{
		try {
			conn = DBContext.makeConnection();
			if (conn != null){
				String query = "EXEC createAccount @Username = ?, "
						+ "@Password = ?, "
						+ "@RoleID = ?, "
						+ "@Email= ?, "
						+ "@FullName = ?, "
						+ "@Date = ?, "
						+ "@Address = ?, "
						+ "@PhoneNumber = ?, "
						+ "@SaltKey = ?";
				ps = conn.prepareStatement(query);

				ps.setString(1, input.getUsername());
				ps.setString(2, input.hashPasswords());
				ps.setInt(3,  input.getRoleID(input.getRole()));
				ps.setString(4, input.getEmail());
				ps.setString(5, input.getFullName());

				java.sql.Date tempDob = new java.sql.Date(input.getDoB().getTime());
				ps.setDate(6, tempDob);

				ps.setString(7,  input.getAddress());
				ps.setString(8, input.getPhoneNumber());
				ps.setString(9, input.getSaltKey());

				ps.executeUpdate();
				return true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return false;
	}

	public boolean changeAccountStatus(String userID, boolean status) throws SQLException{
		Account usr = getAccountByID(userID);
		if (!usr.getRole().equalsIgnoreCase("admin")){
			try {
				conn = DBContext.makeConnection();
				if(conn != null){
					String query =	"EXEC changeHomeCookStatus "
							+ "@IsActive = ?, "
							+ "@UserID = ?";

					ps = conn.prepareStatement(query);

					ps.setBoolean(1, status);
					ps.setString(2, userID);

					ps.executeUpdate();
					return true;
				}
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				closeConnection();
			}
		}


		return false;
	}

	public boolean updateAccountInfo(Account input) throws SQLException{
		try {
			 conn = DBContext.makeConnection();
			String query ="EXEC updateAccountInfo "
					+ "@Username = ?,"
					+ "@Password = ?,"
					+ "@Email = ?,"
					+ "@FullName = ?,"
					+ "@Date = ?,"
					+ "@Address = ?,"
					+ "@PhoneNumber = ?,"
					+ "@UserID = ?, " +
					"@token= ?";
			 ps = conn.prepareStatement(query);

			ps.setString(1, input.getUsername());
			ps.setString(2, input.getPassword());
			ps.setString(3, input.getEmail());
			ps.setString(4, input.getFullName());
			ps.setDate(5, new java.sql.Date(input.DoBForDA()));
			ps.setString(6,  input.getAddress());
			ps.setString(7, input.getPhoneNumber());
			ps.setString(8, input.getUserID());
			ps.setString(9, input.getToken());
			ps.executeUpdate();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return false;
	}
	public boolean setAccountToken(String id, String token) throws SQLException {
		String query= "EXEC setAccountToken " +
				"@AccountID = ?, " +
				"@Token = ? ";
		try {
			conn = DBContext.makeConnection();
			if (conn!= null) {
				ps=conn.prepareStatement(query);
				ps.setString(1, id);
				ps.setString(2, token);
				ps.executeUpdate();
				return true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			closeConnection();
		}
		return false;
	}
	public Account login(String username, String password) throws SQLException {
		try{
			Account a = getAccountByUName(username);
			if (a.getPassword().equalsIgnoreCase(Utils.encryption.toHexString(Utils.encryption.getSHA(password + a.getSaltKey())))) {
				return a;
			}
		} catch (SQLException | NoSuchAlgorithmException throwables) {
			throwables.printStackTrace();
		}
		return null;
	}

	public int getCustomerCount() {
		int count=0;
		String query= "EXEC getCustomerCount";
		try {
			conn = DBContext.makeConnection();
			if (conn != null) {
				ps= conn.prepareStatement(query);
				rs= ps.executeQuery();
				while (rs.next()) {
					count= rs.getInt("Total");
				}
				return count;
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return 0;
	}
	public int getHomecookCount() {
		int count=0;
		String query= "EXEC getHomecookCount";
		try {
			conn = DBContext.makeConnection();
			if (conn != null) {
				ps= conn.prepareStatement(query);
				rs= ps.executeQuery();
				while (rs.next()) {
					count= rs.getInt("Total");
				}
				return count;
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return 0;
	}

	public static void main(String[] args) throws SQLException {
		AccountDAO dao= new AccountDAO();
		System.out.println(dao.getHomecookCount());
//		System.out.println(dao.setAccountToken("5D5B8D91-6817-4F41-87E4-FF584382783F", "cIuGNFJaF44rjaXWuFUsd4:APA91bEyDDY-8er8UjnwZYwgy8FR_334weZ_kpxmU8E20DfWx5xJV7p8CwmnDHZwmDUV9E5S2RsbLkoO25gbkrOxxVRbPegB11c0UKjeA6OwVdKlSBkdHPso4R_mwGY6M19HfqnSRFHR"));
//		output.setFullName("Tr???n Qu??n");
//		output.setAddress("Sky Nine, Qu???n 9, Tp H??? Ch?? Minh");
//		dao.updateAccountInfo(output);
//
//		System.out.println(dao.getAccountByID("e055184a-4ec2-4777-bf5b-a0065281021a"));
	}
}
