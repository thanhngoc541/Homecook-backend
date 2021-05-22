package testdaos;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import org.junit.Test;

import daos.HomeCookDAO;
import dtos.Account;

public class HomeCookDAOTests {
	HomeCookDAO testSubject = new HomeCookDAO();
	ArrayList<Account> expected = new ArrayList<Account>();
	
	//Prepare the test data
	{
		expected = testSubject.getAllHomeCook();
	}
	@Test
	public void testGetHomeCook() {
		ArrayList<Account> output = testSubject.getAllHomeCook();
		for (int i=0; i < expected.size(); i++) {
			assertEquals(expected.get(i), output.get(i));
		}
	}
}
