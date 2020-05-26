package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.item.service.TbItemDescService;
import com.ego.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: cty
 * @Date: 2020/5/26 16:43
 * @Description:
 * @version: 1.0
 */
@Service
public class TbItemDescServiceImpl implements TbItemDescService {
    @Reference
    private TbItemDescDubboService tbItemDescDubboServiceImpl;
    @Resource
    private JedisDao jedisDaoImpl;
    @Value("${redis.desc.key}")
    private String descKey;

    @Override
    public String showDesc(long id) {
        // 若redis缓存中存在描述数据，则从缓存中获取数据
        if(jedisDaoImpl.exists(descKey + id))
        {
            String redisDesc = jedisDaoImpl.get(descKey + id);
            if(redisDesc!=null && !redisDesc.equals(""))
                return redisDesc;
        }

        // 若redis缓存中不存在描述数据，则从数据库中获取数据
        String desc = tbItemDescDubboServiceImpl.selById(id).getItemDesc();

        // 将从数据库中获取数据存入缓存
        jedisDaoImpl.set(descKey+id, desc);

        return desc;
    }
}
