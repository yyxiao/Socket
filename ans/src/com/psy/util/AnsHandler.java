/**
 * AnsHandler.java
 * com.psy.util
 * author      date      	
 * ──────────────────────────────────
 * xiao    2015年7月17日 		
 * Copyright (c)2015, All Rights Reserved.
 * Java源代码,未经许可禁止任何人、任何组织通过任何
 * 渠道使用、修改源代码.
*/
package com.psy.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.psy.entity.Student;
import com.psy.entity.Teacher;
import com.psy.service.GroupService;
import com.psy.service.StuService;
import com.psy.service.StudentService;
import com.psy.service.TeaService;
import com.psy.service.TeacherService;

 
/**
 * ClassName:AnsHandler
 *
 * TODO(mina处理程序Handler)
 *
 * @project ans
 *
 * @author xiao
 *
 * @date   2015年7月17日 下午4:22:44	
 *
 * @class com.psy.util.AnsHandler
 *
 */ 
public class AnsHandler extends IoHandlerAdapter{
	
	/** 
	 *  logger  
	 *  TODO(引入slf4j日志)
	*/
	public static final Logger logger = LoggerFactory.getLogger(AnsHandler.class);
	
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
//	// 所有客户连接池列表
//	public static Set<IoSession> sessions = Collections.synchronizedSet(new HashSet<IoSession>());
//
//	public static Set<IoSession> sessionStus = Collections.synchronizedSet(new HashSet<IoSession>());
//	public static Set<IoSession> sessionTeas = Collections.synchronizedSet(new HashSet<IoSession>());
	
	/**
	 * TODO(当有新的连接建立的时候，该方法被调用。)
	 */
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.info("新连接建立");
		Date nowTime = new Date();
		String time = dateFormat.format(nowTime);
		logger.info("sessionCreated:"+time+"=IDLE " +"sessionCreated" + session.getId());
		super.sessionCreated(session);
	}
	
	/**
	 * TODO(当有新的连接打开的时候，该方法被调用。该方法在 sessionCreated之后被调用。)
	 */
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.info("新连接打开");
		session.setAttribute("opentime", dateFormat.format(new Date()));
		Constants.sessions.add(session);
		Date nowTime = new Date();
		String time = dateFormat.format(nowTime);
		logger.info("sessionOpened="+time);
	}

	/**
	 * TODO(当接收到新的消息的时候，此方法被调用。)
	 * messageArr[0]学校
	 * messageArr[1]角色
	 * messageArr[2]操作类型
	 * messageArr[3]答案、题型
	 * messageArr[4]随机码
	 * messageArr[5]pad标识码
	 * messageArr[6]学生、老师姓名
	 */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		try {
			logger.info("收到新消息");
			String str = message.toString();
			logger.debug("接收数据："+str);
			String[] messageArr = str.split("\t&");
			logger.debug("接收数据长度："+messageArr.length);
			logger.debug(messageArr[2]+messageArr[3]+messageArr[4]+messageArr[5]+"--6--"+messageArr[6]);
			if(messageArr[0].equals("HNZZ")){
				String type = messageArr[2];
				if(messageArr[1].equals("stu")){
					//学生端数据解析
					if(type.equals("login")){
						//需要删除的ioSession列表
						List<IoSession> delList = new ArrayList<IoSession>();
						for(IoSession sessionStu:Constants.sessionStus){
							String paduuid = sessionStu.getAttribute(Constants.PADUUID,"NONE").toString();
							if(messageArr[5].equals(paduuid)){
								delList.add(sessionStu);
//								Constants.sessions.remove(sessionStu);
//								Constants.sessionStus.remove(sessionStu);
								logger.debug("移除Constants.sessions中已有session"+paduuid);
							}
						}
						Constants.sessions.removeAll(delList);
						Constants.sessionStus.removeAll(delList);
						String stuNo = messageArr[6];
						if(!StringHelper.isEmptyObject(stuNo)){
							Student student = StudentService.searchData(stuNo);
							int sex = Integer.valueOf(student.getSex());
							int sexgroup = sex+1;
							session.setAttribute(Constants.SEX,student.getSex());
							session.setAttribute(Constants.SEXGROUP,String.valueOf(sexgroup));
							session.setAttribute(Constants.NAME, student.getUserName());
							session.setAttribute(Constants.YUWEN, student.getYuwen());
							session.setAttribute(Constants.SHUXUE, student.getShuxue());
							session.setAttribute(Constants.YINGYU, student.getYingyu());
						}
						session.setAttribute(Constants.ROLE,"stu");
						session.setAttribute(Constants.NO, messageArr[6]);
						session.setAttribute(Constants.PADUUID, messageArr[5]);
						session.setAttribute(Constants.RANDNUM, messageArr[4]);
						Constants.sessionStus.add(session);
					}else if(type.equals("ans")){
						session.setAttribute(Constants.ANS,messageArr[3]);
					}else if(type.equals("handsup")){
						//学生举手
						session.setAttribute(Constants.HANDSUP,messageArr[3]);
					}else if(type.equals("usualAns")){
						session.setAttribute(Constants.USUALANS,messageArr[3]);
					}else if(type.equals("degree")){
						session.setAttribute(Constants.DEGREE,messageArr[3]);
					}else if(type.equals("estimate")){
						//常规答题评价
						session.setAttribute(Constants.ANSUUID,messageArr[8]);
						session.setAttribute(Constants.PEERASS,messageArr[3]);
						session.setAttribute(Constants.ANSNO,messageArr[9]);
					}else if(type.equals("assess")){
						//通用评价(接受上传评价)
						session.setAttribute(Constants.ANSUUID,messageArr[8]);
						session.setAttribute(Constants.ASSESS,messageArr[3]);
						session.setAttribute(Constants.ANSNO,messageArr[9]);
					}else if(type.equals("peerAss")){
						//学生互评（撒花，鸡蛋）
						session.setAttribute(Constants.ANSUUID,messageArr[8]);
						session.setAttribute(Constants.PEERASS,messageArr[3]);
						session.setAttribute(Constants.ANSNO,messageArr[9]);
					}else if(type.equals("groupAss")){
						//同组互评（撒花，鸡蛋）
						session.setAttribute(Constants.ANSUUID,messageArr[8]);
						session.setAttribute(Constants.PEERASS,messageArr[3]);
						session.setAttribute(Constants.ANSNO,messageArr[9]);
					}else if(type.equals("stuCount")){
						//学生统计信息
						session.setAttribute(Constants.STUCOUNT,messageArr[3]);
					}
					StuService.sendToTeacher(session,type);
				}else if(messageArr[1].equals("tea")){
					//教师端数据解析
					if(type.equals("login")){
						Boolean bl = true;
						List<IoSession> delList = new ArrayList<IoSession>();
						for(IoSession sessionTea:Constants.sessionTeas){
							String paduuid = sessionTea.getAttribute(Constants.PADUUID,"NONE").toString();
							String randnum = sessionTea.getAttribute(Constants.RANDNUM,"NONE").toString();
							if(messageArr[5].equals(paduuid)){
								bl = false;
								session.setAttribute(Constants.RANDNUM,randnum);
								delList.add(sessionTea);
//								Constants.sessions.remove(sessionTea);
//								Constants.sessionTeas.remove(sessionTea);
								logger.debug("rand:"+randnum);
							}
						}
						Constants.sessions.removeAll(delList);
						Constants.sessionTeas.removeAll(delList);
						session.setAttribute(Constants.ROLE,"tea");
						session.setAttribute(Constants.NO, messageArr[6]);
						session.setAttribute(Constants.PADUUID, messageArr[5]);
						if(bl){
							String rand = RandomCode.NextInt(1000,9999);
							session.setAttribute(Constants.RANDNUM,rand);
							logger.debug("rand:"+rand);
						}
						//查询教师详细信息
						Teacher tea = TeacherService.searchSub(messageArr[6]);
						session.setAttribute(Constants.NAME, tea.getTeaName());
						session.setAttribute(Constants.LOGINTYPE,"success");
						session.setAttribute(Constants.LOGININFO,"登录成功!");
						logger.debug("教师端登录成功!");
					}else if(type.equals("ques")){
						session.setAttribute(Constants.QUES,messageArr[3]);
					}else if(type.equals("ans")){
						session.setAttribute(Constants.ANS,messageArr[3]);
						session.setAttribute(Constants.SELUUID,messageArr[8]);
						session.setAttribute(Constants.STUTYPE,messageArr[7]);
						session.setAttribute(Constants.ANSNO,messageArr[9]);
					}else if(type.equals("exit")){
						session.setAttribute(Constants.LOGINTYPE,type);
					}else if(type.equals("usualAns")){
						session.setAttribute(Constants.ANSUUID,messageArr[8]);
						session.setAttribute(Constants.ANSNO,messageArr[9]);
					}else if(type.equals("upload")){
						System.out.println("--7--"+messageArr[7]);
						session.setAttribute(Constants.UPUUID,messageArr[3]);
						session.setAttribute(Constants.STUTYPE,messageArr[7]);
					}else if(type.equals("singleSel")){
						System.out.println("--8--"+messageArr[8]);
						session.setAttribute(Constants.QUES,messageArr[3]);
						session.setAttribute(Constants.SELUUID,messageArr[8]);
						session.setAttribute(Constants.STUTYPE,messageArr[7]);
						session.setAttribute(Constants.ANSNO,messageArr[9]);
					}else if(type.equals("degree")){
						session.setAttribute(Constants.DEGREE,messageArr[3]);
					}else if(type.equals("handsup")){
						session.setAttribute(Constants.HANDSUP,messageArr[3]);
					}else if(type.equals("assess")){
						//通用评价(接受上传评价)
						session.setAttribute(Constants.ANSUUID,messageArr[8]);
						session.setAttribute(Constants.ASSESS,messageArr[3]);
						session.setAttribute(Constants.ANSNO,messageArr[9]);
					}else if(type.equals("estimate")){
						//常规答题评价
						session.setAttribute(Constants.ESTIMATE,messageArr[3]);
						session.setAttribute(Constants.ANSUUID,messageArr[8]);
						session.setAttribute(Constants.ANSNO,messageArr[9]);
						String ansNo = session.getAttribute(Constants.ANSNO,"NONE").toString();
						String ansUuid = session.getAttribute(Constants.ANSUUID,"NONE").toString();
						//进行答题分组
						GroupService.giveAnsGroup(session,ansNo,ansUuid);
					}else if(type.equals("group")){
						//随机分组
						int group = Integer.valueOf(messageArr[3]);
						String randNo = messageArr[4];
						session.setAttribute(Constants.GROUP,messageArr[3]);
						Set<IoSession> sessionStus1 = Collections.synchronizedSet(new HashSet<IoSession>());
						for(IoSession stu:Constants.sessionStus){
							String randNo1 = stu.getAttribute(Constants.RANDNUM,"NONE").toString();
							if(randNo.equals(randNo1)){
								sessionStus1.add(stu);
							}
						}
						//进行分组
						GroupService.giveGroup(sessionStus1,group);
					}else if(type.equals("subject")){
						//科目成绩分组
						int group = Integer.valueOf(messageArr[3]);//几组
						String randNo = messageArr[4];
						session.setAttribute(Constants.GROUP,messageArr[3]);
						Set<IoSession> sessionStus1 = Collections.synchronizedSet(new HashSet<IoSession>());
						for(IoSession stu:Constants.sessionStus){
							String randNo1 = stu.getAttribute(Constants.RANDNUM,"NONE").toString();
							if(randNo.equals(randNo1)){
								sessionStus1.add(stu);
							}
						}
						//查询教师的科目类型
						Teacher tea = TeacherService.searchSub(messageArr[6]);
						//进行分组
						GroupService.giveSubGroup(sessionStus1,group,tea.getSubject());
					}else if(type.equals("sex")){
						//性别分组
						String randNo = messageArr[4];
						for(IoSession stu:Constants.sessionStus){
							String randNo1 = stu.getAttribute(Constants.RANDNUM,"NONE").toString();
							if(randNo.equals(randNo1)){
								String groupsex = stu.getAttribute(Constants.SEXGROUP,"NONE").toString();
								stu.setAttribute(Constants.GROUP,groupsex);
							}
						}
					}else if(type.equals("teaCount")){
						//教师统计信息
						
					}else{
						session.setAttribute(Constants.OPENPARAM,messageArr[3]);
					}
					Constants.sessionTeas.add(session);
					TeaService.sendAllStudent(session,type);
				}
			}
			logger.info(Constants.sessionTeas.size()+"@@"+Constants.sessions.size());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("socket收到新信息出错："+StringHelper.getTrace(e));
		}
	}
	
	
	/**
	 * TODO(当连接被关闭的时候，此方法被调用。)
	 */
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		try {
			List<IoSession> delList = new ArrayList<IoSession>();
			String uuid = session.getAttribute(Constants.PADUUID,"NONE").toString();
			String role = session.getAttribute(Constants.ROLE,"NONE").toString();
			String loginType = session.getAttribute(Constants.LOGINTYPE,"NONE").toString();
			logger.info("连接被关闭");
			if ("stu".equals(role)) {
				StuService.outStu(session);
				for(IoSession sessionTea:Constants.sessionTeas){
					String paduuid = sessionTea.getAttribute(Constants.PADUUID,"NONE").toString();
					if(uuid.equals(paduuid)){
						delList.add(sessionTea);
						logger.debug("移除session");
					}
				}
				logger.debug("移除前所有连接:" + Constants.sessions.size());
				Constants.sessionStus.removeAll(delList);
				Constants.sessions.removeAll(delList);
				logger.debug("移除后所有连接:" + Constants.sessions.size());
				session.close(true);
			}else if ("tea".equals(role)) {
				if("exit".equals(loginType)){
					for(IoSession sessionTea:Constants.sessionTeas){
						String paduuid = sessionTea.getAttribute(Constants.PADUUID,"NONE").toString();
						if(uuid.equals(paduuid)){
							delList.add(sessionTea);
							logger.debug("移除session");
						}
					}
					logger.debug("移除前所有连接:" + Constants.sessions.size());
					Constants.sessions.removeAll(delList);
					Constants.randList.remove(session.getAttribute(Constants.RANDNUM));
					Constants.sessionTeas.removeAll(delList);
					Constants.randList.remove(uuid);
					logger.debug("移除后所有连接:" + Constants.sessions.size());
					session.close(true);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("socket连接被关闭的时出错："+StringHelper.getTrace(e));
		}
	}

	/**
	 * TODO(当连接变成闲置状态的时候，此方法被调用)
	 */
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
//		logger.info("连接闲置");
//		System.out.println("IDLE sessionIdle " 
//				//sessionIdle被触发的次数
//				+ session.getIdleCount(status) + " !! "
//				+ session.getId() + " !! "
//				+ session.getAttribute("rand"));
	}
	
	/**
	 * TODO(当消息被成功发送出去的时候，此方法被调用)
	 */
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
	}
	
	/**
	 * TODO(当 I/O 处理器的实现或是 Apache MINA 中有异常抛出的时候，此方法被调用。)
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		logger.error("session have error and closed");
		cause.printStackTrace();
		sessionClosed(session);
	}
}
