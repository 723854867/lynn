package org.lynn.util.common.enums;

public enum Protocol {

	JAR("jar:file:");
	
	private String value;
	
	private Protocol(String protocol) {
		this.value = protocol;
	} 
	
	public String value() {
		return value;
	}
}
