package com.opera.meitu.bean;

/**
 * Created by Sgq
 * Create Date 2019/4/17 and 17:40
 * desc: TODO
 */
public class InfoBean {

    String iv_url;
    String tv_info;

    public String getIv_url() {
        return iv_url == null ? "" : iv_url;
    }

    public void setIv_url(String iv_url) {
        this.iv_url = iv_url;
    }

    public String getTv_info() {
        return tv_info == null ? "" : tv_info;
    }

    public void setTv_info(String tv_info) {
        this.tv_info = tv_info;
    }

    @Override
    public String toString() {
        return "InfoBean{" +
                "iv_url='" + iv_url + '\'' +
                ", tv_info='" + tv_info + '\'' +
                '}';
    }
}
