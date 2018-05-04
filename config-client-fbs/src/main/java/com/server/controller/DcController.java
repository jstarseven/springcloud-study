package com.server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

@RefreshScope
@RestController
public class DcController {

    @Value("${from}")
    private String from;

    @RequestMapping("/from")
    public String from() {
        return this.from;
    }

    @RequestMapping("/fresh")
    public String refresh() {
        try {
            post("http://localhost:7002/refresh", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
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
