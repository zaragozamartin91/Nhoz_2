package com.mz.nhoz.config;

import java.io.File;

import com.mz.nhoz.config.exception.ConfigurationException;

import junit.framework.TestCase;

public class ConfigurationTest extends TestCase {
	public void testLoadConfigFromTxt() throws ConfigurationException {
		File configFile = new File("testFiles/testLoadConfigFromTxt.txt");
		Configuration configuration = new Configuration(configFile);

		assertEquals("C:\\Users\\martin.zaragoza\\Documents\\Some folder\\some file.dbf", configuration.getDbfFilePath());
		assertEquals("C:\\Users\\martin.zaragoza\\Documents\\Some folder\\some file.xls", configuration.getXlsFilePath());
		assertEquals("06", configuration.getProviderId());
	}
}
