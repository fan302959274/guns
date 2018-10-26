package com.stylefeng.guns.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 球队表
 * </p>
 *
 * @author stylefeng
 * @since 2018-10-11
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
     * 球队积分
     */
	private Integer point;
    /**
     * 球队省份
     */
	private String prov;
    /**
     * 球队市
     */
	private String city;
    /**
     * 球队区
     */
	private String area;
    /**
     * 球队描述
     */
	private String teamdesc;
    /**
     * 所属人
     */
	private Long ownerid;
    /**
     * 球队状态:0:禁用1:启用
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

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
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
			", point=" + point +
			", prov=" + prov +
			", city=" + city +
			", area=" + area +
			", teamdesc=" + teamdesc +
			", ownerid=" + ownerid +
			", status=" + status +
			", createdate=" + createdate +
			", updatedate=" + updatedate +
			"}";
	}
}
