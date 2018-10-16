package com.stylefeng.guns.rest.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylefeng
 * @since 2018-10-16
 */
@TableName("auth_user")
public class AuthUser extends Model<AuthUser> {

    private static final long serialVersionUID = 1L;

	private Long id;
	private String userName;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "AuthUser{" +
			"id=" + id +
			", userName=" + userName +
			"}";
	}
}
