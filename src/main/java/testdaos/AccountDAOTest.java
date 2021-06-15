package testdaos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import dtos.Account;

public class AccountDAOTest {
	private ArrayList<Account> inputData = new ArrayList<Account>();
	private String inputFile = "";
	
	private void readData(String inputFile) {
		FileReader f = null;
        BufferedReader bf = null;
        
        try 
        {
            f = new FileReader(inputFile);
            bf = new BufferedReader(f);
            while (bf.ready())
            {
                String s = bf.readLine();
                String[] arr = s.split(",");
                if (arr.length==4)
                {
                    //
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally 
        {
            try 
            {
                if (f!=null)
                    f.close();
                if (bf!=null)
                    bf.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
