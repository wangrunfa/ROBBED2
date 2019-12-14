package com.robbad.util;

import com.submail.utils.RequestEncoder;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;


/**
 * SUBMAIL
 * 赛邮-工具类
 */
public class SUBMAILUtil {

    /**
     * verificationCodeSMS
     * 验证码短信
     * @return
     * 参数String phone
     * 手机号
     */
    public static Map<String,Object> verificationCodeSMS(String phone) {
        try {
            main(phone);
            return WebTools.returnData(verificationCode,0);
        }catch (NoSuchAlgorithmException e){
            return  WebTools.returnData("失败",1);
        }

    }
    //验证码数量
    private static int verificationCodeNumber=4;
    //随机验证码
    private static String verificationCode=RandomNumberUtil.randomVerificationCode(verificationCodeNumber).toString();;
    //APPID
    private static final String APPID = "44363";
    //APPKEY
    private static final String APPKEY = "c5f0b932f07ff369bc04196e9885d08f";
    //验证码短信内容
    private static String content = "【Cedar8】欢迎您使用Cedar8，您的注册验证码是"+verificationCode;
    //加密方式
    private static final String signtype = "md5";

    /**
     * 时间戳接口配置
     */
    private static final String TIMESTAMP = "https://api.mysubmail.com/service/timestamp";
    /**
     * 备用接口
     * http://api.mysubmail.com/service/timestamp
     * https://api.submail.cn/service/timestamp
     * http://api.submail.cn/service/timestamp
     */

    private static final String TYPE_MD5 = "md5";
    private static final String TYPE_SHA1 = "sha1";
    /**
     * API 请求接口配置
     */
    private static final String URL="https://api.mysubmail.com/message/send";
    /**
     * 备用接口
     * @param phone
     * http://api.mysubmail.com/message/send
     * https://api.submail.cn/message/send
     * http://api.submail.cn/message/send
     * @throws NoSuchAlgorithmException
     */

    private static void main(String phone) throws NoSuchAlgorithmException {
        TreeMap<String, Object> requestData = new TreeMap<String, Object>();
        /**
         * --------------------------------参数配置------------------------------------
         * 请仔细阅读参数配置说明
         *
         * 配置参数包括 appid, appkey ,to , signtype, 	content
         * 用户参数设置，其中 appid, appkey, content, to 为必须参数
         * 请访问 submail 官网创建并获取 appid 和 appkey，链接：https://www.mysubmail.com/chs/sms/apps
         * 请访问 submail 官网创建获取短信模板内容，链接：https://www.mysubmail.com/chs/sms/templates
         * content 参数
         *   |正文中必须提交有效的短信签名，且您的短信签名必须放在短信的最前端，e.g. 【SUBMAIL】您的短信验证码：4438，请在10分钟内输入。
         *   |content 参数将会与您账户中的短信模板进行匹配，如 API 返回 420 错误，请在您的账户中添加短信模板，并提交审核
         *   |请将 短信正文控制在 350 个字符以内
         * signtype 为可选参数: normal | md5 | sha1
         * 当 signtype 参数为空时，默认为 normal
         * --------------------------------------------------------------------------
         */

        /**
         *  ---------------------------------------------------------------------------
         */

/**
 *  签名验证方式
 *  详细说明可参考 SUBMAIL 官网，开发文档 → 开始 → API 授权与验证机制
 */
        requestData.put("appid", APPID);
        requestData.put("content", content);
        requestData.put("to", phone);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        @SuppressWarnings("deprecation")
        ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE,HTTP.UTF_8);
        for(Map.Entry<String, Object> entry: requestData.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            if(value instanceof String){
                builder.addTextBody(key, String.valueOf(value),contentType);
            }
        }
        if(signtype.equals(TYPE_MD5) || signtype.equals(TYPE_SHA1)){
            String timestamp = getTimestamp();
            requestData.put("timestamp", getTimestamp());
            requestData.put("sign_type", signtype);
            String signStr = APPID + APPKEY + RequestEncoder.formatRequest(requestData) + APPID + APPKEY;

            builder.addTextBody("timestamp", timestamp);
            builder.addTextBody("sign_type", signtype);
            builder.addTextBody("signature", RequestEncoder.encode(signtype, signStr), contentType);
        }else{
            builder.addTextBody("signature", APPKEY, contentType);
        }
        /**
         * http post 请求接口
         * 成功返回 status: success,其中 fee 参数为短信费用 ，credits 参数为剩余短信余额
         * 详细的 API 错误日志请访问 SUBMAIL 官网 → 开发文档 → DEBUG → API 错误代码
         */
        HttpPost httpPost = new HttpPost(URL);
        httpPost.addHeader("charset", "UTF-8");
        httpPost.setEntity(builder.build());
        try{
            CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
            HttpResponse response = closeableHttpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if(httpEntity != null){
                String jsonStr = EntityUtils.toString(httpEntity, "UTF-8");
                //打印基本信息
                System.out.println(jsonStr);
            }
        }catch(ClientProtocolException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    /**
     * 获取时间戳
     * @return
     */
    private static String getTimestamp(){
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet(TIMESTAMP);
        try{
            HttpResponse response = closeableHttpClient.execute(httpget);
            HttpEntity httpEntity = response.getEntity();
            String jsonStr = EntityUtils.toString(httpEntity,"UTF-8");
            if(jsonStr != null){
                JSONObject json = JSONObject.fromObject(jsonStr);
                return json.getString("timestamp");
            }
            closeableHttpClient.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
