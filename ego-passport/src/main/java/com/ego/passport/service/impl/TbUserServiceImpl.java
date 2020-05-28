package com.ego.passport.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbUserDubboService;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;
import com.ego.redis.dao.JedisDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: cty
 * @Date: 2020/5/28 16:24
 * @Description:
 * @version: 1.0
 */
@Service
public class TbUserServiceImpl implements TbUserService {
    @Reference
    private TbUserDubboService tbUserDubboServiceImpl;
    @Resource
    private JedisDao jedisDaoImpl;


    @Override
    public EgoResult login(TbUser user, HttpServletRequest request, HttpServletResponse response) {
        EgoResult er = new EgoResult();

        List<TbUser> tbUsers = tbUserDubboServiceImpl.selByUser(user);
        if(tbUsers!=null && tbUsers.size()>0) {
            TbUser tbUser = tbUsers.get(0);

            // 服务器端：redis中添加用户登录信息缓存
            String key = UUID.randomUUID().toString();
            jedisDaoImpl.set(key, JsonUtils.objectToJson(tbUser));
            int daysOf7 = 60*60*24*7;
            jedisDaoImpl.expire(key, daysOf7);  // 设置过期时间
            // 浏览器端：让浏览器存储key的cookie
            CookieUtils.setCookie(request, response, "TT_TOKEN", key, daysOf7);

            er.setStatus(200);
            er.setMsg("OK");
            er.setData(key);
        }
        else
            er.setMsg("用户名或密码错误");

        return er;
    }

    @Override
    public EgoResult getUserInfoByToken(String token) {
        EgoResult er = new EgoResult();
        String userInfoJson = jedisDaoImpl.get(token);
        if(userInfoJson!=null && !userInfoJson.equals(""))
        {
            TbUser tbUser = JsonUtils.jsonToPojo(userInfoJson, TbUser.class);
            tbUser.setPassword(null);  // 不可获取用户密码
            er.setStatus(200);
            er.setMsg("OK");
            er.setData(tbUser);
        }
        else
            er.setMsg("获取用户信息失败");

        return er;
    }

    @Override
    public EgoResult logOut(String token, HttpServletRequest request, HttpServletResponse response) {
        EgoResult er = new EgoResult();

        // 清除用户信息缓存
        jedisDaoImpl.del(token);

        // 清除用户信息cookie
        CookieUtils.deleteCookie(request, response, "TT_TOKEN");


        er.setStatus(200);
        er.setMsg("OK");
        er.setData("");
        return er;
    }

    @Override
    public EgoResult checkUsername(String username) {
        EgoResult er = new EgoResult();

        TbUser user = new TbUser();
        user.setUsername(username);
        List<TbUser> tbUsers = tbUserDubboServiceImpl.selByUsername(user);
        if(tbUsers!=null && tbUsers.size()>0)  // 用户名已经存在
        {
            er.setData(false);
            return er;
        }

        er.setStatus(200);
        er.setMsg("OK");
        er.setData(true);
        return er;
    }

    @Override
    public EgoResult checkPhone(String phone) {
        EgoResult er = new EgoResult();
        er.setStatus(200);
        er.setMsg("OK");
        er.setData(true);

        return er;
    }

    @Override
    public EgoResult checkEmail(String email) {
        EgoResult er = new EgoResult();
        er.setStatus(200);
        er.setMsg("OK");
        er.setData(true);

        return er;
    }

    @Override
    public EgoResult register(TbUser user) {
        EgoResult er = new EgoResult();

        Date date = new Date();
        user.setCreated(date);
        user.setUpdated(date);

        int index = tbUserDubboServiceImpl.insUser(user);
        if(index == 1)
            er.setStatus(200);

        return er;
    }
}
