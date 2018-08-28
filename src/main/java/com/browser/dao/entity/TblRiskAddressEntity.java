package com.browser.dao.entity;





import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author gen
 * @email gen1@ctc
 * @date 2018-07-30 10:26:44
 */

public class TblRiskAddressEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	
	private Long id;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;
	/**
	 * 
	 */
	private String address;
	/**
	 * 
	 */
	private String addressName;

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
	/**
	 * 设置：
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：
	 */
	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	/**
	 * 获取：
	 */
	public String getAddressName() {
		return addressName;
	}
}
