package coreservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
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
import bean.TableContentInfo;
import bean.TableInfo;
import com.dbDao.CreateAndQuery;
import com.dbDao.CreateOrQuery;
import com.dbDao.CreateQueryFromBean;
import com.dbDao.DaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;

public class getsigninlist extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public getsigninlist() {
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
		String activityid=request.getParameter("activityid");
		String currentstuid=request.getParameter("currentstuid").toString();
		ObjectId tableId=null;
		HashMap<String,Integer> map=new HashMap<String,Integer>();
		JSONArray array=new JSONArray();

		
		TableInfo tableinfo=new TableInfo();
		tableinfo.setActivityId(new ObjectId(activityid));
		tableinfo.setType(0);
		
		BasicDBObject pro=new BasicDBObject(StaticString.TableInfo_id,1);
		BasicDBObject pro1=new BasicDBObject();
		pro1.put(StaticString.TableContentInfo_id, 0);
		pro1.put(StaticString.TableContentInfo_StuId,1);
		pro1.put(StaticString.TableContentInfo_TableContentColumn, 1);
		BasicDBObject pro2=new BasicDBObject();
		pro2.put(StaticString.StudentInfo_Degree, 1);
		pro2.put(StaticString.StudentInfo_id, 1);
		pro2.put(StaticString.StudentInfo_Grade, 1);
		pro2.put(StaticString.StudentInfo_MajorName, 1);
		pro2.put(StaticString.StudentInfo_Name, 1);
		pro2.put(StaticString.StudentInfo_IdCard, 1);
		
		
		try{
			MongoCursor<Document> cur=DaoImpl.GetSelectCursor(TableInfo.class, CreateQueryFromBean.EqualObj(tableinfo), pro);
			while(cur.hasNext()){
				Document doc=cur.next();
				tableId=doc.getObjectId("_id");
				
			}
			
			TableContentInfo tainfo=new TableContentInfo();
			tainfo.setTableId(tableId);
			
			MongoCursor<Document> cur1=DaoImpl.GetSelectCursor(TableContentInfo.class, CreateQueryFromBean.EqualObj(tainfo), pro1);
			while(cur1.hasNext()){
				Document doc=cur1.next();
				@SuppressWarnings("unchecked")
				ArrayList<Document> list=(ArrayList<Document>)doc.get(StaticString.TableContentInfo_TableContentColumn);
				for(int i=0;i<list.size();i++){
					if(list.get(i).get("Name").equals("SignIn")){
						map.put(doc.getObjectId("StuId").toString(),Integer.parseInt(list.get(i).getString("Content")));
					}
				}
			}
			
			int len=map.size();
		
			CreateOrQuery or=new CreateOrQuery();
						
			Iterator<Entry<String, Integer>> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iter.next();
				String key = entry.getKey();
				
				StudentInfo student=new StudentInfo();
				student.set_id(new ObjectId(key));
				
				if(currentstuid.equals("0")){
					or.Add(CreateQueryFromBean.EqualObj(student));
				}else{
					
					StudentInfo student1=new StudentInfo();
					student1.setIdCard(currentstuid);
					
					CreateAndQuery and=new CreateAndQuery();
					and.Add(CreateQueryFromBean.LtObj(student1));
					and.Add(CreateQueryFromBean.EqualObj(student));
					
					or.Add(and.GetResult());
				}
			}
			
			if(len!=0){
				MongoCursor<Document> cur2=DaoImpl.GetSelectCursor(StudentInfo.class,or.GetResult(),new BasicDBObject(StaticString.StudentInfo_IdCard,-1) ,30,pro2);
				while(cur2.hasNext()){
					Document doc=cur2.next();
					
					JSONObject obj=new JSONObject();
					obj.put("id", doc.getObjectId("_id"));
					obj.put("degree",doc.getInteger("Degree"));
					obj.put("grade", doc.getInteger("Grade"));
					obj.put("major", doc.getString("MajorName"));
					obj.put("name", doc.getString("Name"));
					obj.put("studentid", doc.getString("IdCard"));
					obj.put("state", map.get(doc.getObjectId("_id").toString()));
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

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("This is post method");
		out.flush();
		out.close();
	}

	public void init() throws ServletException {
		// Put your code here
	}
	
}
