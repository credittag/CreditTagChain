package com.browser.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.browser.dao.entity.LineTblBcTransactions;
import com.browser.dao.entity.TblBcTransaction;
import com.browser.dao.mapper.TblBcTransactionMapper;
import com.browser.service.LineTransactionService;
import com.browser.tools.common.DateUtil;

@Service
public class LineTransactionServiceImpl implements LineTransactionService {

	private static Logger logger = LoggerFactory.getLogger(LineTransactionServiceImpl.class);

	@Autowired
	private TblBcTransactionMapper tblBcTransactionMapper;

	private static Map<String, LineTblBcTransactions> lineMapData = new HashMap<String, LineTblBcTransactions>();

	private static Map<String, TblBcTransaction> lineMapDataByDay = new HashMap<String, TblBcTransaction>();

	/**
	 * 当天的每次都查询。 以前的就不在去查询了
	 * 
	 * @Title: getLineTransactions
	 * @Description: TODO
	 * @param: @return
	 * @throws
	 */
	@Override
	public LineTblBcTransactions getLineTransactions() {

		Date nowDate = new Date();

		int preDay = -13;

		Date tempDate = null;
		String fromatDate = null;
		TblBcTransaction tblBcTransactionByDay;
		List<TblBcTransaction> lineTransactionData = null;

		String nowDateResMapKey = DateUtil.parseStr(nowDate, "yyyyMMdd");
		LineTblBcTransactions res = null;
		if (lineMapData.get(nowDateResMapKey) == null) {
			res = new LineTblBcTransactions();
			List<BigDecimal> series = res.getSeries(); // 数据 交易金额
			List<Integer> series2 = res.getSeries2(); // 数据 交易次数
			List<String> getxAxis = res.getxAxis(); // X轴
			List<String> getTrxTime = res.getTrxTime();
			res.setText(new StringBuffer().append(Math.abs(preDay - 1)).append(" day CTC Transaction History").toString());
			res.setTextNubers(new StringBuffer().append(Math.abs(preDay - 1)).append(" day CTC Transaction Numbers").toString());
			String mapKeyDate = null;

			Date removeKeyDate = DateUtils.addDays(nowDate, (preDay - 1));
			lineMapDataByDay.remove(DateUtil.parseStr(removeKeyDate, "yyyyMMdd"));
			for (int i = preDay; i < 0; i++) {
				tempDate = DateUtils.addDays(nowDate, i);
				fromatDate = DateUtil.parseStr(tempDate, "MM/dd");
				mapKeyDate = DateUtil.parseStr(tempDate, "yyyyMMdd");
				tblBcTransactionByDay = lineMapDataByDay.get(mapKeyDate);
				
				
				series.add(BigDecimal.ZERO);
				series2.add(0);
				getxAxis.add(fromatDate);
				getTrxTime.add(DateUtil.getLineDate(tempDate));
				if (tblBcTransactionByDay == null) {
					// 从数据库里查询出来
					Date endDay = DateUtil.getPlusMaxDay(nowDate, i);
					Date beginDay = DateUtil.getPlusMinDay(nowDate, i);
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("beginDay", beginDay);
					params.put("endDay", endDay);
					lineTransactionData = tblBcTransactionMapper.getLineTransactionData(params);

					if (lineTransactionData != null) {
						for (TblBcTransaction t : lineTransactionData) {
							if (StringUtils.equals(fromatDate, t.getVoLineTrxTime())) {
								series.set(i - preDay, t.getVoAmountByDay());
								series2.set(i - preDay, t.getVoNumsByDay());
								lineMapDataByDay.put(mapKeyDate, t);
								break;
							}
						}
					}
				} else {
					series.set(i - preDay, tblBcTransactionByDay.getVoAmountByDay());
					series2.set(i - preDay, tblBcTransactionByDay.getVoNumsByDay());
				}
			}
			// 今日以前的 都保存到map里
			lineMapData.put(nowDateResMapKey, res);

			// 当天的实时查询
			series.add(BigDecimal.ZERO);
			series2.add(0);
			getxAxis.add(DateUtil.parseStr(nowDate, "MM/dd"));
			getTrxTime.add(DateUtil.getLineDate(nowDate));
			nowDateDataCount(nowDate,res);

		} else {
			res = lineMapData.get(nowDateResMapKey);
			
			nowDateDataCount(nowDate,res);

		}

		return res;
	}
	
	
	private void nowDateDataCount(Date nowDate,LineTblBcTransactions res){
		Date endNowDay = DateUtil.getPlusMaxDay(nowDate, 0);
		Date beginNowDay = DateUtil.getPlusMinDay(nowDate, 0);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beginDay", beginNowDay);
		params.put("endDay", endNowDay);
		List<TblBcTransaction> lineTransactionData = tblBcTransactionMapper.getLineTransactionData(params);

		List<BigDecimal> series = res.getSeries();
		List<Integer> series2 = res.getSeries2();
		//series.add(BigDecimal.ZERO);
		//series2.add(0);
		//res.getxAxis().add(DateUtil.parseStr(nowDate, "MM/dd"));
		//res.getTrxTime().add(DateUtil.getLineDate(nowDate));

		if (lineTransactionData != null) {
			for (TblBcTransaction t : lineTransactionData) {
				if (StringUtils.equals(DateUtil.parseStr(nowDate, "MM/dd"), t.getVoLineTrxTime())) {
					series.set(series.size() - 1, t.getVoAmountByDay());
					series2.set(series2.size() - 1, t.getVoNumsByDay());
					break;
				}
			}
		}
	}
}
