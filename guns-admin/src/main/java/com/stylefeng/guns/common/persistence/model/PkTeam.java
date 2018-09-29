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
 * 球队表
 * </p>
 *
 * @author stylefeng
 * @since 2018-09-29
 */
@TableName("pk_team")
public class PkTeam extends Model<PkTeam> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 球队名称
     */
	@TableField("team_name")
	private String teamName;
    /**
     * 球队级别
     */
	@TableField("team_level")
	private String teamLevel;
    /**
     * 球员数量
     */
	@TableField("team_member_num")
	private Integer teamMemberNum;
    /**
     * 球队胜利场数
     */
	@TableField("team_win_num")
	private Integer teamWinNum;
    /**
     * 球队失败场数
     */
	@TableField("team_debt_num")
	private Integer teamDebtNum;
    /**
     * 球队平局场数
     */
	@TableField("team_draw_num")
	private Integer teamDrawNum;
    /**
     * 球队积分
     */
	@TableField("team_point")
	private Integer teamPoint;
    /**
     * 球队省份
     */
	@TableField("team_province")
	private String teamProvince;
    /**
     * 球队市
     */
	@TableField("team_city")
	private String teamCity;
    /**
     * 球队区
     */
	@TableField("team_area")
	private String teamArea;
    /**
     * 球队描述
     */
	@TableField("team_member_desc")
	private String teamMemberDesc;
    /**
     * 所属人
     */
	@TableField("team_owner_id")
	private Long teamOwnerId;
    /**
     * 球队状态:0:禁用1:启用
     */
	@TableField("team_status")
	private String teamStatus;
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

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamLevel() {
		return teamLevel;
	}

	public void setTeamLevel(String teamLevel) {
		this.teamLevel = teamLevel;
	}

	public Integer getTeamMemberNum() {
		return teamMemberNum;
	}

	public void setTeamMemberNum(Integer teamMemberNum) {
		this.teamMemberNum = teamMemberNum;
	}

	public Integer getTeamWinNum() {
		return teamWinNum;
	}

	public void setTeamWinNum(Integer teamWinNum) {
		this.teamWinNum = teamWinNum;
	}

	public Integer getTeamDebtNum() {
		return teamDebtNum;
	}

	public void setTeamDebtNum(Integer teamDebtNum) {
		this.teamDebtNum = teamDebtNum;
	}

	public Integer getTeamDrawNum() {
		return teamDrawNum;
	}

	public void setTeamDrawNum(Integer teamDrawNum) {
		this.teamDrawNum = teamDrawNum;
	}

	public Integer getTeamPoint() {
		return teamPoint;
	}

	public void setTeamPoint(Integer teamPoint) {
		this.teamPoint = teamPoint;
	}

	public String getTeamProvince() {
		return teamProvince;
	}

	public void setTeamProvince(String teamProvince) {
		this.teamProvince = teamProvince;
	}

	public String getTeamCity() {
		return teamCity;
	}

	public void setTeamCity(String teamCity) {
		this.teamCity = teamCity;
	}

	public String getTeamArea() {
		return teamArea;
	}

	public void setTeamArea(String teamArea) {
		this.teamArea = teamArea;
	}

	public String getTeamMemberDesc() {
		return teamMemberDesc;
	}

	public void setTeamMemberDesc(String teamMemberDesc) {
		this.teamMemberDesc = teamMemberDesc;
	}

	public Long getTeamOwnerId() {
		return teamOwnerId;
	}

	public void setTeamOwnerId(Long teamOwnerId) {
		this.teamOwnerId = teamOwnerId;
	}

	public String getTeamStatus() {
		return teamStatus;
	}

	public void setTeamStatus(String teamStatus) {
		this.teamStatus = teamStatus;
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
		return "PkTeam{" +
			"id=" + id +
			", teamName=" + teamName +
			", teamLevel=" + teamLevel +
			", teamMemberNum=" + teamMemberNum +
			", teamWinNum=" + teamWinNum +
			", teamDebtNum=" + teamDebtNum +
			", teamDrawNum=" + teamDrawNum +
			", teamPoint=" + teamPoint +
			", teamProvince=" + teamProvince +
			", teamCity=" + teamCity +
			", teamArea=" + teamArea +
			", teamMemberDesc=" + teamMemberDesc +
			", teamOwnerId=" + teamOwnerId +
			", teamStatus=" + teamStatus +
			", createDate=" + createDate +
			", updateDate=" + updateDate +
			", creator=" + creator +
			", updator=" + updator +
			", isDeleted=" + isDeleted +
			"}";
	}
}
