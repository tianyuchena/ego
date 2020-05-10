package com.ego.commons.pojo;

/**
 * @Auther: cty
 * @Date: 2020/5/8 20:51
 * @Description: 树节点
 * @version: 1.0
 */
public class EasyUITree {
    private long id;
    private String text;
    private String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
