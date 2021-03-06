/**
 * SortByShuxue.java
 * com.psy.service
 * author      date      	
 * ──────────────────────────────────
 * xiao    2015年9月9日 		
 * Copyright (c)2015, All Rights Reserved.
 * Java源代码,未经许可禁止任何人、任何组织通过任何
 * 渠道使用、修改源代码.
*/
package com.psy.service;

import java.util.Comparator;

import org.apache.mina.core.session.IoSession;

import com.psy.util.Constants;

 
public class SortByShuxue implements Comparator<Object>{  
  
    @Override  
    public int compare(Object o1, Object o2) {  
    	IoSession th1=(IoSession)o1;  
    	IoSession th2=(IoSession)o2;  
		Double shuxue1 = Double.valueOf(th1.getAttribute(Constants.SHUXUE).toString());
		Double shuxue2 = Double.valueOf(th2.getAttribute(Constants.SHUXUE).toString());
        return shuxue2.compareTo(shuxue1);  
    }  

}
