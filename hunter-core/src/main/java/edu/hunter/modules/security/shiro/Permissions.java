/**
 * @(#)Permissions.java 2014年12月9日
 *
 * Copyright 2008-2014 by Woo Cupid.
 * All rights reserved.
 * 
 */
package edu.hunter.modules.security.shiro;


/**
 * @author Woo Cupid
 * @date 2014年12月9日
 * @version $Revision$
 */
public enum Permissions {

	//@off
	RepairView("repair", "view repair"),
	RepairAdd("repair", "add repair"),
	RepairUpdate("repair", "update repair"),
	RepairDelete("repair", "delete repair", RepairView);
	//@on

	Permissions(String group, String display, Permissions... dependOn) {

	}

}
