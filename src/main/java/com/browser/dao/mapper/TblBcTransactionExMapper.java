package com.browser.dao.mapper;

import java.util.List;

import com.browser.dao.entity.TblBcTransactionEx;

public interface TblBcTransactionExMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TblBcTransactionEx record);

    int insertSelective(TblBcTransactionEx record);

    TblBcTransactionEx selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TblBcTransactionEx record);

    int updateByPrimaryKeyWithBLOBs(TblBcTransactionEx record);

    int updateByPrimaryKey(TblBcTransactionEx record);

    int insertBatch(List<TblBcTransactionEx> record);

    List<TblBcTransactionEx> selectByOrigTrxId(String origTrxId);

    int deleteByBlockNum(Long blockNum);
}