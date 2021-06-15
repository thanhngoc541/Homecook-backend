package testdaos;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import daos.OrderDAO;
import dtos.Dish;
import dtos.Order;

public class OrderDAOTest {

	ArrayList<Order> inputData = new ArrayList<Order>();
	
	private void readData(String inputFile) {        
        try 
        {
        	File file = new File("TestData/AddOrderTestData.xlsx");   //creating a new file instance  
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
        		inputData.add(new Order("", data.get(0), data.get(1),
        						  Date.valueOf(data.get(2)),
        						  data.get(3),
        						  data.get(4), 
        						  data.get(5),
        						  data.get(6),
        						  Double.parseDouble(data.get(7)),
        						  data.get(8),
        						  null));
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
		OrderDAOTest testSubject = new OrderDAOTest();
		OrderDAO input = new OrderDAO();
		
		testSubject.readData("");
		try {
			for (Order order : testSubject.inputData) {
				input.createOrder(order);
				System.out.println(order.toString());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
