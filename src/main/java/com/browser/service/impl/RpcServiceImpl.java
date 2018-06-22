package com.browser.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.browser.service.RpcService;
import com.browser.tools.BlockchainTool;
import com.browser.tools.common.RpcLink;
import com.browser.tools.exception.BrowserException;

@Service
public class RpcServiceImpl implements RpcService {

	private static Logger logger = LoggerFactory.getLogger(RpcServiceImpl.class);

	private Socket socket = null;

	@Value("${wallet.socket.username}")
	private String username;

	@Value("${wallet.socket.password}")
	private String password;

	@Override
	public void setConnetion(Socket socket) {
		this.socket = socket;
	}
	
	public boolean login() throws Exception {
		boolean login = false;
		if(socket!=null){
			PrintWriter os = null;
			BufferedReader is = null;
			os = new PrintWriter(socket.getOutputStream());
			is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
			long id = BlockchainTool.getId();
			JSONObject sendObject = new JSONObject();
			sendObject.put("jsonrpc", "2.0");
			sendObject.put("id", id);
			sendObject.put("method", "login");
		
			JSONArray paramsObject = new JSONArray();
			paramsObject.add(username);
			paramsObject.add(password);
			sendObject.put("params", paramsObject);
			String sendMessage = sendObject.toJSONString();
		
			os.println(sendMessage);
			os.flush();
			String returnMessage = is.readLine();
			JSONObject returnObject = JSONObject.parseObject(returnMessage);
			String result = returnObject.getString("result");
			if ("true".equals(result)) {
				login = true;
			}
		}
		return login;
	}
	
	@Override
	public String send(String method, List<Object> params) throws Exception{
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

		PrintWriter os = null;
		BufferedReader is = null;

		//try {
			// 获取链接
			os = new PrintWriter(socket.getOutputStream());
			is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// 发送报文
			os.println(sendMessage);
			os.flush();
	
			// 获取报文
			String returnMessage = is.readLine();

			JSONObject returnObject = JSONObject.parseObject(returnMessage);
			long idReturn = returnObject.getLongValue("id");
	
			if (idSend != idReturn) {
				throw new BrowserException("发送id和返回id不一致 " + returnMessage);
			}
			result = returnObject.getString("result");
		//} catch (IOException e) {
		//	e.printStackTrace();
		//} 
		return result;
	}

	@Override
	public String getBlockNum(Long num) throws Exception{
		List<Object> params = new ArrayList<Object>();
		params.add(num);
		return send(RpcLink.BLOCK_NUM,params);
	}

	@Override
	public String getBlockSignee(Long num) throws Exception{
		List<Object> params = new ArrayList<Object>();
		params.add(num);
		return send(RpcLink.BLOCK_SIGNEE,params);
	}

	@Override
	public String getBlockTrx(String trxId) throws Exception{
		List<Object> params = new ArrayList<Object>();
		params.add(trxId);
		return send(RpcLink.BLOCK_TRX,params);
	}

	@Override
	public Long getBlockCount() throws Exception{
		List<Object> params = new ArrayList<Object>();
		String result = send(RpcLink.BLOCK_COUNT,params);
		if(result!=null)
			return Long.valueOf(result);
		else
			return 0L;
	}

	@Override
	public String getBlockchainBalance(String balanceId) throws Exception{
		List<Object> params = new ArrayList<Object>();
		params.add(balanceId);
		return send(RpcLink.BLOCK_BALANCE,params);
	}

	@Override
	public String getContractTrx(String trxId) throws Exception{
		List<Object> params = new ArrayList<Object>();
		params.add(trxId);
		return send(RpcLink.CONTRACT_TRX,params);
	}

	@Override
	public String getContractInfo(String contractId) throws Exception{
		List<Object> params = new ArrayList<Object>();
		params.add(contractId);
		return send(RpcLink.CONTRACT_INFO,params);
	}

	@Override
	public String getContractBalance(String contractId) throws Exception{
		List<Object> params = new ArrayList<Object>();
		params.add(contractId);
		return send(RpcLink.CONTRACT_BALANCE,params);
	}

	@Override
	public String getAssetInfo(Integer assetId) throws Exception{
		List<Object> params = new ArrayList<Object>();
		params.add(assetId);
		return send(RpcLink.BLOCK_ASSET,params);
	}

	/**
	 * 查询多签账户
	 * @param required
	 * @param addresses
	 * @return
	 */
	@Override
	public String getMultisigAccount(Integer required, JSONArray addresses) throws Exception{
		List<Object> params = new ArrayList<Object>();
		params.add("UB");
		params.add(required);
		params.add(addresses);
		return send(RpcLink.MULTISIG_ACCOUNT,params);
	}

	@Override
    public String getBalanceByAddress(String address) throws Exception{
		List<Object> params = new ArrayList<Object>();
		params.add(address);
		return send(RpcLink.BLOCKCHAIN_LIST_ADDRESS_BALANCES,params);
    }

	
}
