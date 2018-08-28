package com.browser.dao.mapper;

import java.util.List;

import com.browser.dao.entity.TblRiskAddressEntity;

/**
 * 
 * 
 * @author gen
 * @email gen1@ctc
 * @date 2018-07-30 10:26:44
 */
public interface TblRiskAddressDao  {
	//获取全部的风险地址
	List<TblRiskAddressEntity> queryAll();
	
}
