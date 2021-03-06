/**
 * StringHelper.java
 * com.xyy.util
 * author      date      	
 * ──────────────────────────────────
 * xiao    2015年4月22日 		
 * Copyright (c)2015, All Rights Reserved.
 * Java源代码,未经许可禁止任何人、任何组织通过任何
 * 渠道使用、修改源代码.
 */
package com.psy.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName:StringHelper
 *
 * TODO(个人工具类)
 *
 * @project HttpServlet
 *
 * @author xiao
 *
 * @date 2015年4月22日 上午10:03:44
 *
 * @class com.xyy.util.StringHelper
 *
 */
public final class StringHelper {

	/**
	 * TODO(发送接口是否成功)
	 * 
	 * @param type
	 * @return
	 */
	public static String isSuccByType(boolean type) {
		if (type) {
			return "success";
		} else {
			return "error";
		}
	}

	public static boolean isChinese(String str) {
		String strTemp = null;
		try {
			strTemp = new String(str.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 如果值为空，通过校验
		if ("".equals(str))
			return true;
		Pattern p = Pattern.compile("/[^\u4E00-\u9FA5]/g,''");
		Matcher m = p.matcher(strTemp);
		return m.matches();
	}

	public static boolean isEmptyObject(Object obj) {
		if (StringHelper.toString(obj).equals("")) {
			return true;
		} else {
			return false;
		}
	}

	public static String toString(Object obj) {
		if (obj == null || "".equals(obj.toString())
				|| "null".equals(obj.toString())) {
			return "";
		} else {
			String objValue = obj.toString().trim();
			return objValue;
		}
	}

	/**
	 * TODO(特殊除法计算——教师统计)
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static String divide(int a, int b) {
		String type = "";
		int i = 0;
		if (a != 0) {
			float num = (float) a / b;
			DecimalFormat df = new DecimalFormat("0.00");// 格式化小数
			// 返回的是String类型
			String s = df.format(num);
			double d = Double.valueOf(s).doubleValue();
			i = (int)d;
//			if (d > 0.8) {
//				type = "A";
//			} else if (d <= 0.8 && d > 0.6) {
//				type = "B";
//			} else if (d <= 0.6 && d > 0.2) {
//				type = "C";
//			} else if (d <= 0.2) {
//				type = "D";
//			}
		}
		type = String.valueOf(i);
		return type;
	}

	/**
	 * TODO(输出日志)
	 * @param t
	 * @return
	*/
	public static String getTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}
}
