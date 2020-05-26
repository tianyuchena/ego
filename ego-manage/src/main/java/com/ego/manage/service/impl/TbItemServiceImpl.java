package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.ego.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: cty
 * @Date: 2020/5/5 21:59
 * @Description:
 * @version: 1.0
 */
@Service
public class TbItemServiceImpl implements TbItemService {
    @Reference
    private TbItemDubboService tbItemDubboServiceImpl;
    @Reference
    private TbItemDescDubboService tbItemDescDubboServiceImpl;
    @Value("${search.url}")
    private String searchUrl;
    @Resource
    private JedisDao jedisDaoImpl;
    @Value("${redis.item.key}")
    private String itemKey;
    @Value("${redis.desc.key}")
    private String descKey;
    @Value("${redis.param.key}")
    private String paramKey;

    @Override
    public EasyUIDataGrid show(int page, int rows) {
        return tbItemDubboServiceImpl.show(page, rows);
    }

    @Override
    public int updItemStatus(String ids, byte status) {
        int index = 0;
        TbItem item = new TbItem();
        String[] idsStr = ids.split(",");
        for(String id : idsStr){
            item.setId(Long.parseLong(id));
            item.setStatus(status);
            index += tbItemDubboServiceImpl.updItem(item);

            // 缓存同步删除和下架
            if(status==2 || status==3)
                jedisDaoImpl.del(itemKey + id);
        }
        if(index == idsStr.length)
            return 1;
        return 0;
    }

    @Override
    public int insTbItemDesc(TbItem item, String desc, String itemParams) throws Exception {
        // 准备数据
        long id = IDUtils.genItemId();
        Date date = new Date();

        item.setId(id);
        item.setCreated(date);
        item.setUpdated(date);
        item.setStatus((byte)1);

        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(id);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);

        TbItemParamItem paramItem = new TbItemParamItem();
        paramItem.setItemId(id);
        paramItem.setParamData(itemParams);
        paramItem.setCreated(date);
        paramItem.setUpdated(date);

        // 向数据库中插入商品、描述和规格参数
        int index = tbItemDubboServiceImpl.insTbItemDesc(item, itemDesc, paramItem);

        // 若在数据库中插入成功
        if(index != 0)
        {
            // 同步向solr缓存中插入商品、描述和规格参数
            final TbItem itemFinal = item;
            final String descFinal = desc;
            new Thread(){
                public void run(){
                    Map<String, Object> map = new HashMap<>();
                    map.put("item", itemFinal);
                    map.put("desc", descFinal);
                    HttpClientUtil.doPostJson(searchUrl, JsonUtils.objectToJson(map));
                }
            }.start();

            return 1;
        }
        return 0;
    }

    @Override
    public int updItemDesc(TbItem item, String desc, String itemParamId, String itemParams) throws Exception{

        // 准备数据
        Date date = new Date();
        item.setUpdated(date);

        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(date);

        TbItemParamItem paramItem = new TbItemParamItem();
        paramItem.setId(Long.parseLong(itemParamId));
        paramItem.setParamData(itemParams);
        paramItem.setUpdated(date);

        // 在数据库中更新商品内容、商品描述和规格参数
        int index = tbItemDubboServiceImpl.updTbItemDesc(item, itemDesc, paramItem);

        // 若在数据库中修改成功
        if(index != 0)
        {
            // 同步在solr缓存中更新商品内容、商品描述（其实是覆盖掉原来的数据）
            final TbItem itemFinal = item;
            final String descFinal = desc;
            new Thread(){
                public void run(){
                    Map<String, Object> map = new HashMap<>();
                    map.put("item", itemFinal);
                    map.put("desc", descFinal);
                    HttpClientUtil.doPostJson(searchUrl, JsonUtils.objectToJson(map));
                }
            }.start();

            // 同步在redis缓存中更新商品内容、商品描述和规格参数
            if(jedisDaoImpl.exists(itemKey + item.getId()))
            {
                String itemJson = jedisDaoImpl.get(itemKey + item.getId());
                if(itemJson!=null && !itemJson.equals(""))  // 若在redis缓存存在，则用修改后的内容覆盖
                {
                    TbItemChild itemChild = new TbItemChild();
                    itemChild.setId(item.getId());
                    itemChild.setTitle(item.getTitle());
                    itemChild.setSellPoint(item.getSellPoint());
                    itemChild.setPrice(item.getPrice());
                    itemChild.setImages((item.getImage()==null && item.getImage().equals("")) ? new String[1] : item.getImage().split(","));

                    // 将MySQL获取的数据存入Redis缓存
                    jedisDaoImpl.set(itemKey+item.getId(), JsonUtils.objectToJson(itemChild));
                    jedisDaoImpl.set(descKey+item.getId(), desc);
                    jedisDaoImpl.set(paramKey+item.getId(), itemParams);
                }
            }


            return 1;
        }
        return 0;
    }  // end methd updItem

}  // end class TbItemServiceImpl
