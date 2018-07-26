package cn.choleece.zhengboot.common.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源（数据源切换）
 * Created by choleece on 2018/7/27.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSource = getDataSource();
        logger.info("当前数据源: {}", dataSource);
        return dataSource;
    }

    /**
     * 设置数据源
     * @param dataSource
     */
    public static void setDataSource(String dataSource) {
        CONTEXT_HOLDER.set(dataSource);
    }

    /**
     * 获取数据源
     * @return
     */
    public static String getDataSource() {
        String dataSource = CONTEXT_HOLDER.get();
        // 如果没有指定数据源则使用默认数据源
        if (dataSource == null) {
            DynamicDataSource.setDataSource(DataSourceEnum.MASTER.getDefault());
        }
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清除数据源
     */
    public static void clearDataSource() {
        CONTEXT_HOLDER.remove();
    }
}
