package com.browser.dao.mapper;

import java.util.List;

import com.browser.dao.entity.TblBcNews;

public interface TblBcNewsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TblBcNews record);

    int insertSelective(TblBcNews record);

    TblBcNews selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TblBcNews record);

    int updateByPrimaryKey(TblBcNews record);
    
    /**
     * 
    * @Title: selectByNew 
    * @Description:查询
    * @author David
    * @param 
    * @return List<TblBcStatistics> 
    * @throws
     */
    List<TblBcNews> selectByNew();
}