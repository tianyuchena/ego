package com.ego.redis.dao;

/**
 * @Auther: cty
 * @Date: 2020/5/20 20:04
 * @Description:
 * @version: 1.0
 */
public interface JedisDao {

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    Boolean exists(String key);

    /**
     * 删除
     * @param key
     * @return
     */
    Long del(String key);

    /**
     * 设置值
     * @param key
     * @param value
     * @return
     */
    String set(String key, String value);

    /**
     * 取值
     * @param key
     * @return
     */
    String get(String key);


    /**
     * 设置key过期时间
     * @param key
     * @param seconds
     * @return
     */
    Long expire(String key, int seconds);

}
