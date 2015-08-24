package com.mz.nhoz;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.mz.nhoz.xls.ExcelReader;
import com.mz.nhoz.xls.exception.ExcelReaderException;

import junit.framework.TestCase;

public class AlterDbfFromXlsTest extends TestCase {
	public void testAlterDbfFromXls() throws ExcelReaderException, IOException {
		FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("testFiles/testAlterDbfFromXls.xml");

		File listapre_dbf = (File) context.getBean("listapre_dbf");
		File testAlterDbfFromXls_dbf = (File) context.getBean("testAlterDbfFromXls_dbf");

		if (testAlterDbfFromXls_dbf.exists()) {
			testAlterDbfFromXls_dbf.delete();
		}

		FileUtils.copyFile(listapre_dbf, testAlterDbfFromXls_dbf);

		ExcelReader excelReader = (ExcelReader) context.getBean("excelReader");
		System.out.println(excelReader.toString());

		String codProv = "52";

		context.close();
	}
}
