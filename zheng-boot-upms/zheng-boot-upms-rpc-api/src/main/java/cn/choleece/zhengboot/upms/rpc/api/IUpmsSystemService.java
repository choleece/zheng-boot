package cn.choleece.zhengboot.upms.rpc.api;

import cn.choleece.zhengboot.common.base.BaseService;
import cn.choleece.zhengboot.upms.dao.model.UpmsSystem;
import cn.choleece.zhengboot.upms.dao.model.UpmsSystemExample;

/**
* UpmsSystemService接口
* Created by shuzheng on 2018/7/30.
*/
public interface IUpmsSystemService extends BaseService<UpmsSystem, UpmsSystemExample> {
    /**
     * 根据name获取UpmsSystem
     * @param name
     * @return
     */
    UpmsSystem selectUpmsSystemByName(String name);
}