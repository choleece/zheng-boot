package cn.choleece.zhengboot.cms.common.constant;

import cn.choleece.zhengboot.common.base.BaseResult;

/**
 * @author choleece
 * @description: cms系统常量枚举类
 * @date 2018/7/20 11:02
 */
public class CmsResult extends BaseResult {
    public CmsResult(CmsResultConstant cmsResultConstant, Object data) {
        super(cmsResultConstant.getCode(), cmsResultConstant.getMessage(), data);
    }
}
