package com.browser.dao.entity;



import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author gen
 * @email gen1@ctc
 * @date 2018-07-30 12:02:15
 */

public class TblRiskAddressLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	private Long id;
	/**
	 * 
	 */
	private String fromAccount;
	/**
	 * 
	 */
	private String toAccount;
	/**
	 * 
	 */
	private String amount;
	
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	private String balance;
	/**
	 * 
	 */
	private Date trxTime;
	
	public String getRiskAccountType() {
		return riskAccountType;
	}
	public void setRiskAccountType(String riskAccountType) {
		this.riskAccountType = riskAccountType;
	}
	private String trxId;
	
	private String riskAccountType;
	public String getTrxId() {
		return trxId;
	}
	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}
	/**
	 * 获取：
	 */
	public String getFromAccount() {
		return fromAccount;
	}
	/**
	 * 设置：
	 */
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}
	/**
	 * 获取：
	 */
	public String getToAccount() {
		return toAccount;
	}
	/**
	 * 设置：
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * 获取：
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * 设置：
	 */
	public void setTrxTime(Date trxTime) {
		this.trxTime = trxTime;
	}
	/**
	 * 获取：
	 */
	public Date getTrxTime() {
		return trxTime;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
}
