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

import java.util.HashSet;
import java.util.Set;

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
		 add("a");
		 add("b");
		 add("c");
		 add("d");
		 add("e");
		 add("f");
		 add("g");
		 add("h");
		 add("i");
		 add("j");
		 add("k");
		 add("l");
		 add("m");
		 add("n");
		 add("o");
		 add("p");
		 add("q");
		 add("r");
		 add("s");
		 add("t");
	}};
	
	/**
	 * TODO(学生分组)
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
	
	
	public static void main(String[] args) {
//		giveGroup(4);
//		System.out.println(stuAll.size());
	}

}
