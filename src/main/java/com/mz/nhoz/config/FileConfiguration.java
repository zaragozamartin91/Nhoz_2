package com.mz.nhoz.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.mz.nhoz.config.exception.AbstractConfiguration;
import com.mz.nhoz.config.exception.ConfigurationException;
import com.mz.nhoz.util.StringPair;
import com.mz.nhoz.util.StringUtils;

public class FileConfiguration extends AbstractConfiguration {
	Logger logger = Logger.getLogger(FileConfiguration.class);

	private BufferedReader fileReader;
	public static final String DBF_FILE_KEY = "archivoDbf";
	public static final String XLS_FILE_KEY = "archivoExcel";
	public static final String PROVIDER_KEY = "proveedor";
	public static final String DIGITS_KEY = "caracteresArticulo";
	private static final String DELIM = "=";

	public FileConfiguration(File configFile) throws ConfigurationException {
		try {
			logger.info("Leyendo configuraciones de " + configFile.getAbsolutePath());
			fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
		} catch (Exception e) {
			throw new ConfigurationException(e);
		}
	}

	public void load() throws ConfigurationException {
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
			} else if (key.equals(DIGITS_KEY)) {
				this.articleDigits = Integer.valueOf(value);
			}
		}
	}
}// Configuration
