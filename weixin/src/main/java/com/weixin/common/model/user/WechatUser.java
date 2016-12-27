package com.weixin.common.model.user;

import java.util.Date;

/**
 * 微信用户信息表
 */
public class WechatUser {

    private Long id;

    private String open_id;//微信号

    private Integer subscribe_status;//用户是否关注 1 已关注 0取消关注

    private Date status_time;//状态时间

    private Date subscribe_time; //关注时间

    private String nick_name;//用户昵称

    private Integer sex;//性别 1男 0女

    private String city;//所在城市

    private String language; //语言

    private String province;//所在省份

    private String country;//所在国家

    private String union_id;//用户全局ID

    private String remark;//备注

    private String group_id;//所属组ID

    private String head_img_url;//头像url

    private Date create_time;//创建时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public Integer getSubscribe_status() {
        return subscribe_status;
    }

    public void setSubscribe_status(Integer subscribe_status) {
        this.subscribe_status = subscribe_status;
    }

    public Date getStatus_time() {
        return status_time;
    }

    public void setStatus_time(Date status_time) {
        this.status_time = status_time;
    }

    public Date getSubscribe_time() {
        return subscribe_time;
    }

    public void setSubscribe_time(Date subscribe_time) {
        this.subscribe_time = subscribe_time;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUnion_id() {
        return union_id;
    }

    public void setUnion_id(String union_id) {
        this.union_id = union_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getHead_img_url() {
        return head_img_url;
    }

    public void setHead_img_url(String head_img_url) {
        this.head_img_url = head_img_url;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
