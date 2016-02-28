package com.mz.nhoz;

import java.io.File;

import org.apache.log4j.Logger;

import com.mz.nhoz.config.Configuration;
import com.mz.nhoz.config.GuiConfigurationNoArticleDigits;
import com.mz.nhoz.config.exception.ConfigurationException;
import com.mz.nhoz.dbf.DbfPredicateWriter;
import com.mz.nhoz.dbf.ValueEqualsLenientRecordPredicateBuilder;
import com.mz.nhoz.dbf.ValueEqualsRecordPredicate;
import com.mz.nhoz.dbf.exception.DbfManagerException;
import com.mz.nhoz.exception.MainAppException;
import com.mz.nhoz.util.MoneyUtils;
import com.mz.nhoz.util.StringUtils;
import com.mz.nhoz.xls.ExcelReader;
import com.mz.nhoz.xls.RowRecord;
import com.mz.nhoz.xls.RowRecordIterator;
import com.mz.nhoz.xls.exception.ExcelReaderException;

public class MainAppV4 {
	private static final String PRECIOUNI_KEY = "PRECIOUNI";
	private static final String ARTICULO_KEY = "ARTICULO";
	private static final Logger logger = Logger.getLogger(MainAppV4.class);
	private Configuration configuration;
	private ExcelReader excelReader;
	private DbfPredicateWriter dbfWriter;
	private String providerId;

	public static void main(String[] args) {
		try {
			new MainAppV4().run();
			logger.info("El programa finalizó exitosamente.");
		} catch (MainAppException e) {
			logger.error("El programa finalizo con errores: " + e.toString());
		}
	}

	private boolean __loadConfig() throws MainAppException {
		try {
			// this.configuration = new GuiConfiguration();
			this.configuration = new GuiConfigurationNoArticleDigits();
			configuration.load();
		} catch (ConfigurationException e) {
			String msg = "Ocurrió un error durante la carga de configuraciones.";
			logger.error(msg, e);
			throw new MainAppException(msg);
		}

		return true;
	}

	public void run() throws MainAppException {
		logger.info("Corriendo...");
		
		__loadConfig();

		__loadExcelReader();

		File dbfFile = __loadDbfWriter();

		__loadProviderId();

		logger.info("Id del proveedor: " + providerId);

		logger.info("Modificando " + dbfFile.getAbsolutePath());

		try {
			__alterDbf();
		} catch (ExcelReaderException e1) {
			logger.error("Ocurrió un error durante la modificación del archivo dbf " + configuration.getDbfFilePath(), e1);
			throw new MainAppException(e1);
		}

		try {
			dbfWriter.close();
		} catch (DbfManagerException e) {
			logger.error("Ocurrió un error durante el cierre/ actualización del archivo DBF.", e);
			throw new MainAppException(e);
		}
	}// run

	private void __loadProviderId() throws MainAppException {
		this.providerId = configuration.getProviderId();
		if (StringUtils.nullOrEmpty(providerId)) {
			String msg = "Error al obtener el id del proveedor.";
			logger.error(msg);
			throw new MainAppException(msg);
		}
	}

	private File __loadDbfWriter() throws MainAppException {
		File dbfFile = new File(configuration.getDbfFilePath());
		logger.info("Cargando archivo " + dbfFile.getAbsolutePath());
		this.dbfWriter = new DbfPredicateWriter(dbfFile);
		try {
			dbfWriter.open();
		} catch (DbfManagerException e) {
			String msg = "Ocurrió un error durante la apertura del archivo DBF.";
			logger.error(msg, e);
			throw new MainAppException(msg);
		}
		return dbfFile;
	}

	private void __loadExcelReader() throws MainAppException {
		File excFile = new File(configuration.getXlsFilePath());
		logger.info("Cargando archivo " + excFile.getAbsolutePath());
		try {
			excelReader = new ExcelReader(excFile);
		} catch (ExcelReaderException e) {
			String msg = "Ocurrió un error durante la carga del archivo Excel.";
			logger.error(msg, e);
			throw new MainAppException(msg);
		}
	}

	private void __alterDbf() throws ExcelReaderException {
		RowRecordIterator rowRecordIterator = excelReader.rowRecordIterator();
		int parsedRecords = 0;
		ValueEqualsRecordPredicate predicate = new ValueEqualsLenientRecordPredicateBuilder("CODIGOPROV", providerId).buildStandard();

		while (rowRecordIterator.hasNext()) {
			RowRecord rowRecord = (RowRecord) rowRecordIterator.next();

			try {
				if (rowRecord.isEmpty()) {
					continue;
				}

				Object o_articulo = rowRecord.get(ARTICULO_KEY);
				Object o_preciouni = rowRecord.get(PRECIOUNI_KEY);
				if (StringUtils.nullOrEmpty(o_articulo) || StringUtils.nullOrEmpty(o_preciouni)) {
					continue;
				}

				String s_articulo = o_articulo.toString();
				logger.info("Analizando registro xls " + (parsedRecords++) + " :: " + rowRecord.toString());

				predicate.put(ARTICULO_KEY, s_articulo);
				dbfWriter.setPredicate(predicate);

//				Double d_preciouni = MoneyUtils.parsePriceAsDouble(o_preciouni);
				Double d_preciouni = MoneyUtils.parsePriceAsDouble(o_preciouni,configuration.getNumberLocale());
				dbfWriter.updateRecords(PRECIOUNI_KEY, d_preciouni, true);

			} catch (Exception e) {
				logger.error("Error al leer/actualizar el registro " + rowRecord.toString() + "::excepcion::" + e.toString());
			}
		}// while (rowRecordIterator.hasNext())

		if (parsedRecords == 0) {
			logger.error("NO SE ENCONTRARON REGISTROS VALIDOS EN EL EXCEL. ");
			logger.error("VERIFIQUE FORMATO DEL ARCHIVO EXCEL Y TITULOS DE LAS COLUMNAS. ");
		}
	}// __alterDbf
}// MainApp
