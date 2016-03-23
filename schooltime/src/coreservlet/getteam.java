package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;
import staticData.StaticString;
import bean.StudentInfo;
import bean.TeamInfo;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

public class getteam extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public getteam() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String result="error";
		String avtivityid=request.getParameter("activityid").toString();
		
		TeamInfo team=new TeamInfo();
		team.setActivityId(new ObjectId(avtivityid));
		
		BasicDBObject pro=new BasicDBObject();
		pro.put(StaticString.TeamInfo_Abstract, 1);
		pro.put(StaticString.TeamInfo_Name, 1);
		pro.put(StaticString.TeamInfo_Need, 1);
		pro.put(StaticString.TeamInfo_Password, 1);
		pro.put(StaticString.TeamInfo_Slogan, 1);
		pro.put(StaticString.TeamInfo_TeamLeader, 1);
		
		
		JSONArray array=new JSONArray();
		try{
			MongoCursor<Document> cursor= DaoImpl.GetSelectCursor(TeamInfo.class, CreateQueryFromBean.EqualObj(team), pro);
			while(cursor.hasNext()){
				
				Document doc=cursor.next();
				
				StudentInfo student=new StudentInfo();
				student.set_id(doc.getObjectId("TeamLeader"));
				BasicDBObject proStu=new BasicDBObject();
				proStu.put(StaticString.StudentInfo_Name, 1);
				proStu.put(StaticString.StudentInfo_MajorName, 1);
				proStu.put(StaticString.StudentInfo_Grade, 1);
				proStu.put(StaticString.StudentInfo_Degree, 1);
				proStu.put(StaticString.StudentInfo_id, 0);
								
				MongoCursor<Document> cur= DaoImpl.GetSelectCursor(StudentInfo.class, CreateQueryFromBean.EqualObj(student), proStu);
				while(cur.hasNext()){
					
					Document docc=cur.next();
					
					JSONObject obj=new JSONObject();
					obj.put("_id", doc.getObjectId("_id"));
					obj.put("Name", doc.getString("Name"));
					obj.put("Abstract", doc.getString("Abstract"));
					obj.put("Need", doc.getString("Need"));
					obj.put("Password", doc.getInteger("Password"));
					obj.put("Slogan", doc.getString("Slogan"));
					if(docc.getInteger("Degree")==0){
						obj.put("Leader", docc.getString("Name")+"-"+docc.getString("MajorName")+"-"+docc.getInteger("Grade")+"级"+"(专)");
					}else{
						if(docc.getInteger("Degree")==1){
							obj.put("Leader", docc.getString("Name")+"-"+docc.getString("MajorName")+"-"+docc.getInteger("Grade")+"级"+"(本)");
						}else{
							if(docc.getInteger("Degree")==2){
								obj.put("Leader", docc.getString("Name")+"-"+docc.getString("MajorName")+"-"+docc.getInteger("Grade")+"级"+"(研)");
							}else{
								if(docc.getInteger("Degree")==3){
									obj.put("Leader", docc.getString("Name")+"-"+docc.getString("MajorName")+"-"+docc.getInteger("Grade")+"级"+"(博)");
								}
							}
						}
					}
					
					array.put(obj);
					
				}
			}
			
			result=array.toString();
			
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
