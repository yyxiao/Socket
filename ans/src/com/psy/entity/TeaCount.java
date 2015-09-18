/**
 * TeaCount.java
 * com.psy.entity
 * author      date      	
 * ──────────────────────────────────
 * xiao    2015年9月10日 		
 * Copyright (c)2015, All Rights Reserved.
 * Java源代码,未经许可禁止任何人、任何组织通过任何
 * 渠道使用、修改源代码.
*/
package com.psy.entity;

import java.io.Serializable;

 
public class TeaCount implements Serializable{
	
	//No
	private String stuNo;
	//姓名
	private String stuName;
	//pad标识码
	private String stuUuid;
	//随机码
	private String randnum;
	//统计量
	private String counts;
	public String getStuNo() {
		return stuNo;
	}
	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getStuUuid() {
		return stuUuid;
	}
	public void setStuUuid(String stuUuid) {
		this.stuUuid = stuUuid;
	}
	public String getRandnum() {
		return randnum;
	}
	public void setRandnum(String randnum) {
		this.randnum = randnum;
	}
	public String getCounts() {
		return counts;
	}
	public void setCounts(String counts) {
		this.counts = counts;
	}
	
}
