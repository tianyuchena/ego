package com.ego.order.interceptor;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/31 9:21
 * @Description:
 * @version: 1.0
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        // 验证是否已经登录
        Cookie[] cookies = request.getCookies();
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        if(token!=null && !token.equals(""))
        {
            String userInfoJson = HttpClientUtil.doPost("http://localhost:8084/user/token/"+token);
            EgoResult er = JsonUtils.jsonToPojo(userInfoJson, EgoResult.class);
            if(er.getStatus() == 200)
                return true;
        }

        String[] ids = request.getParameterValues("id");
        String param = "%3f";
        for(int i=0; i<ids.length; i++)
        {
            if(i == 0)
                param = param + "id%3d"+ids[0];
            else
                param = param + "%26id%3d" + ids[i];
        }
        String interurl = request.getRequestURL() + param;
        response.sendRedirect("http://localhost:8084/user/showLogin?interurl="+interurl);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
