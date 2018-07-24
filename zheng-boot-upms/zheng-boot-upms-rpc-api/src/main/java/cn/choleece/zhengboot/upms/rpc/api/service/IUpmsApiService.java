package cn.choleece.zhengboot.upms.rpc.api.service;

import cn.choleece.zhengboot.upms.dao.model.UpmsPermission;

import java.util.List;

/**
 * UPMS 系统接口
 * Created by choleece on 2018/7/25.
 */
public interface IUpmsApiService {

    /**
     * 根据用户ID获取用户所拥有的权限（用户和角色权限合集）
     * @param upmsUserId
     * @return
     */
    List<UpmsPermission> selectUpmsPermissionByUpmsUserId(Integer upmsUserId);

    /**
     * 根据用户ID从缓存里获取用户所拥有的权限（用户和角色权限合集）
     * @param upmsUserId
     * @return
     */
    List<UpmsPermission> selectUpmsPermissionByUpmsUserIdByCache(Integer upmsUserId);
}
