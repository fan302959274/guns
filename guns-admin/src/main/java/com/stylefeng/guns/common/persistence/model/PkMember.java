package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 队员表
 * </p>
 *
 * @author stylefeng
 * @since 2018-09-29
 */
@TableName("pk_member")
public class PkMember extends Model<PkMember> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 队员名称
     */
	@TableField("member_name")
	private String memberName;
    /**
     * 队员性别0:男;1:女
     */
	@TableField("member_sex")
	private String memberSex;
    /**
     * 手机号
     */
	@TableField("member_mobile")
	private String memberMobile;
    /**
     * 出生年月
     */
	@TableField("member_birth")
	private Date memberBirth;
    /**
     * 主攻位置
     */
	@TableField("member_position")
	private String memberPosition;
    /**
     * 惯用脚
     */
	@TableField("member_habit_feet")
	private String memberHabitFeet;
    /**
     * 身高
     */
	@TableField("member_height")
	private BigDecimal memberHeight;
    /**
     * 体重
     */
	@TableField("member_weight")
	private BigDecimal memberWeight;
    /**
     * 队员类型:1:队长;2:普通球员
     */
	@TableField("member_type")
	private String memberType;
    /**
     * 队员状态:0:禁用1:启用
     */
	@TableField("member_status")
	private String memberStatus;
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

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberSex() {
		return memberSex;
	}

	public void setMemberSex(String memberSex) {
		this.memberSex = memberSex;
	}

	public String getMemberMobile() {
		return memberMobile;
	}

	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
	}

	public Date getMemberBirth() {
		return memberBirth;
	}

	public void setMemberBirth(Date memberBirth) {
		this.memberBirth = memberBirth;
	}

	public String getMemberPosition() {
		return memberPosition;
	}

	public void setMemberPosition(String memberPosition) {
		this.memberPosition = memberPosition;
	}

	public String getMemberHabitFeet() {
		return memberHabitFeet;
	}

	public void setMemberHabitFeet(String memberHabitFeet) {
		this.memberHabitFeet = memberHabitFeet;
	}

	public BigDecimal getMemberHeight() {
		return memberHeight;
	}

	public void setMemberHeight(BigDecimal memberHeight) {
		this.memberHeight = memberHeight;
	}

	public BigDecimal getMemberWeight() {
		return memberWeight;
	}

	public void setMemberWeight(BigDecimal memberWeight) {
		this.memberWeight = memberWeight;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
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
		return "PkMember{" +
			"id=" + id +
			", memberName=" + memberName +
			", memberSex=" + memberSex +
			", memberMobile=" + memberMobile +
			", memberBirth=" + memberBirth +
			", memberPosition=" + memberPosition +
			", memberHabitFeet=" + memberHabitFeet +
			", memberHeight=" + memberHeight +
			", memberWeight=" + memberWeight +
			", memberType=" + memberType +
			", memberStatus=" + memberStatus +
			", createDate=" + createDate +
			", updateDate=" + updateDate +
			", creator=" + creator +
			", updator=" + updator +
			", isDeleted=" + isDeleted +
			"}";
	}
}
