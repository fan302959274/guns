package com.stylefeng.guns.rest.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 球场表
 * </p>
 *
 * @author stylefeng
 * @since 2018-11-19
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
	private String pkname;
    /**
     * 球场省份
     */
	private Long prov;
    /**
     * 球场市
     */
	private Long city;
    /**
     * 球场区 1 城南、2 城北
     */
	private Long area;
    /**
     * 状态 0正常 1禁用
     */
	private String status;
    /**
     * 具体地址
     */
	private String pkaddr;
    /**
     * 球场简介
     */
	private String pkdesc;
    /**
     * 球场可用时间
     */
	private String businesstime;
    /**
     * 创建时间
     */
	private Date createdate;
    /**
     * 更新时间
     */
	private Date updatedate;
    /**
     * 费用
     */
	private BigDecimal cost;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPkname() {
		return pkname;
	}

	public void setPkname(String pkname) {
		this.pkname = pkname;
	}

	public Long getProv() {
		return prov;
	}

	public void setProv(Long prov) {
		this.prov = prov;
	}

	public Long getCity() {
		return city;
	}

	public void setCity(Long city) {
		this.city = city;
	}

	public Long getArea() {
		return area;
	}

	public void setArea(Long area) {
		this.area = area;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPkaddr() {
		return pkaddr;
	}

	public void setPkaddr(String pkaddr) {
		this.pkaddr = pkaddr;
	}

	public String getPkdesc() {
		return pkdesc;
	}

	public void setPkdesc(String pkdesc) {
		this.pkdesc = pkdesc;
	}

	public String getBusinesstime() {
		return businesstime;
	}

	public void setBusinesstime(String businesstime) {
		this.businesstime = businesstime;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PkPark{" +
			"id=" + id +
			", pkname=" + pkname +
			", prov=" + prov +
			", city=" + city +
			", area=" + area +
			", status=" + status +
			", pkaddr=" + pkaddr +
			", pkdesc=" + pkdesc +
			", businesstime=" + businesstime +
			", createdate=" + createdate +
			", updatedate=" + updatedate +
			", cost=" + cost +
			"}";
	}
}
