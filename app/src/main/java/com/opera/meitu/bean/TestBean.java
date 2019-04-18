package com.opera.meitu.bean;

/**
 * Created by Sgq
 * Create Date 2019/4/18 and 11:47
 * desc: 测试bean
 */
public class TestBean {

    private String name;
    private String uid;

    @Override
    public String toString() {
        return "TestBean{" +
                "name='" + name + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid == null ? "" : uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
