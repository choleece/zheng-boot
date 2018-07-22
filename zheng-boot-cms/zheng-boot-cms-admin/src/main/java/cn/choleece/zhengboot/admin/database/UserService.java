package cn.choleece.zhengboot.admin.database;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author choleece
 * @description: cn.choleece.zhengboot.admin.database
 * @date 2018/7/22 15:16
 */
@Component
public class UserService {
    public UserBean getUser(String username) {
        if (!DataSource.getData().containsKey(username)) {
            return null;
        }

        UserBean user = new UserBean();
        Map<String, String> detail = DataSource.getData().get(username);
        user.setUsername(username);
        user.setPassword(detail.get("password"));
        user.setRole(detail.get("role"));
        user.setPermission(detail.get("permission"));

        return user;
    }
}
