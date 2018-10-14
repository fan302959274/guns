package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2018-10-11
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
	private String prov;
    /**
     * 球场市
     */
	private String city;
    /**
     * 球场区
     */
	private String area;
    /**
     * 球场简介
     */
	private String pkdesc;
	/**
	 * 球场具体地址
	 */
	private String pkaddr;


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
	private String isdeleted;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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

	public String getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(String isdeleted) {
		this.isdeleted = isdeleted;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public String getPkname() {
		return pkname;
	}

	public void setPkname(String pkname) {
		this.pkname = pkname;
	}

	public String getPkdesc() {
		return pkdesc;
	}

	public void setPkdesc(String pkdesc) {
		this.pkdesc = pkdesc;
	}

	public String getPkaddr() {
		return pkaddr;
	}

	public void setPkaddr(String pkaddr) {
		this.pkaddr = pkaddr;
	}

	@Override
	public String toString() {
		return "PkPark{" +
				"id=" + id +
				", pkname='" + pkname + '\'' +
				", prov='" + prov + '\'' +
				", city='" + city + '\'' +
				", area='" + area + '\'' +
				", pkdesc='" + pkdesc + '\'' +
				", pkaddr='" + pkaddr + '\'' +
				", businesstime='" + businesstime + '\'' +
				", createdate=" + createdate +
				", updatedate=" + updatedate +
				", creator='" + creator + '\'' +
				", updator='" + updator + '\'' +
				", isdeleted='" + isdeleted + '\'' +
				'}';
	}
}
