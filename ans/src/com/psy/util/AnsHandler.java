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
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.psy.service.GroupService;
import com.psy.service.RecordService;
import com.psy.service.StuService;
import com.psy.service.TeaService;
import com.psy.servlet.Slf4j;
import com.psy.servlet.UploadServlet;

 
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
	public static final Logger logger = LoggerFactory.getLogger(Slf4j.class);
	
	
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
		logger.debug("新连接建立");
		Date nowTime = new Date();
		String time = dateFormat.format(nowTime);
		logger.debug("sessionCreated:"+time+"=IDLE " +"sessionCreated" + session.getId());
		super.sessionCreated(session);
	}
	
	/**
	 * TODO(当有新的连接打开的时候，该方法被调用。该方法在 sessionCreated之后被调用。)
	 */
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.debug("新连接打开");
		session.setAttribute("opentime", dateFormat.format(new Date()));
		Constants.sessions.add(session);
		Date nowTime = new Date();
		String time = dateFormat.format(nowTime);
		logger.debug("sessionOpened="+time);
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
		logger.debug("收到新消息");
		String str = message.toString();
		String[] messageArr = str.split("\t&");
		logger.debug(messageArr[2]+messageArr[3]+messageArr[4]+messageArr[5]+messageArr[6]);
		if(messageArr[0].equals("HNZZ")){
			String type = messageArr[2];
			if(messageArr[1].equals("stu")){
				if(type.equals("login")){
					for(IoSession sessionStu:Constants.sessionStus){
						String paduuid = sessionStu.getAttribute(Constants.PADUUID,"NONE").toString();
						if(messageArr[5].equals(paduuid)){
							Constants.sessions.remove(sessionStu);
							Constants.sessionStus.remove(sessionStu);
							logger.debug("移除Constants.sessions中已有session"+paduuid);
						}
					}
					session.setAttribute(Constants.ROLE,"stu");
					session.setAttribute(Constants.NAME, messageArr[6]);
					session.setAttribute(Constants.PADUUID, messageArr[5]);
					session.setAttribute(Constants.RANDNUM, messageArr[4]);
					Constants.sessionStus.add(session);
				}else if(type.equals("ans")){
					session.setAttribute(Constants.ANS,messageArr[3]);
				}else if(type.equals("singleAns")){
					session.setAttribute(Constants.SINGLEANS,messageArr[3]);
				}else if(type.equals("degree")){
					session.setAttribute(Constants.DEGREE,messageArr[3]);
				}
				StuService.sendToTeacher(session,type);
			}else if(messageArr[1].equals("tea")){
				if(type.equals("login")){
					Boolean bl = true;
					for(IoSession sessionTea:Constants.sessionTeas){
						String paduuid = sessionTea.getAttribute(Constants.PADUUID,"NONE").toString();
						String randnum = sessionTea.getAttribute(Constants.RANDNUM,"NONE").toString();
						if(messageArr[5].equals(paduuid)){
							bl = false;
							session.setAttribute(Constants.RANDNUM,randnum);
							Constants.sessions.remove(sessionTea);
							Constants.sessionTeas.remove(sessionTea);
							logger.debug("rand:"+randnum);
						}
					}
					session.setAttribute(Constants.ROLE,"tea");
					session.setAttribute(Constants.NAME, messageArr[6]);
					session.setAttribute(Constants.PADUUID, messageArr[5]);
					if(bl){
						String rand = RandomCode.NextInt(100000,999999);
						session.setAttribute(Constants.RANDNUM,rand);
						logger.debug("rand:"+rand);
					}
					session.setAttribute(Constants.LOGINTYPE,"success");
					session.setAttribute(Constants.LOGININFO,"登录成功!");
					logger.debug("教师端登录成功!");
				}else if(type.equals("ques")&&messageArr[3]!="101"){
					session.setAttribute(Constants.QUES,messageArr[3]);
				}else if(type.equals("ans")){
					session.setAttribute(Constants.ANS,messageArr[3]);
				}else if(type.equals("exit")){
					session.setAttribute(Constants.LOGINTYPE,type);
				}else if(type.equals("singleAns")){
					session.setAttribute(Constants.SINGLEANSUUID,messageArr[3]);
				}else if(type.equals("singleUpload")){
					session.setAttribute(Constants.SINGLEUPUUID,messageArr[3]);
				}else if(type.equals("degree")){
					session.setAttribute(Constants.DEGREE,messageArr[3]);
				}else if(type.equals("group")){
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
				}else if(type.equals("count")){
					session.setAttribute(Constants.COUNT,messageArr[3]);
				}
				Constants.sessionTeas.add(session);
				TeaService.sendAllStudent(session,type);
			}
		}
		logger.debug(Constants.sessionTeas.size()+"@@"+Constants.sessions.size());
	}
	
	
	/**
	 * TODO(当连接被关闭的时候，此方法被调用。)
	 */
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		String role = session.getAttribute(Constants.ROLE,"NONE").toString();
		String loginType = session.getAttribute(Constants.LOGINTYPE,"NONE").toString();
		logger.debug("连接被关闭");
		if ("stu".equals(role)) {
			StuService.outStu(session);
			Constants.sessionStus.remove(session);
			logger.debug("移除前所有连接:" + Constants.sessions.size());
			Constants.sessions.remove(session);
			logger.debug("移除后所有连接:" + Constants.sessions.size());
			session.close(true);
		}else if ("tea".equals(role)) {
			if("exit".equals(loginType)){
				Constants.sessionTeas.remove(session);
				Constants.randList.remove(session.getAttribute(Constants.RANDNUM));
				logger.debug("移除前所有连接:" + Constants.sessions.size());
				Constants.sessions.remove(session);
				logger.debug("移除后所有连接:" + Constants.sessions.size());
				session.close(true);
			}
		}
	}

	/**
	 * TODO(当连接变成闲置状态的时候，此方法被调用)
	 */
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		logger.debug("连接闲置");
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
