package com.browser.dao.mapper;

import java.util.List;

import com.browser.dao.entity.TblContractEvent;

public interface TblContractEventMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TblContractEvent record);

    int insertSelective(TblContractEvent record);

    TblContractEvent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TblContractEvent record);

    int updateByPrimaryKey(TblContractEvent record);

    int insertBatch(List<TblContractEvent> record);

    int deleteByBlockNum(Long blockNum);
}