package com.andy.common.service;

import com.andy.common.domains.CheckFiledError;
import com.andy.common.domains.RspResult;
import com.andy.common.domains.CheckFiledError;
import com.andy.common.domains.RspResult;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Set;

/**
 * 服务基础类
 * 1. 自动校验功能
 * 2. 全局自动异常处理功能(?)
 * @author root
 * @createDt 2014-9-4 下午2:20:58
 */
public class BaseService { 
	
	@Autowired
    protected Validator validator;  
  
    public RspResult successed(Object obj) {
    	RspResult ret = new RspResult();  
        ret.setSuccess(true);  
        ret.setData(obj);  
        return ret;  
    }  
    
    @SuppressWarnings("unchecked")
    public RspResult failed(String code, Set<?> failures) {  
		Set<ConstraintViolation<?>> failureSet = (Set<ConstraintViolation<?>>)failures;  
        ArrayList<CheckFiledError> errors = new ArrayList<CheckFiledError>();
        for (ConstraintViolation<?> failure : failureSet) {  
            errors.add(new CheckFiledError(failure.getPropertyPath().toString(),  failure.getMessage()));  
        }  
        RspResult ret = new RspResult();  
        ret.setSuccess(false);
        ret.setCode(code);
        ret.setErrors(errors);  
        return ret; 
    }  
    
//    /**
//    * 异常控制，这便是异常细节可控，将来可用于支持国际化（异常信息国际化）
//    * */
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
//    public ModelAndView handleException(Exception ex, HttpServletRequest request) {
//    	return new ModelAndView().addObject("error", "错误信息");
//    }
    
}  