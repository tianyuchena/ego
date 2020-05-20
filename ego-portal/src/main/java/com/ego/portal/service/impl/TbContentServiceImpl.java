package com.ego.portal.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.pojo.TbContent;
import com.ego.portal.service.TbContentService;
import com.ego.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: cty
 * @Date: 2020/5/20 19:16
 * @Description:
 * @version: 1.0
 */
@Service
public class TbContentServiceImpl implements TbContentService {

    @Reference
    private TbContentDubboService tbContentDubboServiceImpl;
    @Resource
    private JedisDao jedisDaoImpl;
    @Value("${redis.bigPic.key}")
    private String key;
    @Value("${redis.bigPic.number}")
    private int bigPicNum;  // 最多显示几张大广告图片

    @Override
    public String showBigPic() {
        // 先判断redis中是否存在
        if(jedisDaoImpl.exists(key))
        {
            String value = jedisDaoImpl.get(key);
            if(value!=null && !value.equals(""))
            {
                return value;
            }
        }

        // 若不存在从mysql中取，取完后放到redis中
        List<TbContent> contents = tbContentDubboServiceImpl.selByCount(bigPicNum, true);

        List<Map<String, Object>> results = new ArrayList<>();
        for(TbContent content : contents)
        {
            Map<String, Object> map = new HashMap<>();
            map.put("srcB", content.getPic2());
            map.put("height", 240);
            map.put("alt", "对不起,加载图片失败");
            map.put("width", 670);
            map.put("src", content.getPic());
            map.put("widthB", 550);
            map.put("href", content.getUrl() );
            map.put("heightB", 240);

            results.add(map);
        }

        String resultsJson = JsonUtils.objectToJson(results);
        jedisDaoImpl.set(key, resultsJson);
        return resultsJson;
    }
}
