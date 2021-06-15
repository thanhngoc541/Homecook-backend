package testdaos;

import static org.junit.Assert.assertTrue;

import java.io.File;  
import java.io.FileInputStream;  
import java.util.Iterator;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import daos.AccountDAO;

import java.sql.Date;
import java.util.ArrayList;

import dtos.Account;

public class AccountDAOTest {
	ArrayList<Account> inputData = new ArrayList<Account>();
	private String inputFile = "";
	private AccountDAO testSubject = new AccountDAO();
	
	public ArrayList<Account> getData(){
		return inputData;
	}
	
	private void readData(String inputFile) {        
        try 
        {
        	File file = new File("TestData/AddAccountTestData.xlsx");   //creating a new file instance  
        	FileInputStream fis = new FileInputStream(file); 
        	
        	XSSFWorkbook wb = new XSSFWorkbook(file);
        	XSSFSheet sheet = wb.getSheetAt(0);
        	Iterator<Row> itr = sheet.iterator();
        	itr.next();
        	
        	while (itr.hasNext()) {
        		Row row = itr.next();
        		Iterator<Cell> cellIterator = row.cellIterator();
        		ArrayList<String> data = new ArrayList<String>();
        		
        		while(cellIterator.hasNext()) {
        			Cell cell = cellIterator.next();
        			switch (cell.getCellType()) {
        			case Cell.CELL_TYPE_NUMERIC:
        				data.add(String.valueOf(cell.getNumericCellValue()));
        				break;
        			case Cell.CELL_TYPE_STRING:
        				data.add(cell.getStringCellValue());
        				break;
        			}
        		}
        		inputData.add(new Account("", data.get(0), data.get(1), data.get(2),
        				data.get(3), data.get(4), Date.valueOf(data.get(5)), data.get(6),
        				data.get(7), Boolean.getBoolean(data.get(8))));
        	}
        	
        	
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally 
        {
        }
    }
	
	public static void main(String[] args) {
		AccountDAO tempo = new AccountDAO();
		AccountDAOTest subject = new AccountDAOTest();
		subject.readData("TestData/AddAccountTestData.xlsx");
		for (Account account : subject.inputData) {
			tempo.createAccount(account);
			System.out.println("Success");
		}
	}
}
