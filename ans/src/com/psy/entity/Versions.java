/**
 * Versions.java
 * com.psy.entity
 * author      date      	
 * ──────────────────────────────────
 * xiao    2015年9月18日 		
 * Copyright (c)2015, All Rights Reserved.
 * Java源代码,未经许可禁止任何人、任何组织通过任何
 * 渠道使用、修改源代码.
*/
package com.psy.entity;

import java.io.Serializable;

 
public class Versions implements Serializable{
	
	private String school;
	private String type;
	private String versionName;
	private String url;
	private String isMust;
	private String versionText;
	
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIsMust() {
		return isMust;
	}
	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}
	public String getVersionText() {
		return versionText;
	}
	public void setVersionText(String versionText) {
		this.versionText = versionText;
	}
	
}
