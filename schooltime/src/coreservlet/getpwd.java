package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import staticData.StaticString;

import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import com.ykmaiz.mail.MailSupport;

import bean.StudentInfo;

public class getpwd extends HttpServlet {


	private static final long serialVersionUID = 1L;
	
	public getpwd() {
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
		String email=request.getParameter("email");
		String phone=request.getParameter("phone");
		
		
		StudentInfo student=new StudentInfo();
		student.setEmail(email);
		student.setPhone(phone);
		
		BasicDBObject pro=new BasicDBObject();
		pro.put(StaticString.StudentInfo_Name, 1);
		pro.put(StaticString.StudentInfo_Pwd, 1);
		pro.put(StaticString.StudentInfo_id, 0);
		
		try{
			
			String name="",pwd="";
			int type=0;
			
			MongoCursor<Document> cursor=DaoImpl.GetSelectCursor(StudentInfo.class, CreateQueryFromBean.EqualObj(student), pro);

			while(cursor.hasNext()){
				
				Document doc=cursor.next();
				type=1;
				name=doc.getString("Name");
				pwd=doc.getString("Pwd");
			}
			
			if(type==1){
				String content="亲爱的"+name+":"+"您的密码为：<    "+pwd+"  > 请妥善保管～";
				String title="找回密码（校园时光官方邮件）";
				
				MailSupport mailSupport=new MailSupport("smtp.163.com","zhangyiming_klaus@163.com","553355",false);
				mailSupport.send("zhangyiming_klaus@163.com",email,title,content);
				result="ok";
				
			}else{
				result="wrong";
			}

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
