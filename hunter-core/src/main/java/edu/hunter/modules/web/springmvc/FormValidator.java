/**
 * @(#)FormValidator.java 2014年10月30日
 *
 * Copyright 2008-2014 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.web.springmvc;

import java.util.*;

import org.springframework.validation.*;

import com.google.common.collect.*;

/**
 * @author Woo Cupid
 * @date 2014年10月30日
 * @version $Revision$
 */
public class FormValidator {

	public static HashMap<String, String> extractErros(BindingResult br) {
		HashMap<String, String> errors = Maps.newHashMap();
		List<FieldError> fieldErrors = br.getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			String field = fieldError.getField();
			String defaultMessage = fieldError.getDefaultMessage();
			errors.put(field, defaultMessage);
		}
		return errors;
	}

}
