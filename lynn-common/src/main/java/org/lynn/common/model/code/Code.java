package org.lynn.common.model.code;

import org.lynn.common.Lang;

/**
 * 结果码：可以是rpc调用的返回结果码，也可以是网络请求返回的结果码，也可以是方法调用返回的结果码
 * 
 * @author ahab
 */
public enum Code implements ICode {
	
	OK(0, "result.code.ok"),
	
	SYSTEM_ERROR(1, "result.code.system.error"),
	
	PARAM_ERROR(2, "result.code.param.error"),
	
	UPLOAD_SIZE_EXCEEDED(3, "result.code.upload.size.exceeded");
	
	private int value;
	private String desc;
	
	private Code(int value, String langKey) {
		this.value = value;
		this.desc = Lang.get(langKey);
	}
	
	@Override
	public int id() {
		return this.value;
	}
	
	@Override
	public String desc() {
		return this.desc;
	}
}
