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
	    }else if(type.equals("upload")){
	    	//上传照片
	    	String stuType = session.getAttribute(Constants.STUTYPE,"NONE").toString();
	    	ans_uuid = session.getAttribute(Constants.UPUUID,"NONE").toString();
	    	System.out.println(stuType.equals(Constants.SINGLESTU));
	    	if(stuType.equals(Constants.SINGLESTU)){
	    		//上传照片-单人
	    		for (IoSession session_stu : Constants.sessionStus) {
	    			String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
	    			String stu_uuid = session_stu.getAttribute(Constants.PADUUID,"NONE").toString();
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
					if(randStu.equals(tea_randnum)&&stu_uuid.equals(ans_uuid)){
						session_stu.write(data);
						session.write(data);
					}
	    		}
	    	}else if(stuType.equals(Constants.MANYSTU)){
	    		//上传照片-多人
	    		String[] ans_uuidArray = null;  
	    		ans_uuidArray = ans_uuid.split(",");
	    		for (int i = 0; i < ans_uuidArray.length; i++) {
	    			String stu_uuid = ans_uuidArray[i];
	    			for (IoSession session_stu : Constants.sessionStus) {
		    			String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
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
						if(randStu.equals(tea_randnum)&&stu_uuid.equals(ans_uuid)){
							session_stu.write(data);
							session.write(data);
						}
		    		}
				}
	    	}else if(stuType.equals(Constants.ALLSTU)){
	    		//上传照片-全班
				for (IoSession session_stu : Constants.sessionStus) {
					String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
					String stu_uuid = session_stu.getAttribute(Constants.PADUUID,"NONE").toString();
					ans_uuid = session.getAttribute(Constants.UPUUID,"NONE").toString();
			    	data = "HNZZ\t&TeaInfos\t&"
							+tea_paduuid+"\t&"
							+tea_randnum+"\t&"
							+tea_question+"\t&"
							+tea_answer+"\t&"
							+tea_logintype+"\t&"
							+tea_logininfo+"\t&"
							+tea_name+"\t&"
							+type+"\t&"
							+stu_uuid+"\t&"
							+single_ans+"\t&"
							+group_num+"\t&"
							+"END\n";
					if(randStu.equals(tea_randnum)){
						session_stu.write(data);
						session.write(data);
					}
				}
	    	}
	    }else if(type.equals("singleSel")){
	    	//单选
	    	String stuType = session.getAttribute(Constants.STUTYPE,"NONE").toString();
	    	ans_uuid = session.getAttribute(Constants.SELUUID,"NONE").toString();
	    	if(stuType.equals(Constants.SINGLESTU)){
	    		//单选-单人
	    		for (IoSession session_stu : Constants.sessionStus) {
	    			String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
	    			String stu_uuid = session_stu.getAttribute(Constants.PADUUID,"NONE").toString();
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
					if(randStu.equals(tea_randnum)&&stu_uuid.equals(ans_uuid)){
						session_stu.setAttribute(Constants.QUES,tea_question);
						session_stu.write(data);
						session.write(data);
					}
	    		}
	    	}else if(stuType.equals(Constants.MANYSTU)){
	    		//单选-多人
	    		String[] ans_uuidArray = null;  
	    		ans_uuidArray = ans_uuid.split(",");
	    		for (int i = 0; i < ans_uuidArray.length; i++) {
	    			String stu_uuid = ans_uuidArray[i];
	    			for (IoSession session_stu : Constants.sessionStus) {
		    			String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
		    			String stu_uuid1 = session_stu.getAttribute(Constants.PADUUID,"NONE").toString();
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
						if(randStu.equals(tea_randnum)&&stu_uuid.equals(stu_uuid1)){
							session_stu.setAttribute(Constants.QUES,tea_question);
							session_stu.write(data);
							session.write(data);
						}
		    		}
				}
	    	}else if(stuType.equals(Constants.ALLSTU)){
	    		//单选-全班
				for (IoSession session_stu : Constants.sessionStus) {
					String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
					String stu_uuid = session_stu.getAttribute(Constants.PADUUID,"NONE").toString();
					ans_uuid = session.getAttribute(Constants.UPUUID,"NONE").toString();
			    	data = "HNZZ\t&TeaInfos\t&"
							+tea_paduuid+"\t&"
							+tea_randnum+"\t&"
							+tea_question+"\t&"
							+tea_answer+"\t&"
							+tea_logintype+"\t&"
							+tea_logininfo+"\t&"
							+tea_name+"\t&"
							+type+"\t&"
							+stu_uuid+"\t&"
							+single_ans+"\t&"
							+group_num+"\t&"
							+"END\n";
					if(randStu.equals(tea_randnum)){
						session_stu.setAttribute(Constants.QUES,tea_question);
						session_stu.write(data);
						session.write(data);
					}
				}
	    	}else if(stuType.equals(Constants.GROUPSTU)){
	    		//单选-分组
	    		//已选的选项集合
	    		String[] ques = null;  
	    		ques = tea_question.split(",");
	    		for (int i = 0; i < ques.length; i++) {
	    			//当前选项对应的分组组别
	    			int j = i+1;
	    			String group = String.valueOf(j);
	    			String tea_ques = ques[i];
	    			for (IoSession session_stu : Constants.sessionStus) {
		    			String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
		    			String groupStu = session_stu.getAttribute(Constants.GROUP,"NONE").toString();
				    	data = "HNZZ\t&TeaInfos\t&"
								+tea_paduuid+"\t&"
								+tea_randnum+"\t&"
								+tea_ques+"\t&"
								+tea_answer+"\t&"
								+tea_logintype+"\t&"
								+tea_logininfo+"\t&"
								+tea_name+"\t&"
								+type+"\t&"
								+ans_uuid+"\t&"
								+single_ans+"\t&"
								+group_num+"\t&"
								+"END\n";
						if(randStu.equals(tea_randnum)&&groupStu.equals(group)){
							session_stu.setAttribute(Constants.QUES,tea_ques);
							logger.info("第"+group+"组:"+data);
							session_stu.write(data);
							session.write(data);
						}
		    		}
				}
	    	}
	    }else if(type.equals("ans")){
	    	//发布结果（答案）
	    	String stuType = session.getAttribute(Constants.STUTYPE,"NONE").toString();
	    	ans_uuid = session.getAttribute(Constants.SELUUID,"NONE").toString();
	    	if(stuType.equals(Constants.SINGLESTU)){
	    		//发布结果（答案）-单人
	    		for (IoSession session_stu : Constants.sessionStus) {
	    			String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
	    			String stu_uuid = session_stu.getAttribute(Constants.PADUUID,"NONE").toString();
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
					if(randStu.equals(tea_randnum)&&stu_uuid.equals(ans_uuid)){
						session_stu.write(data);
						session.write(data);
					}
	    		}
	    	}else if(stuType.equals(Constants.MANYSTU)){
	    		//发布结果（答案）-多人
	    		String[] ans_uuidArray = null;  
	    		ans_uuidArray = ans_uuid.split(",");
	    		for (int i = 0; i < ans_uuidArray.length; i++) {
	    			String stu_uuid = ans_uuidArray[i];
	    			for (IoSession session_stu : Constants.sessionStus) {
		    			String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
		    			String stu_uuid1 = session_stu.getAttribute(Constants.PADUUID,"NONE").toString();
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
						if(randStu.equals(tea_randnum)&&stu_uuid.equals(stu_uuid1)){
							session_stu.write(data);
							session.write(data);
						}
		    		}
				}
	    	}else if(stuType.equals(Constants.ALLSTU)){
	    		//发布结果（答案）-全班
				for (IoSession session_stu : Constants.sessionStus) {
					String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
					String stu_uuid = session_stu.getAttribute(Constants.PADUUID,"NONE").toString();
					ans_uuid = session.getAttribute(Constants.UPUUID,"NONE").toString();
			    	data = "HNZZ\t&TeaInfos\t&"
							+tea_paduuid+"\t&"
							+tea_randnum+"\t&"
							+tea_question+"\t&"
							+tea_answer+"\t&"
							+tea_logintype+"\t&"
							+tea_logininfo+"\t&"
							+tea_name+"\t&"
							+type+"\t&"
							+stu_uuid+"\t&"
							+single_ans+"\t&"
							+group_num+"\t&"
							+"END\n";
					if(randStu.equals(tea_randnum)){
						session_stu.write(data);
						session.write(data);
					}
				}
	    	}else if(stuType.equals(Constants.GROUPSTU)){
	    		//发布结果（答案）-分组
	    		//已选的选项集合
	    		String[] ans = null;  
	    		ans = tea_answer.split(",");
	    		for (int i = 0; i < ans.length; i++) {
	    			//当前选项对应的分组组别
	    			int j = i+1;
	    			String group = String.valueOf(j);
	    			String tea_ans = ans[i];
	    			for (IoSession session_stu : Constants.sessionStus) {
		    			String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
		    			String groupStu = session_stu.getAttribute(Constants.GROUP,"NONE").toString();
				    	data = "HNZZ\t&TeaInfos\t&"
								+tea_paduuid+"\t&"
								+tea_randnum+"\t&"
								+tea_question+"\t&"
								+tea_ans+"\t&"
								+tea_logintype+"\t&"
								+tea_logininfo+"\t&"
								+tea_name+"\t&"
								+type+"\t&"
								+ans_uuid+"\t&"
								+single_ans+"\t&"
								+group_num+"\t&"
								+"END\n";
						if(randStu.equals(tea_randnum)&&groupStu.equals(group)){
							logger.info("第"+group+"组:"+data);
							session_stu.write(data);
							session.write(data);
						}
		    		}
				}
	    	}
	    }else if(type.equals("degree")){
	    	//发送给全部学生
			for (IoSession session_stu : Constants.sessionStus) {
				String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
				if(randStu.equals(tea_randnum)){
					session_stu.write(data);
				}
			}
//	    	session.write(data);
	    }else if(type.equals("estimate")){
	    	//常规答题评价
	    	ans_uuid = session.getAttribute(Constants.SELUUID,"NONE").toString();
	    	tea_question = session.getAttribute(Constants.ESTIMATE,"NONE").toString();
			for (IoSession session_stu : Constants.sessionStus) {
				String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
				if(randStu.equals(tea_randnum)){
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
	    }else if(type.equals("group")||type.equals("subject")){
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
	    }else if(type.equals("count")||type.equals("picurl")){
	    	single_ans = session.getAttribute(Constants.OPENPARAM,"NONE").toString();
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
	    }else if(type.equals("sex")){
			//性别分组
	    	//发送给全部学生
			for (IoSession session_stu : Constants.sessionStus) {
				String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
				if(randStu.equals(tea_randnum)){
					ans_uuid = session_stu.getAttribute(Constants.PADUUID,"NONE").toString();
					group_num = session_stu.getAttribute(Constants.SEXGROUP,"NONE").toString();
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
