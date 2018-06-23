package com.browser.service;

import java.util.List;

import com.browser.dao.entity.TblBcNews;
import com.browser.dao.entity.TblBcStatistics;

public interface StatisService {
	/**
	 * 
	* @Title: selectByPrimaryKey 
	* @Description:查询统计信息
	* @author David
	* @param 
	* @return TblBcStatistics 
	* @throws
	 */
	TblBcStatistics selectByPrimaryKey(Long id);
	
	/**
	 * 
	* @Title: selectByNew 
	* @Description: 查询新闻信息
	* @author David
	* @param 
	* @return List<TblBcNews> 
	* @throws
	 */
	List<TblBcNews> selectByNew();
	
	/**
	 * 
	* @Title: updateByPrimaryKeySelective 
	* @Description:更新数据
	* @author David
	* @param 
	* @return int 
	* @throws
	 */
	int updateByPrimaryKeySelective(TblBcStatistics statistic);

	void newBlockStatic();

	void newTransactionStatic();
}
