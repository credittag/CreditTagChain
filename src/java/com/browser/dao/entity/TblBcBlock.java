package com.browser.dao.entity;

import com.browser.tools.Constant;
import com.browser.tools.common.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

public class TblBcBlock {
	
    private String blockId;

    private Long blockNum;

    private Long blockSize;

    private String previous;

    private String trxDigest;

    private String prevSecret;

    private String nextSecretHash;

    private String randomSeed;

    private String signee;

    private Date blockTime;

    private String blockTimeStr;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

    //交易总数
    private Integer trxNum;
    //交易总金额
    private Long amount;

    private Long fee;

    private String amountBig;
    private String feeBig;

    
    public String getBlockId() {
        return blockId == null ? "" : blockId.trim();
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public Long getBlockNum() {
        return blockNum == null ? 0L : blockNum;
    }

    public void setBlockNum(Long blockNum) {
        this.blockNum = blockNum;
    }

    public Long getBlockSize() {
        return blockSize == null ? 0L : blockSize;
    }

    public void setBlockSize(Long blockSize) {
        this.blockSize = blockSize;
    }

    public String getPrevious() {
        return previous== null ? "" : previous.trim();
    }

    public void setPrevious(String previous) {
        this.previous = previous ;
    }

    public String getTrxDigest() {
        return trxDigest == null ? "" : trxDigest.trim();
    }

    public void setTrxDigest(String trxDigest) {
        this.trxDigest = trxDigest ;
    }

    public String getPrevSecret() {
        return prevSecret == null ? "" : prevSecret.trim();
    }

    public void setPrevSecret(String prevSecret) {
        this.prevSecret = prevSecret ;
    }

    public String getNextSecretHash() {
        return nextSecretHash == null ? "" : nextSecretHash.trim();
    }

    public void setNextSecretHash(String nextSecretHash) {
        this.nextSecretHash = nextSecretHash ;
    }

    public String getRandomSeed() {
        return randomSeed == null ? "" : randomSeed.trim();
    }

    public void setRandomSeed(String randomSeed) {
        this.randomSeed = randomSeed ;
    }

    public String getSignee() {
        return signee == null ? "" : signee.trim();
    }

    public void setSignee(String signee) {
        this.signee = signee ;
    }

    public Date getBlockTime() {
		return blockTime;
	}

	public void setBlockTime(Date blockTime) {
		this.blockTime = blockTime;
	}
	
	public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getTrxNum() {
        return trxNum == null ? 0 : trxNum;
    }

    public void setTrxNum(Integer trxNum) {
        this.trxNum = trxNum;
    }

    public Long getAmount() {
        return amount == null ? 0L : amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getBlockTimeStr() {
        return DateUtil.parseUTC(blockTime);
    }

    public void setBlockTimeStr(String blockTimeStr) {
        this.blockTimeStr = blockTimeStr;
    }

    public Long getFee() {
        return fee == null ? 0L : fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }



    public String getAmountBig() {
		if(amount != null){
			return new StringBuffer().append(new BigDecimal(amount).divide(new BigDecimal(Constant.PRECISION)).toPlainString()).append(" ").append("CTC").toString();
		}
		return "0 CTC";
	}

	public void setAmountBig(String amountBig) {
		this.amountBig = amountBig;
	}

	public String getFeeBig() {
        return feeBig == null ? "0" : feeBig.toString();
    }

    public void setFeeBig(String feeBig) {
        this.feeBig = feeBig;
    }
}