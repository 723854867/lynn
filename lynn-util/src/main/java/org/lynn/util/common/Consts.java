package org.lynn.util.common;

import java.nio.charset.Charset;

import org.lynn.util.common.model.code.Code;
import org.lynn.util.common.model.message.Result;

public interface Consts {

	final Charset UTF_8				= Charset.forName("UTF-8");
	
	interface Results {
		Result OK					= new Result(Code.OK);
		Result SYSTEM_ERROR			= new Result(Code.SYSTEM_ERROR);
	}
}
