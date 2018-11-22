package com.stylefeng.guns.common.persistence.model;

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
 * 球队表
 * </p>
 *
 * @author stylefeng
 * @since 2018-11-22
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
	private String name;
    /**
     * 球队级别
     */
	private String level;
    /**
     * 球员数量
     */
	private Integer membernum;
    /**
     * 球队胜利场数
     */
	private Integer winnum;
    /**
     * 球队失败场数
     */
	private Integer debtnum;
    /**
     * 球队平局场数
     */
	private Integer drawnum;
    /**
     * 起始积分
     */
	private Integer startpoint;
    /**
     * 球队积分
     */
	private Integer point;
    /**
     * 球队省份
     */
	private Long prov;
    /**
     * 球队市
     */
	private Long city;
    /**
     * 球队区 1南京、2合肥
     */
	private Long area;
    /**
     * 球队描述
     */
	private String teamdesc;
    /**
     * 所属人
     */
	private Long ownerid;
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
    /**
     * 参评队伍总数
     */
	private Integer reviewcount;
    /**
     * 球队状态:1:禁用0:启用
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


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Integer getMembernum() {
		return membernum;
	}

	public void setMembernum(Integer membernum) {
		this.membernum = membernum;
	}

	public Integer getWinnum() {
		return winnum;
	}

	public void setWinnum(Integer winnum) {
		this.winnum = winnum;
	}

	public Integer getDebtnum() {
		return debtnum;
	}

	public void setDebtnum(Integer debtnum) {
		this.debtnum = debtnum;
	}

	public Integer getDrawnum() {
		return drawnum;
	}

	public void setDrawnum(Integer drawnum) {
		this.drawnum = drawnum;
	}

	public Integer getStartpoint() {
		return startpoint;
	}

	public void setStartpoint(Integer startpoint) {
		this.startpoint = startpoint;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Long getProv() {
		return prov;
	}

	public void setProv(Long prov) {
		this.prov = prov;
	}

	public Long getCity() {
		return city;
	}

	public void setCity(Long city) {
		this.city = city;
	}

	public Long getArea() {
		return area;
	}

	public void setArea(Long area) {
		this.area = area;
	}

	public String getTeamdesc() {
		return teamdesc;
	}

	public void setTeamdesc(String teamdesc) {
		this.teamdesc = teamdesc;
	}

	public Long getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(Long ownerid) {
		this.ownerid = ownerid;
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

	public Integer getReviewcount() {
		return reviewcount;
	}

	public void setReviewcount(Integer reviewcount) {
		this.reviewcount = reviewcount;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PkTeam{" +
			"id=" + id +
			", name=" + name +
			", level=" + level +
			", membernum=" + membernum +
			", winnum=" + winnum +
			", debtnum=" + debtnum +
			", drawnum=" + drawnum +
			", startpoint=" + startpoint +
			", point=" + point +
			", prov=" + prov +
			", city=" + city +
			", area=" + area +
			", teamdesc=" + teamdesc +
			", ownerid=" + ownerid +
			", culture=" + culture +
			", ontime=" + ontime +
			", friendly=" + friendly +
			", reviewcount=" + reviewcount +
			", status=" + status +
			", createdate=" + createdate +
			", updatedate=" + updatedate +
			"}";
	}
}
