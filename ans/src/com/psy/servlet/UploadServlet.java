package com.psy.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.psy.util.Constants;

/**
 * ClassName:UploadServlet
 *
 * TODO(上传图片Servlet)
 *
 * @project ans
 *
 * @author xiao
 *
 * @date   2015年8月6日 下午4:14:50	
 *
 * @class com.psy.servlet.UploadServlet
 *
 */ 
public class UploadServlet extends HttpServlet {
	/** 
	 *  logger  
	 *  TODO(引入slf4j日志)
	*/
	public static final Logger logger = LoggerFactory.getLogger(Slf4j.class);
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer=response.getWriter();
		
		String randnum = request.getParameter("randnum");
		String stu_uuid = request.getParameter("stu_uuid");
		String tea_uuid = request.getParameter("tea_uuid");
		
		String fileUrl = "";
		
		//判断本次表单是否是一个multipart表单
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if(isMultipart){			
			//文件保存路径
			String savePath = Constants.UPLOADURL + "/" + randnum;
			File saveFile=new File(savePath);
			//不存在以上路径则创建
			if(!saveFile.exists()){  
				saveFile.mkdirs();  
	        }  
			//获取工厂对象
			DiskFileItemFactory factory=new DiskFileItemFactory();
			//产生servlet上传对象
			ServletFileUpload uploader=new ServletFileUpload(factory);
			//处理中文问题
			uploader.setHeaderEncoding("UTF-8");  
			//设置上传文件的最大大小，位置字节
			uploader.setSizeMax(4*1024*1024);
			//获取表单项
			try {
				List<FileItem> fileItems = uploader.parseRequest(request);
				for (FileItem item : fileItems) {
					System.out.println(item.isFormField());
					//判断表单项是普通字段还是上传项
					if(item.isFormField()){
						logger.debug("处理表单内容 ...");
					}else{
						logger.debug("处理上传的文件 ...");
						//上传项目
						String filename = item.getName();
						int index = filename.lastIndexOf("\\");    
				        filename = filename.substring(index + 1, filename.length());    
				   
				        long fileSize = item.getSize();    
				   
				        if("".equals(filename) && fileSize == 0){    
				        	logger.debug("文件名为空 ...");
				            return;    
				        }
				        //文件存储地址
				        String filePath = savePath + "/" + filename;
				        //图片显示虚拟地址
				        fileUrl = Constants.FILEURL + randnum + "/" + filename;
				        System.out.println(filePath);
				        System.out.println(fileUrl);
				        File uploadFile = new File(filePath);    
				        if(!uploadFile.exists()){  
				            uploadFile.createNewFile();  
				        }  
				        item.write(uploadFile);    
					}
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String data = "";
		
		logger.debug("发送具体地址给客户端！");
		for(IoSession sessionStu:Constants.sessionStus){
			String stu_paduuid = sessionStu.getAttribute(Constants.PADUUID,"NONE").toString();
			if(stu_uuid.equals(stu_paduuid)){
				data = "HNZZ\t&StuInfos\t&"
						+stu_paduuid+"\t&"
						+randnum+"\t&"
						+fileUrl+"\t&"
						+tea_uuid+"\t&"
						+stu_uuid+"\t&"
						+"END\n";
				sessionStu.write(data);
			}
			logger.debug("上传图片发送学生端数据："+data);
		}
		for(IoSession sessionTea:Constants.sessionTeas){
			String tea_paduuid = sessionTea.getAttribute(Constants.PADUUID,"NONE").toString();
			if(tea_uuid.equals(tea_paduuid)){
				data = "HNZZ\t&StuInfos\t&"
						+tea_paduuid+"\t&"
						+randnum+"\t&"
						+fileUrl+"\t&"
						+tea_uuid+"\t&"
						+stu_uuid+"\t&"
						+"END\n";
				sessionTea.write(data);
			}
			logger.debug("上传图片发送教师端数据："+data);
		}
		
	}

}
