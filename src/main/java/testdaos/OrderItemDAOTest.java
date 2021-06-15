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
import dtos.OrderItem;

public class OrderItemDAOTest {
	
	ArrayList<OrderItem> inputData = new ArrayList<OrderItem>();
	
	private void readData(String inputFile) {        
        try 
        {
        	File file = new File("TestData/AddOrderItemTestData.xlsx");   //creating a new file instance  
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
        		System.out.println(data.get(2));
        		inputData.add(new OrderItem("", data.get(0),
        						  new Dish(data.get(1), "", "", 0, ""),
        						  (int)Double.parseDouble(data.get(2)),
        						  data.get(3),
        						  Double.parseDouble(data.get(4))));
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
		OrderItemDAOTest tempo = new OrderItemDAOTest();
		OrderDAO subject = new OrderDAO();
		ArrayList<OrderItem> input = tempo.inputData;
		
		tempo.readData("");
		Order temporari = new Order("", "", "", Date.valueOf("2001-01-01"), "", "", "", "", 0.0, "", input);
		
		try {
			subject.insertOrderItems(temporari);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
