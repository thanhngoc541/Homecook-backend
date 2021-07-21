package daos;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
					+ "@UserID = ?";
			 ps = conn.prepareStatement(query);

			ps.setString(1, input.getUsername());
			ps.setString(2, input.getPassword());
			ps.setString(3, input.getEmail());
			ps.setString(4, input.getFullName());
			ps.setDate(5, new java.sql.Date(input.DoBForDA()));
			ps.setString(6,  input.getAddress());
			ps.setString(7, input.getPhoneNumber());
			ps.setString(8, input.getUserID());
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

	public static void main(String[] args) {
		AccountDAO dao= new AccountDAO();
		System.out.println(dao.getHomecookCount());
	}

}
