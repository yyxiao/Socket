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
	
	/** 随机数集合 */
	public static final List<String> randList = new ArrayList<String>();
	
	/** 定义服务端监听端口 */
	public static final int PORT = 9999;
	
	/** pad端唯一标识码 */
	public static final String PADUUID = "paduuid";
	/** 角色 */
	public static final String ROLE = "role";
	/** 姓名 */
	public static final String NAME = "name";
	/** 答案 */
	public static final String ANS = "ans";
	/** 问题 */
	public static final String QUES = "ques";
	/** 分组 */
	public static final String GROUP = "group";
	/** 随机数 */
	public static final String RANDNUM = "randnum";
	/** 单人答题学生uuid  */
	public static final String SINGLEANSUUID = "singleAnsUuid";
	/** 单人答题选项  */
	public static final String SINGLEANS = "singleAns";
	/** 上传图片学生uuid  */
	public static final String SINGLEUPUUID = "singleUploadUuid";
	/** 上传图片虚拟地址  */
	public static final String SINGLEUPURL = "singleUploadUrl";
	/** 题目难易度 */
	public static final String DEGREE = "degree";
	/** 统计成绩*/
	public static final String COUNT = "count";
	/** 登录状态 */
	public static final String LOGINTYPE = "logintype";
	/** 登录信息 */
	public static final String LOGININFO= "logininfo";
	
	/** 所有客户连接池列表 */
	public static final Set<IoSession> sessions = Collections.synchronizedSet(new HashSet<IoSession>());
	/** 所有学生端连接池列表 */
	public static final Set<IoSession> sessionStus = Collections.synchronizedSet(new HashSet<IoSession>());
	/** 所有教师端连接池列表 */
	public static final Set<IoSession> sessionTeas = Collections.synchronizedSet(new HashSet<IoSession>());
	
}
