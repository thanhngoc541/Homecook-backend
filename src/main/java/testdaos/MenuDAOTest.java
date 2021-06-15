package testdaos;

import java.io.File;  
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import daos.MenuDAO;
import dtos.Menu;


public class MenuDAOTest {
	ArrayList<Menu> inputData = new ArrayList<Menu>();
	ArrayList<DishIn> inputDish =new ArrayList<DishIn>();
	
	class DishIn{
		String DishID, MenuID;

		public DishIn(String dishID, String menuID) {
			DishID = dishID;
			MenuID = menuID;
		}
		
	}
	
	private void readData(String inputFile) {        
        try 
        {
        	File file = new File("TestData/AddDishInTestData.xlsx");   //creating a new file instance  
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
        		inputDish.add(new DishIn(data.get(1), data.get(0)) );
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
		MenuDAOTest subject = new MenuDAOTest();
		MenuDAO input = new MenuDAO();
		
		subject.readData("");
		try {
			for (DishIn dishin : subject.inputDish) {
				input.addDishToMenu(dishin.MenuID, dishin.DishID);
				System.out.println(dishin.toString());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
