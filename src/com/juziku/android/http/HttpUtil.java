package com.juziku.android.http;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;


/**
 * 和HTTP相关的操作
 * 
 * @author
 * 
 */
public class HttpUtil {
	
	/**
	 * 发送GET请求
	 * 
	 * @param link 链接
	 * @param charset 编码
	 * 
	 */
	public static String doGet(String link) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(link);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0");
			BufferedInputStream in = new BufferedInputStream(conn
					.getInputStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			for (int i = 0; (i = in.read(buf)) > 0;) {
				out.write(buf, 0, i);
			}
			out.flush();
			out.close();
			String s = new String(out.toByteArray(), "UTF-8");
			return s;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}finally{
			if(conn != null){
				conn.disconnect();
			}
		}
	}
	

	/**
	 * 发送POST请求
	 * 
	 * @param reqUrl          请求地址
	 * @param parameters      参数
	 * 
	 */
	public static String doPost(String reqUrl, Map<String, String> parameters) {
		HttpURLConnection urlConn = null;
		try {
			urlConn = sendPost(reqUrl, parameters);
			String responseContent = getContent(urlConn);
			return responseContent.trim();
		} finally {
			if (urlConn != null) {
				urlConn.disconnect();
			}
		}
	}

	private static String getContent(HttpURLConnection urlConn) {
		try {
			String responseContent = null;
			InputStream in = urlConn.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in,
					"UTF-8"));
			String tempLine = rd.readLine();
			StringBuffer tempStr = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				tempStr.append(tempLine);
				tempStr.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = tempStr.toString();
			rd.close();
			in.close();
			return responseContent;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private static HttpURLConnection sendPost(String reqUrl,
			Map<String, String> parameters) {
		HttpURLConnection urlConn = null;
		try {
			StringBuffer params = new StringBuffer();
			if (parameters != null) {
				for (Iterator<String> iter = parameters.keySet().iterator(); iter
						.hasNext();) {
					String name = iter.next();
					String value = parameters.get(name);
					params.append(name + "=");
					params.append(URLEncoder.encode(value, "UTF-8"));
					if (iter.hasNext())
						params.append("&");
				}
			}

			URL url = new URL(reqUrl);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setRequestMethod("POST");
			urlConn.setConnectTimeout(5000);// （单位：毫秒）jdk
			urlConn.setReadTimeout(5000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
			urlConn.setDoOutput(true);
			byte[] b = params.toString().getBytes();
			urlConn.getOutputStream().write(b, 0, b.length);
			urlConn.getOutputStream().flush();
			urlConn.getOutputStream().close();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return urlConn;
	}


}
