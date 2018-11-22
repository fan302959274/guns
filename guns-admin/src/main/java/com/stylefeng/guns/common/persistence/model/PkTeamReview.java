package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 球队评价表
 * </p>
 *
 * @author stylefeng
 * @since 2018-11-22
 */
@TableName("pk_team_review")
public class PkTeamReview extends Model<PkTeamReview> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 球队id
     */
	private Long teamid;
    /**
     * 被评队伍id
     */
	private Long oppoid;
    /**
     * openid
     */
	private String openid;
    /**
     * 文明评分 1-5分
     */
	private BigDecimal culture;
    /**
     * 准时评分 1-5分
     */
	private BigDecimal ontime;
    /**
     * 球队面貌评分 1-5
     */
	private BigDecimal friendly;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTeamid() {
		return teamid;
	}

	public void setTeamid(Long teamid) {
		this.teamid = teamid;
	}

	public Long getOppoid() {
		return oppoid;
	}

	public void setOppoid(Long oppoid) {
		this.oppoid = oppoid;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public BigDecimal getCulture() {
		return culture;
	}

	public void setCulture(BigDecimal culture) {
		this.culture = culture;
	}

	public BigDecimal getOntime() {
		return ontime;
	}

	public void setOntime(BigDecimal ontime) {
		this.ontime = ontime;
	}

	public BigDecimal getFriendly() {
		return friendly;
	}

	public void setFriendly(BigDecimal friendly) {
		this.friendly = friendly;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PkTeamReview{" +
			"id=" + id +
			", teamid=" + teamid +
			", oppoid=" + oppoid +
			", openid=" + openid +
			", culture=" + culture +
			", ontime=" + ontime +
			", friendly=" + friendly +
			"}";
	}
}
