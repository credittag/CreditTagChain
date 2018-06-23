package com.browser.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LineTblBcTransactions {

	private String text; // 折线图标题
	private String textNubers; // 交易次数标题

	private List<String> xAxis = new ArrayList<String>(); // 折线图X轴

	private String ytextAmount; // y轴标题
	private String ytextNums;

	private List<BigDecimal> series = new ArrayList<BigDecimal>(); // 折线图X轴数据    交易金额

	private List<Integer> series2 = new ArrayList<Integer>(); // 折线图X轴数据    交易次数
	
	
	
	public String getTextNubers() {
		return textNubers == null ? "" : textNubers.trim();
	}

	public void setTextNubers(String textNubers) {
		this.textNubers = textNubers;
	}

	public List<Integer> getSeries2() {
		return series2;
	}

	public void setSeries2(List<Integer> series2) {
		this.series2 = series2;
	}

	public String getText() {
		return text == null ? "" : text.trim();
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<String> getxAxis() {
		return xAxis;
	}

	public void setxAxis(List<String> xAxis) {
		this.xAxis = xAxis;
	}
	
	

	
	public String getYtextAmount() {
		return ytextAmount == null ? "" : ytextAmount.trim();
	}

	public void setYtextAmount(String ytextAmount) {
		this.ytextAmount = ytextAmount;
	}

	public String getYtextNums() {
		return ytextNums == null ? "" : ytextNums.trim();
	}

	public void setYtextNums(String ytextNums) {
		this.ytextNums = ytextNums;
	}




	private List<String> trxTime = new ArrayList<String>();
	
	



	public List<String> getTrxTime() {
		return trxTime;
	}

	public void setTrxTime(List<String> trxTime) {
		this.trxTime = trxTime;
	}

	public List<BigDecimal> getSeries() {
		return series;
	}

	public void setSeries(List<BigDecimal> series) {
		this.series = series;
	}
	

}