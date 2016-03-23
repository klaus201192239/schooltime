package utils;

import java.io.IOException;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class MemcachedUtil {

	private static MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("114.215.87.133:11211"));
	private static MemcachedClient cachedClient = null;

	public static void start() {
		try {
			
			
			
			cachedClient = builder.build();
			
			cachedClient.setSanitizeKeys(false);
			
		
		} catch (Exception e) {

		}
	}

	public static void stop() {

		try {
			cachedClient.shutdown();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean add(String key, Object value) {

		try {

			return cachedClient.add(key, 0, value);

		} catch (Exception e) {
			return false;
		}
	}

	public static boolean add(String key, Object value, Integer expire) {

		try {
			return cachedClient.add(key, expire, value);
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean put(String key, Object value) {

		try {

			return cachedClient.set(key, 0, value);

		} catch (Exception e) {
			return false;
		}

	}

	public static boolean put(String key, Object value, Integer expire) {

		try {

			return cachedClient.set(key, expire, value);

		} catch (Exception e) {
			return false;
		}

	}

	public static boolean replace(String key, Object value) {

		try {
			return cachedClient.replace(key, 0, value);
		} catch (Exception e) {
			return false;
		}

	}

	public static boolean replace(String key, Object value, Integer expire) {

		try {
			return cachedClient.replace(key, expire, value);
		} catch (Exception e) {
			return false;
		}

	}

	public static boolean delete(String key) {

		try {
			return cachedClient.delete(key);
		} catch (Exception e) {
			return false;
		}
	}

	public static Object get(String key) {

		try {
			return cachedClient.get(key);
		} catch (Exception e) {
			return null;
		}
	}

	

}
