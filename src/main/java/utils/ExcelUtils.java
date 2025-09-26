package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	
	private static Workbook workbook;
	private static Sheet sheet;
	//Load Excel file
	public static void loadExcel(String filepath, String sheetname) throws IOException {
		
		FileInputStream file = new FileInputStream(filepath);
		workbook = new XSSFWorkbook(file);
		sheet = workbook.getSheet(sheetname);
	}
	
	//Get Cell Data as String
	public static String getCellData(int row, int col) {
		Cell cell = sheet.getRow(row).getCell(col);
		if(cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue();
		}
		else if (cell.getCellType() == CellType.NUMERIC) {
			return String.valueOf((int) cell.getNumericCellValue());
		}
		return "";
	}
	
	//Get total rows
	public static int getRowCount() {
		return sheet.getPhysicalNumberOfRows();
	}
	
	//Close workbook
	public static void closeExcel()throws IOException{
		workbook.close();
	}

}
