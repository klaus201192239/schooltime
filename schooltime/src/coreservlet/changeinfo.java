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
import bean.ActivityIntegral;
import bean.StudentInfo;
import bean.TableContentInfo;

import com.dbDao.CreateOrQuery;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

public class changeinfo extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public changeinfo() {
		super();
	}


	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");//post 可以直接设置中文字符编号
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		
		String result="ok";
		String id=request.getParameter("_id").toString();
		ObjectId _id= new ObjectId(id);
		
		
		StudentInfo stuquery=new StudentInfo();
		stuquery.set_id(_id);
		
		StudentInfo student=new StudentInfo();
		student.setPhone(request.getParameter("phone").toString());
		student.setDegree(Integer.parseInt(request.getParameter("degree").toString()));
		student.setEmail(request.getParameter("email").toString());
		student.setGrade(Integer.parseInt(request.getParameter("grade").toString()));
		student.setMajorId(new ObjectId(request.getParameter("majorid").toString()));
		
		try {
			student.setName(Util.DoGetString(request.getParameter("name").toString()));
			student.setMajorName(Util.DoGetString(request.getParameter("majorname").toString()));
		} catch (Exception e1) {
			
		}	
		student.setSex(Integer.parseInt(request.getParameter("sex").toString()));
		student.setIdCard(request.getParameter("studentid").toString());
		student.setSchoolId(new ObjectId(request.getParameter("schoolid").toString()));
		
		try{
			StudentInfo stu=new StudentInfo();
			stu.setPhone(request.getParameter("phone").toString());
			MongoCursor<Document> cur=DaoImpl.GetSelectCursor(StudentInfo.class, CreateQueryFromBean.EqualObj(stu),
					new BasicDBObject(StaticString.StudentInfo_id,1));
			while(cur.hasNext()){
				Document doc=cur.next();
				if(!doc.getObjectId("_id").toString().equals(id)){
					result="wrong3";
				}
			}
			
			
			if(!result.equals("wrong3")){
				StudentInfo stu1=new StudentInfo();
				stu1.setEmail(request.getParameter("email").toString());
				MongoCursor<Document> cur1=DaoImpl.GetSelectCursor(StudentInfo.class, CreateQueryFromBean.EqualObj(stu1),
						new BasicDBObject(StaticString.StudentInfo_id,1));
				while(cur1.hasNext()){
					Document doc=cur1.next();
					if(!doc.getObjectId("_id").toString().equals(id)){
						result="wrong4";
					}
				}
			
				
				if(!result.equals("wrong4")){
					StudentInfo stu2=new StudentInfo();
					stu2.setSchoolId(new ObjectId(request.getParameter("schoolid").toString()));
					stu2.setIdCard(request.getParameter("studentid").toString());
					MongoCursor<Document> cur2=DaoImpl.GetSelectCursor(StudentInfo.class, CreateQueryFromBean.EqualObj(stu2),
							new BasicDBObject(StaticString.StudentInfo_id,1));
					while(cur2.hasNext()){
						Document doc=cur2.next();
						if(!doc.getObjectId("_id").toString().equals(id)){
							result="wrong5";
						}
					}
				}
			}
			
			if(result.equals("ok")){
				
				int ty=0;
				
				String scid="",usid="";

				StudentInfo stu3=new StudentInfo();
				stu3.set_id(_id);
				
				BasicDBObject proo=new BasicDBObject();
				proo.put(StaticString.StudentInfo_id, 0);
				proo.put(StaticString.StudentInfo_IdCard, 1);
				proo.put(StaticString.StudentInfo_SchoolId, 1);
				MongoCursor<Document> cur3=DaoImpl.GetSelectCursor(StudentInfo.class, CreateQueryFromBean.EqualObj(stu3),proo);
				while(cur3.hasNext()){
					Document doc=cur3.next();
					if(!doc.getObjectId("SchoolId").toString().equals(request.getParameter("schoolid").toString())){
						ty=1;
						scid=doc.getObjectId("SchoolId").toString();
						usid=doc.getString("IdCard");
					}
				}
				
				if(ty==1){
					
					TableContentInfo stu4=new TableContentInfo();
					stu4.setIdCard(usid);
					stu4.setSchoolId(new ObjectId(scid));
					
					TableContentInfo stu5=new TableContentInfo();
					stu5.setStuId(_id);
					
					CreateOrQuery or=new CreateOrQuery();
					or.Add(CreateQueryFromBean.EqualObj(stu4));
					or.Add(CreateQueryFromBean.EqualObj(stu5));
					
					DaoImpl.DeleteDocment(TableContentInfo.class, or.GetResult());
					
					ActivityIntegral stu6=new ActivityIntegral();
					stu6.setIdCard(usid);
					stu6.setSchoolId(new ObjectId(scid));
					
					ActivityIntegral stu7=new ActivityIntegral();
					stu7.setStuId(_id);	
					
					CreateOrQuery or1=new CreateOrQuery();
					or1.Add(CreateQueryFromBean.EqualObj(stu6));
					or1.Add(CreateQueryFromBean.EqualObj(stu7));
					
					DaoImpl.DeleteDocment(ActivityIntegral.class,or1.GetResult());
									
				}
				
				DaoImpl.update(CreateQueryFromBean.EqualObj(stuquery), student,false);
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
