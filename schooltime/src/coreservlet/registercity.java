package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import staticData.StaticString;
import bean.Province;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

public class registercity extends HttpServlet {

	private static final long serialVersionUID = 1L;


	public registercity() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		
		String result="error";
		
		JSONArray array=new JSONArray();
		
		BasicDBObject pro=new BasicDBObject();
		pro.put(StaticString.Province_City, 1);
		pro.put(StaticString.Province_Name, 1);
		pro.put(StaticString.Province_id, 0);
		
		try{
			
			
			MongoCursor<Document> cursor= DaoImpl.GetSelectCursor(Province.class, new BasicDBObject(), pro);
			while(cursor.hasNext()){
				Document doc=cursor.next();
				JSONObject obj=new JSONObject();
				obj.put("pro", doc.getString("Name"));
				
				JSONArray arrayCity=new JSONArray();
				@SuppressWarnings("unchecked")
				ArrayList<Document> aa=(ArrayList<Document>)doc.get("City");
				for(int i=0;i<aa.size();i++){
					arrayCity.put(aa.get(i).getString("Name"));
				}
				obj.put("city", arrayCity);
				array.put(obj);
			}
			
			result=array.toString();
			
		}catch(Exception e){
			result="error";
		}
		
		
		log(result);
		
		System.out.println(result);
		
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
