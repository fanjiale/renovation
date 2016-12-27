package com.andy.common.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 日志类
 * 支持异步日志
 * 支持日志统一归集到服务器
 * 支持,支持占位符形式，如
 * log.debug("{}{}", 1, 2);
 * @author root
 *
 */
public class Log {
	private Logger logger;
	
	private Log(){
	}
	
	public static Log getLog(String name){
		Log log = new Log();
		log.logger = LogManager.getLogger(name);
		return log;
	}
	
	public static Log getLog(Class<?> clz){
		Log log = new Log();
		log.logger = LogManager.getLogger(clz);
		return log;
	}

	public void debug(Object paramObject) {
		logger.debug(paramObject);
	}
	public void debug(Object paramObject, Throwable paramThrowable) {
		logger.debug(paramObject, paramThrowable);
	}

	public void debug(String paramString) {
		logger.debug(paramString);		
	}

	public void debug(String paramString, Object... paramArrayOfObject) {
		logger.debug(paramString, paramArrayOfObject);
	}

	public void debug(String paramString, Throwable paramThrowable) {
		logger.debug(paramString, paramThrowable);
	}

	public void error(Object paramObject) {
		logger.error(paramObject);
	}

	
	public void error(Object paramObject, Throwable paramThrowable) {
		logger.error(paramObject, paramThrowable);
	}

	
	public void error(String paramString) {
		logger.error(paramString);
	}

	public void error(String paramString, Object... paramArrayOfObject) {
		logger.error(paramString, paramArrayOfObject);
	}
	
	public void error(String paramString, Throwable paramThrowable) {
		logger.error(paramString, paramThrowable);
	}

	public Level getLevel() {
		return logger.getLevel();
	}

	public String getName() {
		return logger.getName();
	}

	public void info(Object paramObject) {
		logger.info(paramObject);
	}

	
	public void info(Object paramObject, Throwable paramThrowable) {
		logger.info(paramObject, paramThrowable);
	}

	
	public void info(String paramString) {
		logger.info(paramString);
	}

	
	public void info(String paramString, Object... paramArrayOfObject) {
		logger.info(paramString, paramArrayOfObject);
	}
	
	public void info(String paramString, Throwable paramThrowable) {
		logger.info(paramString, paramThrowable);
	}

	public void trace(Object paramObject) {
		logger.trace(paramObject);
	}

	public void trace(Object paramObject, Throwable paramThrowable) {
		logger.trace(paramObject, paramThrowable);
		
	}

	public void trace(String paramString) {
		logger.trace(paramString);
	}

	
	public void trace(String paramString, Object... paramArrayOfObject) {
		logger.trace(paramString, paramArrayOfObject);
	}

	
	public void trace(String paramString, Throwable paramThrowable) {
		logger.trace(paramString, paramThrowable);
	}

	
	public void warn(Object paramObject) {
		logger.warn(paramObject);
	}

	
	public void warn(Object paramObject, Throwable paramThrowable) {
		logger.warn(paramObject, paramThrowable);
	}

	
	public void warn(String paramString) {
		logger.warn(paramString);
	}

	
	public void warn(String paramString, Object... paramArrayOfObject) {
		logger.warn(paramString, paramArrayOfObject);
	}

	
	public void warn(String paramString, Throwable paramThrowable) {
		logger.warn(paramString, paramThrowable);
	}

}
