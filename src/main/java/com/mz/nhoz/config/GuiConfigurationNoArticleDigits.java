package com.mz.nhoz.config;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.mz.nhoz.config.exception.AbstractConfiguration;
import com.mz.nhoz.config.exception.ConfigurationException;
import com.mz.nhoz.util.StringUtils;

public class GuiConfigurationNoArticleDigits extends AbstractConfiguration {
	static Map<String, Locale> decimalLocaleMap = new HashMap<String, Locale>();
	Logger logger = Logger.getLogger(GuiConfigurationNoArticleDigits.class);
	private boolean cancel = false;

	static {
		decimalLocaleMap.put(".", Locale.US);
		decimalLocaleMap.put(",", Locale.ITALY);
	}

	@Override
	public void load() throws ConfigurationException {
		setDbfFilePath(__chooseFile("SELECCIONE ARCHIVO DBF").getAbsolutePath());
		if (cancel) {
			throw new ConfigurationException("PROCESO DE CONFIGURAICON CANCELADO");
		}

		setXlsFilePath(__chooseFile("SELECCIONE ARCHIVO XLS").getAbsolutePath());
		if (cancel) {
			throw new ConfigurationException("PROCESO DE CONFIGURAICON CANCELADO");
		}

		setProviderId(__getProviderId("INGRESE EL CODIGO DE PROVEEDOR"));

		setNumberLocale(__getNumberLocale("INGRESE EL SEPARADOR DECIMAL"));
	}

	private Locale __getNumberLocale(String message) {
		String decimalDelim = JOptionPane.showInputDialog(message, ".");
		if (StringUtils.nullOrEmpty(decimalDelim)) {
			return __getNumberLocale(message);
		}

		Locale locale = decimalLocaleMap.get(decimalDelim);
		if (locale == null) {
			return __getNumberLocale(message);
		}

		return locale;
	}

	private File __chooseFile(String dialogTitle) {
		JFileChooser fc = new JFileChooser(new File("."));
		fc.setDialogTitle(dialogTitle);
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile();
		} else if (returnVal == JFileChooser.CANCEL_OPTION) {
			cancel = true;
			return new File(".");
		} else {
			return __chooseFile(dialogTitle);
		}
	}

	private String __getProviderId(String message) {
		String provider = JOptionPane.showInputDialog(message);
		if (StringUtils.nullOrEmpty(provider)) {
			return __getProviderId(message);
		}

		return provider;
	}

	public static void main(String[] args) throws ConfigurationException {
		GuiConfigurationNoArticleDigits guiConfiguration = new GuiConfigurationNoArticleDigits();
		guiConfiguration.load();

		System.out.println(guiConfiguration);
	}

}
