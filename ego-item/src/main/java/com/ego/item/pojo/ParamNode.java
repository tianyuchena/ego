package com.ego.item.pojo;

/**
 * @Auther: cty
 * @Date: 2020/5/26 23:48
 * @Description:
 * @version: 1.0
 */
public class ParamNode {
    private String k;
    private String v;

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return "ParamNode{" +
                "k='" + k + '\'' +
                ", v='" + v + '\'' +
                '}';
    }
}
