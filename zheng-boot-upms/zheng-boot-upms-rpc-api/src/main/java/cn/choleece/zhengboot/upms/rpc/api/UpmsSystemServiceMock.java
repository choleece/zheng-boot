package cn.choleece.zhengboot.upms.rpc.api;

import cn.choleece.zhengboot.common.base.BaseServiceMock;
import cn.choleece.zhengboot.upms.dao.mapper.UpmsSystemMapper;
import cn.choleece.zhengboot.upms.dao.model.UpmsSystem;
import cn.choleece.zhengboot.upms.dao.model.UpmsSystemExample;

/**
* 降级实现UpmsSystemService接口
* Created by shuzheng on 2018/7/30.
*/
public class UpmsSystemServiceMock extends BaseServiceMock<UpmsSystemMapper, UpmsSystem, UpmsSystemExample> implements IUpmsSystemService {
    @Override
    public UpmsSystem selectUpmsSystemByName(String name) {
        return null;
    }
}
