package com.mz.nhoz;

import java.io.File;

import org.apache.log4j.Logger;

import com.mz.nhoz.config.Configuration;
import com.mz.nhoz.config.exception.ConfigurationException;
import com.mz.nhoz.dbf.DbfPredicateWriter;
import com.mz.nhoz.dbf.ValueEqualsRecordPredicate;
import com.mz.nhoz.dbf.exception.DbfManagerException;
import com.mz.nhoz.dbf.exception.DbfWriterException;
import com.mz.nhoz.util.StringUtils;
import com.mz.nhoz.xls.ExcelReader;
import com.mz.nhoz.xls.RowRecord;
import com.mz.nhoz.xls.RowRecordIterator;
import com.mz.nhoz.xls.exception.ExcelReaderException;

public class MainApp {
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

		logger.info("Leyendo configuraciones desde " + configFile.getAbsolutePath());
		try {
			configuration = new Configuration(configFile);
		} catch (ConfigurationException e) {
			logger.error("Ocurrió un error durante la carga de configuraciones.", e);
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
			logger.error("Ocurrió un error durante la modificación del archivo dbf " + configuration.getDbfFilePath(), e1);
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

	private void __alterDbf() throws ExcelReaderException {
		RowRecordIterator rowRecordIterator = excelReader.rowRecordIterator();

		while (rowRecordIterator.hasNext()) {
			RowRecord rowRecord = (RowRecord) rowRecordIterator.next();

			if (rowRecord.isEmpty()) {
				continue;
			}

			try {
				String articulo = (String) rowRecord.get("ARTICULO");
				ValueEqualsRecordPredicate predicate = new ValueEqualsRecordPredicate("CODIGOPROV", providerId).add("ARTICULO", articulo);
				dbfWriter.setPredicate(predicate);

				dbfWriter.updateRecords("PRECIOUNI", rowRecord.get("PRECIOUNI"), true);
			} catch (DbfWriterException e) {
				logger.error("Error al actualizar el registro " + rowRecord.toString());
			}
		}// while (rowRecordIterator.hasNext())
	}// __alterDbf
}// MainApp
