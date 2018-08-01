package cn.choleece.zhengboot.upms.rpc.service.impl;

import cn.choleece.zhengboot.common.annotation.BaseService;
import cn.choleece.zhengboot.common.base.BaseServiceImpl;
import cn.choleece.zhengboot.upms.dao.mapper.UpmsSystemMapper;
import cn.choleece.zhengboot.upms.dao.model.UpmsSystem;
import cn.choleece.zhengboot.upms.dao.model.UpmsSystemExample;
import cn.choleece.zhengboot.upms.rpc.api.IUpmsSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UpmsSystemService实现
* Created by shuzheng on 2018/7/30.
*/
@Service
@Transactional
public class UpmsSystemServiceImpl extends BaseServiceImpl<UpmsSystemMapper, UpmsSystem, UpmsSystemExample> implements IUpmsSystemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpmsSystemServiceImpl.class);

    @Autowired
    UpmsSystemMapper upmsSystemMapper;

    @Override
    public UpmsSystem selectUpmsSystemByName(String name) {
        return null;
    }

    @Override
    public UpmsSystem selectByPrimaryKeyWithBLOBs(Integer id) {
        return null;
    }

}