package com.browser.dao.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.browser.tools.Constant;

public class TblBcTransaction {

	private String trxId;

	private String blockId;

	private Long blockNum;

	private String fromAccount;

	private String fromAccountName;

	private String toAccount;

	private String toAccountName;

	private Long amount;

	private String direction; // 0收入 1支出

	private String amountStr;

	private BigDecimal amountBig;
	private String voIndexAmountBig;

	private String voAmountBig;

	private Long fee;

	private String feeStr;

	private BigDecimal feeBig;
	private String voFeeBig;

	private Long amountNum;

	private String memo;

	private Date trxTime;
	private String trxTimeStr;

	private String disTime;

	private Date createdAt;

	private Date updatedAt;

	private Date deletedAt;

	private String balanceId;

	// 以下合约交易新增字段
	// 调用的合约函数，非合约交易该字段为空
	private String calledAbi;

	// 调用合约函数时传入的参数，非合约交易该字段为空
	private String abiParams;

	// 结果交易id仅针对合约交易
	private String extraTrxId;

	/**
	 * 合约调用结果 0 - 成功 1- 失败
	 */
	private Integer isCompleted;

	/**
	 * 0 - 普通转账 1 - 代理领工资 2 - 注册账户 3 - 注册代理 10 - 注册合约 11 - 合约充值 12 - 合约升级
	 */
	private Integer trxType;
	private String trxTypeStr;

	// 合约id
	private String contractId;

	// 对应块号的打包代理
	private String signee;

	private Integer assetId;

	private String symbol;

	private String voBalance; // 余额
	

	public String getVoBalance() {
		return voBalance == null ? "" : voBalance.trim();
	}

	public void setVoBalance(String voBalance) {
		this.voBalance = voBalance;
	}

	// 结果交易
	private List<TblBcTransactionEx> transactionExList;

	public String getTrxId() {
		return trxId == null ? "" : trxId.trim();
	}

	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}

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

	public String getFromAccount() {
		return fromAccount == null ? "" : fromAccount.trim();
	}

	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	public String getToAccount() {
		return toAccount == null ? "" : toAccount.trim();
	}

	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}

	public Long getAmount() {
		return amount == null ? 0L : amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getFee() {
		return fee == null ? 0L : fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

	public String getMemo() {
		return memo == null ? "" : memo.trim();
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getTrxTime() {
		return trxTime;
	}

	public void setTrxTime(Date trxTime) {
		this.trxTime = trxTime;
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

	public String getFromAccountName() {
		return fromAccountName == null ? "" : fromAccountName.trim();
	}

	public void setFromAccountName(String fromAccountName) {
		this.fromAccountName = fromAccountName;
	}

	public String getToAccountName() {
		return toAccountName == null ? "" : toAccountName.trim();
	}

	public void setToAccountName(String toAccountName) {
		this.toAccountName = toAccountName;
	}

	public String getDisTime() {
		return disTime == null ? "" : disTime.trim();
	}

	public void setDisTime(String disTime) {
		this.disTime = disTime;
	}

	public Long getAmountNum() {
		return amountNum == null ? 0L : amountNum;
	}

	public void setAmountNum(Long amountNum) {
		this.amountNum = amountNum;
	}

	public BigDecimal getAmountBig() {
		return amountBig == null ? new BigDecimal(0) : amountBig;
	}

	public void setAmountBig(BigDecimal amountBig) {
		this.amountBig = amountBig;
	}

	public String getBalanceId() {
		return balanceId == null ? "" : balanceId.trim();
	}

	public void setBalanceId(String balanceId) {
		this.balanceId = balanceId;
	}

	public String getCalledAbi() {
		return calledAbi == null ? "" : calledAbi.trim();
	}

	public void setCalledAbi(String calledAbi) {
		this.calledAbi = calledAbi;
	}

	public String getAbiParams() {
		return abiParams == null ? "" : abiParams.trim();
	}

	public void setAbiParams(String abiParams) {
		this.abiParams = abiParams;
	}

	public String getExtraTrxId() {
		return extraTrxId == null ? "" : extraTrxId.trim();
	}

	public void setExtraTrxId(String extraTrxId) {
		this.extraTrxId = extraTrxId;
	}

	public Integer getIsCompleted() {
		return isCompleted == null ? 0 : isCompleted;
	}

	public void setIsCompleted(Integer isCompleted) {
		this.isCompleted = isCompleted;
	}

	public Integer getTrxType() {
		return trxType == null ? 0 : trxType;
	}

	public void setTrxType(Integer trxType) {
		this.trxType = trxType;
	}

	public List<TblBcTransactionEx> getTransactionExList() {
		return transactionExList;
	}

	public void setTransactionExList(List<TblBcTransactionEx> transactionExList) {
		this.transactionExList = transactionExList;
	}

	public String getTrxTypeStr() {
		return trxTypeStr == null ? "" : trxTypeStr.trim();
	}

	public void setTrxTypeStr(String trxTypeStr) {
		this.trxTypeStr = trxTypeStr;
	}

	public String getTrxTimeStr() {
		return trxTimeStr == null ? "" : new StringBuffer(trxTimeStr.trim()).append(" +").append("UTC").toString();
	}

	public void setTrxTimeStr(String trxTimeStr) {
		this.trxTimeStr = trxTimeStr;
	}

	public String getContractId() {
		return contractId == null ? "" : contractId.trim();
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getSignee() {
		return signee == null ? "" : signee.trim();
	}

	public void setSignee(String signee) {
		this.signee = signee;
	}

	public String getAmountStr() {
		return amountStr == null ? "" : amountStr.trim();
	}

	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}

	public String getFeeStr() {
		return feeStr == null ? "" : feeStr.trim();
	}

	public void setFeeStr(String feeStr) {
		this.feeStr = feeStr;
	}

	public Integer getAssetId() {
		return assetId == null ? 0 : assetId;
	}

	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}

	public String getSymbol() {
		return symbol == null ? "" : symbol.trim();
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public BigDecimal getFeeBig() {
		return feeBig == null ? new BigDecimal(0) : feeBig;
	}

	public void setFeeBig(BigDecimal feeBig) {
		this.feeBig = feeBig;
	}

	public String getDirection() {
		return direction == null ? "" : direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getVoAmountBig() {
		return voAmountBig == null ? "" : new StringBuffer(voAmountBig.trim()).append(" ").append("CTC").toString();
	}

	public void setVoAmountBig(String voAmountBig) {
		this.voAmountBig = voAmountBig;
	}

	public String getVoIndexAmountBig() {
		return amountBig == null ? "0" : amountBig.toPlainString();
	}

	public void setVoIndexAmountBig(String voIndexAmountBig) {
		this.voIndexAmountBig = voIndexAmountBig;
	}

	public String getVoFeeBig() {
		return feeBig == null ? "0" : feeBig.toPlainString();
	}

	public void setVoFeeBig(String voFeeBig) {
		this.voFeeBig = voFeeBig;
	}

	private String voLineTrxTime;

	public String getVoLineTrxTime() {
		return voLineTrxTime == null ? "" : voLineTrxTime.substring(5).replace("-", "/");
	}

	public void setVoLineTrxTime(String voLineTrxTime) {
		this.voLineTrxTime = voLineTrxTime;
	}
	
	private BigDecimal voAmountByDay;
	
	private int voNumsByDay;

	public int getVoNumsByDay() {
		return voNumsByDay;
	}

	public void setVoNumsByDay(int voNumsByDay) {
		this.voNumsByDay = voNumsByDay;
	}

	public BigDecimal getVoAmountByDay() {
		return voAmountByDay == null ? BigDecimal.ZERO : voAmountByDay.divide(new BigDecimal(Constant.PRECISION)).setScale(8, BigDecimal.ROUND_HALF_UP);
	}

	public void setVoAmountByDay(BigDecimal voAmountByDay) {
		this.voAmountByDay = voAmountByDay;
	}

	
	

}