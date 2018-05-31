package com.browser.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.browser.config.RealData;
import com.browser.dao.entity.*;
import com.browser.dao.mapper.TblBcBlockMapper;
import com.browser.dao.mapper.TblBcNewsMapper;
import com.browser.dao.mapper.TblBcStatisticsMapper;
import com.browser.dao.mapper.TblBcTransactionMapper;
import com.browser.service.StatisService;
import com.browser.tools.Constant;
import com.browser.tools.common.DateUtil;
import com.browser.tools.common.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StatisServiceImpl implements StatisService {
	
	@Autowired
	private TblBcStatisticsMapper tblBcStatisticsMapper;
	
	@Autowired
	private TblBcNewsMapper tblBcNewsMapper;

	@Autowired
	private TblBcBlockMapper tblBcBlockMapper;

	@Autowired
	private TblBcTransactionMapper tblBcTransactionMapper;

	@Autowired
	private RealData realData;

	@Override
	public TblBcStatistics selectByPrimaryKey(Long id) {
		return tblBcStatisticsMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<TblBcNews> selectByNew() {
		return tblBcNewsMapper.selectByNew();
	}

	@Override
	public int updateByPrimaryKeySelective(TblBcStatistics statistic) {
		return tblBcStatisticsMapper.updateByPrimaryKeySelective(statistic);
	}

	@Override
	public 	void newBlockStatic(){
		List<TblBcBlock> block = tblBcBlockMapper.selectByNewData();
		realData.setBlockInfo(JSONObject.toJSONString(block));
	}

	@Override
	public void newTransactionStatic(){
		List<TblBcTransaction> trx = tblBcTransactionMapper.selectByTrxData();
		if (null != trx && trx.size() > 0) {
			for (TblBcTransaction tb : trx) {
				DateUtil.trxTimeToDay(tb);
				tb.setAmountBig((new BigDecimal(tb.getAmount()).divide(new BigDecimal(Constant.PRECISION))));
				tb.setFeeBig((new BigDecimal(tb.getFee()).divide(new BigDecimal(Constant.PRECISION))));
			}
		}
		realData.setTransactionInfo(JSONObject.toJSONString(trx));
	}
	
}
