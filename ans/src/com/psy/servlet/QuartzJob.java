/**
 * QuartzJob.java
 * com.psy.servlet
 * author      date      	
 * ──────────────────────────────────
 * xiao    2015年7月21日 		
 * Copyright (c)2015, All Rights Reserved.
 * Java源代码,未经许可禁止任何人、任何组织通过任何
 * 渠道使用、修改源代码.
*/
package com.psy.servlet;

import com.psy.util.Constants;

 
public class QuartzJob {
	
	/**
	 * TODO(清除list)
	*/
	public void cleanList(){
		Constants.randList.clear();
		System.out.println("定时清除randList数据");
	}

}
