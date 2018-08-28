package com.browser.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.browser.WeChatMessage.Send_weChatMsg;
import com.browser.WeChatMessage.UrlData;
import com.browser.dao.entity.TblBcTransaction;
import com.browser.dao.entity.TblRiskAddressEntity;
import com.browser.dao.entity.TblRiskAddressLogEntity;
import com.browser.dao.mapper.TblBcTransactionMapper;
import com.browser.dao.mapper.TblRiskAddressDao;
import com.browser.dao.mapper.TblRiskAddressLogDao;
import com.browser.service.RiskAddressReport;
import com.browser.tools.rabbitmq.RabbitProducer;
import com.google.gson.Gson;

@Service("riskAddressReport")
public class RiskAddressReportImpl implements RiskAddressReport {

	@Resource
	private TblRiskAddressDao tblRiskAddressDao;
	@Resource
	private TblRiskAddressLogDao tblRiskAddressLogDao;
	@Resource
	private RabbitProducer rabbitProducer;
	
	@Resource
	private TblBcTransactionMapper tblBcTransactionMapper;
	
	//private static RiskAddressReportImpl riskAddressReportImpl;

	private static List<TblRiskAddressEntity> tblRiskAddressEntityList;
	private static List<String> bourseList = new ArrayList<String>();;
	private static Set<String> riskAddressSet = new HashSet<String>();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static String lim9 = "CRMBxoKa3nusQq44BdLytDArYXD6Egm3W8";//用于告警m9的余额
	private static String assure = "CTj37NcVWPb4ToknGnLYCBtqVEWQ2tHiKm";
	
	private static int lim910 = 0;
	private static int lim95 = 0;
	private static int lim93 = 0;

	// static {
	//
	// //加载所有的风险地址
	// tblRiskAddressEntityList = tblRiskAddressDao.queryAll();
	// if(tblRiskAddressEntityList!=null&&tblRiskAddressEntityList.size()>0) {
	// for(TblRiskAddressEntity tblRiskAddressEntity :tblRiskAddressEntityList) {
	// riskAddressSet.add(tblRiskAddressEntity.getAddress());
	// }
	// }
	// }
	@PostConstruct
	public void initParams() {
		// 加载所有的风险地址
		tblRiskAddressEntityList = tblRiskAddressDao.queryAll();
		if (tblRiskAddressEntityList != null && tblRiskAddressEntityList.size() > 0) {
			for (TblRiskAddressEntity tblRiskAddressEntity : tblRiskAddressEntityList) {
				riskAddressSet.add(tblRiskAddressEntity.getAddress());
			}
		}
		bourseList.add("CSovZB19P6SijBYe2GV6eLXdUJn8FRq7Aw");//bcex
		bourseList.add("CMAu1Nfbs76nuoc8XLvxzJJnq81Rf2DhhG");//bcex  两个bcex地址
		bourseList.add("CXZh9mX9P2hsiHyVzcF1ixZU8J9MKGZq1s");//idax
		bourseList.add("CZ35LexSddgq6DgH3VTPPWzT13MTzuqARN");//oex
		
		
		
		riskAddressSet.add("CGzbX8c39BrZSR8s9HekPoULT34kJj9pbW");
		riskAddressSet.add("CcR3ZAy9xUe4tVS4UpwTp6R5SYyMh3KSGP");
		riskAddressSet.add("CTtNcCRNYXXmRSsjtEsuk9CaNcUPUwR6p9");
		riskAddressSet.add("CVw3FT8JhkHAr4UFv24DinAJCWn8N4zRJX");
		riskAddressSet.add("CdwCRwj55nbdWV6qHR5tEE13L7KDpSEDAb");
		riskAddressSet.add("Cc555xAYtFR4y4bdLicCR7EDCNxdiFzpbm");
		riskAddressSet.add("CZ35LexSddgq6DgH3VTPPWzT13MTzuqARN");//oex
		riskAddressSet.add("CSovZB19P6SijBYe2GV6eLXdUJn8FRq7Aw");//bcex
		riskAddressSet.add("CMAu1Nfbs76nuoc8XLvxzJJnq81Rf2DhhG");//bcex
		riskAddressSet.add("CYYRwNwr5zjg4GnbjtiygYSJ5q89FoNhhV");
		riskAddressSet.add("CXZh9mX9P2hsiHyVzcF1ixZU8J9MKGZq1s");//idax
		riskAddressSet.add("CR4Xnbt1dojxfqdMhu8rtmJkU8hZxmrAWw");
		riskAddressSet.add("CPNrNizVkE9eczLbAE8LZoUcc4AC3HYqNV");
		riskAddressSet.add("CW3FukqFQn6ekDBzz6i7Vyw2RkhxkxHBvu");
		riskAddressSet.add("CQLSkpj6xKuXqSFRibB1ziPPBoNSc7i5NM");
		riskAddressSet.add("CPXS1jux13SwxP8dShhm63As545cBRWWsy");
		riskAddressSet.add("CSTNHKYZLt6tbXWGQnySNiU8jabAAVb6wE");
		riskAddressSet.add("CfZ5mW5LnauFsvSGnhs9Ldc4gWiAxeuK1j");
		riskAddressSet.add("CPx13CbFRxqBGdwQsMepTe91a8VFY8etm3");
		
	}

	@Override
	public void RiskAddressVerify(TblBcTransaction tblBcTransaction) {
		// TODO Auto-generated method stub
		String fromAccount = tblBcTransaction.getFromAccount();
		String toAccount = tblBcTransaction.getToAccount();
		Long amount = tblBcTransaction.getAmount() / 100000000;
		switch (toAccount.length()) {
		case 33:
			toAccount = toAccount.substring(0, 33);
			break;
		case 34:
			toAccount = toAccount.substring(0, 34);
			break;
		case 65:
			toAccount = toAccount.substring(0, 33);
			break;
		case 66:
			toAccount = toAccount.substring(0, 34);
			break;
		}
		String result = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Gson gson = new Gson();
		if (riskAddressSet.contains(fromAccount)) {
			// 发送微信公众号告警
			tblRiskAddressLogDao.insertTblRiskAddressLogEntity(tblBcTransaction);
			TblRiskAddressLogEntity t = new TblRiskAddressLogEntity();
			t.setAmount(tblBcTransaction.getAmount()+"");
			t.setFromAccount(fromAccount);
			t.setRiskAccountType("FROMACCOUNT");
			t.setToAccount(toAccount);
			t.setTrxId(tblBcTransaction.getTrxId());
			t.setTrxTime(tblBcTransaction.getTrxTime());
			String msg = gson.toJson(t);
			rabbitProducer.producerMsg(msg);

		}
		if (riskAddressSet.contains(toAccount)) {
			// 发送微信公众号告警
			tblBcTransaction.setRiskAccountType("TOACCOUNT");
			tblRiskAddressLogDao.insertTblRiskAddressLogEntity(tblBcTransaction);
			TblRiskAddressLogEntity t = new TblRiskAddressLogEntity();
			t.setAmount(tblBcTransaction.getAmount()+"");
			t.setFromAccount(fromAccount);
			t.setRiskAccountType("TOACCOUNT");
			t.setToAccount(toAccount);
			t.setTrxId(tblBcTransaction.getTrxId());
			t.setTrxTime(tblBcTransaction.getTrxTime());
			String msg = gson.toJson(t);
			rabbitProducer.producerMsg(msg);
			
		}
		if (bourseList.contains(fromAccount) && bourseList.contains(toAccount)) {
			tblBcTransaction.setRiskAccountType("BOURSE");
			tblRiskAddressLogDao.insertTblRiskAddressLogEntity(tblBcTransaction);
			TblRiskAddressLogEntity t = new TblRiskAddressLogEntity();
			t.setAmount(tblBcTransaction.getAmount()+"");
			t.setFromAccount(fromAccount);
			t.setRiskAccountType("BOURSE");
			t.setToAccount(toAccount);
			t.setTrxId(tblBcTransaction.getTrxId());
			t.setTrxTime(tblBcTransaction.getTrxTime());
			String msg = gson.toJson(t);
			rabbitProducer.producerMsg(msg);
		}
		if (amount >= 1000) {
			tblBcTransaction.setRiskAccountType("AMOUNT");
			tblRiskAddressLogDao.insertTblRiskAddressLogEntity(tblBcTransaction);
			TblRiskAddressLogEntity t = new TblRiskAddressLogEntity();
			t.setAmount(tblBcTransaction.getAmount()+"");
			t.setFromAccount(fromAccount);
			t.setRiskAccountType("AMOUNT");
			t.setToAccount(toAccount);
			t.setTrxId(tblBcTransaction.getTrxId());
			t.setTrxTime(tblBcTransaction.getTrxTime());
			String msg = gson.toJson(t);
			rabbitProducer.producerMsg(msg);
		}
		//判断m9余额
		if(fromAccount.equals(lim9)) {
			//转出地址是m9的，需要查询m9的余额
			Map<String, String> parammap = new HashMap<String,String>();
			parammap.put("addr", lim9);
			Map<String,BigDecimal> resultmap = tblBcTransactionMapper.bourseAssureBalance(parammap);
			int m910 = resultmap.get("balance").divide(new BigDecimal("100000000")).compareTo(new BigDecimal("100000"));
			int m95 = resultmap.get("balance").divide(new BigDecimal("100000000")).compareTo(new BigDecimal("50000"));
			int m93 = resultmap.get("balance").divide(new BigDecimal("100000000")).compareTo(new BigDecimal("30000"));
			if((m910==-1 ||m910 ==0) && m95==1) {//5-10万
				if(lim910<3) {
					tblBcTransaction.setRiskAccountType("M9BALANCE10");
					tblRiskAddressLogDao.insertTblRiskAddressLogEntity(tblBcTransaction);
					TblRiskAddressLogEntity t = new TblRiskAddressLogEntity();
					t.setAmount(tblBcTransaction.getAmount()+"");
					t.setFromAccount(fromAccount);
					t.setRiskAccountType("M9BALANCE10");
					t.setToAccount(toAccount);
					t.setTrxId(tblBcTransaction.getTrxId());
					t.setTrxTime(tblBcTransaction.getTrxTime());
					t.setBalance(resultmap.get("balance").divide(new BigDecimal("100000000"))+"");//m9的余额
					String msg = gson.toJson(t);
					rabbitProducer.producerMsg(msg);
					lim910++;
				}
				
			}
			if((m95==-1|| m95 ==0) && m93==1) {//3-5万
				if(lim95<3) {
					tblBcTransaction.setRiskAccountType("M9BALANCE5");
					tblRiskAddressLogDao.insertTblRiskAddressLogEntity(tblBcTransaction);
					TblRiskAddressLogEntity t = new TblRiskAddressLogEntity();
					t.setAmount(tblBcTransaction.getAmount()+"");
					t.setFromAccount(fromAccount);
					t.setRiskAccountType("M9BALANCE5");
					t.setToAccount(toAccount);
					t.setTrxId(tblBcTransaction.getTrxId());
					t.setTrxTime(tblBcTransaction.getTrxTime());
					t.setBalance(resultmap.get("balance").divide(new BigDecimal("100000000"))+"");
					String msg = gson.toJson(t);
					rabbitProducer.producerMsg(msg);
					lim95++;
				}
				
				
			}
			if(m93==-1||m93 == 0) {//小于等于3万
				if(lim93<3) {
					tblBcTransaction.setRiskAccountType("M9BALANCE3");
					tblRiskAddressLogDao.insertTblRiskAddressLogEntity(tblBcTransaction);
					TblRiskAddressLogEntity t = new TblRiskAddressLogEntity();
					t.setAmount(tblBcTransaction.getAmount()+"");
					t.setFromAccount(fromAccount);
					t.setRiskAccountType("M9BALANCE3");
					t.setToAccount(toAccount);
					t.setTrxId(tblBcTransaction.getTrxId());
					t.setTrxTime(tblBcTransaction.getTrxTime());
					t.setBalance(resultmap.get("balance").divide(new BigDecimal("100000000"))+"");
					String msg = gson.toJson(t);
					rabbitProducer.producerMsg(msg);
					lim93++;
				}
			}
			
			
		}
		if(!lim9.equals(fromAccount)&&assure.equals(toAccount)) {
			tblBcTransaction.setRiskAccountType("NOTLIM9TOASSURE");
			tblRiskAddressLogDao.insertTblRiskAddressLogEntity(tblBcTransaction);
			TblRiskAddressLogEntity t = new TblRiskAddressLogEntity();
			t.setAmount(tblBcTransaction.getAmount()+"");
			t.setFromAccount(fromAccount);
			t.setRiskAccountType("NOTLIM9TOASSURE");
			t.setToAccount(toAccount);
			t.setTrxId(tblBcTransaction.getTrxId());
			t.setTrxTime(tblBcTransaction.getTrxTime());
			String msg = gson.toJson(t);
			rabbitProducer.producerMsg(msg);
		}
	}
	@Scheduled(cron = "0 0 0 * * ?")
	private void updatelim9status() {
		lim910=0;
		lim95=0;
		lim93=0;
	}
}
