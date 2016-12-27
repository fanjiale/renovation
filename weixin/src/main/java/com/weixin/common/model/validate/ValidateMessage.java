package com.weixin.common.model.validate;

import java.util.Date;

/**
 * 验证码消息
 *
 * @author H
 */
public class ValidateMessage {

    //与表中MSG_ID对应
    private Long id;

    //关注者openid
    private String open_id;

    //关注着号码
    private String phone_num;

    //验证码
    private String ver_code;

    //有效期
    private int expires_in;

    //创建时间
    private Date create_time;

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

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getVer_code() {
        return ver_code;
    }

    public void setVer_code(String ver_code) {
        this.ver_code = ver_code;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
