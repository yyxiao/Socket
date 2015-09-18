/**
 * StuCountService.java
 * com.psy.service
 * author      date      	
 * ──────────────────────────────────
 * xiao    2015年9月10日 		
 * Copyright (c)2015, All Rights Reserved.
 * Java源代码,未经许可禁止任何人、任何组织通过任何
 * 渠道使用、修改源代码.
*/
package com.psy.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.psy.util.DBHelper;

 
public class StuCountService {

	static String sql = null;  
	static DBHelper db1 = null;  
	static Boolean isUp = true;
	static ResultSet resultSet = null;
	
	/**
	 * TODO(保存学生统计信息)
	 * @param stuNo
	 * @return
	*/
	public static Boolean insertData(String stu_name, String stu_no, String pad_uuid, String rand_num, String params) {
		String[] paramArr = params.split(",");
		sql = "insert into stucount(stu_name,stu_no,stu_uuid,randnum,score,qus_num,right_num,wrong_num,ans_num,active_num,upnum,save_num,flower,egg,create_time) "
				+ "values( '" + stu_name	+ "','"	+ stu_no + "','" + pad_uuid + "','" + rand_num	+ "','"
				+ paramArr[0] + "','" + paramArr[1] + "','" + paramArr[2]	+ "','"	+ paramArr[3]	+ "','" 
				+ paramArr[4] + "','" + paramArr[5] + "','" + paramArr[6] + "','" + paramArr[7] + "','"
				+ paramArr[8] + "','" + paramArr[9] + "',NOW()) ";
		db1 = new DBHelper(sql);
		// 执行语句，得到结果集
		try {
			int ret = db1.pst.executeUpdate();
			if (ret > 0) {
				// 更新成功
				System.out.println("success");
				isUp = true;
			} else {
				isUp = false;
			}
			db1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isUp;
	}
}
