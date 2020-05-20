package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;
import com.ego.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: cty
 * @Date: 2020/5/18 21:06
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
    public EasyUIDataGrid show(long categoryId, int page, int rows) {
        return tbContentDubboServiceImpl.show(categoryId, page, rows);
    }

    @Override
    public EgoResult insert(TbContent content) {
        EgoResult er = new EgoResult();

        Date date = new Date();
        content.setCreated(date);
        content.setUpdated(date);
        int index = tbContentDubboServiceImpl.insert(content);
        if(index > 0)
        {
            er.setStatus(200);
        }

        // 同步redis缓存
        if(jedisDaoImpl.exists(key))
        {
            String value = jedisDaoImpl.get(key);
            if(value!=null && !value.equals(""))
            {
                List<HashMap> list = JsonUtils.jsonToList(value, HashMap.class);

                HashMap<String, Object> map = new HashMap<>();
                map.put("srcB", content.getPic2());
                map.put("height", 240);
                map.put("alt", "对不起,加载图片失败");
                map.put("width", 670);
                map.put("src", content.getPic());
                map.put("widthB", 550);
                map.put("href", content.getUrl() );
                map.put("heightB", 240);

                if(list.size() == bigPicNum)  // 如果已经满，减去一个
                    list.remove(bigPicNum-1);
                list.add(0, map);

                jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
            }
        }

        return er;
    }

    @Override
    public EgoResult delete(long ids) {
        EgoResult er = new EgoResult();

        int index = tbContentDubboServiceImpl.delete(ids);
        if(index > 0)
        {
            er.setStatus(200);
        }

        // 同步redis缓存
        jedisDaoImpl.del(key);

        return er;
    }

    @Override
    public EgoResult update(TbContent content) {
        EgoResult er = new EgoResult();

        Date date = new Date();
        content.setUpdated(date);
        int index = tbContentDubboServiceImpl.update(content);
        if(index > 0)
        {
            er.setStatus(200);
        }

        // 同步redis缓存
        jedisDaoImpl.del(key);

        return er;
    }
}
