package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbUserDubboService;
import com.ego.mapper.TbUserMapper;
import com.ego.pojo.TbUser;
import com.ego.pojo.TbUserExample;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: cty
 * @Date: 2020/5/28 16:08
 * @Description:
 * @version: 1.0
 */
public class TbUserDubboServiceImpl implements TbUserDubboService {
    @Resource
    private TbUserMapper tbUserMapper;

    @Override
    public List<TbUser> selByUser(TbUser user) {
        TbUserExample example = new TbUserExample();
        example.createCriteria().andUsernameEqualTo(user.getUsername()).andPasswordEqualTo(user.getPassword());
        return tbUserMapper.selectByExample(example);
    }

    @Override
    public List<TbUser> selByUsername(TbUser user) {
        TbUserExample example = new TbUserExample();
        example.createCriteria().andUsernameEqualTo(user.getUsername());
        return tbUserMapper.selectByExample(example);
    }

    @Override
    public int insUser(TbUser tbUser) {
        int index = tbUserMapper.insertSelective(tbUser);
        if(index > 0)
            return 1;
        return 0;
    }
}
