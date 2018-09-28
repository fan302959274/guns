package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 球场表
 * </p>
 *
 * @author stylefeng
 * @since 2018-09-28
 */
@TableName("pk_park")
public class PkPark extends Model<PkPark> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 球场名称
     */
	@TableField("park_name")
	private String parkName;
    /**
     * 球场省份
     */
	@TableField("park_province")
	private String parkProvince;
    /**
     * 球场市
     */
	@TableField("park_city")
	private String parkCity;
    /**
     * 球场区
     */
	@TableField("park_area")
	private String parkArea;
    /**
     * 球场简介
     */
	@TableField("park_desc")
	private String parkDesc;
    /**
     * 球场可用时间
     */
	@TableField("park_business_time")
	private String parkBusinessTime;
    /**
     * 创建时间
     */
	@TableField("create_date")
	private Date createDate;
    /**
     * 更新时间
     */
	@TableField("update_date")
	private Date updateDate;
    /**
     * 创建人
     */
	private String creator;
    /**
     * 更新人
     */
	private String updator;
    /**
     * 是否删除
     */
	@TableField("is_deleted")
	private String isDeleted;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getParkProvince() {
		return parkProvince;
	}

	public void setParkProvince(String parkProvince) {
		this.parkProvince = parkProvince;
	}

	public String getParkCity() {
		return parkCity;
	}

	public void setParkCity(String parkCity) {
		this.parkCity = parkCity;
	}

	public String getParkArea() {
		return parkArea;
	}

	public void setParkArea(String parkArea) {
		this.parkArea = parkArea;
	}

	public String getParkDesc() {
		return parkDesc;
	}

	public void setParkDesc(String parkDesc) {
		this.parkDesc = parkDesc;
	}

	public String getParkBusinessTime() {
		return parkBusinessTime;
	}

	public void setParkBusinessTime(String parkBusinessTime) {
		this.parkBusinessTime = parkBusinessTime;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PkPark{" +
			"id=" + id +
			", parkName=" + parkName +
			", parkProvince=" + parkProvince +
			", parkCity=" + parkCity +
			", parkArea=" + parkArea +
			", parkDesc=" + parkDesc +
			", parkBusinessTime=" + parkBusinessTime +
			", createDate=" + createDate +
			", updateDate=" + updateDate +
			", creator=" + creator +
			", updator=" + updator +
			", isDeleted=" + isDeleted +
			"}";
	}
}
