package com.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @Title: URLConnection
 * @Description: 
 * @Company: 北京华宇元典信息服务有限公司
 *
 * @author YinChengfeng
 * @version 1.0
 * @date 2018年4月9日 下午6:00:26
 *
 */
public class URLConnection {
    private static final Logger logger = LoggerFactory.getLogger(URLConnection.class);

    /**
     * @param geturl
     * @param params
     * @return
     * @throws IOException
     */
    public static String get(String geturl, String params) throws IOException {

        String realUrl = geturl + ((null == params || 0 == params.length()) ? "" : "?" + params);

        //1.通过在 URL 上调用 openConnection 方法创建连接对象
        URL url = new URL(realUrl);
        //此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类的子类HttpURLConnection,
        //故此处最好将其转化为HttpURLConnection类型的对象
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        //2.处理设置参数和一般请求属性
        //2.1设置参数
        //可以根据请求的需要设置参数 
        conn.setRequestMethod("GET"); //默认为GET 所以GET不设置也行
        conn.setUseCaches(false);
        conn.setConnectTimeout(600 * 1000); //请求超时时间

        //2.2请求属性 
        //设置通用的请求属性 更多的头字段信息可以查阅HTTP协议
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");

        //3.使用 connect 方法建立到远程对象的实际连接。 
        conn.connect();

        //4.远程对象变为可用。远程对象的头字段和内容变为可访问。
        //4.1获取HTTP 响应消息获取状态码
        if (conn.getResponseCode() == 200) {
            //4.2获取响应的头字段
            Map<String, List<String>> headers = conn.getHeaderFields();
            logger.info("{}", headers); //输出头字段

            //4.3获取响应正文
            BufferedReader reader = null;
            StringBuffer resultBuffer = new StringBuffer();
            String tempLine = null;

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
            //System.out.println(resultBuffer);
            reader.close();
            return resultBuffer.toString();
        }

        return null;
    }

    /**
     * @param posturl
     * @param params
     * @return
     * @throws IOException 
     */
    public static String post(String posturl, String params) throws IOException {

        //1.通过在 URL 上调用 openConnection 方法创建连接对象
        URL url = new URL(posturl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        //2.处理设置参数和一般请求属性
        //2.1设置参数
        //可以根据请求的需要设置参数 
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setUseCaches(false); //是否可以使用缓存 不使用缓存
        conn.setConnectTimeout(5000);//请求超时时间

        //2.2请求属性 
        //设置通用的请求属性 消息报头 即设置头字段 更多的头字段信息可以查阅HTTP协议
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");

        //2.3设置请求正文 即要提交的数据
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
        pw.print(params);
        pw.flush();
        pw.close();

        //3.使用 connect 方法建立到远程对象的实际连接。 
        conn.connect();

        //4.远程对象变为可用。远程对象的头字段和内容变为可访问。 
        //4.1获取HTTP 响应消息获取状态码
        if (conn.getResponseCode() == 200) {
            //4.2获取响应的头字段
            Map<String, List<String>> headers = conn.getHeaderFields();
            System.out.println(headers); //输出头字段

            //4.3获取响应正文
            BufferedReader reader = null;
            StringBuffer resultBuffer = new StringBuffer();
            String tempLine = null;

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
            //System.out.println(resultBuffer);
            reader.close();
            return resultBuffer.toString();
        }
        return null;
    }

}
