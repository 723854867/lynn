package org.lynn.util.common.enums;

public enum FileType {
	
	LUA(".lua"),
	
	PROPERTIES(".properties");
	
	private String suffix;
	
	private FileType(String suffix) {
		this.suffix = suffix;
	}
	
	public String suffix() {
		return this.suffix;
	}
}
