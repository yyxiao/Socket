/**
 * TeacherService.java
 * com.psy.service
 * author      date      	
 * ──────────────────────────────────
 * xiao    2015年8月27日 		
 * Copyright (c)2015, All Rights Reserved.
 * Java源代码,未经许可禁止任何人、任何组织通过任何
 * 渠道使用、修改源代码.
*/
package com.psy.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.psy.util.DBHelper;
import com.psy.util.StringHelper;

 
public class TeacherService {
	
	static String sql = null;  
	static DBHelper db1 = null;  
	static Boolean isUp = true;
	static ResultSet resultSet = null;
	
	/**
	 * TODO(查询学生详细信息)
	 * @param stuNo
	 * @return
	*/
	public static String searchSub(String teaName){
		//填写sql语句
		sql = "select * from teacher where tea_name = '"+teaName+"' LIMIT 1";
		db1 = new DBHelper(sql);
		String sub = "";
		try {
			//获取结果集
			resultSet = db1.pst.executeQuery();
			if(!StringHelper.isEmptyObject(resultSet)){
				while(resultSet.next()){
					//拼接、处理数据
					sub = resultSet.getString(3);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sub;
	}
}
