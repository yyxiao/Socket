/**
 * SameGroup.java
 * com.psy.entity
 * author      date      	
 * ──────────────────────────────────
 * xiao    2015年9月8日 		
 * Copyright (c)2015, All Rights Reserved.
 * Java源代码,未经许可禁止任何人、任何组织通过任何
 * 渠道使用、修改源代码.
*/
package com.psy.entity;

import java.io.Serializable;

 
public class SameGroup implements Serializable{
	
	//同组学生姓名
	private String userNames;
	//同组学生学号
	private String userNos;
	//同组学生pad标识码
	private String userUuids;
	
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public String getUserNos() {
		return userNos;
	}
	public void setUserNos(String userNos) {
		this.userNos = userNos;
	}
	public String getUserUuids() {
		return userUuids;
	}
	public void setUserUuids(String userUuids) {
		this.userUuids = userUuids;
	}

}
