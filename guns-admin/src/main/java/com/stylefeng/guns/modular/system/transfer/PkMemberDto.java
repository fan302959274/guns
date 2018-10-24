package com.stylefeng.guns.modular.system.transfer;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 队员表
 * </p>
 *
 * @author stylefeng
 * @since 2018-10-10
 */
@TableName("pk_member")
public class PkMemberDto extends Model<PkMemberDto> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 队员账号
     */
    private String account;
    /**
     * 队员名称
     */
    private String name;
    /**
     * 队员性别0:男;1:女
     */
    private String sex;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 出生年月
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;
    /**
     * 主攻位置
     */
    private String position;
    /**
     * 惯用脚
     */
    private String habitfeet;
    /**
     * 身高
     */
    private BigDecimal height;
    /**
     * 体重
     */
    private BigDecimal weight;
    /**
     * 队员类型:1:队长;2:普通球员
     */
    private String type;
    /**
     * 队员状态:0:禁用1:启用
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

    /**
     * 头像
     */
    private String avatar;


    /**
     * 身份证
     */
    private String idcard;

    private String teamlogo;

    private String teamname;

    private String area;
    private Long teamId;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTeamlogo() {
        return teamlogo;
    }

    public void setTeamlogo(String teamlogo) {
        this.teamlogo = teamlogo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getHabitfeet() {
        return habitfeet;
    }

    public void setHabitfeet(String habitfeet) {
        this.habitfeet = habitfeet;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PkMember{" +
                "id=" + id +
                ", account=" + account +
                ", name=" + name +
                ", sex=" + sex +
                ", mobile=" + mobile +
                ", birth=" + birth +
                ", position=" + position +
                ", habitfeet=" + habitfeet +
                ", height=" + height +
                ", weight=" + weight +
                ", type=" + type +
                ", status=" + status +
                ", createdate=" + createdate +
                ", updatedate=" + updatedate +
                ", creator=" + creator +
                ", updator=" + updator +
                ", isdeleted=" + isdeleted +
                "}";
    }
}
