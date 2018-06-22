package com.browser.dao.mapper;

import java.util.List;
import java.util.Map;

import com.browser.dao.entity.TblBcTransaction;

public interface TblBcTransactionMapper {
	int deleteByPrimaryKey(String trxId);

	int insert(TblBcTransaction record);

	int insertBatch(List<TblBcTransaction> list);

	int insertSelective(TblBcTransaction record);

	TblBcTransaction selectByPrimaryKey(String trxId);

	int updateByPrimaryKeySelective(TblBcTransaction record);

	int updateByPrimaryKey(TblBcTransaction record);

	/**
	 * 
	 * @Title: selectByTrxData
	 * @Description:查询最新的5条交易信息
	 * @author David
	 * @param
	 * @return List<TblBcTransaction> 
	 * @throws
	 */
	List<TblBcTransaction> selectByTrxData();

	/**
	 * 
	 * @Title: selectByBlockId
	 * @Description:根据快Id查询快信息
	 * @author David
	 * @param
	 * @return List<TblBcTransaction> 
	 * @throws
	 */
	List<TblBcTransaction> selectByBlockId(String blockId);

	/**
	 * 
	 * @Title: selectByAmountNum
	 * @Description:统计交易额
	 * @author David
	 * @param
	 * @return Map<String,Object> 
	 * @throws
	 */
	Long selectByAmountNum();

	/**
	 * 
	 * @Title: selectByFee
	 * @Description:统计手续费
	 * @author David
	 * @param
	 * @return Map<String,Object> 
	 * @throws
	 */
	Long selectByFee();

	/**
	 * 
	 * @Title: selectByNum
	 * @Description:统计交易数量
	 * @author David
	 * @param
	 * @return Map<String,Object> 
	 * @throws
	 */
	Integer selectByNum();

	/**
	 * 
	 * @Title: queryByTrxId
	 * @Description:通过交易ID查询交易信息
	 * @author David
	 * @param
	 * @return Map<String,Object> 
	 * @throws
	 */
	TblBcTransaction queryByTrxId(String trxId);

	/**
	 * 
	 * @Title: selectHour
	 * @Description:统计上一个小时的交易量
	 * @author David
	 * @param
	 * @return Integer 
	 * @throws
	 */
	Integer selectHour(String time);

	/**
	 * 
	 * @Title: selectHour2
	 * @Description: 统计最后一个小时的交易量
	 * @param: @param time
	 * @param: @return
	 * @return: Integer
	 * @throws
	 */
	Integer selectLashHour(Map<String, Object> params);

	/**
	 * 
	 * @Title: selectAll
	 * @Description:查询所有数据
	 * @author David
	 * @param
	 * @return List<TblBcTransaction> 
	 * @throws
	 */
	Integer selectAll();

	List<TblBcTransaction> getTransactionList(TblBcTransaction record);

	int deleteByBlockNum(Long blockNum);

	List<TblBcTransaction> queryContractTrx(TblBcTransaction record);

	List<TblBcTransaction> getAllTransactionList(TblBcTransaction transaction);

	List<TblBcTransaction> getLineTransactionData(Map<String, Object> params);
}