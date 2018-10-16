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
 * 队员表
 * </p>
 *
 * @author stylefeng
 * @since 2018-10-16
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
     * 队员账号
     */
	private String account;
    /**
     * 队员名称
     */
	private String name;
    /**
     * 队员性别0:男;1:女
     */
	private String sex;
    /**
     * 手机号
     */
	private String mobile;
    /**
     * 出生年月
     */
	private Date birth;
    /**
     * 主攻位置
     */
	private String position;
    /**
     * 惯用脚
     */
	private String habitfeet;
    /**
     * 身高
     */
	private BigDecimal height;
    /**
     * 体重
     */
	private BigDecimal weight;
    /**
     * 队员类型:1:队长;2:普通球员
     */
	private String type;
    /**
     * 队员状态:0:禁用1:启用
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


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getHabitfeet() {
		return habitfeet;
	}

	public void setHabitfeet(String habitfeet) {
		this.habitfeet = habitfeet;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
		return "PkMember{" +
			"id=" + id +
			", account=" + account +
			", name=" + name +
			", sex=" + sex +
			", mobile=" + mobile +
			", birth=" + birth +
			", position=" + position +
			", habitfeet=" + habitfeet +
			", height=" + height +
			", weight=" + weight +
			", type=" + type +
			", status=" + status +
			", createdate=" + createdate +
			", updatedate=" + updatedate +
			", creator=" + creator +
			", updator=" + updator +
			", isdeleted=" + isdeleted +
			"}";
	}
}
