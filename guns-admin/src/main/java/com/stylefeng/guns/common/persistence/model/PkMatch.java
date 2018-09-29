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
 * 比赛表
 * </p>
 *
 * @author stylefeng
 * @since 2018-09-29
 */
@TableName("pk_match")
public class PkMatch extends Model<PkMatch> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 比赛名称
     */
	@TableField("match_name")
	private String matchName;
    /**
     * 比赛时间
     */
	@TableField("match_time")
	private Integer matchTime;
    /**
     * 比赛地点
     */
	@TableField("match_place")
	private String matchPlace;
    /**
     * 比赛发起人
     */
	@TableField("match_initiator_id")
	private String matchInitiatorId;
    /**
     * 东道主team
     */
	@TableField("match_host_team_id")
	private Long matchHostTeamId;
    /**
     * 挑战team
     */
	@TableField("match_challenge_team_id")
	private Long matchChallengeTeamId;
    /**
     * 比赛省份
     */
	@TableField("match_province")
	private String matchProvince;
    /**
     * 比赛市
     */
	@TableField("match_city")
	private String matchCity;
    /**
     * 比赛区
     */
	@TableField("match_area")
	private String matchArea;
    /**
     * 球赛开始时间
     */
	@TableField("match_start_time")
	private Date matchStartTime;
    /**
     * 球赛结束时间
     */
	@TableField("match_end_time")
	private Date matchEndTime;
    /**
     * 球场id
     */
	@TableField("match_park_id")
	private Long matchParkId;
    /**
     * 比赛状态(待定)
     */
	@TableField("match_status")
	private String matchStatus;
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

	public String getMatchName() {
		return matchName;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}

	public Integer getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(Integer matchTime) {
		this.matchTime = matchTime;
	}

	public String getMatchPlace() {
		return matchPlace;
	}

	public void setMatchPlace(String matchPlace) {
		this.matchPlace = matchPlace;
	}

	public String getMatchInitiatorId() {
		return matchInitiatorId;
	}

	public void setMatchInitiatorId(String matchInitiatorId) {
		this.matchInitiatorId = matchInitiatorId;
	}

	public Long getMatchHostTeamId() {
		return matchHostTeamId;
	}

	public void setMatchHostTeamId(Long matchHostTeamId) {
		this.matchHostTeamId = matchHostTeamId;
	}

	public Long getMatchChallengeTeamId() {
		return matchChallengeTeamId;
	}

	public void setMatchChallengeTeamId(Long matchChallengeTeamId) {
		this.matchChallengeTeamId = matchChallengeTeamId;
	}

	public String getMatchProvince() {
		return matchProvince;
	}

	public void setMatchProvince(String matchProvince) {
		this.matchProvince = matchProvince;
	}

	public String getMatchCity() {
		return matchCity;
	}

	public void setMatchCity(String matchCity) {
		this.matchCity = matchCity;
	}

	public String getMatchArea() {
		return matchArea;
	}

	public void setMatchArea(String matchArea) {
		this.matchArea = matchArea;
	}

	public Date getMatchStartTime() {
		return matchStartTime;
	}

	public void setMatchStartTime(Date matchStartTime) {
		this.matchStartTime = matchStartTime;
	}

	public Date getMatchEndTime() {
		return matchEndTime;
	}

	public void setMatchEndTime(Date matchEndTime) {
		this.matchEndTime = matchEndTime;
	}

	public Long getMatchParkId() {
		return matchParkId;
	}

	public void setMatchParkId(Long matchParkId) {
		this.matchParkId = matchParkId;
	}

	public String getMatchStatus() {
		return matchStatus;
	}

	public void setMatchStatus(String matchStatus) {
		this.matchStatus = matchStatus;
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
		return "PkMatch{" +
			"id=" + id +
			", matchName=" + matchName +
			", matchTime=" + matchTime +
			", matchPlace=" + matchPlace +
			", matchInitiatorId=" + matchInitiatorId +
			", matchHostTeamId=" + matchHostTeamId +
			", matchChallengeTeamId=" + matchChallengeTeamId +
			", matchProvince=" + matchProvince +
			", matchCity=" + matchCity +
			", matchArea=" + matchArea +
			", matchStartTime=" + matchStartTime +
			", matchEndTime=" + matchEndTime +
			", matchParkId=" + matchParkId +
			", matchStatus=" + matchStatus +
			", createDate=" + createDate +
			", updateDate=" + updateDate +
			", creator=" + creator +
			", updator=" + updator +
			", isDeleted=" + isDeleted +
			"}";
	}
}
