package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.item.pojo.ParamItem;
import com.ego.item.service.TbItemParamItemService;
import com.ego.pojo.TbItemParamItem;
import com.ego.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/26 22:25
 * @Description:
 * @version: 1.0
 */
@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService {
    @Reference
    private TbItemParamItemDubboService tbItemParamItemDubboServiceImpl;
    @Resource
    private JedisDao jedisDaoImpl;
    @Value("${redis.param.key}")
    private String paramKey;

    @Override
    public String showParamItem(long itemId) {
        String paramStr = "";
        List<ParamItem> paramItems = null;

        // 若redis缓存中存在规格参数数据，则从缓存中获取数据
        if(jedisDaoImpl.exists(paramKey + itemId))
        {
            paramStr = jedisDaoImpl.get(paramKey + itemId);
            if(paramStr!=null && !paramStr.equals(""))
            {
                paramItems = JsonUtils.jsonToList(paramStr, ParamItem.class);
                return ParamItem.ParamItemToHtmlStr(paramItems);
            }
        }

        // 若redis缓存中不存在规格参数数据，则从数据库中获取数据
        List<TbItemParamItem> tbItemParamItems = tbItemParamItemDubboServiceImpl.selByItemId(itemId);
        if(paramItems!=null && paramItems.size()>0)
        {
            paramStr = tbItemParamItems.get(0).getParamData();

            // 将从数据库查询到的数据存入缓存
            jedisDaoImpl.set(paramKey + itemId, paramStr);

            paramItems = JsonUtils.jsonToList(paramStr, ParamItem.class);
            return ParamItem.ParamItemToHtmlStr(paramItems);
        }

        return null;
    }
}
