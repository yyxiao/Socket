/**
 * RecordService.java
 * com.psy.service
 * author      date      	
 * ──────────────────────────────────
 * xiao    2015年7月30日 		
 * Copyright (c)2015, All Rights Reserved.
 * Java源代码,未经许可禁止任何人、任何组织通过任何
 * 渠道使用、修改源代码.
*/
package com.psy.service;

import java.sql.SQLException;

import com.psy.util.DBHelper;

 
public class RecordService {
	
	static String sql = null;  
	static DBHelper db1 = null;  
	static Boolean isUp = true;
	
	public static Boolean insertData(String name,String role,String rand_num,String pad_uuid,String question,String answer){
		
		sql = "insert into record(name,role,rand_num,pad_uuid,question,answer,create_time) values( '"
				+name+"','"+role+"','"+rand_num+"','"+pad_uuid+"','"+question+"','"+answer+"',NOW()) ";
		db1 = new DBHelper(sql);
		//执行语句，得到结果集  
        try {
			int ret = db1.pst.executeUpdate();
			if(ret>0){
				//更新成功
				System.out.println("success");
				isUp = true;
			}else{
				isUp = false;
			}
            db1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isUp;
	}
}
