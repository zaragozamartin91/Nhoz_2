package com.mz.nhoz.config;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.mz.nhoz.config.exception.AbstractConfiguration;
import com.mz.nhoz.config.exception.ConfigurationException;
import com.mz.nhoz.util.StringUtils;

public class GuiConfigurationNoArticleDigits extends AbstractConfiguration {
	Logger logger = Logger.getLogger(GuiConfigurationNoArticleDigits.class);

	private boolean cancel = false;

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
//		setArticleDigits(__getArticleDigits("INGRESE CANTIDAD DE CARACTERES DEL CODIGO DE ARTICULO. EN CASO QUE SEA 'CUALQUIERA', NO INGRESE NINGUN VALOR"));
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
