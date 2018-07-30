package cn.choleece.zhengboot.upms.rpc.api;

import cn.choleece.zhengboot.common.base.BaseServiceMock;
import cn.choleece.zhengboot.upms.dao.mapper.UpmsLogMapper;
import cn.choleece.zhengboot.upms.dao.model.UpmsLog;
import cn.choleece.zhengboot.upms.dao.model.UpmsLogExample;

/**
* 降级实现UpmsLogService接口
* Created by shuzheng on 2018/7/30.
*/
public class UpmsLogServiceMock extends BaseServiceMock<UpmsLogMapper, UpmsLog, UpmsLogExample> implements IUpmsLogService {

}
