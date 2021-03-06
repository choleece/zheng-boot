package cn.choleece.zhengboot.upms.rpc.service.impl;

import cn.choleece.zhengboot.common.annotation.BaseService;
import cn.choleece.zhengboot.common.base.BaseServiceImpl;
import cn.choleece.zhengboot.upms.dao.mapper.UpmsLogMapper;
import cn.choleece.zhengboot.upms.dao.model.UpmsLog;
import cn.choleece.zhengboot.upms.dao.model.UpmsLogExample;
import cn.choleece.zhengboot.upms.rpc.api.IUpmsLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UpmsLogService实现
* Created by shuzheng on 2018/7/30.
*/
@Service
@Transactional
@BaseService
public class UpmsLogServiceImpl extends BaseServiceImpl<UpmsLogMapper, UpmsLog, UpmsLogExample> implements IUpmsLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpmsLogServiceImpl.class);

    @Autowired
    UpmsLogMapper upmsLogMapper;

    @Override
    public UpmsLog selectByPrimaryKeyWithBLOBs(Integer id) {
        return null;
    }
}