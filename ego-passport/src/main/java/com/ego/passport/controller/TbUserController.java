package com.ego.passport.controller;

import com.ego.commons.pojo.EgoResult;
import com.ego.passport.service.TbUserService;
import com.ego.pojo.TbUser;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Path;

/**
 * @Auther: cty
 * @Date: 2020/5/28 15:48
 * @Description:
 * @version: 1.0
 */
@Controller
public class TbUserController {
    @Resource
    private TbUserService tbUserServiceImpl;

    /**
     * 显示用户登录界面
     * @param url
     * @param model
     * @return
     */
    @RequestMapping("user/showLogin")
    public String login(@RequestHeader("Referer") String url, Model model)
    {
        model.addAttribute("redirect", url);
        return "login";
    }

    /**
     * 用户登录
     * @param user
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("user/login")
    @ResponseBody
    public EgoResult loginCheck(TbUser user, HttpServletRequest request, HttpServletResponse response)
    {
        return tbUserServiceImpl.login(user, request, response);
    }

    /**
     * 获取用户信息
     * @param token
     * @param callback
     * @return
     */
    @RequestMapping("user/token/{token}")
    @ResponseBody
    public Object getUserInfo(@PathVariable String token, String callback)
    {
        EgoResult er = tbUserServiceImpl.getUserInfoByToken(token);
        if(callback!=null && !callback.equals(""))  // 跨域访问，jsonp
        {
            MappingJacksonValue mjv = new MappingJacksonValue(er);
            mjv.setJsonpFunction(callback);
            return mjv;
        }
        return er;
    }

    /**
     * 用户退出
     * @param token
     * @param callback
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("user/logout/{token}")
    @ResponseBody
    public Object logout(@PathVariable String token, String callback, HttpServletRequest request, HttpServletResponse response)
    {
        EgoResult er = tbUserServiceImpl.logOut(token, request, response);
        if(callback!=null && !callback.equals(""))  // 跨域访问，jsonp
        {
            MappingJacksonValue mjv = new MappingJacksonValue(er);
            mjv.setJsonpFunction(callback);
            return mjv;
        }
        return er;
    }

    /**
     * 显示注册界面
     * @return
     */
    @RequestMapping("user/showRegister")
    public String showRegister()
    {
        return "register";
    }

    /**
     * 检查数据是否可用
     * @param param
     * @param type
     * @param callback
     * @return
     */
    @RequestMapping("user/check/{param}/{type}")
    @ResponseBody
    public Object check(@PathVariable String param, @PathVariable String type, String callback)
    {
        EgoResult er = new EgoResult();

        if(param!=null && !param.equals("") && type!=null && !type.equals(""))
        {
            switch(type)
            {
                case "1":
                    er = tbUserServiceImpl.checkUsername(param);
                    break;
                case "2":
                    er = tbUserServiceImpl.checkPhone(param);
                    break;
                case "3":
                    er = tbUserServiceImpl.checkEmail(param);
                    break;
                default:
                    break;
            }
        }

        if(callback!=null && !callback.equals(""))  // 跨域访问，jsonp
        {
            MappingJacksonValue mjv = new MappingJacksonValue(er);
            mjv.setJsonpFunction(callback);
            return mjv;
        }

        return er;
    }

    /**
     * 用戶注冊
     * @param tbUser
     * @return
     */
    @RequestMapping("user/register")
    @ResponseBody
    public EgoResult Register(TbUser tbUser)
    {
        return tbUserServiceImpl.register(tbUser);
    }

}
