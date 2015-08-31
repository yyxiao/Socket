/**
 * Student.java
 * com.psy.entity
 * author      date      	
 * ──────────────────────────────────
 * xiao    2015年8月26日 		
 * Copyright (c)2015, All Rights Reserved.
 * Java源代码,未经许可禁止任何人、任何组织通过任何
 * 渠道使用、修改源代码.
*/
package com.psy.entity;

import java.io.Serializable;

 
public class Student implements Serializable {
	//姓名
	private String userName;
	//学号（唯一）
	private String userNo;
	//性别
	private String sex;
	//语文
	private String yuwen;
	//数学
	private String shuxue;
	//英语
	private String yingyu;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getYuwen() {
		return yuwen;
	}
	public void setYuwen(String yuwen) {
		this.yuwen = yuwen;
	}
	public String getShuxue() {
		return shuxue;
	}
	public void setShuxue(String shuxue) {
		this.shuxue = shuxue;
	}
	public String getYingyu() {
		return yingyu;
	}
	public void setYingyu(String yingyu) {
		this.yingyu = yingyu;
	}
	
	
}
