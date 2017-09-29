package org.lynn.util.common;

import java.nio.charset.Charset;

import org.lynn.util.common.model.code.Code;
import org.lynn.util.common.model.message.Result;

public interface Consts {

	final Charset UTF_8				= Charset.forName("UTF-8");
	
	interface Results {
		Result<Void> OK						= new Result<Void>(Code.OK);
		Result<Void> SYSTEM_ERROR			= new Result<Void>(Code.SYSTEM_ERROR);
	}
}
