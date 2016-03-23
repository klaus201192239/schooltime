package daoimpl;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class dbutil {
	public static Connection getConnection() {

		try {

			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://localhost:3306/schooltime";

			String username = "root";

			String password = "201192239";

			Connection conn = DriverManager.getConnection(url, username,
					password);

			return conn;

		} catch (Exception e) {

			throw new IllegalArgumentException(e);

		}

	}

	public static int execute(String sql) {
		int result = -1;
		try {

			Connection conn = dbutil.getConnection();

			Statement st = conn.createStatement();

			result = st.executeUpdate(sql);

			st.close();

			conn.close();

			return result;

		} catch (SQLException e) {

			return result;

		}

	}

	public static List<Map<String, Object>> executeQuery(String sql) {

		try {

			Connection conn = dbutil.getConnection();

			Statement st = conn.createStatement();

			ResultSet set = st.executeQuery(sql);

			ResultSetMetaData metaData = set.getMetaData();

			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

			int columnCount = metaData.getColumnCount();

			while (set.next()) {

				Map<String, Object> map = new HashMap<String, Object>();

				for (int i = 1; i <= columnCount; i++) {

					String name = metaData.getColumnName(i);

					Object value = set.getObject(name);

					map.put(name, value);

				}

				result.add(map);

			}

			set.close();

			st.close();

			conn.close();

			return result;

		} catch (SQLException e) {

			throw new IllegalArgumentException(e);

		}

	}

	/*public void abc() {
	 * Date date = new Date();
       Timestamp timeStamp = new Timestamp(date.getTime());
		String sql = "insert into temp values('还是法度')";
		dbutil.execute(sql);
		sql = "select * from ActivityCallOver";
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = executeQuery(sql);
		String id = "";
		for (int i = 0; i < list.size(); i++) {
			map = (Map<String, Object>) list.get(i);
			id = map.get("id").toString();
			// name=map.get("name").toString();
			System.out.println(id);
		}
	}*/
}
