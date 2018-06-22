package com.browser.dao.mapper;

import java.util.List;

import com.browser.dao.entity.TblContractStorage;

public interface TblContractStorageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TblContractStorage record);

    int insertSelective(TblContractStorage record);

    TblContractStorage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TblContractStorage record);

    int updateByPrimaryKey(TblContractStorage record);

    int insertBatch(List<TblContractStorage> record);

    int deleteByBlockNum(Long blockNum);
}