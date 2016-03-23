package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ykmaiz.mail.MailSupport;

import utils.Util;

public class getbackpwd extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public getbackpwd() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");//post 可以直接设置中文字符编号
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String result="error";
		
		String pwd=request.getParameter("pwd");
		String email=request.getParameter("email");
		String name="";
		try {
			name=Util.DoGetString(request.getParameter("name").toString());
		} catch (Exception e) {
			result="error";
		}
		
		String content="亲爱的"+name+":"+"您的密码为："+pwd+"请妥善保管～";
		String title="找回密码（校园时光官方邮件）";
		
		try{
			MailSupport mailSupport=new MailSupport("smtp.163.com","zhangyiming_klaus@163.com","553355",false);
			mailSupport.send("zhangyiming_klaus@163.com",email,title,content);
			result="ok";
		}catch(Exception e){
			
		}
		out.println(result);
		out.flush();
		out.close();
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
