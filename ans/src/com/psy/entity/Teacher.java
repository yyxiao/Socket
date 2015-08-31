/**
 * Teacher.java
 * com.psy.entity
 * author      date      	
 * ──────────────────────────────────
 * xiao    2015年8月27日 		
 * Copyright (c)2015, All Rights Reserved.
 * Java源代码,未经许可禁止任何人、任何组织通过任何
 * 渠道使用、修改源代码.
*/
package com.psy.entity;

import java.io.Serializable;

 
public class Teacher implements Serializable{
	
	//姓名
	private String teaName;
	//科目
	private String subject;
	public String getTeaName() {
		return teaName;
	}
	public void setTeaName(String teaName) {
		this.teaName = teaName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

}
