package com.browser.utils;

import java.util.HashMap;
import java.util.Map;

import com.browser.dao.entity.TblContractTokenInfo;
import com.browser.dao.mapper.TblContractTokenInfoMapper;

public class CtcBrowserUtil {

	private static TblContractTokenInfoMapper tblContractTokenInfoMapper = SpringContextUtil
			.getBean(TblContractTokenInfoMapper.class);

	
	// 根据代币合约ID 获得 代币合约信息
	public static TblContractTokenInfo getContractTokenInfo(String contractId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contractId", contractId);
		return tblContractTokenInfoMapper.selectByPrimaryKey(params);
	}
	
}
