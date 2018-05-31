package com.browser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.browser.tools.BlockchainTool;
import com.browser.tools.exception.BrowserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/5 0005.
 */
public class Test {

    public static String send(String method, List<Object> params) {
        String result = null;

        long idSend = BlockchainTool.getId();

        JSONObject sendObject = new JSONObject();
        sendObject.put("jsonrpc", "2.0");
        sendObject.put("id", idSend);
        sendObject.put("method", method);

        // list 转换位 json
        JSONArray paramsObject = new JSONArray();
        paramsObject.addAll(params);

        sendObject.put("params", paramsObject);

        String sendMessage = sendObject.toJSONString();
       System.out.println("发送字符串："+sendMessage);

        PrintWriter os = null;

        BufferedReader is = null;

        Socket socket = null;
        long start = System.currentTimeMillis();
        try {
            // 获取链接
            socket = new Socket();

            SocketAddress remoteAddr = new InetSocketAddress("127.0.0.1", 17001);

            socket.connect(remoteAddr, 30000);
            os = new PrintWriter(socket.getOutputStream());
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 登录
            //login(os, is);

            // 发送报文
            os.println(sendMessage);
            os.flush();

            // 获取报文
            String returnMessage = is.readLine();
            System.out.println("返回报文信息："+returnMessage);
            JSONObject returnObject = JSONObject.parseObject(returnMessage);
            long idReturn = returnObject.getLongValue("id");

            if (idSend != idReturn) {
                throw new BrowserException("发送id和返回id不一致 " + returnMessage);
            }

            result = returnObject.getString("result");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Throwable e) {
                e.printStackTrace();
            }

            try {
                os.close();
            } catch (Throwable e) {
                e.printStackTrace();
            }

            try {
                socket.close();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
       // System.out.println("RPC接口调用耗时为："+ (end-start));
        return result;
    }

    public static void login(PrintWriter os, BufferedReader is) throws IOException {

        long id = BlockchainTool.getId();

        JSONObject sendObject = new JSONObject();
        sendObject.put("jsonrpc", "2.0");
        sendObject.put("id", id);
        sendObject.put("method", "login");

        JSONArray paramsObject = new JSONArray();
        paramsObject.add("test");
        paramsObject.add("test");

        sendObject.put("params", paramsObject);

        String sendMessage = sendObject.toJSONString();

        os.println(sendMessage);
        os.flush();

        String returnMessage = is.readLine();

        JSONObject returnObject = JSONObject.parseObject(returnMessage);

        String result = returnObject.getString("result");

        if (!"true".equals(result)) {
            throw new BrowserException("登录失败 " + returnMessage);
        }
    }

    public static void test1(){
        for(int i=631316;i>0;i--){
            List<Object> params = new ArrayList<>();
            params.add(i);
            JSONObject jso = JSONObject.parseObject(send("blockchain_get_block",params));
            JSONArray jsa = jso.getJSONArray("user_transaction_ids");
            if(jsa.size()>0){
                System.out.println(jso);
                break;
            }
        }
    }

    public static void getBlock(){
        List<Object> params = new ArrayList<>();
        params.add(1);
        JSONObject jso = JSONObject.parseObject(send("blockchain_get_block",params));
        System.out.println(jso);
    }

    public static void transaction(){
        List<Object> params = new ArrayList<>();
        params.add("000b3e8721ee23f6f81c19d14ebc96735e475fec");
        String result = send("blockchain_get_transaction",params);
        System.out.println(result);
    }

    public static void get_asset(){
        List<Object> params = new ArrayList<>();
        params.add(0);
        String result = send("blockchain_get_asset",params);
        System.out.println(result);
    }

    public static void pretty_transaction(){
        List<Object> params = new ArrayList<>();
        params.add("0426569489f5122ea435f560f737cbff5f0d1278");
        String result = send("blockchain_get_pretty_transaction",params);
        System.out.println(result);
    }

    public static void contract_transaction(){
        List<Object> params = new ArrayList<>();
        params.add("d5553a34805ce2f363cf0deb005397c5332d6c75");
        String result = send("blockchain_get_pretty_contract_transaction",params);
        System.out.println(result);
    }

    public static void contract_info(){
        List<Object> params = new ArrayList<>();
        params.add("CONFBqDNRUidZyRyaJKiZfKc4W6AwZcLWFpL");
        String result = send("get_contract_info",params);
        System.out.println(result);
    }

    public static void get_contract_balance(){
        List<Object> params = new ArrayList<>();
        params.add("CONLEbQryxefxK4aZy4mvKfaRL2vdSSWkbpn");
        String result = send("get_contract_balance",params);
        System.out.println(result);
    }

    public static void main(String[] args){

    }

}
