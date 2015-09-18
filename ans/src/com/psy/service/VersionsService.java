/**
 * VersionsService.java
 * com.psy.service
 * author      date      	
 * ──────────────────────────────────
 * xiao    2015年9月18日 		
 * Copyright (c)2015, All Rights Reserved.
 * Java源代码,未经许可禁止任何人、任何组织通过任何
 * 渠道使用、修改源代码.
*/
package com.psy.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.psy.entity.Versions;
import com.psy.util.DBHelper;
import com.psy.util.StringHelper;

 
public class VersionsService {
	static String sql;
	static DBHelper db1;
	static ResultSet resultSet;
	
	public static Versions findVersions(String school,String type){
		//填写sql语句
		sql = "SELECT * FROM versions WHERE school = '"+school+"' AND modelType = '"+type+"' LIMIT 1";
		db1 = new DBHelper(sql);
		Versions versions = new Versions();
		try {
			//获取结果集
			resultSet = db1.pst.executeQuery();
			if(!StringHelper.isEmptyObject(resultSet)){
				while(resultSet.next()){
					//拼接、处理数据
					versions.setSchool(resultSet.getString(2));
					versions.setType(resultSet.getString(3));
					versions.setVersionName(resultSet.getString(4));
					versions.setUrl(resultSet.getString(5));
					versions.setIsMust(resultSet.getString(6));
					versions.setVersionText(resultSet.getString(7));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return versions;
	}
}
