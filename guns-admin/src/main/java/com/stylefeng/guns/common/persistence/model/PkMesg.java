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
 * 
 * </p>
 *
 * @author stylefeng
 * @since 2018-11-23
 */
@TableName("pk_mesg")
public class PkMesg extends Model<PkMesg> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 消息名称
     */
	private String mesgname;
    /**
     * 0 全部 1 队长 2队员
     */
	private String objtype;
	private Date createdate;
    /**
     * 推送内容
     */
	private String content;
    /**
     * 渠道 1 短信 2 其他
     */
	private String channel;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMesgname() {
		return mesgname;
	}

	public void setMesgname(String mesgname) {
		this.mesgname = mesgname;
	}

	public String getObjtype() {
		return objtype;
	}

	public void setObjtype(String objtype) {
		this.objtype = objtype;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "PkMesg{" +
			"id=" + id +
			", mesgname=" + mesgname +
			", objtype=" + objtype +
			", createdate=" + createdate +
			", content=" + content +
			", channel=" + channel +
			"}";
	}
}
