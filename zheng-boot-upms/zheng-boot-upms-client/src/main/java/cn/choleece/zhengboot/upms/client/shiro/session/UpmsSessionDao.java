package cn.choleece.zhengboot.upms.client.shiro.session;

import cn.choleece.upms.common.constant.UpmsConstant;
import cn.choleece.zhengboot.common.util.RedisUtil;
import cn.choleece.zhengboot.upms.client.util.SerializableUtil;
import org.apache.commons.lang.ObjectUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.Set;

/**
 * 基于redis 的sessionDao, 缓存共享session
 * Created by choleece on 2018/7/25.
 */
public class UpmsSessionDao extends CachingSessionDAO {

    private static final Logger logger = LoggerFactory.getLogger(UpmsSessionDao.class);

    private static final String ZHENG_UPMS_SHIRO_SESSION_ID = "zheng-upms-shiro-session-id";

    private static final String ZHENG_UPMS_SERVER_SESSION_ID = "zheng-upms-server-session-id";

    private static final String ZHENG_UPMS_SERVER_SESSION_IDS = "zheng-upms-server-session-ids";

    private static final String ZHENG_UPMS_SERVER_CODE = "zheng-upms-server-code";

    private static final String ZHENG_UPMS_CLIENT_SESSION_ID = "zheng-upms-client-session-id";

    private static final String ZHENG_UPMS_CLIENT_SESSION_IDS = "zheng-upms-client-session-ids";

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        RedisUtil.set(ZHENG_UPMS_SHIRO_SESSION_ID + "_" + sessionId, SerializableUtil.serialize(session), (int)session.getTimeout());
        logger.debug("do create >>>>>>>> sessionId={}", sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        String session = RedisUtil.get(ZHENG_UPMS_SHIRO_SESSION_ID + "_" + serializable);
        logger.debug("do read session >>>>>>> sessionId = {}", serializable);
        return SerializableUtil.deserialize(session);
    }

    @Override
    protected void doUpdate(Session session) {
        // 如果会话过期/停止，则没必要更新了
        if (session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) {
            return;
        }
        // 更新session最后一次访问时间
        UpmsSession upmsSession = (UpmsSession) session;
        UpmsSession cacheUpmsSession = (UpmsSession) doReadSession(session.getId());
        if (null != cacheUpmsSession) {
            upmsSession.setStatus(cacheUpmsSession.getStatus());
            upmsSession.setAttribute("FORCE_LOGOUT", cacheUpmsSession.getAttribute("FORCE_LOGOUT"));
        }
        RedisUtil.set(ZHENG_UPMS_SHIRO_SESSION_ID + "_" + session.getId(), SerializableUtil.serialize(session), (int)session.getTimeout());
        // 更新server session id ,server session code 过期时间
        logger.error("do update >>>>>> sessionId = {}", session.getId());
    }

    @Override
    protected void doDelete(Session session) {
        String sessionId = session.getId().toString();
        String upmsType = ObjectUtils.toString(session.getAttribute(UpmsConstant.UPMS_TYPE));
        if ("client".equals(upmsType)) {
            // 删除局部会话和统一code注册的局部会话
            String code = RedisUtil.get(ZHENG_UPMS_CLIENT_SESSION_ID + "_" + sessionId);
            Jedis jedis = RedisUtil.getJedis();
            jedis.del(ZHENG_UPMS_CLIENT_SESSION_ID + "_" + sessionId);
            jedis.srem(ZHENG_UPMS_CLIENT_SESSION_IDS + "_" + code, sessionId);
            jedis.close();
        }
        if ("server".equals(upmsType)) {
            // 当前全局会话code
            String code = RedisUtil.get(ZHENG_UPMS_SERVER_SESSION_ID + "_" + sessionId);
            // 清除全局会话
            RedisUtil.remove(ZHENG_UPMS_SERVER_SESSION_ID + "_" + sessionId);
            // 清除code校验值
            RedisUtil.remove(ZHENG_UPMS_SERVER_CODE + "_" + code);
            // 清除所有局部会话
            Jedis jedis = RedisUtil.getJedis();
            Set<String> clientSessionIds = jedis.smembers(ZHENG_UPMS_CLIENT_SESSION_IDS + "_" + code);
            for (String clientSessionId : clientSessionIds) {
                jedis.del(ZHENG_UPMS_CLIENT_SESSION_ID + "_" + clientSessionId);
                jedis.srem(ZHENG_UPMS_CLIENT_SESSION_IDS + "_" + code, clientSessionId);
            }
            logger.error("当前code = {}， 对应的注册系统个数，{}个", code, jedis.scard(ZHENG_UPMS_CLIENT_SESSION_IDS + "_" + code));
            jedis.close();
            // 维护会话ID列表，提供会话分页管理
            RedisUtil.lrem(ZHENG_UPMS_SERVER_SESSION_IDS, 1, sessionId);
        }
        // 删除session
        RedisUtil.remove(ZHENG_UPMS_SHIRO_SESSION_ID + "_" + sessionId);
        logger.debug("do delete >>>> sessionId = {}", sessionId);
    }
}
