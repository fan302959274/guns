package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 比赛表
 * </p>
 *
 * @author stylefeng
 * @since 2018-10-27
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
	private String name;
    /**
     * 比赛时间
     */
	private Integer time;
    /**
     * 比赛地点
     */
	private String place;
    /**
     * 比赛发起人
     */
	private String initiatorid;
    /**
     * 东道主team
     */
	private Long hostteamid;
    /**
     * 挑战team
     */
	private Long challengeteamid;
    /**
     * 比赛省份
     */
	private String prov;
    /**
     * 比赛市
     */
	private String city;
    /**
     * 比赛区
     */
	private String area;
    /**
     * 球赛开始时间
     */
	private Date starttime;
    /**
     * 球赛结束时间
     */
	private Date endtime;
    /**
     * 球场id
     */
	private Long parkid;
    /**
     * 比赛状态(待定)
     */
	private Integer status;
    /**
     * 创建时间
     */
	private Date createdate;
    /**
     * 发起方支付状态
     */
	private Integer hostpaystatus;
    /**
     * 挑战者支付状态
     */
	private Integer challengepaystatus;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getInitiatorid() {
		return initiatorid;
	}

	public void setInitiatorid(String initiatorid) {
		this.initiatorid = initiatorid;
	}

	public Long getHostteamid() {
		return hostteamid;
	}

	public void setHostteamid(Long hostteamid) {
		this.hostteamid = hostteamid;
	}

	public Long getChallengeteamid() {
		return challengeteamid;
	}

	public void setChallengeteamid(Long challengeteamid) {
		this.challengeteamid = challengeteamid;
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

	public Long getParkid() {
		return parkid;
	}

	public void setParkid(Long parkid) {
		this.parkid = parkid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Integer getHostpaystatus() {
		return hostpaystatus;
	}

	public void setHostpaystatus(Integer hostpaystatus) {
		this.hostpaystatus = hostpaystatus;
	}

	public Integer getChallengepaystatus() {
		return challengepaystatus;
	}

	public void setChallengepaystatus(Integer challengepaystatus) {
		this.challengepaystatus = challengepaystatus;
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
		return "PkMatch{" +
			"id=" + id +
			", name=" + name +
			", time=" + time +
			", place=" + place +
			", initiatorid=" + initiatorid +
			", hostteamid=" + hostteamid +
			", challengeteamid=" + challengeteamid +
			", prov=" + prov +
			", city=" + city +
			", area=" + area +
			", starttime=" + starttime +
			", endtime=" + endtime +
			", parkid=" + parkid +
			", status=" + status +
			", createdate=" + createdate +
			", hostpaystatus=" + hostpaystatus +
			", challengepaystatus=" + challengepaystatus +
			", updatedate=" + updatedate +
			", creator=" + creator +
			", updator=" + updator +
			", isdeleted=" + isdeleted +
			"}";
	}
}
