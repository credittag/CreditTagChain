package com.browser.WeChatMessage;

public class WechatData {
    //发送微信消息的URL
    //    String sendMsgUrl="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";
    String touser;
    String msgtype;
    int agentid;
    Object text;//实际接收Map类型数据

    public Object getText() {
        return text;
    }

    public void setText(Object text) {
        this.text = text;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public int getAgentid() {
        return agentid;
    }

    public void setAgentid(int agentid) {
        this.agentid = agentid;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

}