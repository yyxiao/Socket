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

import com.psy.entity.SameGroup;
import com.psy.servlet.Slf4j;
import com.psy.util.Constants;
import com.psy.util.StringHelper;

 
public class TeaService {
	/** 
	 *  logger  
	 *  TODO(引入slf4j日志)
	*/
	public static final Logger logger = LoggerFactory.getLogger(Slf4j.class);
	
	/**
	 * TODO(描述这个方法的作用)
	 * @param session
	 * @param type
	*/
	public static void sendAllStudent(IoSession session,String type){
		String tea_paduuid = session.getAttribute(Constants.PADUUID,"NONE").toString();
		String tea_randnum = session.getAttribute(Constants.RANDNUM,"NONE").toString();
		String tea_question = session.getAttribute(Constants.QUES,"NONE").toString();
		String tea_answer = session.getAttribute(Constants.ANS,"NONE").toString();
		String tea_logintype = session.getAttribute(Constants.LOGINTYPE,"NONE").toString();
		String tea_logininfo = session.getAttribute(Constants.LOGININFO,"NONE").toString();
		String tea_name = session.getAttribute(Constants.NAME,"NONE").toString();
		String tea_no = session.getAttribute(Constants.NO,"NONE").toString();
		String tea_assess = session.getAttribute(Constants.ASSESS,"NONE").toString();
		String tea_degree = session.getAttribute(Constants.DEGREE,"NONE").toString();
		String tea_peerass = session.getAttribute(Constants.PEERASS,"NONE").toString();
		String ans_uuid = session.getAttribute(Constants.ANSUUID,"NONE").toString();
		String ans_no = session.getAttribute(Constants.ANSNO,"NONE").toString();
		String single_ans = session.getAttribute(Constants.USUALANS,"NONE").toString();
		String group_num = session.getAttribute(Constants.GROUP,"NONE").toString();
		String role = "tea";
		String data = "HNZZ\t&TeaInfos\t&"
				+tea_paduuid+"\t&"
				+tea_randnum+"\t&"
				+tea_question+"\t&"
				+tea_answer+"\t&"
				+tea_logintype+"\t&"
				+tea_logininfo+"\t&"
				+tea_no+"\t&"
				+type+"\t&"
				+ans_uuid+"\t&"
				+single_ans+"\t&"
				+group_num+"\t&"
				+tea_name+"\t&"
				+"END\n";
	    logger.debug("sendAllStudent:"+data);
	    
	    if(type.equals("login")){
	    	session.write(data);
	    }else if(type.equals("usualAns")){
	    	//常规答题
	    	String[] ans_noArray = null;  
	    	ans_noArray = ans_no.split(",");
    		for (int i = 0; i < ans_noArray.length; i++) {
    			String stu_no = ans_noArray[i];
    			for (IoSession session_stu : Constants.sessionStus) {
	    			String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
	    			String ansStuNo = session_stu.getAttribute(Constants.NO,"NONE").toString();
			    	data = "HNZZ\t&TeaInfos\t&"
							+tea_paduuid+"\t&"
							+tea_randnum+"\t&"
							+tea_question+"\t&"
							+tea_answer+"\t&"
							+tea_logintype+"\t&"
							+ansStuNo+"\t&"
							+tea_no+"\t&"
							+type+"\t&"
							+ans_uuid+"\t&"
							+single_ans+"\t&"
							+group_num+"\t&"
							+tea_name+"\t&"
							+"END\n";
					if(randStu.equals(tea_randnum)){
						session_stu.write(data);
						session.write(data);
					}
	    		}
			}
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
							+tea_no+"\t&"
							+type+"\t&"
							+ans_uuid+"\t&"
							+single_ans+"\t&"
							+group_num+"\t&"
							+tea_name+"\t&"
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
		    			String ansStuUuid = session_stu.getAttribute(Constants.PADUUID,"NONE").toString();
				    	data = "HNZZ\t&TeaInfos\t&"
								+tea_paduuid+"\t&"
								+tea_randnum+"\t&"
								+tea_question+"\t&"
								+tea_answer+"\t&"
								+tea_logintype+"\t&"
								+tea_logininfo+"\t&"
								+tea_no+"\t&"
								+type+"\t&"
								+ans_uuid+"\t&"
								+single_ans+"\t&"
								+group_num+"\t&"
								+tea_name+"\t&"
								+"END\n";
						if(randStu.equals(tea_randnum)&&stu_uuid.equals(ansStuUuid)){
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
							+tea_no+"\t&"
							+type+"\t&"
							+stu_uuid+"\t&"
							+single_ans+"\t&"
							+group_num+"\t&"
							+tea_name+"\t&"
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
							+tea_no+"\t&"
							+type+"\t&"
							+ans_uuid+"\t&"
							+single_ans+"\t&"
							+group_num+"\t&"
							+tea_name+"\t&"
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
								+tea_no+"\t&"
								+type+"\t&"
								+ans_uuid+"\t&"
								+single_ans+"\t&"
								+group_num+"\t&"
								+tea_name+"\t&"
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
							+tea_no+"\t&"
							+type+"\t&"
							+stu_uuid+"\t&"
							+single_ans+"\t&"
							+group_num+"\t&"
							+tea_name+"\t&"
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
								+tea_no+"\t&"
								+type+"\t&"
								+ans_uuid+"\t&"
								+single_ans+"\t&"
								+group_num+"\t&"
								+tea_name+"\t&"
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
							+tea_no+"\t&"
							+type+"\t&"
							+ans_uuid+"\t&"
							+single_ans+"\t&"
							+group_num+"\t&"
							+tea_name+"\t&"
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
								+tea_no+"\t&"
								+type+"\t&"
								+ans_uuid+"\t&"
								+single_ans+"\t&"
								+group_num+"\t&"
								+tea_name+"\t&"
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
							+tea_no+"\t&"
							+type+"\t&"
							+stu_uuid+"\t&"
							+single_ans+"\t&"
							+group_num+"\t&"
							+tea_name+"\t&"
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
								+tea_no+"\t&"
								+type+"\t&"
								+ans_uuid+"\t&"
								+single_ans+"\t&"
								+group_num+"\t&"
								+tea_name+"\t&"
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
	    	tea_question = session.getAttribute(Constants.ESTIMATE,"NONE").toString();
	    	String ans_nos = session.getAttribute(Constants.ANSNO,"NONE").toString();
	    	String[] ansNos = null;  
			ansNos = ans_nos.split(",");
			//临时Set集合
			for(int i = 0; i < ansNos.length; i++) {
				String ansNo1 = ansNos[i];
				String ansName = "";
				//获取答题人姓名
				for (IoSession session_stu : Constants.sessionStus) {
					String stuNo = session_stu.getAttribute(Constants.NO,"NONE").toString();
					if(ansNo1.equals(stuNo)){
						ansName = session_stu.getAttribute(Constants.NAME,"NONE").toString();
					}
				}
				single_ans = ansName;
				for (IoSession session_stu : Constants.sessionStus) {
					ans_uuid = session_stu.getAttribute(Constants.ANSUUID,"NONE").toString();
					String pad_uuid = session_stu.getAttribute(Constants.PADUUID,"NONE").toString();
			    	ans_no = session_stu.getAttribute(Constants.ANSNO,"NONE").toString();
					String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
					String stuNo = session_stu.getAttribute(Constants.NO,"NONE").toString();
					if(randStu.equals(tea_randnum)){
						//判断是否为答题者
				    	if(ansNo1.equals(stuNo)){
				    		ans_uuid = pad_uuid;
				    		ans_no = stuNo;
						}
				    	data = "HNZZ\t&TeaInfos\t&"
								+tea_paduuid+"\t&"
								+tea_randnum+"\t&"
								+tea_question+"\t&"
								+tea_answer+"\t&"
								+tea_logintype+"\t&"
								+tea_logininfo+"\t&"
								+tea_no+"\t&"
								+type+"\t&"
								+ans_uuid+"\t&"
								+single_ans+"\t&"
								+group_num+"\t&"
								+tea_name+"\t&"
								+ans_no+"\t&"
								+"END\n";
				    	if(ansNo1.equals(stuNo)||ansNo1.equals(ans_no)){
				    		session_stu.write(data);
							session.write(data);
				    	}
					}
				}
			}
			
	    }else if(type.equals("group")||type.equals("subject")||type.equals("sex")){
	    	//发送给全部学生
			for (IoSession session_stu : Constants.sessionStus) {
				String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
				if(randStu.equals(tea_randnum)){
					String ans_uuid1 = session_stu.getAttribute(Constants.PADUUID,"NONE").toString();
					group_num = session_stu.getAttribute(Constants.GROUP,"NONE").toString();
					String stuNo = session_stu.getAttribute(Constants.NO,"NONE").toString();
					SameGroup sameGroup = TeacherService.sameGroupInfo(randStu, group_num, stuNo,type);
					String userNos = sameGroup.getUserNos();
					single_ans = sameGroup.getUserNames();
					System.out.println("同组人员："+single_ans);
					String userUuids = sameGroup.getUserUuids();
			    	data = "HNZZ\t&TeaInfos\t&"
							+tea_paduuid+"\t&"
							+tea_randnum+"\t&"
							+tea_question+"\t&"
							+tea_answer+"\t&"
							+tea_logintype+"\t&"
							+tea_logininfo+"\t&"
							+tea_no+"\t&"
							+type+"\t&"
							+ans_uuid1+"\t&"
							+single_ans+"\t&"
							+group_num+"\t&"
							+tea_name+"\t&"
							+userNos+"\t&"
							+userUuids+"\t&"
							+"END\n";
			    	session.write(data);
					session_stu.write(data);
				}
			}
	    }else if(type.equals("picurl")){
	    	single_ans = session.getAttribute(Constants.OPENPARAM,"NONE").toString();
	    	data = "HNZZ\t&TeaInfos\t&"
					+tea_paduuid+"\t&"
					+tea_randnum+"\t&"
					+tea_question+"\t&"
					+tea_answer+"\t&"
					+tea_logintype+"\t&"
					+tea_logininfo+"\t&"
					+tea_no+"\t&"
					+type+"\t&"
					+ans_uuid+"\t&"
					+single_ans+"\t&"
					+group_num+"\t&"
					+tea_name+"\t&"
					+"END\n";
	    	//发送给全部学生
			for (IoSession session_stu : Constants.sessionStus) {
				String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
				if(randStu.equals(tea_randnum)){
					session_stu.write(data);
				}
			}
	    }else if(type.equals("ques")){
			//问题发送给全部学生
			for (IoSession session_stu : Constants.sessionStus) {
				String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
				if(randStu.equals(tea_randnum)){
					session_stu.setAttribute(Constants.QUES,tea_question);
					session_stu.write(data);
					logger.debug("提问发送的数据："+data);
				}
			}
	    }else if(type.equals("teaCount")){
	    	//本节课数据总量
	    	String count1 = TeaCountService.searchNums(tea_randnum);
//	    	//小于count1
//	    	int count2 = TeaCountService.searchLessNum(count1,"00A");
//	    	//本节课数据总量
//	    	int count3 = TeaCountService.searchLessNum("","");
//	    	//本节课活跃度
//	    	String score = StringHelper.divide(count2, count3);
	    	//最活跃的
	    	String stuName1 = TeaCountService.searchStuNames(tea_randnum, "00A");
	    	//需观察
	    	String stuName2 = TeaCountService.searchStuNames(tea_randnum, "00B");
	    	data = "HNZZ\t&TeaInfos\t&"
					+tea_paduuid+"\t&"
					+tea_randnum+"\t&"
					+stuName1+"\t&"
					+stuName2+"\t&"
					+count1+"\t&"
					+tea_logininfo+"\t&"
					+tea_no+"\t&"
					+type+"\t&"
					+ans_uuid+"\t&"
					+single_ans+"\t&"
					+group_num+"\t&"
					+tea_name+"\t&"
					+"END\n";
	    	//问题发送给全部学生
			for (IoSession session_stu : Constants.sessionStus) {
				String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
				if(randStu.equals(tea_randnum)){
					session_stu.write(data);
				}
			}
			session.write(data);
			logger.debug(tea_randnum+"本节课教师端统计的数据："+data);
	    }else{
	    	//发送给全部学生
			for (IoSession session_stu : Constants.sessionStus) {
				String randStu = session_stu.getAttribute(Constants.RANDNUM,"NONE").toString();
				if(randStu.equals(tea_randnum)){
					session_stu.setAttribute(Constants.QUES,tea_question);
					session_stu.write(data);
					logger.debug("保存服务端发送的数据："+data);
				}
			}
	    }
    	Boolean isUp = RecordService.insertData(tea_name, tea_no, role, tea_randnum, tea_paduuid, ans_uuid, ans_no, type, tea_question, tea_answer, tea_assess, tea_degree,tea_peerass);
	    if(isUp){
	    	logger.debug("保存老师数据成功！");
	    }else{
	    	logger.error("保存老师数据失败！");
	    }
	    if(tea_question.equals(101)){
	    	session.removeAttribute(Constants.QUES);
	    }
		session.removeAttribute(Constants.ANS);
	}
	
}
