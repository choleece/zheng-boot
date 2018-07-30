package cn.choleece.zhengboot.upms.rpc.api;

import cn.choleece.zhengboot.common.base.BaseServiceMock;
import cn.choleece.zhengboot.upms.dao.mapper.UpmsUserMapper;
import cn.choleece.zhengboot.upms.dao.model.UpmsUser;
import cn.choleece.zhengboot.upms.dao.model.UpmsUserExample;

/**
* 降级实现UpmsUserService接口
* Created by shuzheng on 2018/7/30.
*/
public class UpmsUserServiceMock extends BaseServiceMock<UpmsUserMapper, UpmsUser, UpmsUserExample> implements IUpmsUserService {

}
