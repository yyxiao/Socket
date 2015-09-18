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

import org.apache.mina.core.session.IoSession;

import com.psy.entity.SameGroup;
import com.psy.entity.Teacher;
import com.psy.util.Constants;
import com.psy.util.DBHelper;
import com.psy.util.StringHelper;

 
public class TeacherService {
	
	static String sql = null;  
	static DBHelper db1 = null;  
	static Boolean isUp = true;
	static ResultSet resultSet = null;
	
	/**
	 * TODO(查询教师详细信息)
	 * @param teaNo 教师端No
	 * @return
	*/
	public static Teacher searchSub(String teaNo){
		//填写sql语句
		sql = "select * from teacher where tea_no = '"+teaNo+"' LIMIT 1";
		db1 = new DBHelper(sql);
		Teacher tea = new Teacher();
		try {
			//获取结果集
			resultSet = db1.pst.executeQuery();
			if(!StringHelper.isEmptyObject(resultSet)){
				while(resultSet.next()){
					//拼接、处理数据
					tea.setTeaId(resultSet.getString(1));
					tea.setTeaNo(resultSet.getString(2));
					tea.setTeaName(resultSet.getString(3));
					tea.setSubject(resultSet.getString(4));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tea;
	}
	
	/**
	 * TODO(查找本组其他成员信息)
	 * @param randNo 课堂随机号
	 * @param groupNo	分组号
	 * @param stuNo	学号
	 * @return
	*/
	public static SameGroup sameGroupInfo(String randNo,String groupNo,String stuNo,String type){
		SameGroup sameGroup = new SameGroup();
		String userNames = "";
		String userNos = "";
		String userUuids = "";
		for(IoSession session_stu : Constants.sessionStus) {
			String rand_no = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
			String group_no = "";
			if(type.equals("sex")){
				group_no = session_stu.getAttribute(Constants.SEXGROUP,"NONE").toString();
			}else{
				group_no = session_stu.getAttribute(Constants.GROUP,"NONE").toString();
			}
			String stu_no = session_stu.getAttribute(Constants.NO,"NONE").toString();
			String stu_name = session_stu.getAttribute(Constants.NAME,"NONE").toString();
			String stu_uuid = session_stu.getAttribute(Constants.PADUUID,"NONE").toString();
			if(randNo.equals(rand_no)&&groupNo.equals(group_no)&&!stuNo.equals(stu_no)){
				userNames += stu_name +",";
				userNos += stu_no +",";
				userUuids += stu_uuid +",";
			}
		}
		if(userNames.length()>0){
			userNames = userNames.substring(0, userNames.length()-1);
			userNos = userNos.substring(0, userNos.length()-1);
			userUuids = userUuids.substring(0, userUuids.length()-1);
		}
		sameGroup.setUserNames(userNames);
		sameGroup.setUserNos(userNos);
		sameGroup.setUserUuids(userUuids);
		
		return sameGroup;
	}
}
