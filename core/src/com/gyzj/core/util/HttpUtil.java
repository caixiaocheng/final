package com.gyzj.core.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import android.util.Log;

public class HttpUtil {
	/**
	 * 
	 * 
	 * @param xmlData
	 *            请求数据
	 * @param serverURL
	 *            服务器地址
	 * @return
	 * @throws IOException 
	 * @author 程才
	 * @date 2004、09、03
	 * @detail 向Internet发送请求
	 */
	public static String ServerURL(String xmlData) throws IOException {
		String result = null;
		InputStream is = null;
		DataOutputStream outStream = null;
		HttpURLConnection conn = null;
		byte[] xmlbyte = null;
		try {
			xmlbyte = xmlData.getBytes("UTF-8");
			URL url = new URL(Constant.HTTP_REQ_PICTURE_PREFIX);
			conn = (HttpURLConnection) url.openConnection();
			// conn.setConnectTimeout(30 * 1000);
			conn.setDoOutput(true);// 允许输出
			conn.setUseCaches(false);// 不使用Cache
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Length",String.valueOf(xmlbyte.length));
			conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
			outStream = new DataOutputStream(conn.getOutputStream());
			outStream.write(xmlbyte);// 发送xml数据
			outStream.flush();
			if (conn.getResponseCode() != 200)
				throw new RuntimeException("请求url失败");
			is = conn.getInputStream();// 获取返回数据
			result = ReadAsString(is, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.i("ServerURL", e.getMessage());
			e.printStackTrace();
		} catch (MalformedURLException e) {
			Log.i("ServerURL", e.getMessage());
			e.printStackTrace();
		} catch (ProtocolException e) {
			Log.i("ServerURL", e.getMessage());
			e.printStackTrace();
		}  finally {
			if (outStream != null && conn != null && xmlbyte != null&&is!=null) {
				try {
					outStream.close();
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				xmlbyte = null;
				conn.disconnect();
			}
		}
		return result;
	}
	public static String ReadAsString(InputStream is, String Coding)
			throws IOException {
		byte[] by = new byte[is.available()];
		StringBuffer data = new StringBuffer();
		InputStreamReader isr=new InputStreamReader(is);
		char buf[] = new char[20];
		try {
//			int n;
//			while ((n = is.read(by)) != -1) {
//				data.append(new String(by, 0, n,"utf-8"));
//			}
			int nBufLen = isr.read(buf);
			while(nBufLen!=-1){
				data.append(new String(buf, 0, nBufLen));
				nBufLen = isr.read(buf);
				} 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null && by != null) {
				is.close();
				by = null;
			}
		}
		return data.toString();
	}



}
