package testdaos;

import java.io.File;  
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import daos.DishDAO;
import dtos.Dish;

public class DishesDAOTest {
	ArrayList<Dish> inputData = new ArrayList<Dish>();
	
	private void readData(String inputFile) {        
        try 
        {
        	File file = new File("TestData/AddDishTestData.xlsx");   //creating a new file instance  
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
        		inputData.add(new Dish("", data.get(0), data.get(1),
        						  Double.parseDouble(data.get(2)),
        						  Boolean.getBoolean(data.get(3)),
        						  data.get(4), data.get(5)));
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
		DishesDAOTest subject = new DishesDAOTest();
		DishDAO test = new DishDAO();
		subject.readData("");
		try {
			for (Dish dish : subject.inputData) {
				System.out.println(dish.toString());
				test.createDish(dish);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
