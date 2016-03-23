package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;

import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;

import bean.StudentInfo;

public class changepwd extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public changepwd() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String result="ok";
		
		String userid=request.getParameter("userid");
		String pwd=request.getParameter("pwd");
		
		StudentInfo stu1=new StudentInfo();
		stu1.set_id(new ObjectId(userid));
		
		StudentInfo stu2=new StudentInfo();
		stu2.setPwd(pwd);
		
		try{
			DaoImpl.update(CreateQueryFromBean.EqualObj(stu1), stu2, false);
		}catch(Exception e){
			result="error";
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

	public void init() throws ServletException {
		// Put your code here
	}

}
