package com.mz.nhoz.config.exception;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.mz.nhoz.config.Configuration;
import com.mz.nhoz.util.MapBuilder;

public abstract class AbstractConfiguration implements Configuration {
	protected String dbfFilePath = "C:\\Martin\\LISTAPRE.DBF";
	protected String xlsFilePath = "C:\\Xls\\PROVEEDOR.xls";
	protected String providerId = null;
	protected Integer articleDigits = ANY_DIGITS;

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

	public String toString() {
		MapBuilder mapBuilder = new MapBuilder();
		Map map = mapBuilder.put("dbfFilePath", dbfFilePath).put("xlsFilePath", xlsFilePath)
				.put("providerId", providerId).put("articleDigits", articleDigits).build();
		return new Gson().toJson(map);
	}
}
