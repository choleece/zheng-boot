package cn.choleece.zhengboot.upms.rpc.api.service;

import cn.choleece.zhengboot.upms.dao.model.UpmsPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 降级实现IUpmsApiService接口
 * Created by choleece on 2018/7/25.
 */
public class UpmsApiServiceMock implements IUpmsApiService {

    private static final Logger logger = LoggerFactory.getLogger(UpmsApiServiceMock.class);

    @Override
    public List<UpmsPermission> selectUpmsPermissionByUpmsUserId(Integer upmsUserId) {
        logger.info("upms api service impl >>>> selectUpmsPermissionByUpmsUserId");
        return null;
    }

    @Override
    public List<UpmsPermission> selectUpmsPermissionByUpmsUserIdByCache(Integer upmsUserId) {
        logger.info("upms api service impl >>>> selectUpmsPermissionByUpmsUserId");
        return null;
    }
}
