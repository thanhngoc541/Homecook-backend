//package testdaos;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//import org.junit.Test;
//
//import daos.AccountDAO;
//import dtos.Account;
//
//public class AccountDAOTest {
//	AccountDAO testSubject = new AccountDAO();
//	ArrayList<Account> expected = new ArrayList<Account>();
//
//	//Prepare the test data
//	{
//		expected = testSubject.getAllAccountByRole("HomeCook");
//	}
//	@Test
//	public void testGetHomeCook() {
//		ArrayList<Account> output = testSubject.getAllAccountByRole("HomeCook");
//		for (int i=0; i < expected.size(); i++) {
//			assertEquals(expected.get(i), output.get(i));
//		}
//	}
//}
