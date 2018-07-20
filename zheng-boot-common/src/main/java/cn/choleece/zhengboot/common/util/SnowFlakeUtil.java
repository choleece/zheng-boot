package cn.choleece.zhengboot.common.util;

import com.relops.snowflake.Snowflake;

/**
 * @author choleece
 * @description: 生成雪花ID
 * @date 2018/7/20 16:57
 */
public class SnowFlakeUtil {

    public static long genId() {
        return SingletonSnowFlake.getInstance().next();
    }

    public static class SingletonSnowFlake {
        private static Snowflake snowflake;

        public static Snowflake getInstance() {
            synchronized (snowflake) {
                if (snowflake == null) {
                    return new Snowflake(1);
                }
                return snowflake;
            }
        }
    }
}
