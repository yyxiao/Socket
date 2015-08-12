/**
 * TeaService.java
 * com.psy.service
 * author      date      	
 * ──────────────────────────────────
 * xiao    2015年8月6日 		
 * Copyright (c)2015, All Rights Reserved.
 * Java源代码,未经许可禁止任何人、任何组织通过任何
 * 渠道使用、修改源代码.
*/
package com.psy.service;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.psy.servlet.Slf4j;
import com.psy.util.Constants;

 
public class TeaService {
	/** 
	 *  logger  
	 *  TODO(引入slf4j日志)
	*/
	public static final Logger logger = LoggerFactory.getLogger(Slf4j.class);
	
	public static void sendAllStudent(IoSession session,String type){
		String tea_paduuid = session.getAttribute(Constants.PADUUID,"NONE").toString();
		String tea_randnum = session.getAttribute(Constants.RANDNUM,"NONE").toString();
		String tea_question = session.getAttribute(Constants.QUES,"NONE").toString();
		String tea_answer = session.getAttribute(Constants.ANS,"NONE").toString();
		String tea_logintype = session.getAttribute(Constants.LOGINTYPE,"NONE").toString();
		String tea_logininfo = session.getAttribute(Constants.LOGININFO,"NONE").toString();
		String tea_name = session.getAttribute(Constants.NAME,"NONE").toString();
		String ans_uuid = session.getAttribute(Constants.SINGLEANSUUID,"NONE").toString();
		String single_ans = session.getAttribute(Constants.SINGLEANS,"NONE").toString();
		String group_num = session.getAttribute(Constants.GROUP,"NONE").toString();
		String role = "tea";
		String data = "HNZZ\t&TeaInfos\t&"
				+tea_paduuid+"\t&"
				+tea_randnum+"\t&"
				+tea_question+"\t&"
				+tea_answer+"\t&"
				+tea_logintype+"\t&"
				+tea_logininfo+"\t&"
				+tea_name+"\t&"
				+type+"\t&"
				+ans_uuid+"\t&"
				+single_ans+"\t&"
				+group_num+"\t&"
				+"END\n";
	    logger.debug("sendAllStudent:"+data);
	    
	    if(type.equals("login")){
	    	session.write(data);
	    }else if(type.equals("singleAns")){
	    	//发送给全部学生
			for (IoSession session_stu : Constants.sessionStus) {
				String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
				if(randStu.equals(tea_randnum)){
					session_stu.setAttribute(Constants.SINGLEANSUUID, ans_uuid);
					session_stu.write(data);
				}
			}
	    	session.write(data);
	    }else if(type.equals("singleUpload")){
	    	//发送给全部学生
			for (IoSession session_stu : Constants.sessionStus) {
				String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
				ans_uuid = session.getAttribute(Constants.SINGLEUPUUID,"NONE").toString();
		    	data = "HNZZ\t&TeaInfos\t&"
						+tea_paduuid+"\t&"
						+tea_randnum+"\t&"
						+tea_question+"\t&"
						+tea_answer+"\t&"
						+tea_logintype+"\t&"
						+tea_logininfo+"\t&"
						+tea_name+"\t&"
						+type+"\t&"
						+ans_uuid+"\t&"
						+single_ans+"\t&"
						+group_num+"\t&"
						+"END\n";
				if(randStu.equals(tea_randnum)){
					session_stu.write(data);
				}
			}
	    	session.write(data);
	    }else if(type.equals("degree")){
	    	//发送给全部学生
			for (IoSession session_stu : Constants.sessionStus) {
				String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
				if(randStu.equals(tea_randnum)){
					session_stu.write(data);
				}
			}
//	    	session.write(data);
	    }else if(type.equals("group")){
	    	//发送给全部学生
			for (IoSession session_stu : Constants.sessionStus) {
				String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
				if(randStu.equals(tea_randnum)){
					ans_uuid = session_stu.getAttribute(Constants.PADUUID,"NONE").toString();
					group_num = session_stu.getAttribute(Constants.GROUP,"NONE").toString();
			    	data = "HNZZ\t&TeaInfos\t&"
							+tea_paduuid+"\t&"
							+tea_randnum+"\t&"
							+tea_question+"\t&"
							+tea_answer+"\t&"
							+tea_logintype+"\t&"
							+tea_logininfo+"\t&"
							+tea_name+"\t&"
							+type+"\t&"
							+ans_uuid+"\t&"
							+single_ans+"\t&"
							+group_num+"\t&"
							+"END\n";
					session_stu.write(data);
					session.write(data);
				}
			}
	    }else if(type.equals("count")){
	    	single_ans = session.getAttribute(Constants.COUNT,"NONE").toString();
	    	data = "HNZZ\t&TeaInfos\t&"
					+tea_paduuid+"\t&"
					+tea_randnum+"\t&"
					+tea_question+"\t&"
					+tea_answer+"\t&"
					+tea_logintype+"\t&"
					+tea_logininfo+"\t&"
					+tea_name+"\t&"
					+type+"\t&"
					+ans_uuid+"\t&"
					+single_ans+"\t&"
					+group_num+"\t&"
					+"END\n";
	    	//发送给全部学生
			for (IoSession session_stu : Constants.sessionStus) {
				String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
				if(randStu.equals(tea_randnum)){
					session_stu.write(data);
				}
			}
//	    	session.write(data);
	    }else{
	    	//发送给全部学生
			for (IoSession session_stu : Constants.sessionStus) {
				String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
				if(randStu.equals(tea_randnum)){
					session_stu.setAttribute(Constants.QUES,tea_question);
					session_stu.write(data);
				}
			}
			Boolean isUp = RecordService.insertData(tea_name, role, tea_randnum, tea_paduuid, tea_question, tea_answer);
		    if(isUp){
		    	logger.debug("保存老师数据成功！");
		    }else{
		    	logger.error("保存老师数据失败！");
		    }
			session.removeAttribute(Constants.ANS);
	    }
	}
	
}
