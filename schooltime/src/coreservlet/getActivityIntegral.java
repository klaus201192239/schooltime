package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;
import staticData.StaticString;
import bean.ActivityIntegral;
import com.dbDao.CreateAndQuery;
import com.dbDao.CreateOrQuery;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

public class getActivityIntegral extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public getActivityIntegral() {
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
		int year=Integer.parseInt(request.getParameter("year"));
		String schoolid=request.getParameter("schoolid");
		String idcard=request.getParameter("idcard");
		
		Calendar caMin=Calendar.getInstance();
		caMin.set(year, 9, 1,0,0,0);
		Calendar caMix=Calendar.getInstance();
		caMix.set(year+1, 9, 1,0,0,0);
		
		
		ActivityIntegral inte=new ActivityIntegral();
		inte.setCreateTime(caMin.getTime());
		
		ActivityIntegral inte1=new ActivityIntegral();
		inte1.setCreateTime(caMix.getTime());
		
		ActivityIntegral inte2=new ActivityIntegral();
		inte2.setStuId(new ObjectId("000000000000000000000000"));
		inte2.setIdCard(idcard);
		inte2.setSchoolId(new ObjectId(schoolid));
		
		ActivityIntegral inte3=new ActivityIntegral();
		inte3.setStuId(new ObjectId(userid));
		

		BasicDBObject query1=null;
		BasicDBObject query2=null;
		try {
			query1 = CreateQueryFromBean.EqualObj(inte2);
			query2 = CreateQueryFromBean.EqualObj(inte3);
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		
		CreateOrQuery or=new CreateOrQuery();
		or.Add(query1);
		or.Add(query2);
		
		CreateAndQuery and=new CreateAndQuery();
		and.Add(or.GetResult());
		try {
			and.Add(CreateQueryFromBean.GteObj(inte));
			and.Add(CreateQueryFromBean.LteObj(inte1));
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		
		BasicDBObject pro=new BasicDBObject();
		pro.put(StaticString.ActivityIntegral_id, 0);
		pro.put(StaticString.ActivityIntegral_CategoryName, 1);
		pro.put(StaticString.ActivityIntegral_CategoryId, 1);
		pro.put(StaticString.ActivityIntegral_Grade, 1);
		pro.put(StaticString.ActivityIntegral_Level, 1);
		pro.put(StaticString.ActivityIntegral_Scope, 1);
		pro.put(StaticString.ActivityIntegral_Name, 1);
		pro.put(StaticString.ActivityIntegral_ThingName, 1);

		ArrayList<Document> listdoc=new ArrayList<Document>();
		
		try{
			MongoCursor<Document>  cursor= DaoImpl.GetSelectCursor(ActivityIntegral.class, and.GetResult(),new BasicDBObject(StaticString.ActivityIntegral_id,1) ,pro);
			while(cursor.hasNext()){		
				
				Document doc=cursor.next();
				
				listdoc.add(doc);
			}
			
			HashMap<String,JSONObject> mapAll=new HashMap<String,JSONObject>(); 

			JSONObject map=null;
			
			int len=listdoc.size();
			
			for(int i=0;i<len;i++){
				
				Document docc=listdoc.get(i);

				if(mapAll.get(docc.get("CategoryId").toString())==null){
		
					map=new JSONObject();

					JSONArray detailList=new JSONArray();
					
					JSONObject mapp=new JSONObject();

					map.put("id", docc.get("CategoryId"));
					map.put("name", docc.get("CategoryName"));
					mapp.put("grade", docc.get("Grade"));		
					mapp.put("level", docc.get("Level"));
					
					log(docc.get("ThingName")+"");
					
					mapp.put("name", docc.get("ThingName"));
					mapp.put("scope", docc.get("Scope"));
					detailList.put(mapp);
					map.put("detail", detailList);

					mapAll.put(docc.get("CategoryId").toString(), map);

				}
				else
				{
					
					map=mapAll.get(docc.get("CategoryId").toString());
					
					JSONObject mapp=new JSONObject();

					JSONArray detailList=map.getJSONArray("detail");
					
					mapp.put("grade", docc.get("Grade"));
					mapp.put("level", docc.get("Level"));
					mapp.put("name", docc.get("ThingName"));
					mapp.put("scope", docc.get("Scope"));
					detailList.put(mapp);
					
					map.put("detail", detailList);
					mapAll.put(docc.get("CategoryId").toString(), map);
				}

			}
					
			JSONArray array=new JSONArray();
			
			for (String key : mapAll.keySet()) {  
				  
				array.put(mapAll.get(key));

			}  
			
			result=array.toString();
			
			log(result);
			
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

	public static void main(String[] args) {

		String result="error";
		
		String userid="55e69cf4b9032a1698a66355";
		int year=2014;
		String schoolid="55e19fb039aeaf07481b08dc";
		String idcard="201192239";
		
		
	//	log(userid);//55e69cf4b9032a1698a66355
	//	log(schoolid);//55e19fb039aeaf07481b08dc
	//	log(idcard);
		
		Calendar caMin=Calendar.getInstance();
		caMin.set(year, 9, 1,0,0,0);
		Calendar caMix=Calendar.getInstance();
		caMix.set(year+1, 9, 1,0,0,0);
		
		
		ActivityIntegral inte=new ActivityIntegral();
		inte.setCreateTime(caMin.getTime());
		
		ActivityIntegral inte1=new ActivityIntegral();
		inte1.setCreateTime(caMix.getTime());
		
		ActivityIntegral inte2=new ActivityIntegral();
		inte2.setIdCard(idcard);
		inte2.setSchoolId(new ObjectId(schoolid));
		
		ActivityIntegral inte3=new ActivityIntegral();
		inte3.setStuId(new ObjectId(userid));
		

		BasicDBObject query1=null;
		BasicDBObject query2=null;
		try {
			query1 = CreateQueryFromBean.EqualObj(inte2);
			query2 = CreateQueryFromBean.EqualObj(inte3);
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		
		CreateOrQuery or=new CreateOrQuery();
		or.Add(query1);
		or.Add(query2);
		
		CreateAndQuery and=new CreateAndQuery();
		and.Add(or.GetResult());
		try {
			and.Add(CreateQueryFromBean.GteObj(inte));
			and.Add(CreateQueryFromBean.LteObj(inte1));
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		
		BasicDBObject pro=new BasicDBObject();
		pro.put(StaticString.ActivityIntegral_id, 0);
		pro.put(StaticString.ActivityIntegral_CategoryName, 1);
		pro.put(StaticString.ActivityIntegral_CategoryId, 1);
		pro.put(StaticString.ActivityIntegral_Grade, 1);
		pro.put(StaticString.ActivityIntegral_Level, 1);
		pro.put(StaticString.ActivityIntegral_Scope, 1);
		pro.put(StaticString.ActivityIntegral_Name, 1);

		ArrayList<Document> listdoc=new ArrayList<Document>();
		
		try{
			MongoCursor<Document>  cursor= DaoImpl.GetSelectCursor(ActivityIntegral.class, and.GetResult(),new BasicDBObject(StaticString.ActivityIntegral_id,1) ,pro);
			while(cursor.hasNext()){		
				
				Document doc=cursor.next();
				
				listdoc.add(doc);
			}
			
			HashMap<String,JSONObject> mapAll=new HashMap<String,JSONObject>(); 

			JSONObject map=null;
			
			int len=listdoc.size();
			
			for(int i=0;i<len;i++){
				
				Document docc=listdoc.get(i);

				if(mapAll.get(docc.get("CategoryId").toString())==null){
		
					map=new JSONObject();

					JSONArray detailList=new JSONArray();
					
					JSONObject mapp=new JSONObject();

					map.put("id", docc.get("CategoryId"));
					map.put("name", docc.get("CategoryName"));
					mapp.put("grade", docc.get("Grade"));		
					mapp.put("level", docc.get("Level"));
					mapp.put("name", docc.get("ThingName"));
					mapp.put("scope", docc.get("Scope"));
					detailList.put(mapp);
					map.put("detail", detailList);

					mapAll.put(docc.get("CategoryId").toString(), map);

				}
				else
				{
					
					map=mapAll.get(docc.get("CategoryId").toString());
					
					JSONObject mapp=new JSONObject();

					JSONArray detailList=map.getJSONArray("detail");
					
					mapp.put("grade", docc.get("Grade"));
					mapp.put("level", docc.get("Level"));
					mapp.put("name", docc.get("ThingName"));
					mapp.put("scope", docc.get("Scope"));
					detailList.put(mapp);
					
					map.put("detail", detailList);
					mapAll.put(docc.get("CategoryId").toString(), map);
				}

			}
					
			JSONArray array=new JSONArray();
			
			for (String key : mapAll.keySet()) {  
				  
				array.put(mapAll.get(key));

			}  
			
			result=array.toString();
			
		}catch(Exception e){
			result="error";
		}
		
		System.out.println(result);
	}
}
