package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.manage.service.TbItemDescService;
import com.ego.pojo.TbItemDesc;
import org.springframework.stereotype.Service;

/**
 * @Auther: cty
 * @Date: 2020/5/26 18:46
 * @Description:
 * @version: 1.0
 */
@Service
public class TbItemDescServiceImpl implements TbItemDescService {
    @Reference
    private TbItemDescDubboService tbItemDescDubboServiceImpl;

    @Override
    public TbItemDesc showDesc(long id) {
        return tbItemDescDubboServiceImpl.selById(id);
    }
}
