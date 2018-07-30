package cn.choleece.zhengboot.upms.rpc.api;

import cn.choleece.zhengboot.common.base.BaseServiceMock;
import cn.choleece.zhengboot.upms.dao.mapper.UpmsUserRoleMapper;
import cn.choleece.zhengboot.upms.dao.model.UpmsUserRole;
import cn.choleece.zhengboot.upms.dao.model.UpmsUserRoleExample;

/**
* 降级实现UpmsUserRoleService接口
* Created by shuzheng on 2018/7/30.
*/
public class UpmsUserRoleServiceMock extends BaseServiceMock<UpmsUserRoleMapper, UpmsUserRole, UpmsUserRoleExample> implements IUpmsUserRoleService {

}
