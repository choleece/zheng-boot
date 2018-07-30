package cn.choleece.zhengboot.upms.rpc.api;

import cn.choleece.zhengboot.common.base.BaseServiceMock;
import cn.choleece.zhengboot.upms.dao.mapper.UpmsRoleMapper;
import cn.choleece.zhengboot.upms.dao.model.UpmsRole;
import cn.choleece.zhengboot.upms.dao.model.UpmsRoleExample;

/**
* 降级实现UpmsRoleService接口
* Created by shuzheng on 2018/7/30.
*/
public class UpmsRoleServiceMock extends BaseServiceMock<UpmsRoleMapper, UpmsRole, UpmsRoleExample> implements IUpmsRoleService {

}
