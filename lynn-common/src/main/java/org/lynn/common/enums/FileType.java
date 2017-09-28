package org.lynn.common.enums;

public enum FileType {
	
	PROPERTIES(".properties");
	
	private String suffix;
	
	private FileType(String suffix) {
		this.suffix = suffix;
	}
	
	public String suffix() {
		return this.suffix;
	}
}
