package com.mz.nhoz.config.exception;

import java.util.Locale;

import com.mz.nhoz.config.Configuration;

public abstract class AbstractConfiguration implements Configuration {
	protected String dbfFilePath = "C:\\Martin\\LISTAPRE.DBF";
	protected String xlsFilePath = "C:\\Xls\\PROVEEDOR.xls";
	protected String providerId = null;
	protected Integer articleDigits = ANY_DIGITS;
	protected Locale numberLocale = Locale.US;

	public String getDbfFilePath() {
		return dbfFilePath;
	}

	public void setDbfFilePath(String dbfFilePath) {
		this.dbfFilePath = dbfFilePath;
	}

	public String getXlsFilePath() {
		return xlsFilePath;
	}

	public void setXlsFilePath(String xlsFilePath) {
		this.xlsFilePath = xlsFilePath;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public Integer getArticleDigits() {
		return articleDigits;
	}

	public void setArticleDigits(Integer articleDigits) {
		this.articleDigits = articleDigits;
	}

	@Override
	public Locale getNumberLocale() {
		return numberLocale;
	}

	public void setNumberLocale(Locale numberLocale) {
		this.numberLocale = numberLocale;
	}

	@Override
	public String toString() {
		return "AbstractConfiguration [dbfFilePath=" + dbfFilePath + ", xlsFilePath=" + xlsFilePath + ", providerId="
				+ providerId + ", articleDigits=" + articleDigits + ", numberLocale=" + numberLocale + "]";
	}
}
