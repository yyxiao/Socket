/**
 * StuService.java
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

 
public class StuService {
	/** 
	 *  logger  
	 *  TODO(引入slf4j日志)
	*/
	public static final Logger logger = LoggerFactory.getLogger(Slf4j.class);
	
	/**
	 * TODO(学生端发送给教师端)
	 * @param session
	*/
	public static void sendToTeacher(IoSession session,String type){
		String stu_paduuid = session.getAttribute(Constants.PADUUID,"NONE").toString();
		String stu_randnum = session.getAttribute(Constants.RANDNUM,"NONE").toString();
		String stu_question = session.getAttribute(Constants.QUES,"NONE").toString();
		String stu_answer = session.getAttribute(Constants.ANS,"NONE").toString();
		String stu_no = session.getAttribute(Constants.NO,"NONE").toString();
		String stu_name = session.getAttribute(Constants.NAME,"NONE").toString();
		String stu_logintype = session.getAttribute(Constants.LOGINTYPE,"NONE").toString();
		String stu_logininfo = session.getAttribute(Constants.LOGININFO,"NONE").toString();
		String stu_opear = type;
		String stu_assess = session.getAttribute(Constants.ASSESS,"NONE").toString();
		String stu_degree = session.getAttribute(Constants.DEGREE,"NONE").toString();
		String stu_peerass = session.getAttribute(Constants.PEERASS,"NONE").toString();
		String ans_uuid = session.getAttribute(Constants.ANSUUID,"NONE").toString();
		String ans_no = session.getAttribute(Constants.ANSNO,"NONE").toString();
		String stu_option = "";
		String stu_group = session.getAttribute(Constants.GROUP,"NONE").toString();
		String stu_count = session.getAttribute(Constants.STUCOUNT,"NONE").toString();
		String role = "stu";
		if(Constants.randList.contains(stu_randnum)){
			for(IoSession sessionTea:Constants.sessionTeas){
				String randTea = sessionTea.getAttribute(Constants.RANDNUM,"NONE").toString();
				if(randTea.equals(stu_randnum)){
					if(type.equals("login")){
						session.setAttribute(Constants.LOGINTYPE,"success");
						session.setAttribute(Constants.LOGININFO,"登录成功!");
						logger.debug("学生端登录成功!");
						stu_logintype = session.getAttribute(Constants.LOGINTYPE,"NONE").toString();
						stu_logininfo = session.getAttribute(Constants.LOGININFO,"NONE").toString();
						String data = "HNZZ\t&StuInfos\t&" 
								+stu_paduuid+"\t&" 
								+stu_randnum+"\t&" 
								+stu_question+"\t&"
								+stu_answer+"\t&"
								+stu_logintype+"\t&"
								+stu_logininfo+"\t&"
								+stu_no+"\t&"
								+stu_opear+"\t&"
								+ans_uuid+"\t&"
								+stu_option+"\t&"
								+stu_group+"\t&"
								+stu_name+"\t&"
								+"END\n";
						logger.debug("sendToTeacher:"+data);
						sessionTea.write(data);
						session.write(data);
					}else if(type.equals("ans")){
						logger.debug("学生端答题成功!");
						String data = "HNZZ\t&StuInfos\t&" 
								+stu_paduuid+"\t&" 
								+stu_randnum+"\t&" 
								+stu_question+"\t&"
								+stu_answer+"\t&"
								+stu_logintype+"\t&"
								+stu_logininfo+"\t&"
								+stu_no+"\t&"
								+stu_opear+"\t&"
								+ans_uuid+"\t&"
								+stu_option+"\t&"
								+stu_group+"\t&"
								+stu_name+"\t&"
								+"END\n";
						logger.debug("sendToTeacher:"+data);
						sessionTea.write(data);
						session.write(data);
					}else if(type.equals("usualAns")){
						logger.debug("常规答题成功!");
						stu_option = session.getAttribute(Constants.USUALANS,"NONE").toString();
						String data = "HNZZ\t&StuInfos\t&" 
								+stu_paduuid+"\t&" 
								+stu_randnum+"\t&" 
								+stu_question+"\t&"
								+stu_answer+"\t&"
								+stu_logintype+"\t&"
								+stu_logininfo+"\t&"
								+stu_no+"\t&"
								+stu_opear+"\t&"
								+ans_uuid+"\t&"
								+stu_option+"\t&"
								+stu_group+"\t&"
								+stu_name+"\t&"
								+"END\n";
						logger.debug("sendToTeacher:"+data);
						sessionTea.write(data);
						for(IoSession session_stu : Constants.sessionStus){
							String stu_uuid = session_stu.getAttribute(Constants.PADUUID,"NONE").toString();
							if(stu_uuid.equals(ans_uuid)){
								session_stu.write(data);
								logger.debug("其他学生端评论反馈于当前答题学生端!");
							}
						}
					}else if(type.equals("degree")){
						logger.debug("评论题目难易度!");
						stu_option = session.getAttribute(Constants.DEGREE,"NONE").toString();
						String data = "HNZZ\t&StuInfos\t&" 
								+stu_paduuid+"\t&" 
								+stu_randnum+"\t&" 
								+stu_question+"\t&"
								+stu_answer+"\t&"
								+stu_logintype+"\t&"
								+stu_logininfo+"\t&"
								+stu_no+"\t&"
								+stu_opear+"\t&"
								+ans_uuid+"\t&"
								+stu_option+"\t&"
								+stu_group+"\t&"
								+stu_name+"\t&"
								+"END\n";
						logger.debug("sendToTeacher:"+data);
						sessionTea.write(data);
						session.write(data);
					}else if(type.equals("handsup")){
						logger.debug("学生举手!");
						stu_answer = session.getAttribute(Constants.HANDSUP,"NONE").toString();
						String data = "HNZZ\t&StuInfos\t&" 
								+stu_paduuid+"\t&" 
								+stu_randnum+"\t&" 
								+stu_question+"\t&"
								+stu_answer+"\t&"
								+stu_logintype+"\t&"
								+stu_logininfo+"\t&"
								+stu_no+"\t&"
								+stu_opear+"\t&"
								+ans_uuid+"\t&"
								+stu_option+"\t&"
								+stu_group+"\t&"
								+stu_name+"\t&"
								+"END\n";
						logger.debug("sendToTeacher:"+data);
						sessionTea.write(data);
					}else if(type.equals("estimate")||type.equals("peerAss")){
						stu_option = stu_peerass;
						logger.debug("学生常规评价!");
						String data = "HNZZ\t&StuInfos\t&" 
								+stu_paduuid+"\t&" 
								+stu_randnum+"\t&" 
								+stu_question+"\t&"
								+stu_answer+"\t&"
								+stu_logintype+"\t&"
								+stu_logininfo+"\t&"
								+stu_no+"\t&"
								+stu_opear+"\t&"
								+ans_uuid+"\t&"
								+stu_option+"\t&"
								+stu_group+"\t&"
								+stu_name+"\t&"
								+ans_no+"\t&"
								+"END\n";
						logger.debug("sendToTeacher:"+data);
						for(IoSession session_stu : Constants.sessionStus){
							String stu_uuid = session_stu.getAttribute(Constants.PADUUID,"NONE").toString();
							if(stu_uuid.equals(ans_uuid)){
								sessionTea.write(data);
								session_stu.write(data);
								logger.debug("其他学生端评论反馈于当前答题学生端!");
							}
						}
					}
					if(stu_question.equals(101)){
						session.removeAttribute(Constants.QUES);
				    }
				}
			}
		}else{
			session.setAttribute(Constants.LOGINTYPE,"error");
			session.setAttribute(Constants.LOGININFO,"不存在该教室!");
			logger.debug("学生端登录失败!不存在该教室!");
			stu_logintype = session.getAttribute(Constants.LOGINTYPE,"NONE").toString();
			stu_logininfo = session.getAttribute(Constants.LOGININFO,"NONE").toString();
			String data = "HNZZ\t&StuInfos\t&" 
					+stu_paduuid+"\t&" 
					+stu_randnum+"\t&" 
					+stu_question+"\t&"
					+stu_answer+"\t&"
					+stu_logintype+"\t&"
					+stu_logininfo+"\t&"
					+stu_no+"\t&"
					+stu_opear+"\t&"
					+ans_uuid+"\t&"
					+stu_option+"\t&"
					+stu_group+"\t&"
					+stu_name+"\t&"
					+"END\n";
			logger.debug("sendToTeacher:"+data);
			session.write(data);
		}
		//区分学生统计信息
		if(type.equals("stuCount")){
			Boolean isUp = StuCountService.insertData(stu_name, stu_no, stu_paduuid, stu_randnum, stu_count);
		    if(isUp){
		    	logger.debug("保存学生统计数据成功！学号"+stu_no);
		    }else{
		    	logger.error("保存学生统计数据失败！学号"+stu_no);
		    }
		}else{
			Boolean isUp = RecordService.insertData(stu_name, stu_no, role, stu_randnum, stu_paduuid, ans_uuid, ans_no, type, stu_question, stu_answer,stu_assess,stu_degree,stu_peerass);
		    if(isUp){
		    	logger.debug("保存学生数据成功！");
		    }else{
		    	logger.error("保存学生数据失败！");
		    }
		}
	}
	
	/**
	 * TODO(学生端发送给教师端-退出)
	 * @param session
	*/
	public static void outStu(IoSession session){
		String stu_paduuid = session.getAttribute(Constants.PADUUID,"NONE").toString();
		String stu_randnum = session.getAttribute(Constants.RANDNUM,"NONE").toString();
		String stu_question = session.getAttribute(Constants.QUES,"NONE").toString();
		String stu_answer = session.getAttribute(Constants.ANS,"NONE").toString();
		String stu_no = session.getAttribute(Constants.NO,"NONE").toString();
		String stu_name = session.getAttribute(Constants.NAME,"NONE").toString();
		String stu_opear = "exit";
		String ans_uuid = session.getAttribute(Constants.ANSUUID,"NONE").toString();
		String stu_option = "";
		String stu_group = session.getAttribute(Constants.GROUP,"NONE").toString();
		for(IoSession sessionTea:Constants.sessionTeas){
			String randTea = sessionTea.getAttribute(Constants.RANDNUM,"NONE").toString();
			if(randTea.equals(stu_randnum)){
				session.setAttribute(Constants.LOGINTYPE,"exit");
				session.setAttribute(Constants.LOGININFO,"退出成功!");
				String stu_logintype = session.getAttribute(Constants.LOGINTYPE,"NONE").toString();
				String stu_logininfo = session.getAttribute(Constants.LOGININFO,"NONE").toString();
				logger.debug("学生端退出成功!");
				String data = "HNZZ\t&StuInfos\t&" 
						+stu_paduuid+"\t&" 
						+stu_randnum+"\t&" 
						+stu_question+"\t&"
						+stu_answer+"\t&"
						+stu_logintype+"\t&"
						+stu_logininfo+"\t&"
						+stu_no+"\t&"
						+stu_opear+"\t&"
						+ans_uuid+"\t&"
						+stu_option+"\t&"
						+stu_group+"\t&"
						+stu_name+"\t&"
						+"END\n";
				System.out.println(data);
				sessionTea.write(data);
			}
		}
	}
	
}
