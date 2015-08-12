/**
 * Slf4j.java
 * com.psy.servlet
 * author      date      	
 * ──────────────────────────────────
 * xiao    2015年7月27日 		
 * Copyright (c)2015, All Rights Reserved.
 * Java源代码,未经许可禁止任何人、任何组织通过任何
 * 渠道使用、修改源代码.
*/
package com.psy.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 
public class Slf4j {
	public static final Logger logger = LoggerFactory.getLogger(Slf4j.class);
    
    public static void main(String[] args){
//        System.setProperty("log4j.configuration", "log4j.properties");
        logger.debug("test start...");
        
        logger.error("test error...");
    }
}
