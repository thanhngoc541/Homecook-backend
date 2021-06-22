package dtos;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Utils.encryption;

public class Account {
    private String Username, Password, Role, Email, FullName, Address, PhoneNumber, UserID, SaltKey;
    private boolean IsActive;
    java.util.Date DoB;
    private Map<Integer, String> roleTable = new HashMap<Integer, String>();

    //Initiate the roles in the table: 
    {
    	roleTable.put(1, "Customer");
    	roleTable.put(2, "HomeCook");
    	roleTable.put(3, "Admin");
    }
    
    public Account(String userID, String username, String password, String role, String email, String fullName, java.util.Date doB, String address, String phoneNumber, boolean isActive) {
        UserID = userID;
        Username = username;
        Password = password;
        Role = role;
        Email = email;
        FullName = fullName.trim();
        DoB = doB;
        Address = address;
        PhoneNumber = phoneNumber;
        IsActive = isActive;
        SaltKey = encryption.saltKeyGenerate(fullName.trim());
    }

    public String getSaltKey() {
		return SaltKey;
	}

	public void setSaltKey(String saltKey) {
		SaltKey = saltKey;
	}

	public Account() {
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getDoB() {
        return String.valueOf(DoB);
    }

    public void setDoB(Date doB) {
        DoB = doB;
    }

    public String getAddress() {
        return Address;
    }
    
    public long DoBForDA() {
    	return DoB.getTime();
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    @Override
    public String toString() {
        return "Account{" +
                "UserID=" + UserID +
                ", Username='" + Username + '\'' +
                ", Password='" + Password + '\'' +
                ", Role='" + Role + '\'' +
                ", Email='" + Email + '\'' +
                ", FullName='" + FullName + '\'' +
                ", DoB='" + DoB + '\'' +
                ", Address='" + Address + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", IsActive=" + IsActive +
                '}';
    }
    
    public void addRole(int roleID, String roleName) {
    	roleTable.put(roleID, roleName);
    }
    
    public String getRoleName(int roleID) {
    	return roleTable.get(roleID);
    }
    
    public int getRoleID(String role) {
    	for (Integer key : roleTable.keySet()) {
			if(role.equals(roleTable.get(key)))
				return key.intValue();
		}
    	return 0;
    }

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Account o = (Account) obj;
		return this.UserID.equals(o.UserID) && this.IsActive == o.IsActive && this.Address.equals(o.Address)
	        	&& this.Email.equals(o.Email) && this.FullName.equals(o.FullName) && this.Username.equals(o.Username)
	        	&& this.Password.equals(o.Password) && (this.DoB.compareTo(o.DoB)) == 0 
	        	&& this.PhoneNumber.equals(o.PhoneNumber) && this.Role.equals(o.Role);
	}
    
	public String hashPasswords() {
		try {
			String output = encryption.toHexString(encryption.getSHA(Password + SaltKey));
			return output;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
}
