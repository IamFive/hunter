/**
 * @(#)JsonResult.java 2014年5月19日
 *
 * Copyright 2008-2014 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.web.springmvc;

import java.io.*;

import org.slf4j.*;

/**
 * @author Woo Cupid
 * @date 2014年5月19日
 * @version $Revision$
 */
public class JsonResult implements Serializable {

	private static final long serialVersionUID = 5706185031278936807L;

	private static final Logger logger = LoggerFactory.getLogger(JsonResult.class);

	private Boolean result = true;
	private String description = "Request processed.";
	private Object data;

	public static JsonResult success(Object data) {
		JsonResult result = new JsonResult();
		result.setData(data);
		return result;
	}

	public static JsonResult success() {
		JsonResult result = new JsonResult();
		return result;
	}

	public static JsonResult failed(String message) {
		JsonResult result = new JsonResult();
		result.setResult(false);
		result.setDescription(message);
		result.setData("");
		return result;
	}

	public static JsonResult failed(String message, Object data) {
		JsonResult result = new JsonResult();
		result.setResult(false);
		result.setDescription(message);
		result.setData(data);
		return result;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
