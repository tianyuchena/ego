package com.ego.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbUser;
import com.ego.redis.dao.JedisDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/31 11:19
 * @Description:
 * @version: 1.0
 */
@Service
public class CartServiceImpl implements CartService {
    Logger log = Logger.getLogger(CartServiceImpl.class);

    @Resource
    private JedisDao jedisDaoImpl;
    @Reference
    private TbItemDubboService tbItemDubboServiceImpl;
    @Value("${cart.key}")
    private String cartKey;

    @Override
    public void addCart(long itemId, int num, HttpServletRequest request) {
        // 通过cookie获取用户信息，并生成用户的购物车userCartkey
        String userInfoKey = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String userInfoJson = jedisDaoImpl.get(userInfoKey);
        TbUser tbUser = JsonUtils.jsonToPojo(userInfoJson, TbUser.class);
        String userCartKey = cartKey+tbUser.getUsername();

        // 根据userCartkey查询缓存中用户的购物车商品列表userCartList
        List<TbItemChild> userCartList = new ArrayList<>();

        if(jedisDaoImpl.exists(userCartKey))  // userCartList存在
        {
            log.debug("根据userCartkey查询缓存中用户的购物车商品列表userCartList");
            String userCartListJson = jedisDaoImpl.get(userCartKey);
            if(userCartListJson!=null && !userCartListJson.equals(""))  // userCartList非空
            {
                userCartList = JsonUtils.jsonToList(userCartListJson, TbItemChild.class);

                for(TbItemChild tbItemChild: userCartList)
                {
                    log.debug("若userCartList非空且商品已经存在，商品数目增加，覆盖原缓存信息");
                    if(tbItemChild.getId() == itemId)
                    {
                        log.debug("商品已经存在");
                        tbItemChild.setNum(tbItemChild.getNum()+num);  // 商品数目累加
                        jedisDaoImpl.set(userCartKey, JsonUtils.objectToJson(userCartList));
                        return ;
                    }
                }
            }
        }

        log.debug("若userCartList不存在或为空，将当前商品信息itemInfo添加到userCartList，并写入缓存");
        TbItemChild tbItemChild = new TbItemChild();
        TbItem tbItem = tbItemDubboServiceImpl.selById(itemId);  // 从数据库查询商品
        tbItemChild.setId(tbItem.getId());
        tbItemChild.setTitle(tbItem.getTitle());
        tbItemChild.setImages((tbItem.getImage()==null || tbItem.getImage().equals(""))?new String[1]:tbItem.getImage().split(","));
        tbItemChild.setNum(num);
        tbItemChild.setPrice(tbItem.getPrice());

        userCartList.add(tbItemChild);
        jedisDaoImpl.set(userCartKey, JsonUtils.objectToJson(userCartList));
    }  // end method addCart

    @Override
    public List<TbItemChild> getCart(HttpServletRequest request) {
        // 通过cookie获取用户信息，并生成用户的购物车userCartkey
        String userInfoKey = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String userInfoJson = jedisDaoImpl.get(userInfoKey);
        TbUser tbUser = JsonUtils.jsonToPojo(userInfoJson, TbUser.class);
        String userCartKey = cartKey+tbUser.getUsername();

        String userCartListJson = jedisDaoImpl.get(userCartKey);
        return JsonUtils.jsonToList(userCartListJson, TbItemChild.class);
    }

    @Override
    public EgoResult updateCart(long itemId, int updateNum, HttpServletRequest request) {
        String userInfoKey = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String userInfoJson = jedisDaoImpl.get(userInfoKey);
        TbUser tbUser = JsonUtils.jsonToPojo(userInfoJson, TbUser.class);
        String userCartKey = cartKey+tbUser.getUsername();

        String userCartListJson = jedisDaoImpl.get(userCartKey);
        List<TbItemChild> tbItemChildList = JsonUtils.jsonToList(userCartListJson, TbItemChild.class);
        for(TbItemChild child: tbItemChildList)
        {
            if(child.getId() == itemId)
               child.setNum(updateNum);
        }
        jedisDaoImpl.set(userCartKey, JsonUtils.objectToJson(tbItemChildList));

        EgoResult er = new EgoResult();
        er.setStatus(200);
        er.setMsg("OK");
        er.setData(tbItemChildList);
        return er;
    }

    @Override
    public EgoResult deleteItem(long itemId, HttpServletRequest request) {
        String userInfoKey = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String userInfoJson = jedisDaoImpl.get(userInfoKey);
        TbUser tbUser = JsonUtils.jsonToPojo(userInfoJson, TbUser.class);
        String userCartKey = cartKey+tbUser.getUsername();

        String userCartListJson = jedisDaoImpl.get(userCartKey);
        List<TbItemChild> tbItemChildList = JsonUtils.jsonToList(userCartListJson, TbItemChild.class);

        TbItemChild tbItemChild = null;
        for(TbItemChild child: tbItemChildList)
        {
            if(child.getId() == itemId)
                tbItemChild = child;
        }
        tbItemChildList.remove(tbItemChild);
        jedisDaoImpl.set(userCartKey, JsonUtils.objectToJson(tbItemChildList));

        EgoResult er = new EgoResult();
        er.setStatus(200);
        er.setMsg("OK");
        er.setData(tbItemChildList);
        return er;
    }


}  // end class CartServiceImpl
