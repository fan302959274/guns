package com.stylefeng.guns.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;


/**
 * <p>
 * 队员附件表
 * </p>
 *
 * @author stylefeng
 * @since 2018-10-11
 */
@TableName("pk_park_relation")
public class PkParkRelation extends Model<PkParkRelation> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 球场Id
     */
	private Long parkid;
    /**
     * 可用时间
     */
	private String usetime;
	@Override
	protected Serializable pkVal() {
		return this.id;
	}
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
}
