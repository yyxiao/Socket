/**
 * StudentService.java
 * com.psy.service
 * author      date      	
 * ──────────────────────────────────
 * xiao    2015年8月26日 		
 * Copyright (c)2015, All Rights Reserved.
 * Java源代码,未经许可禁止任何人、任何组织通过任何
 * 渠道使用、修改源代码.
*/
package com.psy.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.psy.entity.Student;
import com.psy.util.DBHelper;
import com.psy.util.StringHelper;


 
public class StudentService {
	
	static String sql = null;  
	static DBHelper db1 = null;  
	static Boolean isUp = true;
	static ResultSet resultSet = null;
	
	/**
	 * TODO(查询学生详细信息)
	 * @param stuNo
	 * @return
	*/
	public static Student searchData(String stuNo){
		//填写sql语句
		sql = "select * from student where stu_no = '"+stuNo+"' LIMIT 1";
		db1 = new DBHelper(sql);
		Student student = new Student();
		try {
			//获取结果集
			resultSet = db1.pst.executeQuery();
			if(!StringHelper.isEmptyObject(resultSet)){
				while(resultSet.next()){
					//拼接、处理数据
					student.setUserNo(resultSet.getString(2));
					student.setUserName(resultSet.getString(3));
					student.setSex(resultSet.getString(4));
					student.setYuwen(resultSet.getString(11));
					student.setShuxue(resultSet.getString(12));
					student.setYingyu(resultSet.getString(13));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

}
