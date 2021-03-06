package com.robbad.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MessagePostFromUtil {
    private static String  privateAppCode="304e05a307924cb2aa933fedf58379b1";
    public static JSONObject  messagePostFrom(String name, String mobile, String idcard) throws IOException {
        String url = "https://mobile3elements.shumaidata.com/mobile/verify_real_name";
        String appCode = privateAppCode;
        Map<String,String> params = new HashMap<>();
        params.put("idcard",idcard);
        params.put("mobile",mobile);
        params.put("name",name);
        return postForm(appCode,url,params);

    }

    /**
     * 用到的HTTP工具包：okhttp 3.13.1
     *  <dependency>
     *     <groupId>com.squareup.okhttp3</groupId>
     *     <artifactId>okhttp</artifactId>
     *     <version>3.13.1</version>
     *  </dependency>
     */
    public static JSONObject  postForm(String appCode, String url, Map<String,String> params) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().build();
        FormBody.Builder formbuilder = new FormBody.Builder();
        Iterator<String> it = params.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            formbuilder.add(key, params.get(key));
        }
        FormBody body = formbuilder.build();
        Request request = new Request.Builder().url(url).addHeader("Authorization", "APPCODE " + appCode).post(body).build();
        Response response = client.newCall(request).execute();
        System.out.println("返回状态码" + response.code() + ",message:" + response.message());
       JSONObject jsonObjects= JSON.parseObject(response.body().string());
        return jsonObjects;
//
    }
}
