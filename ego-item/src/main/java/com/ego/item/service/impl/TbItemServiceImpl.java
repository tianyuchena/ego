package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.item.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: cty
 * @Date: 2020/5/26 9:42
 * @Description:
 * @version: 1.0
 */
@Service
public class TbItemServiceImpl implements TbItemService {
    @Reference
    private TbItemDubboService tbItemDubboService;
    @Resource
    private JedisDao jedisDaoImpl;
    @Value("${redis.item.key}")
    private String itemKey;

    @Override
    public TbItemChild showItem(long id) {
        // 若Redis缓存中存在数据，从缓存中获取数据
        if(jedisDaoImpl.exists(itemKey + id))
        {
            String itemJson = jedisDaoImpl.get(itemKey + id);
            if(itemJson!=null && !itemJson.equals(""))
                return JsonUtils.jsonToPojo(itemJson, TbItemChild.class);
        }

        // 若Redis缓存中不存在数据，从MySQL中获取数据
        TbItemChild itemChild = new TbItemChild();
        TbItem item = tbItemDubboService.selById(id);

        itemChild.setId(item.getId());
        itemChild.setTitle(item.getTitle());
        itemChild.setSellPoint(item.getSellPoint());
        itemChild.setPrice(item.getPrice());
        itemChild.setImages((item.getImage()==null && item.getImage().equals("")) ? new String[1] : item.getImage().split(","));

        // 将MySQL获取的数据存入Redis缓存
        jedisDaoImpl.set(itemKey+id, JsonUtils.objectToJson(itemChild));

        return itemChild;
    }  // end method showItem
}
