package com.ego.commons.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/5 21:00
 * @Description:
 * @version: 1.0
 */
public class EasyUIDataGrid implements Serializable {  // 一定要序列化
    // 当前页显示数据
    private List<?> rows;
    // 表中数据总条数
    private long total;

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
