/**
 * ReadProperties.java
 * com.xyy.util
 * author      date      	
 * ──────────────────────────────────
 * xiao    2015-4-14 		
 * Copyright (c)2015, All Rights Reserved.
 * Java源代码,未经许可禁止任何人、任何组织通过任何
 * 渠道使用、修改源代码.
*/
package com.psy.util;

import java.io.InputStream;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

 
/**
 * ClassName:ReadProperties
 *
 * TODO(个人工具类)
 *
 * @project HttpServlet
 *
 * @author xiao
 *
 * @date   2015年4月27日 下午1:34:21	
 *
 * @class com.xyy.util.ReadProperties
 *
 */ 
public class ReadProperties {

	private Properties properties;
	
	/**
	 * TODO
	 * @param fileName
	*/
	public ReadProperties(String fileName) {
		properties = new Properties();
		InputStream stream = ReadProperties.class.getResourceAsStream(fileName);
		try {
			properties.load(stream);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		properties.putAll(System.getProperties());
	}

	public Object getProperty(String propertyName) {
		Object result = properties.getProperty(propertyName);
		return result;
	}

	public String getProperty(String propertyName,String fileName){
		String result = getFile(fileName).getString(propertyName);
		return null;
	}
	
	public PropertyResourceBundle getFile(String fileName){
		return (PropertyResourceBundle) ResourceBundle.getBundle(fileName);
	}
}
