package cn.choleece.zhengboot.cms.common.constant;

/**
 * @author choleece
 * @description: cms系统接口常量枚举类
 * @date 2018/7/20 11:02
 */
public enum CmsResultConstant {

    /**
     * 失败
     */
    FAILED(0, "failed"),

    /**
     * 成功
     */
    SUCCESS(1, "success"),

    /**
     * 用户不存在
     */
    NO_USER(-1, "用户不存在"),

    /**
     * 文件类型不支持
     */
    FILE_TYPE_ERROR(20001, "File type not supported"),

    /**
     * 无效长度
     */
    INVALID_LENGTH(20002, "Invalid length");

    private int code;

    private String message;

    CmsResultConstant(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
