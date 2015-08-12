package tutorials.jexcel;

import java.io.File;
import java.io.IOException;

import jxl.CellType;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class JExcelAPIDemo_editXls {
	public static void main(String[] args) throws BiffException, IOException, WriteException {
		Workbook workbook = Workbook.getWorkbook(new File("output.xls"));
		WritableWorkbook copy = Workbook.createWorkbook( new File("output.xls") , workbook);
		WritableSheet sheet = copy.getSheet(0);
		WritableCell cell = sheet.getWritableCell(0, 2);
		if (cell.getType() == CellType.LABEL) 
		{ 
		  Label l = (Label) cell; 
		  l.setString("modified cell"); 
		} 

		copy.write();
		copy.close();
	}
}