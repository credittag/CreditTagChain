package com.browser.dao.mapper;

import java.util.List;

import com.browser.dao.entity.TblContractInfo;

public interface TblContractInfoMapper {
    int deleteByPrimaryKey(String contractId);

    int insert(TblContractInfo record);

    int insertSelective(TblContractInfo record);

    TblContractInfo selectByPrimaryKey(String contractId);

    int updateByPrimaryKeySelective(TblContractInfo record);

    int updateByPrimaryKeyWithBLOBs(TblContractInfo record);

    int updateByPrimaryKey(TblContractInfo record);

    List<TblContractInfo> selectContractList(TblContractInfo record);
    int deleteByBlockNum(Long blockNum);
}