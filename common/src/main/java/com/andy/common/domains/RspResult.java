/**
 * 
 */
package com.andy.common.domains;

import java.util.List;

/**
 * 请求标准返回类
 * 
 * @author root
 * @createDt 2014-8-29 上午10:30:08
 */
public final class RspResult {
	// 标准返回类标志,前台JS中会根据此标志判断是否是标准RspResult数据
	private final String rsphead = "s"; 
	private boolean success; // 调用成功标志
	private String code; // 异常编码
	private String msg; // 错误信息
	private Object data; // 后台返回数据
	// 使用后台自动校验时生成的校验错误信息
	private List<CheckFiledError> errors;
	
	public String getRsphead() {
		return rsphead;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public RspResult setSuccess(boolean success) {
		this.success = success;
		return this;
	}
	
	public String getCode() {
		return code;
	}
	
	public RspResult setCode(String code) {
		this.code = code;
		return this;
	}
	
	public String getMsg() {
		return msg;
	}
	public RspResult setMsg(String msg) {
		this.msg = msg;
		return this;
	}
	public Object getData() {
		return data;
	}
	public RspResult setData(Object data) {
		this.data = data;
		return this;
	}
	
	public static RspResult getSuccessResult(){
		RspResult result = new RspResult();
		return result.setSuccess(true);
	}
	
	public static RspResult getFailtResult(final String errMsg){
		RspResult result = new RspResult();
		return result.setSuccess(false).setMsg(errMsg);
	}
	
	public List<CheckFiledError> getErrors() {
		return errors;
	}
	
	public RspResult setErrors(List<CheckFiledError> errors) {
		this.errors = errors;
		return this;
	}
}
