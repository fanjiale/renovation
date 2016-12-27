package com.andy.common.exception;

import com.andy.common.domains.RspResult;
import com.andy.common.service.BaseService;
import com.andy.common.domains.RspResult;
import com.andy.common.service.BaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * API提供时异常封装URL  error/error
 * @author root
 * @createDt 2014-8-29 上午11:03:49
 */

@Controller
@RequestMapping("/error")
public class ExceptionService extends BaseService {
	@RequestMapping(value = "/error")
	@ResponseBody
	public RspResult exception(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		Object exception = request.getAttribute("exception");
		return RspResult.getFailtResult("出现异常:" +  exception);
	}
}
