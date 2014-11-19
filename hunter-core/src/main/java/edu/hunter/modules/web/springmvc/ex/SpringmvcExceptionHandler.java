/**
 * @(#)SpringmvcExceptionHandler.java 2014年6月3日
 *
 * Copyright 2008-2014 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.web.springmvc.ex;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

import edu.hunter.modules.mapper.*;
import edu.hunter.modules.web.springmvc.*;

/**
 * @author Woo Cupid
 * @date 2014年6月3日
 * @version $Revision$
 */
@ControllerAdvice
public class SpringmvcExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { ApiException.class })
	public final ResponseEntity<String> handleApiException(ApiException ex, ServletRequest request,
			ServletResponse response, HttpServletRequest hr) throws IOException {
		String json = JsonMapper.nonEmptyMapper().toJson(JsonResult.failed(ex.getMessage()));
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

}
