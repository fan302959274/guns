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
 * 广告表
 * </p>
 *
 * @author stylefeng
 * @since 2018-10-27
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
	private String mainhead;
    /**
     * 广告副标题
     */
	private String subhead;
    /**
     * 开始时间
     */
	private Date starttime;
    /**
     * 结束时间
     */
	private Date endtime;
    /**
     * 广告链接
     */
	private String url;
    /**
     * 广告状态:0:预上线;1:已上线
     */
	private String status;
    /**
     * 创建时间
     */
	private Date createdate;
    /**
     * 更新时间
     */
	private Date updatedate;
    /**
     * 0 约战 1 联盟广告 2联盟活动
     */
	private String type;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMainhead() {
		return mainhead;
	}

	public void setMainhead(String mainhead) {
		this.mainhead = mainhead;
	}

	public String getSubhead() {
		return subhead;
	}

	public void setSubhead(String subhead) {
		this.subhead = subhead;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PkAd{" +
			"id=" + id +
			", mainhead=" + mainhead +
			", subhead=" + subhead +
			", starttime=" + starttime +
			", endtime=" + endtime +
			", url=" + url +
			", status=" + status +
			", createdate=" + createdate +
			", updatedate=" + updatedate +
			", type=" + type +
			"}";
	}
}
