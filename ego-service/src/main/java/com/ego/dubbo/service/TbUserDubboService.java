package com.ego.dubbo.service;

import com.ego.pojo.TbUser;

import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/28 16:06
 * @Description:
 * @version: 1.0
 */
public interface TbUserDubboService {

    /**
     * 通过用户名和密码查询用户
     * @param user
     * @return
     */
    List<TbUser> selByUser(TbUser user);

    /**
     * 通过用户名查询用户
     * @param user
     * @return
     */
    List<TbUser> selByUsername(TbUser user);


    /**
     * 插入新用户
     * @param tbUser
     * @return
     */
    int insUser(TbUser tbUser);

}
