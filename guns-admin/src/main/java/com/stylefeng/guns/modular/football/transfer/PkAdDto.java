package com.stylefeng.guns.modular.football.transfer;

import com.baomidou.mybatisplus.activerecord.Model;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 广告表
 * </p>
 *
 * @author stylefeng
 * @since 2018-10-09
 */
public class PkAdDto extends Model<PkAdDto> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
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
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date starttime;
    /**
     * 结束时间
     */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
	/**
	 * 广告类型
	 */
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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
			", creator=" + creator +
			", updator=" + updator +
			", isdeleted=" + isdeleted +
			"}";
	}
}
