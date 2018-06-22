package com.browser.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.browser.config.RealData;
import com.browser.dao.entity.TblAsset;
import com.browser.dao.entity.TblBcStatistics;
import com.browser.dao.entity.TblBcTransaction;
import com.browser.dao.entity.TblBcTransactionEx;
import com.browser.dao.mapper.TblBcStatisticsMapper;
import com.browser.dao.mapper.TblBcTransactionExMapper;
import com.browser.dao.mapper.TblBcTransactionMapper;
import com.browser.protocol.EUDataGridResult;
import com.browser.service.RpcService;
import com.browser.service.TransactionService;
import com.browser.tools.Constant;
import com.browser.tools.common.DateUtil;
import com.browser.tools.common.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class TransactionServiceImpl implements TransactionService {

	private static Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
	@Autowired
	private RealData realData;

	@Autowired
	private TblBcTransactionMapper tblBcTransactionMapper;

	@Autowired
	private TblBcStatisticsMapper tblBcStatisticsMapper;

	@Autowired
	private TblBcTransactionExMapper tblBcTransactionExMapper;

	@Autowired
	private RpcService socketService;


	@Override
	public String selectByTrxData() throws Exception{
		List<TblBcTransaction> trx = tblBcTransactionMapper.selectByTrxData();
		if (null != trx && trx.size() > 0) {
			for (TblBcTransaction tb : trx) {
				DateUtil.trxTimeToDay(tb);
				TblAsset tblAsset = realData.getSymbolByAssetId(tb.getAssetId());
				tb.setFeeStr(StringUtil.div(new BigDecimal(tb.getFee()), new BigDecimal(tblAsset.getPrecision()), Constant.PRECISION_LENGTH).toString());
				tb.setAmountStr(StringUtil.div(new BigDecimal(tb.getAmount()), new BigDecimal(tblAsset.getPrecision()), Constant.PRECISION_LENGTH).toString());
				tb.setSymbol(tblAsset.getSymbol());
			}
		}
		return JSONObject.toJSONString(trx);
	}

	@Override
	public List<TblBcTransaction> selectByBlockId(String blockId) {
		List<TblBcTransaction> list = tblBcTransactionMapper.selectByBlockId(blockId);
		if (list != null && list.size() > 0) {
			for (TblBcTransaction trx : list) {
				String trxTypeStr = getTrxTypeStr(trx.getTrxType());
				trx.setTrxTypeStr(trxTypeStr);
				trx.setAmountBig(new BigDecimal(trx.getAmount()).divide(new BigDecimal(Constant.PRECISION)));
				trx.setFeeBig(new BigDecimal(trx.getFee()).divide(new BigDecimal(Constant.PRECISION)));
			}
		}
		return list;
	}

	// TODO
	@Override
	public Map<String, Object> queryNum(String blockId) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<TblBcTransaction> trx = tblBcTransactionMapper.selectByBlockId(blockId);
		if (null != trx) {
			Long amountNum = 0L;
			Long feeNum = 0L;
			for (TblBcTransaction tran : trx) {
				amountNum += tran.getAmount();
				feeNum += tran.getFee();
			}

			result.put("amountNum", StringUtil.div(new BigDecimal(amountNum), new BigDecimal(Constant.PRECISION), Constant.PRECISION_LENGTH));
			result.put("feeNum", StringUtil.div(new BigDecimal(feeNum), new BigDecimal(Constant.PRECISION), Constant.PRECISION_LENGTH));
		}

		return result;
	}

	@Override
	public TblBcTransaction selectByPrimaryKey(String trxId) {
		return tblBcTransactionMapper.selectByPrimaryKey(trxId);
	}

	@Override
	public TblBcTransaction queryByTrxId(String trxId) {
		TblBcTransaction result = tblBcTransactionMapper.queryByTrxId(trxId);

		// 查询扩展交易
		if (result.getTrxType() != Constant.TRX_TYPE_TRANSFER) {
			List<TblBcTransactionEx> transactionExList = tblBcTransactionExMapper.selectByOrigTrxId(result.getTrxId());
			if (transactionExList != null && transactionExList.size() > 0) {
				for (TblBcTransactionEx ex : transactionExList) {
					ex.setAmountBig(new BigDecimal(ex.getAmount()).divide(new BigDecimal(Constant.PRECISION)));
					ex.setFeeBig(new BigDecimal(ex.getFee()).divide(new BigDecimal(Constant.PRECISION)));
				}
				result.setTransactionExList(transactionExList);
			}
		}
		
		// 调用合约,整理abi参数
/*		if (result.getTrxType() == Constant.TRX_TYPE_CALL_CONTRACT) {
			if (!StringUtils.isEmpty(result.getAbiParams())) {
				String params = "";
				if (result.getAbiParams().contains("{")) {
					String jsonStr = result.getAbiParams().replace("'", "\"");
					Map<String, Object> map = JSONObject.parseObject(jsonStr, Map.class);
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						params = params + entry.getValue() + "|";
					}
					params = params.substring(0, params.length() - 1);
				} else if (result.getAbiParams().contains(":")) {
					params = result.getAbiParams().replace(":", "|");
				} else {
					params = result.getAbiParams();
				}
				result.setAbiParams(params);
			}
		}*/
		String trxTypeStr = getTrxTypeStr(result.getTrxType());
		result.setTrxTypeStr(trxTypeStr);

		result.setAmountBig(new BigDecimal(result.getAmount()).divide(new BigDecimal(Constant.PRECISION)));
		result.setFeeBig(new BigDecimal(result.getFee()).divide(new BigDecimal(Constant.PRECISION)));
		return result;
	}
	
	@Override
	public void updateSelect() {
		Long amountNum = tblBcTransactionMapper.selectByAmountNum();
		if (amountNum == null) {
			return;
		}
		Integer num = tblBcTransactionMapper.selectByNum();
		Long amountFee = tblBcTransactionMapper.selectByFee();
		
		Map<String, Object> params = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		params.put("endDate", cal.getTime());
		cal.add(Calendar.HOUR_OF_DAY, -1);
		params.put("beginDate", cal.getTime());
		
		Integer hourNum = tblBcTransactionMapper.selectLashHour(params); // 返回最后一个小时的交易笔数
		Integer hourMax = tblBcTransactionMapper.selectAll();

		// 20180427 chengmaotao 替换为 BigDecimail
		// Double allTrx = Double.valueOf(amountNum) / Constant.PRECISION;
		// Double trxFee = Double.valueOf(amountFee) / Constant.PRECISION;

		BigDecimal allTrx = new BigDecimal(amountNum).divide(new BigDecimal(Constant.PRECISION)).setScale(8, BigDecimal.ROUND_HALF_UP);
		BigDecimal trxFee = new BigDecimal(amountFee).divide(new BigDecimal(Constant.PRECISION));

		TblBcStatistics statics = new TblBcStatistics();
		statics.setAllTrxAmount(allTrx);
		statics.setAllTrxFee(trxFee);

		statics.setTrxCount(num);
		statics.setTrxLatestHour(hourNum);
		if (hourMax != null) {
			statics.setTrxMaxHour(hourMax);
		} else {
			statics.setTrxMaxHour(0);
		}

		tblBcStatisticsMapper.updateByPrimaryKeySelective(statics);

		// 缓存在内存里
		// 20180427 chengmaotao bigdecimal 当转换为json数据时 前端js 对数据进行了截断, 转化为String
		// 类型就OK了
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allTrxAmount", statics.getAllTrxAmount().toPlainString());
		map.put("allTrxFee", statics.getAllTrxFee().toPlainString());
		map.put("trxCount", statics.getTrxCount());
		map.put("trxLatestHour", statics.getTrxLatestHour());
		map.put("trxMaxHour", statics.getTrxMaxHour());
		// realData.setStatisInfo(JSONObject.toJSONString(statics));
		realData.setStatisInfo(JSONObject.toJSONString(map));
	}

	@Override
	public Integer selectAll() {
		return this.tblBcTransactionMapper.selectAll();
	}

	@Override
	public EUDataGridResult getTransactionList(TblBcTransaction transaction, Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		EUDataGridResult result = new EUDataGridResult();
		// 分页处理
		List<TblBcTransaction> list = tblBcTransactionMapper.getTransactionList(transaction);
		if (list != null && list.size() > 0) {
			for (TblBcTransaction trx : list) {
				String trxTypeStr = getTrxTypeStr(trx.getTrxType());
				trx.setTrxTypeStr(trxTypeStr);
				trx.setAmountBig(new BigDecimal(trx.getAmount()).divide(new BigDecimal(Constant.PRECISION)));
				trx.setFeeBig(new BigDecimal(trx.getFee()).divide(new BigDecimal(Constant.PRECISION)));

				trx.setVoAmountBig(new StringBuffer().append(new BigDecimal(trx.getAmount()).divide(new BigDecimal(Constant.PRECISION)).toPlainString()).toString());
			}
		}
		// 创建一个返回值对象
		result.setRows(list);
		PageInfo<?> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		result.setPages(pageInfo.getPages());

/*		// 从链上查询余额
		if (!org.apache.commons.lang.StringUtils.isEmpty(transaction.getFromAccount())) {
			
			transaction.setVoBalance(getBalanceByAddress(transaction.getFromAccount()));
		}*/
		
		return result;
	}
	
	/**
	  * @Title: getBalanceByAddress   
	  * @Description: 根据地址 从链上查询余额
	  * @param
	  * @return String    返回类型   
	  * @throws
	 */
	@Override
	public String getBalanceByAddress(String address){
		try{


			// 获取余额信息
			String balanceMsg = socketService.getBalanceByAddress(address); 

			JSONObject jsonObject = JSONObject.parseArray(balanceMsg).getJSONArray(0).getJSONObject(1);
			
			// 余额
			String balance = jsonObject.getString("balance");
			
			// 资产ID
			int assertId = jsonObject.getJSONObject("condition").getInteger("asset_id");
			
			// 根据资产id 获得资产对象
			TblAsset symbolByAssetId = realData.getSymbolByAssetId(assertId);
			
			// 去除精度的（10的8次方）  余额
			String voBalance = new BigDecimal(balance).divide(new BigDecimal(symbolByAssetId.getPrecision())).toPlainString();
			return new StringBuffer(voBalance).append(" ").append(symbolByAssetId.getSymbol()).toString();
		}catch(Exception e){
			logger.error("根据地址获取余额时出现错误：");
			e.printStackTrace();	
			return "";
		}
		
	}

	private String getTrxTypeStr(int trxType) {
		String trxTypeStr = null;
		switch (trxType) {
		case 1:
			// 代理领工资
			//trxTypeStr = "Agent Fee";
			trxTypeStr = "Transaction Fee";
			break;
		case 2:
			// 注册账户
			trxTypeStr = "Account Registration";
			break;
		case 3:
			// 注册代理
			trxTypeStr = "Agent Registration";
			break;
		case 10:
			// 注册合约
			trxTypeStr = "Contract Registration";
			break;
		case 11:
			// 合约充值
			trxTypeStr = "Contract Recharge";
			break;
		case 12:
			// 合约升级
			trxTypeStr = "Contract Upgrade";
			break;
		case 13:
			// 合约销毁
			trxTypeStr = "Contract Destruction";
			break;
		case 14:
			// 调用合约
			trxTypeStr = "Calling Contract";
			break;
		default:
			// 普通转账
			trxTypeStr = "Transfer";
			break;
		}
		return trxTypeStr;
	}

	@Override
	public void insertBatchTransactionEx(List<TblBcTransactionEx> list) {
		for (int j = 0; j < list.size(); j = j + Constant.BATCH_LENGTH) {
			List<TblBcTransactionEx> sub = null;
			if (j + Constant.BATCH_LENGTH > list.size()) {
				sub = list.subList(j, list.size());
			} else {
				sub = list.subList(j, j + Constant.BATCH_LENGTH);
			}
			tblBcTransactionExMapper.insertBatch(sub);
		}
	}

	@Override
	public void insertBatchTransaction(List<TblBcTransaction> list) {
		for (int j = 0; j < list.size(); j = j + Constant.BATCH_LENGTH) {
			List<TblBcTransaction> sub = null;
			if (j + Constant.BATCH_LENGTH > list.size()) {
				sub = list.subList(j, list.size());
			} else {
				sub = list.subList(j, j + Constant.BATCH_LENGTH);
			}
			tblBcTransactionMapper.insertBatch(sub);
		}
	}

	@Override
	public List<TblBcTransaction> queryContractTrx(TblBcTransaction transaction) {
		return tblBcTransactionMapper.queryContractTrx(transaction);
	}

	/**
	 * @Title: getAllTransactionList
	 * @Description: 交易列表（所有的）
	 * @param
	 * @throws
	 */
	@Override
	public EUDataGridResult getAllTransactionList(TblBcTransaction transaction, Integer page, Integer rows) {
		PageHelper.startPage(page, rows);
		EUDataGridResult result = new EUDataGridResult();
		// 分页处理
		List<TblBcTransaction> list = tblBcTransactionMapper.getAllTransactionList(transaction);
		if (list != null && list.size() > 0) {
			for (TblBcTransaction trx : list) {
				String trxTypeStr = getTrxTypeStr(trx.getTrxType());
				trx.setTrxTypeStr(trxTypeStr);
				trx.setAmountBig(new BigDecimal(trx.getAmount()).divide(new BigDecimal(Constant.PRECISION)));
				trx.setFeeBig(new BigDecimal(trx.getFee()).divide(new BigDecimal(Constant.PRECISION)));

				trx.setVoAmountBig(new BigDecimal(trx.getAmount()).divide(new BigDecimal(Constant.PRECISION)).toPlainString());
			}
		}
		// 创建一个返回值对象
		result.setRows(list);
		PageInfo<?> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		result.setPages(pageInfo.getPages());
		return result;
	}
}
