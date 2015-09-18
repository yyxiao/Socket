/**
 * GroupService.java
 * com.psy.service
 * author      date      	
 * ──────────────────────────────────
 * xiao    2015年8月7日 		
 * Copyright (c)2015, All Rights Reserved.
 * Java源代码,未经许可禁止任何人、任何组织通过任何
 * 渠道使用、修改源代码.
*/
package com.psy.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.mina.core.session.IoSession;

import com.psy.util.Constants;

 
/**
 * ClassName:GroupService
 *
 * TODO(分组Service)
 *
 * @project ans
 *
 * @author xiao
 *
 * @date   2015年8月7日 上午10:05:19	
 *
 * @class com.psy.service.GroupService
 *
 */ 
public class GroupService {
	
	public static final Set<String> stuAll = new HashSet<String>(){{
		 add("0");
		 add("2");
		 add("1");
		 add("3");
		 add("4");
		 add("5");
		 add("6");
		 add("7");
		 add("8");
		 add("9");
		 add("11");
		 add("10");
		 add("12");
		 add("13");
		 add("14");
		 add("15");
		 add("16");
		 add("17");
		 add("18");
		 add("19");
	}};
	
	/**
	 * TODO(学生随机分组)
	 * @param sessionStus1	需要分组的学生信息set集合
	 * @param groupNo	分为几组
	*/
	public static void giveGroup(Set<IoSession> sessionStus1,int groupNo){
		//数组长度20
		int len = sessionStus1.size();
		//初始每组长度2
		int size1 = len/groupNo;
		//额外每组长度3
		int size2 = size1+1;
		//余数3
		int size3 = len%groupNo;
		//标识变量
		int flag = 0;
		int flag1 = 0;
		int flag2 = 0;
		int flag3 = 0;
		for(IoSession session_stu:sessionStus1){
			flag1 = flag/size2;
			if(flag1>=size3){
				flag2 = (flag-size3)/size1;
				flag2 = flag2+1;
				session_stu.setAttribute(Constants.GROUP,flag2);
//				a = a + flag2;
			}else{
				flag3 = flag1+1;
				session_stu.setAttribute(Constants.GROUP,flag3);
//				a = a + flag3;
			}
			//累加flag，标识set数组长度
			flag++;
			Constants.sessionStus.add(session_stu);
		}
	}
	
	
	/**
	 * TODO(科目成绩排序分组)
	 * @param sessionStus1	需要分组的学生信息set集合
	 * @param groupNo	分为几组
	 * @param subType	科目类型
	*/
	public static void giveSubGroup(Set<IoSession> sessionStus1,int groupNo,String subType){
		SortedSet<IoSession> ssa= new TreeSet<IoSession>();
		if(subType.equals("00A")){//语文
			ssa=new TreeSet<IoSession>(new SortByYuwen());  //创建一个按照Id排序的TreeSet实例a  
		}else if(subType.equals("00B")){//数学
			ssa=new TreeSet<IoSession>(new SortByShuxue());  //创建一个按照Id排序的TreeSet实例a  
		}else if(subType.equals("00C")){//英语
			ssa=new TreeSet<IoSession>(new SortByYingyu());  //创建一个按照Id排序的TreeSet实例a  
		}
		ssa.addAll(sessionStus1);
		//数组长度20
		int len = ssa.size();
		//初始每组长度2
		int size1 = len/groupNo;
		//额外每组长度3
		int size2 = size1+1;
		//余数3
		int size3 = len%groupNo;
		//标识变量
		int flag = 0;
		int flag1 = 0;
		int flag2 = 0;
		int flag3 = 0;
		for(IoSession session_stu:ssa){
			flag1 = flag/size2;
			if(flag1>=size3){
				flag2 = (flag-size3)/size1;
				flag2 = flag2+1;
				session_stu.setAttribute(Constants.GROUP,flag2);
			}else{
				flag3 = flag1+1;
				session_stu.setAttribute(Constants.GROUP,flag3);
			}
			//累加flag，标识set数组长度
			flag++;
			Constants.sessionStus.add(session_stu);
		}
	}
	
	/**
	 * TODO(学生随机分组)
	 * @param sessionStus1	需要分组的学生信息set集合
	 * @param groupNo	分为几组
	*/
	public static void giveAnsGroup(IoSession session,String ansNo,String ansUuid){
		String[] ansNos = null;  
		ansNos = ansNo.split(",");
		String[] ansUuids = null;  
		ansUuids = ansUuid.split(",");
		int groupNo = ansNos.length;
		String randNo = session.getAttribute(Constants.RANDNUM,"NONE").toString();
		//临时Set集合
		Set<IoSession> sessionStus1 = Collections.synchronizedSet(new HashSet<IoSession>());
		Set<IoSession> sessionStus2 = Collections.synchronizedSet(new HashSet<IoSession>());
		for(IoSession stu:Constants.sessionStus){
			String randNo1 = stu.getAttribute(Constants.RANDNUM,"NONE").toString();
			if(randNo.equals(randNo1)){
				//本班
				sessionStus1.add(stu);
				sessionStus2.add(stu);
			}
		}
		
		for(int i = 0; i < ansNos.length; i++) {
			String ansNo1 = ansNos[i];
			for(IoSession stu:sessionStus1){
				String stuNo = stu.getAttribute(Constants.NO,"NONE").toString();
				if(ansNo1.equals(stuNo)){
					sessionStus2.remove(stu);
				}
			}
		}
		
		//数组长度20
		int len = sessionStus2.size();
		//初始每组长度2
		int size1 = len/groupNo;
		//额外每组长度3
		int size2 = size1+1;
		//余数3
		int size3 = len%groupNo;
		//标识变量
		int flag = 0;
		int flag1 = 0;
		int flag2 = 0;
		int flag3 = 0;
		for(IoSession session_stu:sessionStus2){
			flag1 = flag/size2;
			if(flag1>=size3){
				flag2 = (flag-size3)/size1;
				flag2 = flag2+1;
				session_stu.setAttribute(Constants.ANSNO,ansNos[flag2-1]);
				session_stu.setAttribute(Constants.ANSUUID,ansUuids[flag2-1]);
			}else{
				flag3 = flag1+1;
				session_stu.setAttribute(Constants.ANSNO,ansNos[flag3-1]);
				session_stu.setAttribute(Constants.ANSUUID,ansUuids[flag3-1]);
			}
			//累加flag，标识set数组长度
			flag++;
			Constants.sessionStus.add(session_stu);
		}
	}
	
	public static void main(String[] args) {
//		giveSubGroup(stuAll,4);
//		System.out.println(stuAll.size());
	}

}
