package com.ego.commons.pojo;

import com.ego.pojo.TbItem;

import java.util.Arrays;

/**
 * @Auther: cty
 * @Date: 2020/5/24 15:36
 * @Description:
 * @version: 1.0
 */
public class TbItemChild extends TbItem {
    private String[] images;

    private Boolean enough;

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public Boolean getEnough() {
        return enough;
    }

    public void setEnough(Boolean enough) {
        this.enough = enough;
    }

    @Override
    public String toString() {
        return "TbItemChild{" +
                "images=" + Arrays.toString(images) +
                ", enough=" + enough +
                '}';
    }
}
