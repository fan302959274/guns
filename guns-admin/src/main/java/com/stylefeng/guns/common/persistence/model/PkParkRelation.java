package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 球场可用时间表
 * </p>
 *
 * @author stylefeng
 * @since 2018-11-02
 */
@TableName("pk_park_relation")
public class PkParkRelation extends Model<PkParkRelation> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	private Long parkid;
	private String usetime;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParkid() {
		return parkid;
	}

	public void setParkid(Long parkid) {
		this.parkid = parkid;
	}

	public String getUsetime() {
		return usetime;
	}

	public void setUsetime(String usetime) {
		this.usetime = usetime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PkParkRelation{" +
			"id=" + id +
			", parkid=" + parkid +
			", usetime=" + usetime +
			"}";
	}
}
