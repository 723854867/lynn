package org.lynn.example.web.controller.internal;

import java.util.List;

import org.lynn.util.common.model.code.Code;
import org.lynn.util.common.model.message.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Result<?> handler(Exception e) {
		Result<?> result = new Result(Code.SYSTEM_ERROR);
		result.setDesc(e.getMessage());
		logger.warn("系统错误！", e);
		return result;
	}
	
	@ExceptionHandler(BindException.class)
	@ResponseBody
	public Result<?> bindExceptionHandler(BindException ex) {
		return _validatorError(ex.getBindingResult());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public Result<?> argumentNotValidHandler(MethodArgumentNotValidException ex) {
		return _validatorError(ex.getBindingResult());
	}
	
	/**
	 * 上传文件超过上限
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	@ResponseBody
	public Result<?> uploadSizeExceededHandler(MaxUploadSizeExceededException ex) {
		return new Result(Code.UPLOAD_SIZE_EXCEEDED, ex.getMaxUploadSize());
	}
	
	private Result<?> _validatorError(BindingResult bindingResult) { 
		List<FieldError> errors = bindingResult.getFieldErrors();
		StringBuilder reason = new StringBuilder("[");
		for (FieldError error : errors) 
			reason.append(error.getField()).append("-").append(error.getDefaultMessage()).append(";");
		reason.deleteCharAt(reason.length() - 1);
		reason.append("]");
		Result<?> result = new Result(Code.PARAM_ERROR);
		result.setDesc(result.getDesc() + ":" + reason.toString());
		return result;
	}
}
