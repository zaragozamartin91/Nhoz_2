package com.mz.nhoz;

import java.io.File;

import org.apache.log4j.Logger;

import com.mz.nhoz.xls.ExcelReader;
import com.mz.nhoz.xls.exception.ExcelReaderException;

public class MainApp {

	public MainApp() {
	}

	public static void main(String[] args) throws ExcelReaderException {
		Logger logger = Logger.getLogger(MainApp.class);
		logger.info("running Nhoz");

		// ExcelReader excelReader = new ExcelReader(new
		// File("testFiles/testAlterDbfFromXls.xls"));
	}
}
