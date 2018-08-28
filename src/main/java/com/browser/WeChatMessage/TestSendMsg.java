package com.browser.WeChatMessage;



import java.io.IOException;

public class TestSendMsg {
    public static void main(String[] args) {
        Send_weChatMsg sw = new Send_weChatMsg();
        try {
            String token = sw.getToken("wwf32888b1e25a3218", "f2k30zBlJKOogzQ1DTjm-LTqHWwjw8OJopZ-C_7q1Xc");
            String postdata = sw.createpostdata("@all", "text", 1000002, "content", "测试消息123");
            String resp = sw.post("utf-8", Send_weChatMsg.CONTENT_TYPE, (new UrlData()).getSendMessage_Url(), postdata, token);
            System.out.println("获取到的token======>" + token);
            System.out.println("请求数据======>" + postdata);
            System.out.println("发送微信的响应数据======>" + resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
