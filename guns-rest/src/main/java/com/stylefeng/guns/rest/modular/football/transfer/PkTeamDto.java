package com.stylefeng.guns.rest.modular.football.transfer;

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
 * @since 2018-10-17
 */
public class PkTeamDto extends Model<PkTeamDto> {

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
	private Long city;
    /**
     * 球队区
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

	private String logo;

	private String openid;//登录者的openid


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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
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
			", creator=" + creator +
			", updator=" + updator +
			", isdeleted=" + isdeleted +
			"}";
	}
}
