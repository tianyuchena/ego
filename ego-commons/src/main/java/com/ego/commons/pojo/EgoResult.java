package com.ego.commons.pojo;

/**
 * @Auther: cty
 * @Date: 2020/5/5 23:09
 * @Description:
 * @version: 1.0
 */
public class EgoResult {
    private int status;
    private Object data;
    private Object msg;

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
