package com.ego.passport.service;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: cty
 * @Date: 2020/5/28 16:22
 * @Description:
 * @version: 1.0
 */
public interface TbUserService {

    /**
     * 根据用户名和密码登录
     * @param user
     * @param request
     * @param response
     * @return
     */
    EgoResult login(TbUser user, HttpServletRequest request, HttpServletResponse response);

    /**
     * 获取用户信息，实现单点登录
     * @param token
     * @return
     */
    EgoResult getUserInfoByToken(String token);

    /**
     * 安全退出
     * @param token
     * @return
     */
    EgoResult logOut(String token, HttpServletRequest request, HttpServletResponse response);

    /**
     * 检查名称
     * @param username
     * @return
     */
    EgoResult checkUsername(String username);

    /**
     * 检查电话
     * @param phone
     * @return
     */
    EgoResult checkPhone(String phone);

    /**
     * 检查电话
     * @param email
     * @return
     */
    EgoResult checkEmail(String email);

    /**
     * 用户注册
     * @param user
     * @return
     */
    EgoResult register(TbUser user);


}
