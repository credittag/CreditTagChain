package com.browser.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.browser.dao.entity.BcBlock;
import com.browser.dao.entity.TblBcBlock;
import com.browser.dao.entity.TblBcStatistics;
import com.browser.dao.entity.TblBcTransaction;
import com.browser.dao.entity.TblBcTransactionEx;
import com.browser.dao.mapper.TblBcBlockMapper;
import com.browser.dao.mapper.TblBcStatisticsMapper;
import com.browser.dao.mapper.TblBcTransactionExMapper;
import com.browser.dao.mapper.TblBcTransactionMapper;
import com.browser.protocol.EUDataGridResult;
import com.browser.service.BlockService;
import com.browser.tools.Constant;
import com.browser.tools.common.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class BlockServiceImpl implements BlockService {

	private static Logger logger = LoggerFactory.getLogger(BlockServiceImpl.class);

	@Autowired
	private TblBcBlockMapper tblBcBlockMapper;

	@Autowired
	private TblBcTransactionMapper tblBcTransactionMapper;

	@Autowired
	private TblBcStatisticsMapper tblBcStatisticsMapper;

	@Autowired
	private TblBcTransactionExMapper tblBcTransactionExMapper;

	@Override
	public TblBcBlock selectByBlockNum(Long blocknUm) {
		return tblBcBlockMapper.selectByBlockNum(blocknUm);
	}

	@Override
	public int insertSelective(String blockMsg, String trxStr, String singee) {
		TblBcBlock bc = StringUtil.getBlockInfo(blockMsg, singee);
		int num = tblBcBlockMapper.insertSelective(bc);
		if (null != trxStr) {
			TblBcTransaction trx = StringUtil.getBlockTrx(JSONArray.parseArray(trxStr));
			trx.setBlockId(bc.getBlockId());
			int sum = tblBcTransactionMapper.insertSelective(trx);
			// 判断交易数据是否成功,统计交易信息
			if (sum > 0) {
				byCountTrx(trx);
			}
		}
		return num;
	}

	@Override
	public void insertBatchBlock(List<TblBcBlock> list) {
		tblBcBlockMapper.insertBatch(list);
	}

	@Override
	public void insertTransactionBlock(List<TblBcTransaction> list) {
		tblBcTransactionMapper.insertBatch(list);
	}

	public void byCountTrx(TblBcTransaction trx) {
		int sum = tblBcStatisticsMapper.selectByCount();
		if (sum != 0) {
			TblBcStatistics record = tblBcStatisticsMapper.selectByPrimaryKey(100L);
			record.setAllTrxAmount(new BigDecimal(trx.getAmount()));
			record.setAllTrxFee(new BigDecimal(trx.getFee()));
			tblBcStatisticsMapper.updateByPrimaryKey(record);
		} else {
			TblBcStatistics bs = new TblBcStatistics();
			bs.setTrxCount(1);
			bs.setAllTrxAmount(new BigDecimal(trx.getAmount()));
			bs.setAllTrxFee(new BigDecimal(trx.getFee()));
			bs.setId(100L);
			tblBcStatisticsMapper.insertSelective(bs);
		}
	}

	@Override
	public int queryByCount() {
		return tblBcBlockMapper.queryByCount();
	}

	@Override
	public Long queryBlockNum() {
		return tblBcBlockMapper.queryBlockNum();
	}

	@Override
	public String selectByNewData() {
		List<TblBcBlock> block = tblBcBlockMapper.selectByNewData();
		List<BcBlock> bcBlock = new ArrayList<BcBlock>();
		if (null != block) {
			for (TblBcBlock bc : block) {
				BcBlock bcs = new BcBlock();
				bcs.setBlockId(bc.getBlockId());
				bcs.setBlockNum(bc.getBlockNum());
				bcs.setBlockSize(bc.getBlockSize());
				bcs.setSignee(bc.getSignee());
				bcs.setSigneeTime(bc.getBlockTime());
				List<TblBcTransaction> trx = tblBcTransactionMapper.selectByBlockId(bc.getBlockId());
				if (null != trx && trx.size() > 0) {
					bcs.setTrxNum(trx.size());
					Double amount = (double) 0;
					for (TblBcTransaction tran : trx) {
						amount = amount + tran.getAmount();
					}
					bcs.setTrxAmount(String.format("%.8f", amount / Constant.PRECISION));
				} else {
					bcs.setTrxNum(0);
					bcs.setTrxAmount("0");
				}
				bcBlock.add(bcs);
			}
		}
		return JSONObject.toJSONString(bcBlock);
	}

	@Override
	public String blockNew() {
		List<TblBcBlock> block = tblBcBlockMapper.selectByNewData();

		return JSONObject.toJSONString(block);
	}

	@Override
	public TblBcBlock selectByPrimaryKey(String blockId) {
		return tblBcBlockMapper.selectByPrimaryKey(blockId);
	}

	@Override
	public String search(Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("key", "fail");
		String keyword = String.valueOf(params.get("keyword")).trim();
		if (keyword.matches("^\\d+$")) {
			TblBcBlock bc = tblBcBlockMapper.selectByBlockNum(Long.valueOf(keyword));
			if (null != bc) {
				result.put("key", "blockNum");
				result.put("values", bc.getBlockNum());
			}
		} else {
			TblBcTransaction trx = tblBcTransactionMapper.selectByPrimaryKey(keyword);
			if (null != trx) {
				result.put("key", "trx");
				result.put("values", trx.getTrxId());
			} else {
				TblBcBlock ct = tblBcBlockMapper.selectByPrimaryKey(keyword);
				if (null != ct) {
					result.put("key", "block");
					result.put("values", ct.getBlockId());
				}
				if (null == ct && null == trx) {
					result.put("key", "fail");
				}
			}
		}
		return JSONObject.toJSONString(result);
	}

	@Override
	public void insertTransactionEx(List<TblBcTransactionEx> list) {
		if (list != null && list.size() > 0) {
			tblBcTransactionExMapper.insertBatch(list);
		}
	}

	@Override
	public EUDataGridResult queryBlockList(Integer page, Integer rows,TblBcBlock tblBcBlock) {
		PageHelper.startPage(page, rows);
		EUDataGridResult result = new EUDataGridResult();
		List<TblBcBlock> list = tblBcBlockMapper.selectByNewDataNoPage(tblBcBlock);

		result.setRows(list);
		PageInfo<TblBcBlock> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		result.setPages(pageInfo.getPages());
		return result;
	}

}
