package com.mz.nhoz.config;

import com.mz.nhoz.config.exception.ConfigurationException;

public interface Configuration {
	public static final Integer ANY_DIGITS = 0;
	
	public void load() throws ConfigurationException;
	
	public String getDbfFilePath();

	public String getXlsFilePath();

	public String getProviderId();

	public Integer getArticleDigits();
}
