package com.andy.common.exception;



/**
 * 服务异常
 * @author root
 *
 */
public class ServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	// 错误编码
	private String code;

	/**
	 * 构造方法

	 * @param code 返回码

	 * @param msg  错误消息
	 */
	public ServiceException(String code, String msg) {
		super(msg);
		this.code = code;
	}


	/**
	 * 构造方法
	 * @param code	返回码
	 * @param cause  异常堆栈
	 */
	public ServiceException(String code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	/**
	 * 构造方法

	 * @param code	返回码

	 * @param msg	返回消息
	 * @param cause 异常堆栈
	 */
	public ServiceException(String code, String msg, Throwable cause) {
		super(msg, cause);
		this.code = code;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}
	
	
}
