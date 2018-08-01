package cn.choleece.zhengboot.upms.rpc.service.impl;

import cn.choleece.zhengboot.common.annotation.BaseService;
import cn.choleece.zhengboot.common.base.BaseServiceImpl;
import cn.choleece.zhengboot.upms.dao.mapper.UpmsUserOrganizationMapper;
import cn.choleece.zhengboot.upms.dao.model.UpmsUserOrganization;
import cn.choleece.zhengboot.upms.dao.model.UpmsUserOrganizationExample;
import cn.choleece.zhengboot.upms.rpc.api.IUpmsUserOrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* UpmsUserOrganizationService实现
* Created by shuzheng on 2018/7/30.
*/
@Service
@Transactional
@BaseService
public class UpmsUserOrganizationServiceImpl extends BaseServiceImpl<UpmsUserOrganizationMapper, UpmsUserOrganization, UpmsUserOrganizationExample> implements IUpmsUserOrganizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpmsUserOrganizationServiceImpl.class);

    @Autowired
    UpmsUserOrganizationMapper upmsUserOrganizationMapper;

    @Override
    public UpmsUserOrganization selectByPrimaryKeyWithBLOBs(Integer id) {
        return null;
    }
}