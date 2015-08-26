package com.mz.nhoz;

import java.io.File;

import org.apache.log4j.Logger;

import com.mz.nhoz.config.Configuration;
import com.mz.nhoz.config.exception.ConfigurationException;
import com.mz.nhoz.dbf.DbfPredicateWriter;
import com.mz.nhoz.dbf.ValueEqualsRecordPredicate;
import com.mz.nhoz.dbf.exception.DbfManagerException;
import com.mz.nhoz.util.NumberUtils;
import com.mz.nhoz.util.StringUtils;
import com.mz.nhoz.util.exception.NumberUtilsException;
import com.mz.nhoz.xls.ExcelReader;
import com.mz.nhoz.xls.RowRecord;
import com.mz.nhoz.xls.RowRecordIterator;
import com.mz.nhoz.xls.exception.ExcelReaderException;

public class MainApp {
	private static final String PRECIOUNI_KEY = "PRECIOUNI";
	private static final String ARTICULO_KEY = "ARTICULO";
	private static final String CFG_FILE_PATH = "config/nhoz.cfg";
	private Logger logger = Logger.getLogger(MainApp.class);
	private Configuration configuration;
	private ExcelReader excelReader;
	private DbfPredicateWriter dbfWriter;
	private String providerId;

	public static void main(String[] args) throws ExcelReaderException {
		new MainApp().run();
	}

	public void run() {
		logger.info("Corriendo nhoz...");
		File configFile = new File(CFG_FILE_PATH);

		if (configFile.exists()) {
			logger.info("Leyendo configuraciones desde " + configFile.getAbsolutePath());
			try {
				configuration = new Configuration(configFile);
			} catch (ConfigurationException e) {
				logger.error("Ocurrió un error durante la carga de configuraciones.", e);
				return;
			}
		} else {
			logger.error("ARCHIVO DE CONFIGURACION " + configFile.getAbsolutePath() + " NO EXISTE!");
			return;
		}

		File excFile = new File(configuration.getXlsFilePath());
		logger.info("Cargando archivo " + excFile.getAbsolutePath());
		try {
			excelReader = new ExcelReader(excFile);
		} catch (ExcelReaderException e) {
			logger.error("Ocurrió un error durante la carga del archivo Excel.", e);
			return;
		}

		File dbfFile = new File(configuration.getDbfFilePath());
		logger.info("Cargando archivo " + dbfFile.getAbsolutePath());
		this.dbfWriter = new DbfPredicateWriter(dbfFile);
		try {
			dbfWriter.open();
		} catch (DbfManagerException e) {
			logger.error("Ocurrió un error durante la apertura del archivo DBF.", e);
		}

		this.providerId = configuration.getProviderId();
		if (StringUtils.nullOrEmpty(providerId)) {
			logger.error("Error al obtener el id del proveedor.");
			return;
		}
		logger.info("Id del proveedor: " + providerId);

		logger.info("Modificando " + dbfFile.getAbsolutePath());
		try {
			__alterDbf();
		} catch (ExcelReaderException e1) {
			logger.error("Ocurrió un error durante la modificación del archivo dbf " + configuration.getDbfFilePath(),
					e1);
			return;
		}

		try {
			dbfWriter.close();
		} catch (DbfManagerException e) {
			logger.error("Ocurrió un error durante el cierre/ actualización del archivo DBF.", e);
			return;
		}
		logger.info("Nhoz finalizó exitosamente.");
	}// run

	private Object __parseArticulo(Object o_articulo) throws NumberUtilsException {
		if (o_articulo.getClass().getSuperclass().equals(Number.class)) {
			o_articulo = NumberUtils.parseUsLocaleNumberStringAsInteger(o_articulo.toString());

			if (configuration.getArticleDigits() != Configuration.ANY_DIGITS) {
				o_articulo = NumberUtils.parseIntegerAsString((Integer) o_articulo, configuration.getArticleDigits());
			}
		}

		return o_articulo;
	}

	private void __alterDbf() throws ExcelReaderException {
		RowRecordIterator rowRecordIterator = excelReader.rowRecordIterator();
		int i = 0;
		ValueEqualsRecordPredicate predicate = new ValueEqualsRecordPredicate("CODIGOPROV", providerId);

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

				o_articulo = __parseArticulo(o_articulo);

				String s_articulo = o_articulo.toString();
				rowRecord.put(ARTICULO_KEY, s_articulo);

				logger.info("Analizando registro xls " + (i++) + " :: " + rowRecord.toString());

				predicate.put(ARTICULO_KEY, s_articulo);
				dbfWriter.setPredicate(predicate);

				dbfWriter.updateRecords(PRECIOUNI_KEY, o_preciouni, true);
			} catch (Exception e) {
				logger.error("Error al actualizar el registro " + rowRecord.toString() + "::excepcion::" + e.toString());
			}
		}// while (rowRecordIterator.hasNext())
	}// __alterDbf
}// MainApp
