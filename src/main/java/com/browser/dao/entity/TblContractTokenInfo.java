package com.browser.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TblContractTokenInfo {

	private String contractId;

	private String tokenName;

	private String tokenSymbol;

	private BigDecimal precision;

	private BigDecimal totalSupply;

	private Long blockNum;

	private Date regTime;
	
	private String contractState;
	
	private String addminAddress;

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getTokenName() {
		return tokenName;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	public String getTokenSymbol() {
		return tokenSymbol;
	}

	public void setTokenSymbol(String tokenSymbol) {
		this.tokenSymbol = tokenSymbol;
	}

	public BigDecimal getPrecision() {
		return precision;
	}

	public void setPrecision(BigDecimal precision) {
		this.precision = precision;
	}

	public BigDecimal getTotalSupply() {
		return totalSupply;
	}

	public void setTotalSupply(BigDecimal totalSupply) {
		this.totalSupply = totalSupply;
	}

	public Long getBlockNum() {
		return blockNum;
	}

	public void setBlockNum(Long blockNum) {
		this.blockNum = blockNum;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public String getContractState() {
		return contractState;
	}

	public void setContractState(String contractState) {
		this.contractState = contractState;
	}

	public String getAddminAddress() {
		return addminAddress;
	}

	public void setAddminAddress(String addminAddress) {
		this.addminAddress = addminAddress;
	}

	
	
}