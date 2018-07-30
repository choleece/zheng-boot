package cn.choleece.zhengboot.upms.rpc.api;

import cn.choleece.zhengboot.common.base.BaseServiceMock;
import cn.choleece.zhengboot.upms.dao.mapper.UpmsPermissionMapper;
import cn.choleece.zhengboot.upms.dao.model.UpmsPermission;
import cn.choleece.zhengboot.upms.dao.model.UpmsPermissionExample;

/**
* 降级实现UpmsPermissionService接口
* Created by shuzheng on 2018/7/30.
*/
public class UpmsPermissionServiceMock extends BaseServiceMock<UpmsPermissionMapper, UpmsPermission, UpmsPermissionExample> implements IUpmsPermissionService {

}
