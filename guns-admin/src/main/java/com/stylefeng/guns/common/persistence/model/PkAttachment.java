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
 * 队员附件表
 * </p>
 *
 * @author stylefeng
 * @since 2018-09-29
 */
@TableName("pk_attachment")
public class PkAttachment extends Model<PkAttachment> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 附件名称
     */
	@TableField("attachment_name")
	private String attachmentName;
    /**
     * 附件osskey
     */
	@TableField("attachment_osskey")
	private String attachmentOsskey;
    /**
     * 附件链接
     */
	@TableField("attachment_url")
	private String attachmentUrl;
    /**
     * 附件类型1:队员附件;2:球队附件;3:球场附件;4:广告附件
     */
	@TableField("attachment_type")
	private String attachmentType;
    /**
     * 关联主键:队员id|球队id|球场id|广告id
     */
	@TableField("attachment_key")
	private String attachmentKey;
    /**
     * 附件后缀
     */
	@TableField("attachment_suffix")
	private String attachmentSuffix;
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

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getAttachmentOsskey() {
		return attachmentOsskey;
	}

	public void setAttachmentOsskey(String attachmentOsskey) {
		this.attachmentOsskey = attachmentOsskey;
	}

	public String getAttachmentUrl() {
		return attachmentUrl;
	}

	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public String getAttachmentKey() {
		return attachmentKey;
	}

	public void setAttachmentKey(String attachmentKey) {
		this.attachmentKey = attachmentKey;
	}

	public String getAttachmentSuffix() {
		return attachmentSuffix;
	}

	public void setAttachmentSuffix(String attachmentSuffix) {
		this.attachmentSuffix = attachmentSuffix;
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
		return "PkAttachment{" +
			"id=" + id +
			", attachmentName=" + attachmentName +
			", attachmentOsskey=" + attachmentOsskey +
			", attachmentUrl=" + attachmentUrl +
			", attachmentType=" + attachmentType +
			", attachmentKey=" + attachmentKey +
			", attachmentSuffix=" + attachmentSuffix +
			", createDate=" + createDate +
			", updateDate=" + updateDate +
			", creator=" + creator +
			", updator=" + updator +
			", isDeleted=" + isDeleted +
			"}";
	}
}
