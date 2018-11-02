package com.stylefeng.guns.rest.common.persistence.model;

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
 * @since 2018-11-02
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
	private Long time;
    /**
     * 比赛地点
     */
	private String place;
    /**
     * 比赛发起人
     */
	private Long initiatorid;
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
	private Long prov;
    /**
     * 比赛市
     */
	private Long city;
    /**
     * 比赛区
     */
	private Long area;
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

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Long getInitiatorid() {
		return initiatorid;
	}

	public void setInitiatorid(Long initiatorid) {
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
			", updatedate=" + updatedate +
			", creator=" + creator +
			", updator=" + updator +
			", isdeleted=" + isdeleted +
			"}";
	}
}
