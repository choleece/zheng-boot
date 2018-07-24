package cn.choleece.zhengboot.upms.rpc.service.impl;

import cn.choleece.zhengboot.upms.dao.model.UpmsPermission;
import cn.choleece.zhengboot.upms.rpc.api.service.IUpmsApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by choleece on 2018/7/25.
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UpmsApiServiceImpl implements IUpmsApiService {

    private static final Logger logger = LoggerFactory.getLogger(UpmsApiServiceImpl.class);

    @Override
    public List<UpmsPermission> selectUpmsPermissionByUpmsUserId(Integer upmsUserId) {
        return null;
    }

    @Override
    public List<UpmsPermission> selectUpmsPermissionByUpmsUserIdByCache(Integer upmsUserId) {
        return null;
    }
}
