package com.browser.service;

import java.net.Socket;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

public interface RpcService {
	
	/**
	 * 
	* @Title: send 
	* @Description: 获取接口数据
	* @author David
	* @param 
	* @return String 
	* @throws
	 */

	void setConnetion(Socket socket);
	boolean login() throws Exception;
	String send(String method, List<Object> params) throws Exception;

	/**
	 *
	* @Title: getBlockNum
	* @Description:调用获取快号数据
	* @author David
	* @param
	* @return String 
	* @throws
	 */
	String getBlockNum(Long num) throws Exception;

	/**
	 *
	* @Title: getBlockSignee
	* @Description:获取打包代理数据
	* @author David
	* @param
	* @return String 
	* @throws
	 */
	String getBlockSignee(Long num) throws Exception;

	/**
	 *
	* @Title: getBlockTrx
	* @Description:获取快交易信息
	* @author David
	* @param
	* @return String 
	* @throws
	 */
	String getBlockTrx(String trxId) throws Exception;

	/**
	 * 获取最大块号
	 * @return
	 */
	Long getBlockCount() throws Exception;

	String getBlockchainBalance(String balanceId) throws Exception;

	/**
	 * 获取合约交易信息
	 * @param trxId
	 * @param socket
	 * @param os
	 * @param is
	 * @return
	 */
	String getContractTrx(String trxId) throws Exception;

	String getContractInfo(String contractId) throws Exception;

	String getContractBalance(String contractId) throws Exception;

	String getAssetInfo(Integer assetId) throws Exception;

	String getMultisigAccount(Integer required, JSONArray addresses) throws Exception;
	
	String getBalanceByAddress(String address) throws Exception;

}
