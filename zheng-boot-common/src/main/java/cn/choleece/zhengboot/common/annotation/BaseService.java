package cn.choleece.zhengboot.common.annotation;

import java.lang.annotation.*;

/**
 * @author choleece
 * @description: 初始化继承BaseService的Service
 * @date 2018/7/20 9:22
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BaseService {
}
