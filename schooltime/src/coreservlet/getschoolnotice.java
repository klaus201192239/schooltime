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
import utils.jsonUtil;
import com.dbDao.CreateAndQuery;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import bean.SchoolNotice;

public class getschoolnotice extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public getschoolnotice() {
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
		String userid=request.getParameter("userid");
		String currentid=request.getParameter("currentid");
		
		
		SchoolNotice notice=new SchoolNotice();
		notice.setReceiver(new ObjectId(userid));
		
		BasicDBObject query=null;
		
		if(currentid.equals("0")){
			try {
				query=CreateQueryFromBean.EqualObj(notice);
			} catch (Exception e) {
				
			}
		}else{
			SchoolNotice notice1=new SchoolNotice();
			notice1.set_id(new ObjectId(currentid));
			
			BasicDBObject query0=null;
			BasicDBObject query1=null;
			try {
				query0 = CreateQueryFromBean.EqualObj(notice);
				query1 = CreateQueryFromBean.GtObj(notice1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			CreateAndQuery and=new CreateAndQuery();
			and.Add(query0);
			and.Add(query1);
			query=and.GetResult();
		}
		
		BasicDBObject pro=new BasicDBObject();
		pro.put(StaticString.SchoolNotice_Content, 1);
		pro.put(StaticString.SchoolNotice_OrganizationName, 1);
		pro.put(StaticString.SchoolNotice_ReleaseTime, 1);
		pro.put(StaticString.SchoolNotice_Title, 1);
		
		try{
			
			JSONArray array=new JSONArray();
			MongoCursor<Document> cursor= DaoImpl.GetSelectCursor(SchoolNotice.class, query, pro);
			while(cursor.hasNext()){
				JSONObject obj=jsonUtil.ParaFromDocument(cursor.next());
				array.put(obj);
			}
			
			if(array.length()==0){
				
			}else{
				result=array.toString();
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
