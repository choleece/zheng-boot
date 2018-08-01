package cn.choleece.zhengboot.upms.rpc.service.impl;

import cn.choleece.zhengboot.common.annotation.BaseService;
import cn.choleece.zhengboot.common.base.BaseServiceImpl;
import cn.choleece.zhengboot.upms.dao.mapper.UpmsPermissionMapper;
import cn.choleece.zhengboot.upms.dao.model.UpmsPermission;
import cn.choleece.zhengboot.upms.dao.model.UpmsPermissionExample;
import cn.choleece.zhengboot.upms.rpc.api.IUpmsPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UpmsPermissionService实现
* Created by shuzheng on 2018/7/30.
*/
@Service
@Transactional
@BaseService
public class UpmsPermissionServiceImpl extends BaseServiceImpl<UpmsPermissionMapper, UpmsPermission, UpmsPermissionExample> implements IUpmsPermissionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpmsPermissionServiceImpl.class);

    @Autowired
    UpmsPermissionMapper upmsPermissionMapper;

    @Override
    public UpmsPermission selectByPrimaryKeyWithBLOBs(Integer id) {
        return null;
    }
}