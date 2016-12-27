package com.weixin.common.model.user;

import java.util.Date;

public class UserBound {

    private Long id;

    private String open_id;  //用户微信号

    private String phone_num;  //用户手机号

    private String msg_id; //内部校验短信ID

    private Date eff_time; //生效时间

    private Date exp_time; //失效时间

    private String ver_code; //验证码

    private Date create_time; //创建时间

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

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public Date getEff_time() {
        return eff_time;
    }

    public void setEff_time(Date eff_time) {
        this.eff_time = eff_time;
    }

    public Date getExp_time() {
        return exp_time;
    }

    public void setExp_time(Date exp_time) {
        this.exp_time = exp_time;
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
}
