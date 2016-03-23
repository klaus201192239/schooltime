package daoimpl;

import java.util.List;
import java.util.Map;

public class daoutil {
	public static int Add(String JavaBeanName,Object obj){
		String sql=sqlutil.insertSql(JavaBeanName, obj);
		int resu=dbutil.execute(sql);
		return resu;
	}
	public static int delete(String JavaBeanName,Object obj){
		String sql=sqlutil.deleteSql(JavaBeanName, obj);
		int resu=dbutil.execute(sql);
		return resu;
	}
	public static int update(String JavaBeanName,Object Oldobj,Object Newobj){
		String sql=sqlutil.updateSql(JavaBeanName, Oldobj,Newobj);
		int resu=dbutil.execute(sql);
		return resu;
	}
	public static List<Map<String, Object>> selectSingleTable(String JavaBeanName,Object obj){
		String sql=sqlutil.selectSql(JavaBeanName,obj);
		return dbutil.executeQuery(sql);
	}
}
