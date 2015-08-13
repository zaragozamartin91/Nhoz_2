package tutorials.jexcel;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import jxl.read.biff.BiffException;

import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class JExcelAPIDemo_createXls {
	public static void main(String[] args) throws BiffException, IOException, WriteException {
		WritableWorkbook wworkbook;
		wworkbook = Workbook.createWorkbook(new File("testFiles/output.xls"));
		WritableSheet wsheet = wworkbook.createSheet("First Sheet", 0);
		Label label = new Label(0, 2, "A label record");
		wsheet.addCell(label);
		Number number = new Number(3, 4, 3.1459);
		wsheet.addCell(number);
		wworkbook.write();
		wworkbook.close();
	}
}