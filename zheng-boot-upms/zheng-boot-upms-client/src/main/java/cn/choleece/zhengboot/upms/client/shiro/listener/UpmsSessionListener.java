package cn.choleece.zhengboot.upms.client.shiro.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author choleece
 * @description: session监听器
 * @date 2018/7/23 13:25
 */
public class UpmsSessionListener implements SessionListener {

    private static final Logger logger = LoggerFactory.getLogger(UpmsSessionListener.class);

    @Override
    public void onStart(Session session) {
        logger.debug("会话创建" + session.getId());
    }

    @Override
    public void onStop(Session session) {
        logger.debug("会话停止" + session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        logger.debug("会话过期" + session.getId());
    }
}
