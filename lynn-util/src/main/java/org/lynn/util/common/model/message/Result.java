package org.lynn.util.common.model.message;

import java.io.Serializable;

import org.lynn.util.common.model.code.ICode;

/**
 * 调用结果
 * 
 * @author ahab
 *
 * @param <T>
 */
public class Result<T extends Serializable> implements Message {

	private static final long serialVersionUID = 2129932129375627930L;
	
	private T attach;
	private int code;
	private String desc;
	
	public Result() {}
	
	public Result(ICode code) {
		this.code = code.id();
		this.desc = code.desc();
	}
	
	public Result(ICode code, T attach) {
		this.code = code.id();
		this.desc = code.desc();
		this.attach = attach;
	}
	
	public T getAttach() {
		return attach;
	}
	
	public void setAttach(T attach) {
		this.attach = attach;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
