package com.browser.WeChatMessage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class Send_weChatMsg {
    private CloseableHttpClient httpClient;
    private HttpPost httpPost;//用于提交登陆数据
    private HttpGet httpGet;//用于获得登录后的页面
    public static final String CONTENT_TYPE = "Content-Type";
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//


    private static Gson gson = new Gson();


    /**
     * 微信授权请求，GET类型，获取授权响应，用于其他方法截取token
     *
     * @param Get_Token_Url
     * @return String 授权响应内容
     * @throws IOException
     */
    protected String toAuth(String Get_Token_Url) throws IOException {

        httpClient = HttpClients.createDefault();
        httpGet = new HttpGet(Get_Token_Url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        String resp;
        try {
            HttpEntity entity = response.getEntity();
            resp = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        LoggerFactory.getLogger(getClass()).info(" resp:{}", resp);
        return resp;
    }

    /**
     * 获取toAuth(String Get_Token_Url)返回结果中键值对中access_token键的值
     *
     * @param corpid 应用组织编号 corpsecret 应用秘钥
     */
    public  String getToken(String corpid, String corpsecret) throws IOException {
        Send_weChatMsg sw = new Send_weChatMsg();
        UrlData uData = new UrlData();
        uData.setGet_Token_Url(corpid, corpsecret);
        String resp = sw.toAuth(uData.getGet_Token_Url());

        Map<String, Object> map = gson.fromJson(resp,
                new TypeToken<Map<String, Object>>() {
                }.getType());
        return map.get("access_token").toString();
    }


    /**
     * @param  //touser发送消息接收者                                ，msgtype消息类型（文本/图片等），
     * @param  //application_id应用编号。
     * @param //本方法适用于text型微信消息，contentKey和contentValue只能组一对
     * @return String
     * @Title:创建微信发送请求post数据
     */
    public  String createpostdata(String touser, String msgtype,
                                    int application_id, String contentKey, String contentValue) {
        WechatData wcd = new WechatData();
        wcd.setTouser(touser);
        wcd.setAgentid(application_id);
        wcd.setMsgtype(msgtype);
        Map<Object, Object> content = new HashMap<Object, Object>();
        content.put(contentKey, contentValue  + "\n" +df.format(new Date()));
        wcd.setText(content);
        return gson.toJson(wcd);
    }

    /**
     * @param //charset消息编码                            ，contentType消息体内容类型，
     * @param url微信消息发送请求地址，data为post数据，token鉴权token
     * @return String
     * @Title 创建微信发送请求post实体
     */
    public String post(String charset, String contentType, String url,
                       String data, String token) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        httpPost = new HttpPost(url + token);
        httpPost.setHeader(CONTENT_TYPE, contentType);
        httpPost.setEntity(new StringEntity(data, charset));
        CloseableHttpResponse response = httpclient.execute(httpPost);
        String resp;
        try {
            HttpEntity entity = response.getEntity();
            resp = EntityUtils.toString(entity, charset);
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        LoggerFactory.getLogger(getClass()).info(
                "call [{}], param:{}, resp:{}", url, data, resp);
        return resp;
    }

}