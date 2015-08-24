package com.mz.nhoz.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.mz.nhoz.config.exception.ConfigurationException;
import com.mz.nhoz.util.StringPair;
import com.mz.nhoz.util.StringUtils;

public class Configuration {
	public static final String DBF_FILE_KEY = "archivoDbf";
	public static final String XLS_FILE_KEY = "archivoExcel";
	public static final String PROVIDER_KEY = "proveedor";
	private static final String DELIM = "=";

	private BufferedReader fileReader;
	private String dbfFilePath = "C:\\Martin\\LISTAPRE.DBF";
	private String xlsFilePath = "C:\\Xls\\PROVEEDOR.xls";
	private String providerId = null;

	public Configuration(File configFile) throws ConfigurationException {
		try {
			fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
			__load();
		} catch (Exception e) {
			throw new ConfigurationException(e);
		}
	}

	private void __load() throws IOException, ConfigurationException {
		try {
			String line = null;

			while ((line = fileReader.readLine()) != null) {
				if (StringUtils.empty(line) || line.startsWith("#")) {
					continue;
				}

				StringPair stringPair = StringUtils.parsePair(line, DELIM);
				__setValue(stringPair);
			}
		} catch (Exception e) {
			throw new ConfigurationException(e);
		}
	}// __load

	private void __setValue(StringPair stringPair) {
		String key = stringPair.getFirst();
		String value = stringPair.getSecond();

		if (StringUtils.notNullNorEmpty(value)) {
			if (key.equals(DBF_FILE_KEY)) {
				this.dbfFilePath = value;
			} else if (key.equals(XLS_FILE_KEY)) {
				this.xlsFilePath = value;
			} else if (key.equals(PROVIDER_KEY)) {
				this.providerId = value;
			}
		}
	}

	public String getDbfFilePath() {
		return dbfFilePath;
	}

	public String getXlsFilePath() {
		return xlsFilePath;
	}

	public String getProviderId() {
		return providerId;
	}
}// Configuration
