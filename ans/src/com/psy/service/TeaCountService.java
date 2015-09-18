/**
 * TeaCountService.java
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
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.psy.entity.TeaCount;
import com.psy.util.AnsHandler;
import com.psy.util.DBHelper;
import com.psy.util.StringHelper;

 
/**
 * ClassName:TeaCountService
 *
 * TODO(教师端下课统计)
 *
 * @project ans
 *
 * @author xiao
 *
 * @date   2015年9月10日 下午1:31:48	
 *
 * @class com.psy.service.TeaCountService
 *
 */ 
public class TeaCountService {
	/** 
	 *  logger  
	 *  TODO(引入slf4j日志)
	*/
	public static final Logger logger = LoggerFactory.getLogger(TeaCountService.class);
	
	static String sql = null;  
	static DBHelper db1 = null;  
	static Boolean isUp = true;
	static ResultSet resultSet = null;
	
	/**
	 * TODO(统计本节课学生信息——最活跃的)
	 * @param randnum
	 * @return
	*/
	public static List<TeaCount> searchData(String randnum){
		//填写sql语句
		sql = "SELECT Count(0) AS counts,name,user_no,rand_num,pad_uuid from record where"
				+ "	record.rand_num = '"+randnum+"' AND record.type "
				+ " IN ('ques','ans','upload','degree','estimate','handsup','assess','peerAss','groupAss')"
				+ " AND role = 'stu' GROUP BY user_no ORDER BY counts DESC limit 0,3";
		db1 = new DBHelper(sql);
		List<TeaCount> teaCountList = new ArrayList<TeaCount>();
		try {
			//获取结果集
			resultSet = db1.pst.executeQuery();
			if(!StringHelper.isEmptyObject(resultSet)){
				while(resultSet.next()){
					TeaCount teaCount = new TeaCount();
					//拼接、处理数据
					teaCount.setCounts(resultSet.getString(1));
					teaCount.setStuName(resultSet.getString(2));
					teaCount.setStuNo(resultSet.getString(3));
					teaCount.setRandnum(resultSet.getString(4));
					teaCount.setStuUuid(resultSet.getString(5));
					teaCountList.add(teaCount);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teaCountList;
	}
	
	/**
	 * TODO(统计本节课学生信息——需观察)
	 * @param randnum
	 * @return
	*/
	public static List<TeaCount> searchOtherStu(String randnum){
		//填写sql语句
		sql = "SELECT Count(0) AS counts,name,user_no,rand_num,pad_uuid from record where"
				+ "	record.rand_num = '"+randnum+"' AND record.type "
				+ " IN ('ques','ans','upload','degree','estimate','handsup','assess','peerAss','groupAss')"
				+ " AND role = 'stu' GROUP BY user_no ORDER BY counts ASC limit 0,3";
		db1 = new DBHelper(sql);
		List<TeaCount> teaCountList = new ArrayList<TeaCount>();
		try {
			//获取结果集
			resultSet = db1.pst.executeQuery();
			if(!StringHelper.isEmptyObject(resultSet)){
				while(resultSet.next()){
					TeaCount teaCount = new TeaCount();
					//拼接、处理数据
					teaCount.setCounts(resultSet.getString(1));
					teaCount.setStuName(resultSet.getString(2));
					teaCount.setStuNo(resultSet.getString(3));
					teaCount.setRandnum(resultSet.getString(4));
					teaCount.setStuUuid(resultSet.getString(5));
					teaCountList.add(teaCount);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teaCountList;
	}
	
	/**
	 * TODO(查询教师数据量)
	 * @param counts
	 * @return
	*/
	public static int searchLessNum(String counts,String type){
		//填写sql语句
		sql = "SELECT COUNT(*) as counts FROM `teacount` ";
		if(type.equals("00A")){//小于
			sql += " WHERE teacount.score <= '"+counts+"'";
		}
		db1 = new DBHelper(sql);
		int num = 0;
		try {
			//获取结果集
			resultSet = db1.pst.executeQuery();
			if(!StringHelper.isEmptyObject(resultSet)){
				while(resultSet.next()){
					//拼接、处理数据
					num = resultSet.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}
	
	/**
	 * TODO(查询本堂课数据总量)
	 * @param counts
	 * @return
	*/
	public static String searchNums(String randnum){
		//填写sql语句
//		sql = "SELECT counts FROM teacounts WHERE rand_num = '"+7156+ "'";
		sql = "SELECT teaview. name,teaview.user_no,teaview.rand_num,teaview.pad_uuid,"
				+ " teaview.counts2 + COALESCE(stuhandsup.counts1, 0) AS counts "
				+ " FROM	(SELECT	Count(0) AS counts1,rand_num FROM record "
				+ " WHERE type IN ('handsup') AND record.role = 'stu' AND record.rand_num = '"
				+ randnum +"' GROUP BY	rand_num) stuhandsup "
				+ " RIGHT JOIN (	SELECT	Count(0) AS counts2,record.`name`,record.user_no,"
				+ " record.rand_num,record.pad_uuid FROM	record"
				+ "	WHERE record.type IN ('usualAns','group','sex','subject','singleSel','picurl')"
				+ "	AND record.role = 'tea'	AND record.rand_num = '"+randnum+"'"
				+ " GROUP BY record.rand_num) teaview ON stuhandsup.rand_num = teaview.rand_num"
				+ " GROUP BY teaview. NAME,teaview.user_no,teaview.rand_num,teaview.pad_uuid";
		db1 = new DBHelper(sql);
		String name = "";
		String tea_no = "";
		String rand_num = "";
		String pad_uuid = "";
		String counts = "";
		try {
			//获取结果集
			resultSet = db1.pst.executeQuery();
			if(!StringHelper.isEmptyObject(resultSet)){
				while(resultSet.next()){
					//拼接、处理数据
					name = resultSet.getString(1);
					tea_no = resultSet.getString(2);
					rand_num = resultSet.getString(3);
					pad_uuid = resultSet.getString(4);
					counts = resultSet.getString(5);
				}
				if(!StringHelper.isEmptyObject(name)){
					Boolean isUp = TeaCountService.insertData(tea_no, name, pad_uuid, rand_num, counts);
				    if(isUp){
				    	logger.debug("保存老师统计数据成功！");
				    }else{
				    	logger.error("保存老师统计数据失败！");
				    }
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return counts;
	}
	
	/**
	 * TODO(存储查询出的信息)
	 * @param tea_no
	 * @param tea_name
	 * @param pad_uuid
	 * @param rand_num
	 * @param score
	 * @return
	*/
	public static Boolean insertData(String tea_no, String tea_name, String pad_uuid,
			String rand_num,String score) {

		sql = "insert into teacount(tea_no,tea_name,tea_uuid,rand_num,score,create_time) values( '"
				+ tea_no	+ "','"	+ tea_name + "','" + pad_uuid + "','" + rand_num + "','" + score + "',NOW()) ";
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
	
	/**
	 * TODO(将list转为字符串)
	 * @param randnum 课堂随机号
	 * @param type	类型
	 * @return
	*/
	public static String searchStuNames(String randnum,String type){
		List<TeaCount> teaCounts = new ArrayList<TeaCount>();
		if(type.equals("00A")){//最活跃的
			teaCounts = TeaCountService.searchData(randnum);
		}else if(type.equals("00B")){//需观察
			teaCounts = TeaCountService.searchOtherStu(randnum);
		}
		String stuNames = "";
		for (int i = 0; i < teaCounts.size(); i++) {
			TeaCount teaCount = teaCounts.get(i);
			stuNames += teaCount.getStuName() + ",";
		}
		if(stuNames.length()>0){
			stuNames = stuNames.substring(0, stuNames.length()-1);
		}
		System.out.println(stuNames);
		return stuNames;
	}
	
//	public static void main(String[] args) {
//		List<TeaCount> teaCounts = TeaCountService.searchData("2124");
//		String stuNames = "";
//		for (int i = 0; i < teaCounts.size(); i++) {
//			TeaCount teaCount = teaCounts.get(i);
//			stuNames += teaCount.getStuName() + ",";
//		}
//		stuNames = stuNames.substring(0, stuNames.length()-1);
//		System.out.println(stuNames);
//	}
}
