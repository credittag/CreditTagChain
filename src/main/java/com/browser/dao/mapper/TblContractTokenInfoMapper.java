package com.browser.dao.mapper;

import java.util.Map;

import com.browser.dao.entity.TblContractTokenInfo;

public interface TblContractTokenInfoMapper {

	public int insert(TblContractTokenInfo record);

	public TblContractTokenInfo selectByPrimaryKey(Map<String, Object> params);
}