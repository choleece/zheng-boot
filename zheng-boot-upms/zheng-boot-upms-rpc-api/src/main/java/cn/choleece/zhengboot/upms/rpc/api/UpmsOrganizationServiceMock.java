package cn.choleece.zhengboot.upms.rpc.api;

import cn.choleece.zhengboot.common.base.BaseServiceMock;
import cn.choleece.zhengboot.upms.dao.mapper.UpmsOrganizationMapper;
import cn.choleece.zhengboot.upms.dao.model.UpmsOrganization;
import cn.choleece.zhengboot.upms.dao.model.UpmsOrganizationExample;

/**
* 降级实现UpmsOrganizationService接口
* Created by shuzheng on 2018/7/30.
*/
public class UpmsOrganizationServiceMock extends BaseServiceMock<UpmsOrganizationMapper, UpmsOrganization, UpmsOrganizationExample> implements IUpmsOrganizationService {

}
