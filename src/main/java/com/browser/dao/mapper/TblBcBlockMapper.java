package com.browser.dao.mapper;

import java.util.List;

import com.browser.dao.entity.TblBcBlock;

public interface TblBcBlockMapper {
	int deleteByPrimaryKey(String blockId);

	int insert(TblBcBlock record);

	int insertBatch(List<TblBcBlock> list);

	int insertSelective(TblBcBlock record);

	TblBcBlock selectByPrimaryKey(String blockId);

	int updateByPrimaryKeySelective(TblBcBlock record);

	int updateByPrimaryKey(TblBcBlock record);

	/**
	 * 
	 * @Title: selectByBlockNum @Description:根据快号查询快信息 @author
	 * David @param @return TblBcBlock  @throws
	 */
	TblBcBlock selectByBlockNum(Long blocknUm);

	/**
	 * 
	 * @Title: queryByCount @Description:判断快表是否有数据 @author David @param @return
	 * int  @throws
	 */
	int queryByCount();

	/**
	 * 
	 * @Title: queryBlockNum @Description:获取最大快号 @author David @param @return
	 * int  @throws
	 */
	Long queryBlockNum();

	/**
	 * 
	 * @Title: selectByNewData @Description: @author David @param @return
	 * List<TblBcBlock>  @throws
	 */
	List<TblBcBlock> selectByNewData();

	/**
	 * 查询所有块数据
	 * 
	 * @return
	 */
	List<TblBcBlock> selectByNewDataNoPage(TblBcBlock tblBcBlock);

	int deleteByBlockNum(Long blockNum);
}