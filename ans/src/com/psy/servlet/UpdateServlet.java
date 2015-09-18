package com.psy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.org.mozilla.javascript.internal.json.JsonParser;

import com.psy.entity.Versions;
import com.psy.service.VersionsService;
import com.psy.util.StringHelper;
import com.sun.xml.internal.ws.message.StringHeader;

/**
 * Servlet implementation class UpdateServlet
 */
public class UpdateServlet extends HttpServlet {
	public static final Logger logger = LoggerFactory.getLogger(UpdateServlet.class);
	private static final long serialVersionUID = 1L;
	private JSONObject jsonObject;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
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
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String school = request.getParameter("school");
		String type = request.getParameter("type");
		
		if(!StringHelper.isEmptyObject(school)&&!StringHelper.isEmptyObject(type)){
			Versions versions = VersionsService.findVersions(school, type);
			jsonObject = JSONObject.fromObject(versions);
			
			out.print(jsonObject.toString());
		}
	}

}
