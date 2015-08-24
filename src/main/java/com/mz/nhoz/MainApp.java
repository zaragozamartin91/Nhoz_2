package com.mz.nhoz;

import java.io.File;

import com.mz.nhoz.xls.ExcelReader;
import com.mz.nhoz.xls.exception.ExcelReaderException;

public class MainApp {

	public MainApp() {
	}

	public static void main(String[] args) throws ExcelReaderException {
		System.out.println("Running Nhoz");
		
		ExcelReader excelReader = new ExcelReader(new File("testFiles/testAlterDbfFromXls.xls"));
	}
}
