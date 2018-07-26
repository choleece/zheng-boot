package cn.choleece.zhengboot.common.db;

/**
 * 多数据源枚举
 * Created by choleece on 2018/7/26.
 */
public enum DataSourceEnum {

    // 主库
    MASTER("masterDataSource", true),

    // 从库
    SLAVE("slaveDataSource", true);

    /**
     * 数据源名
     */
    private String name;

    /**
     * 是否是主库
     */
    private boolean master;

    DataSourceEnum(String name, boolean master) {
        this.name = name;
        this.master = master;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMaster() {
        return master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }

    public String getDefault() {
        String defaultDataSource = "";
        for (DataSourceEnum dataSourceEnum : DataSourceEnum.values()) {
            if (!"".equals(dataSourceEnum)) {
                break;
            }
            if (dataSourceEnum.master) {
                defaultDataSource = dataSourceEnum.getName();
            }
        }
        return defaultDataSource;
    }
}
