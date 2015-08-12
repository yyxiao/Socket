/**
 * AnsServer.java
 * com.psy.util
 * author      date      	
 * ──────────────────────────────────
 * xiao    2015年7月17日 		
 * Copyright (c)2015, All Rights Reserved.
 * Java源代码,未经许可禁止任何人、任何组织通过任何
 * 渠道使用、修改源代码.
*/
package com.psy.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

 
/**
 * ClassName:AnsServer
 *
 * TODO(监听Server)
 *
 * @project ans
 *
 * @author xiao
 *
 * @date   2015年7月17日 上午11:10:28	
 *
 * @class com.psy.util.AnsServer
 *
 */ 
public class AnsServer implements ServletContextListener{
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		event.getServletContext().log("监听结束"); 
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// 创建一个非阻塞的server端的Socket
		IoAcceptor acceptor = new NioSocketAcceptor();
		//设置主服务监听的端口可以重用
//		((NioSocketAcceptor)acceptor).setReuseAddress(true);
		event.getServletContext().log("监听启动");
		//加入过滤器（Filter）到Acceptor 
		LoggingFilter loggingFilter = new LoggingFilter();
		loggingFilter.setSessionCreatedLogLevel(LogLevel.NONE);// 一个新的session被创建时触发  
        loggingFilter.setSessionOpenedLogLevel(LogLevel.NONE);// 一个新的session打开时触发  
        loggingFilter.setSessionClosedLogLevel(LogLevel.NONE);// 一个session被关闭时触发  
        loggingFilter.setMessageReceivedLogLevel(LogLevel.NONE);// 接收到数据时触发  
        loggingFilter.setMessageSentLogLevel(LogLevel.NONE);// 数据被发送后触发  
        loggingFilter.setSessionIdleLogLevel(LogLevel.INFO);// 一个session空闲了一定时间后触发  
        loggingFilter.setExceptionCaughtLogLevel(LogLevel.INFO);// 当有异常抛出时触发  
		
        acceptor.getFilterChain().addLast("logger", loggingFilter);
        //UTF-8,new ProtocolCodecFilter(new TextLineCodecFactory())，这个过滤器的作用是将来自客户端输入的信息转换成一行行的文本后传递给IoHandler
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        //载入mina处理类
        acceptor.setHandler(new AnsHandler());
        //配置缓冲区大小
		acceptor.getSessionConfig().setReadBufferSize(2048); 
		//读写 通道均在10 秒内无任何操作就进入空闲状态  
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		try {
			acceptor.bind(new InetSocketAddress(Constants.PORT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
