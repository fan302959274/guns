package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * <p>
 * 广告表
 * </p>
 *
 * @author stylefeng
 * @since 2018-09-29
 */
@TableName("pk_ad")
public class PkAd extends Model<PkAd> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 广告主标题
     */
	@TableField("ad_main_head")
	private String adMainHead;
    /**
     * 广告副标题
     */
	@TableField("ad_sub_head")
	private String adSubHead;
    /**
     * 开始时间
     */
	@TableField("ad_start_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date adStartTime;
    /**
     * 结束时间
     */
	@TableField("ad_end_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date adEndTime;
    /**
     * 广告链接
     */
	@TableField("ad_url")
	private String adUrl;
    /**
     * 广告状态:0:预上线;1:已上线
     */
	@TableField("ad_status")
	private String adStatus;
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

	public String getAdMainHead() {
		return adMainHead;
	}

	public void setAdMainHead(String adMainHead) {
		this.adMainHead = adMainHead;
	}

	public String getAdSubHead() {
		return adSubHead;
	}

	public void setAdSubHead(String adSubHead) {
		this.adSubHead = adSubHead;
	}

	public Date getAdStartTime() {
		return adStartTime;
	}

	public void setAdStartTime(Date adStartTime) {
		this.adStartTime = adStartTime;
	}

	public Date getAdEndTime() {
		return adEndTime;
	}

	public void setAdEndTime(Date adEndTime) {
		this.adEndTime = adEndTime;
	}

	public String getAdUrl() {
		return adUrl;
	}

	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}

	public String getAdStatus() {
		return adStatus;
	}

	public void setAdStatus(String adStatus) {
		this.adStatus = adStatus;
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
		return "PkAd{" +
			"id=" + id +
			", adMainHead=" + adMainHead +
			", adSubHead=" + adSubHead +
			", adStartTime=" + adStartTime +
			", adEndTime=" + adEndTime +
			", adUrl=" + adUrl +
			", adStatus=" + adStatus +
			", createDate=" + createDate +
			", updateDate=" + updateDate +
			", creator=" + creator +
			", updator=" + updator +
			", isDeleted=" + isDeleted +
			"}";
	}
}
