package cn.choleece.zhengboot.upms.rpc.service.impl;

import cn.choleece.zhengboot.common.annotation.BaseService;
import cn.choleece.zhengboot.common.base.BaseServiceImpl;
import cn.choleece.zhengboot.upms.dao.mapper.UpmsRolePermissionMapper;
import cn.choleece.zhengboot.upms.dao.model.UpmsRolePermission;
import cn.choleece.zhengboot.upms.dao.model.UpmsRolePermissionExample;
import cn.choleece.zhengboot.upms.rpc.api.UpmsRolePermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UpmsRolePermissionService实现
* Created by shuzheng on 2018/7/30.
*/
@Service
@Transactional
@BaseService
public class UpmsRolePermissionServiceImpl extends BaseServiceImpl<UpmsRolePermissionMapper, UpmsRolePermission, UpmsRolePermissionExample> implements IUpmsRolePermissionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpmsRolePermissionServiceImpl.class);

    @Autowired
    UpmsRolePermissionMapper upmsRolePermissionMapper;

}