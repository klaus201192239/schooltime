package com.dbDao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.bson.Document;
import bean.ObjBean;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

public class DaoImpl {
	
	public static void main(String[] args) throws Exception {
		
		/*ArrayList<Document> list=new ArrayList<Document>();
		
		MongoCursor<Document> cursor=GetSelectCursor(StudentInfo.class, new BasicDBObject(),new BasicDBObject());
		while(cursor.hasNext()){
			Document doc=cursor.next();
			list.add(doc);
		}
		
		for(int i=0;i<list.size();i++){
			MongoDatabase db=DBClientFactory.getDB();
			MongoCollection<Document> collection = db.getCollection("StudentInfo");
			
			
			
			Calendar ca=Calendar.getInstance();
			ca.set(2015, 10, (i+1+92)%27+1, (32+i)%20+1,(432+i)%50+1 ,(362+i)%50+1);

			
			BasicDBObject a2=new BasicDBObject("CreateTime",ca.getTime());
			BasicDBObject a3=new BasicDBObject("$set",a2);
			
			UpdateOptions op=new UpdateOptions();
			op.upsert(true);
			
			collection.updateMany(new BasicDBObject("_id",list.get(i).getObjectId("_id")),a3,op);
			
		}*/

		
		/*JSONArray array=new JSONArray();
		
		array.put("aaaaaaaaa");
		array.put("bbbbbbbbbbbbbb");
		
		String json=array.toString();
		
		System.out.println(json);		
		
		int leng=json.length();
		
		String json1=json.substring(1,leng-2);
		
		String json2="{"+json1+"}";
		
		System.out.println(json2);
		
		
		int leng1=json.length();
		
		String json3=json.substring(1,leng1-2);
		
		String json4="["+json3+"]";
		
		System.out.println(json4);
		*/
		
	}
	
	public static String FileInputStreamDemo(String path) throws Exception{
		
        File file=new File(path);
        if(!file.exists()||file.isDirectory())
            throw new FileNotFoundException();
        FileInputStream fis=new FileInputStream(file);
        byte[] buf = new byte[1024];
        StringBuffer sb=new StringBuffer();
        while((fis.read(buf))!=-1){
            sb.append(new String(buf));    
            buf=new byte[1024];//重新生成，避免和上次读取的数据重复
        }
        return sb.toString();
    }

	
	public static void show()  {
		MongoDatabase db=DBClientFactory.getDB();
		MongoCollection<Document> collection = db.getCollection("UserSkimTime");
		FindIterable<Document> iterable = collection.find();		
		MongoCursor<Document> cursor = iterable.iterator();
		while(cursor.hasNext()){
						
			Document doc=cursor.next();
			
			System.out.println(doc.toString());

		}
	}
	
	public static void InsertWholeBean(Object obj) throws Exception{
		Document doc=JsonBeanUtil.getDocumentFromBean(obj);
		String DadClassName=obj.getClass().getName();
		int x1=DadClassName.lastIndexOf(".");
		MongoDatabase db=DBClientFactory.getDB();
		MongoCollection<Document> collection = db.getCollection(DadClassName.substring(x1+1));
		collection.insertOne(doc);
	}
	
	public static void InsertSonBean(Class<?> DadClass,BasicDBObject query,Class<?> SonClass,ArrayList<?> list) throws Exception{
	
		String DadClassName=DadClass.getName();
		int x1=DadClassName.lastIndexOf(".");
		String collectionName=DadClassName.substring(x1+1);
		String SonClassName=SonClass.getName();
		x1=SonClassName.lastIndexOf(".");
		String SonBeanName=SonClassName.substring(x1+1);
	
		ArrayList<BasicDBObject> lis=new ArrayList<BasicDBObject>();
		for(int i=0;i<list.size();i++){
			BasicDBObject obj=JsonBeanUtil.getJsonFromBean(list.get(i));
			lis.add(obj);
		}

		BasicDBObject  each= new BasicDBObject("$each",lis);
		
		BasicDBObject  SonBean= new BasicDBObject(SonBeanName,each);
		
		BasicDBObject  update= new BasicDBObject("$addToSet",SonBean);
		
		MongoDatabase db=DBClientFactory.getDB();
		
		MongoCollection<Document> collection = db.getCollection(collectionName);
		
		collection.updateMany(query, update);
	} 
	
	public static long GetSelectCount(Class<?> DadClass,BasicDBObject query) throws Exception{
		String DadClassName=DadClass.getName();
		int x1=DadClassName.lastIndexOf(".");
		String collectionName=DadClassName.substring(x1+1);
		MongoDatabase db=DBClientFactory.getDB();
		MongoCollection<Document> collection = db.getCollection(collectionName);
		return collection.count(query);
	}
	public static MongoCursor<Document> GetSelectCursor(Class<?> DadClass,BasicDBObject query,BasicDBObject projection) throws Exception{
		String DadClassName=DadClass.getName();
		int x1=DadClassName.lastIndexOf(".");
		String collectionName=DadClassName.substring(x1+1);
		MongoDatabase db=DBClientFactory.getDB();
		MongoCollection<Document> collection = db.getCollection(collectionName);
		FindIterable<Document> iterable = collection.find(query).projection(projection);		
		MongoCursor<Document> cursor = iterable.iterator();
		return  cursor;
	}
	
	public static MongoCursor<Document> GetSelectCursor(Class<?> DadClass,BasicDBObject query,BasicDBObject sort,BasicDBObject projection) throws Exception{
		String DadClassName=DadClass.getName();
		int x1=DadClassName.lastIndexOf(".");
		String collectionName=DadClassName.substring(x1+1);
		MongoDatabase db=DBClientFactory.getDB();
		MongoCollection<Document> collection = db.getCollection(collectionName);
		FindIterable<Document> iterable = collection.find(query).sort(sort).projection(projection);		
		MongoCursor<Document> cursor = iterable.iterator();
		return  cursor;
	}
	
	public static MongoCursor<Document> GetSelectCursor(Class<?> DadClass,BasicDBObject query,int limit,BasicDBObject projection) throws Exception{
		String DadClassName=DadClass.getName();
		int x1=DadClassName.lastIndexOf(".");
		String collectionName=DadClassName.substring(x1+1);
		MongoDatabase db=DBClientFactory.getDB();
		MongoCollection<Document> collection = db.getCollection(collectionName);
		FindIterable<Document> iterable = collection.find(query).limit(limit).projection(projection);
		MongoCursor<Document> cursor = iterable.iterator();
		return  cursor;
	}
	
	public static MongoCursor<Document> GetSelectCursor(Class<?> DadClass,BasicDBObject query,BasicDBObject sort,int limit,BasicDBObject projection) throws Exception{
		String DadClassName=DadClass.getName();
		int x1=DadClassName.lastIndexOf(".");
		String collectionName=DadClassName.substring(x1+1);
		MongoDatabase db=DBClientFactory.getDB();
		MongoCollection<Document> collection = db.getCollection(collectionName);
		FindIterable<Document> iterable = collection.find(query).sort(sort).limit(limit).projection(projection);		
		MongoCursor<Document> cursor = iterable.iterator();
		return  cursor;
	}
	
	public static DeleteResult DeleteDocment(Class<?> DadClass,BasicDBObject query) throws Exception{
		String name=DadClass.getName();
		int x1=name.lastIndexOf(".");
		MongoDatabase db=DBClientFactory.getDB();
		MongoCollection<Document> collection = db.getCollection(name.substring(x1+1));
		DeleteResult result= collection.deleteMany(query);
		return result;
	}
	public static void DeleteSonSomeBean(Class<?> DadClass,BasicDBObject query,Class<?> SonClass,ArrayList<?> list) throws Exception{
		String name=DadClass.getName();
		int x1=name.lastIndexOf(".");
		String name1=SonClass.getName();
		int x2=name1.lastIndexOf(".");
		MongoDatabase db=DBClientFactory.getDB();
		MongoCollection<Document> collection = db.getCollection(name.substring(x1+1));
		for(int i=0;i<list.size();i++){
			BasicDBObject bb=new BasicDBObject("$pull",new BasicDBObject(name1.substring(x2+1),JsonBeanUtil.getJsonFromBean(list.get(i))));		
			collection.updateMany(query, bb);
		}
	}
	
	public static void DeleteSonWholeBean(Class<?> DadClass,BasicDBObject query,Class<?> SonClass,ArrayList<?> list) throws Exception{
		String name=DadClass.getName();
		int x1=name.lastIndexOf(".");
		String name1=SonClass.getName();
		int x2=name1.lastIndexOf(".");
		
		ArrayList<BasicDBObject> objlist=new ArrayList<BasicDBObject>();
		for(int i=0;i<list.size();i++){
			objlist.add(JsonBeanUtil.getJsonFromBean(list.get(i)));
		}
		
		BasicDBObject  dele= new BasicDBObject(name1.substring(x2+1),objlist);		
		BasicDBObject  delet= new BasicDBObject("$pullAll",dele);
		
		MongoDatabase db=DBClientFactory.getDB();
		MongoCollection<Document> collection = db.getCollection(name.substring(x1+1));
		collection.updateMany(query,delet);
	}
	
	public static void update(BasicDBObject query,Object DadBean,boolean Updatemany) throws Exception{
		String name=DadBean.getClass().getName();
		int x1=name.lastIndexOf(".");
		
		BasicDBObject obj= JsonBeanUtil.getUpateFromBean(DadBean);
		BasicDBObject update=new BasicDBObject("$set",obj);
		
		MongoDatabase db=DBClientFactory.getDB();
		MongoCollection<Document> collection = db.getCollection(name.substring(x1+1));
		
		if(Updatemany==true){

			collection.updateMany(query,update);
		}
		else{
			collection.updateOne(query,update);
		}
		
	}
	
	public static void update(BasicDBObject query,Object DadBean,boolean Updatemany,boolean wholeSon) throws Exception{
		String name=DadBean.getClass().getName();
		
		int x1=name.lastIndexOf(".");
		
		BasicDBObject obj= null;
		
		if(wholeSon==true){
			obj= JsonBeanUtil.getUpateWholeFromBean(DadBean);
		}else{
			obj= JsonBeanUtil.getUpateFromBean(DadBean);
		}
		
		BasicDBObject update=new BasicDBObject("$set",obj);
		
		MongoDatabase db=DBClientFactory.getDB();
		MongoCollection<Document> collection = db.getCollection(name.substring(x1+1));
		
		if(Updatemany==true){
			
			collection.updateMany(query,update);
		}
		else{
			collection.updateOne(query,update);
		}
		
	}
	
	public static void update(BasicDBObject query,Object DadBean,CreateIncQuery inc, boolean Updatemany) throws Exception{
		String name=DadBean.getClass().getName();
		int x1=name.lastIndexOf(".");
		
		BasicDBObject obj= JsonBeanUtil.getUpateFromBean(DadBean);
	
		BasicDBObject incObj=new BasicDBObject();
		ArrayList<ObjBean> list=inc.getList();
		for(int i=0;i<list.size();i++){
			incObj.put(list.get(i).getName(), list.get(i).getId());
		}
		
		
		BasicDBObject update=new BasicDBObject();
		update.put("$set", obj);
		update.put("$inc", incObj);
		
		MongoDatabase db=DBClientFactory.getDB();
		MongoCollection<Document> collection = db.getCollection(name.substring(x1+1));
		
		if(Updatemany==true){

			collection.updateMany(query,update);
		}
		else{
			collection.updateOne(query,update);
		}
		
	}
}
