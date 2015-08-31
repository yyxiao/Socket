/**
 * Constants.java
 * com.xyy.util
 * author      date      	
 * ──────────────────────────────────
 * xiao    2015年4月22日 		
 * Copyright (c)2015, All Rights Reserved.
 * Java源代码,未经许可禁止任何人、任何组织通过任何
 * 渠道使用、修改源代码.
*/
package com.psy.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.mina.core.session.IoSession;

/**
 * ClassName:Constants
 *
 * TODO(保存项目中所用常量)
 *
 * @project ans
 *
 * @author xiao
 *
 * @date   2015年7月20日 上午11:16:03	
 *
 * @class com.psy.util.Constants
 *
 */ 
public class Constants {
	
	/** 图片存储地址 */
	public static final String UPLOADURL = "F://HNZZ/";
	
	/** 图片发布地址 */
	public static final String FILEURL = "http://192.168.1.94:8080/pic/";
	
//	/** 图片存储地址 */
//	public static final String UPLOADURL = "E://HNZZ/";
//	
//	/** 图片发布地址 */
//	public static final String FILEURL = "http://192.168.1.125:8088/pic/";
	
	/** 随机数集合 */
	public static final List<String> randList = new ArrayList<String>();
	
	/** 定义服务端监听端口 */
	public static final int PORT = 9999;
	
	/** pad端唯一标识码 */
	public static final String PADUUID = "paduuid";
	/** 角色 */
	public static final String ROLE = "role";
	/** 姓名 */
	public static final String NAME1 = "name1";
	/** 学号 */
	public static final String NAME = "name";
	/** 答案 */
	public static final String ANS = "ans";
	/** 问题 */
	public static final String QUES = "ques";
	/** 随机分组 */
	public static final String GROUP = "group";
	/** 性别分组 */
	public static final String SEXGROUP = "sexgroup";
	/** 类型 */
	public static final String STUTYPE = "stutype";
	/** 随机数 */
	public static final String RANDNUM = "randnum";
	/** 单人答题学生uuid  */
	public static final String SINGLEANSUUID = "singleAnsUuid";
	/** 单人答题选项  */
	public static final String SINGLEANS = "singleAns";
	/** 单选  */
	public static final String SINGLESEL = "singleSel";
	/** 单选学生uuid  */
	public static final String SELUUID = "selUuid";
	/** 上传图片学生uuid  */
	public static final String UPUUID = "uploadUuid";
	/** 上传图片虚拟地址  */
	public static final String SINGLEUPURL = "singleUploadUrl";
	/** 题目难易度 */
	public static final String DEGREE = "degree";
	/** 进行评价 */
	public static final String ESTIMATE = "estimate";
	/** 通用参数(统计成绩、转发url)*/
	public static final String OPENPARAM = "openparam";
	/** 登录状态 */
	public static final String LOGINTYPE = "logintype";
	/** 登录信息 */
	public static final String LOGININFO= "logininfo";
	
	//学生
	/** 性别 */
	public static final String SEX= "sex";
	/** 语文 */
	public static final String YUWEN= "yuwen";
	/** 数学 */
	public static final String SHUXUE= "shuxue";
	/** 英语 */
	public static final String YINGYU= "yingyu";

	
	//连接池
	/** 所有客户连接池列表 */
	public static final Set<IoSession> sessions = Collections.synchronizedSet(new HashSet<IoSession>());
	/** 所有学生端连接池列表 */
	public static final Set<IoSession> sessionStus = Collections.synchronizedSet(new HashSet<IoSession>());
	/** 所有教师端连接池列表 */
	public static final Set<IoSession> sessionTeas = Collections.synchronizedSet(new HashSet<IoSession>());
	
	
	//类别
	/** 单人 */
	public static final String SINGLESTU = "00A";
	/** 多人 */
	public static final String MANYSTU = "00B";
	/** 全班 */
	public static final String ALLSTU = "00C";
	/** 分组 */
	public static final String GROUPSTU = "00D";
	
}
