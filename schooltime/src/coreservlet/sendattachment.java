package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;
import org.bson.types.ObjectId;
import staticData.StaticString;
import utils.Util;
import bean.InActivity;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import com.ykmaiz.mail.MailSupport;

public class sendattachment extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public sendattachment() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");//post ����ֱ�����������ַ����
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String title="";
		String content="";
		
		String activityid=request.getParameter("activityid");
		String email=request.getParameter("email");
		String name="";
		try {
			name=Util.DoGetString(request.getParameter("name").toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		InActivity ac=new InActivity();
		ac.set_id(new ObjectId(activityid));
				
		BasicDBObject pro=new BasicDBObject();
		pro.put(StaticString.InActivity_id,0);
		pro.put(StaticString.InActivity_AttachmentName, 1);
		pro.put(StaticString.InActivity_AttachmentUrl, 1);
		pro.put(StaticString.InActivity_DeadLine, 1);
		pro.put(StaticString.InActivity_Name, 1);
		
		
		try {
			MongoCursor<Document> cousor= DaoImpl.GetSelectCursor(InActivity.class, CreateQueryFromBean.EqualObj(ac), pro);
			while(cousor.hasNext()){
				Document doc=cousor.next();
				content="�װ���"+name+":"+"��������Ҫ�������Ѿ���"+doc.getString("Name")+"�ĸ���"+doc.getString("AttachmentName")+"���͵�����ָ�����䡣�����Է����������ӽ��и�������"
						+doc.getString("AttachmentUrl")+"������������û�ı�����ֹʱ��Ϊ��"+doc.getDate("DeadLine");
				title=doc.getString("Name");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		title+="��У԰ʱ��ٷ��ʼ���";
		
		try{
			MailSupport mailSupport=new MailSupport("smtp.163.com","zhangyiming_klaus@163.com","553355",false);
			mailSupport.send("zhangyiming_klaus@163.com",email,title,content);
		}catch(Exception e){
			
		}

		out.println("ok");
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
