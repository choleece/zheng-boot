package cn.choleece.zhengboot.common.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Redis工具类
 * Created by choleece on 2018/7/23.
 */
public class RedisUtil {

    protected static ReentrantLock lockPool = new ReentrantLock();
    protected static ReentrantLock lookJedis = new ReentrantLock();

    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    /**
     * Redis 服务器IP
     */
    private static String IP = PropertiesFileUtil.getInstance("redis").get("master.redis.ip");

    /**
     * Redis 服务器端口
     */
    private static int PORT = PropertiesFileUtil.getInstance("redis").getInt("master.redis.port");

    /**
     * 访问密码
     */
    private static String PASSWORD = AESUtil.aesDecode(PropertiesFileUtil.getInstance("redis").get("master.redis.password"));

    /**
     * 可用连接实例的最大数据，默认值为8，如果赋值为-1，则表示不限制；如果poll已经分配了maxActive个Jedis实例，则此时pool的状态为exhausted(耗尽)
     */
    private static int MAX_ACTIVE = PropertiesFileUtil.getInstance("redis").getInt("master.redis.max_active");

    /**
     * 控制一个pool最多又多少个状态为idle（空闲）的jdies实例，默认也是8
     */
    private static int MAX_IDLE = PropertiesFileUtil.getInstance("redis").getInt("master.redis.max_idle");

    /**
     * 等待可用连接的最大时间，单位毫秒，默认值为-1， 表示用不超时。如果超过等待时间，则直接抛出JedisConnectionException;
     */
    private static int MAX_WAIT = PropertiesFileUtil.getInstance("redis").getInt("master.redis.max_wait");

    /**
     * 超时时间
     */
    private static int TIMEOUT = PropertiesFileUtil.getInstance("redis").getInt("master.redis.timeout");

    /**
     * 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
     */
    private static boolean TEST_ON_BORROW = false;

    private static JedisPool jedisPool = null;

    /**
     * redis 过期时间，以秒为单位
     */
    public final static int EXPR_HOUR = 60 * 60;

    public final static int EXPR_DAY = EXPR_HOUR * 24;

    public final static int EXPR_MONTH = EXPR_DAY * 30;

    /**
     * 初始化Redis连接池
     */
    private static void initialPool() {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, IP, PORT, TIMEOUT, PASSWORD);
        } catch (Exception e) {
            logger.error("First create JedisPool error: " + e);
        }
    }

    /**
     * 在多线程环境下同步初始化
     */
    private static synchronized void poolInit() {
        if (null == jedisPool) {
            initialPool();
        }
    }

    public synchronized static Jedis getJedis() {
        poolInit();
        Jedis jedis = null;
        try {
            if (null != jedisPool) {
                jedis = jedisPool.getResource();
                try {
                    jedis.auth(PASSWORD);
                } catch (Exception e) {
                    logger.error("auth error");
                }
            }
        } catch (Exception e) {
            logger.error("Get jedis error: " + e);
        }
        return jedis;
    }

    /**
     * 设置String
     * @param key
     * @param value
     */
    public synchronized static void set(String key, String value) {
        try {
            value = StringUtils.isBlank(value) ? "" : value;
            Jedis jedis = getJedis();
            jedis.set(key, value);
            jedis.close();
        } catch (Exception e) {
            logger.error("Set key error: " + e);
        }
    }

    /**
     * 设置byte[]
     * @param key
     * @param value
     */
    public synchronized static void set(byte[] key, byte[] value) {
        try {
            Jedis jedis = getJedis();
            jedis.set(key, value);
            jedis.close();
        } catch (Exception e) {
            logger.error("set key error: " + e);
        }
    }

    /**
     * 设置 String过期时间
     * @param key
     * @param value
     * @param seconds 以秒为单位
     */
    public synchronized static void set(String key, String value, int seconds) {
        try {
            value = StringUtils.isBlank(value) ? "" : value;
            Jedis jedis = getJedis();
            jedis.setex(key, seconds, value);
            jedis.close();
        } catch (Exception e) {
            logger.error("set key error: " + e);
        }
    }

    /**
     * 设置byte[] 过期时间
     * @param key
     * @param value
     * @param seconds
     */
    public synchronized static void set(byte[] key, byte[] value, int seconds) {
        try {
            Jedis jedis = getJedis();
            jedis.setex(key, seconds, value);
            jedis.close();
        } catch (Exception e) {
            logger.error("set key error: " + e);
        }
    }

    /**
     * 获取String 值
     * @param key
     * @return
     */
    public synchronized static String get(String key) {
        Jedis jedis = getJedis();
        if (null == jedis) {
            return null;
        }
        String value = jedis.get(key);
        jedis.close();
        return value;
    }

    /**
     * 获取byte[]值
     * @param key
     * @return
     */
    public synchronized static byte[] get(byte[] key) {
        Jedis jedis = getJedis();
        if (null == jedis) {
            return null;
        }
        byte[] value = jedis.get(key);
        jedis.close();
        return value;
    }

    /**
     * 根据String key删除值
     * @param key
     */
    public synchronized static void remove(String key) {
        try {
            Jedis jedis = getJedis();
            jedis.del(key);
            jedis.close();
        } catch (Exception e) {
            logger.error("remove keyex error: " + e);
        }
    }

    /**
     * 根据byte[] key删除值
     * @param keys
     */
    public synchronized static void remove(byte[] keys) {
        try {
            Jedis jedis = getJedis();
            jedis.del(keys);
            jedis.close();
        } catch (Exception e) {
            logger.error("remove key error: " + e);
        }
    }

    /**
     * lpush
     * @param key
     * @param strings
     */
    public synchronized static void lpush(String key, String... strings) {
        try {
            Jedis jedis = getJedis();
            jedis.lpush(key, strings);
            jedis.close();
        } catch (Exception e) {
            logger.error("lpush error : " + e);
        }
    }

    /**
     * lrem
     * @param key
     * @param count
     * @param value
     */
    public synchronized static void lrem(String key, long count, String value) {
        try {
            Jedis jedis = getJedis();
            jedis.lrem(key, count, value);
            jedis.close();
        } catch (Exception e) {
            logger.error("lrem error: " + e);
        }
    }

    /**
     * sadd
     * @param key
     * @param value
     * @param seconds
     */
    public synchronized static void sadd(String key, String value, int seconds) {
        try {
            Jedis jedis = getJedis();
            jedis.sadd(key, value);
            jedis.expire(key, seconds);
            jedis.close();
        } catch (Exception e) {
            logger.error("sadd error: " + e);
        }
    }

    /**
     * incr
     * @param key
     * @return
     */
    public synchronized static Long incr(String key) {
        Jedis jedis = getJedis();
        if (null == jedis) {
            return null;
        }
        long value = jedis.incr(key);
        jedis.close();
        return value;
    }

    /**
     * decr
     * @param key
     * @return
     */
    public synchronized static Long decr(String key) {
        Jedis jedis = getJedis();
        if (null == jedis) {
            return null;
        }
        long value = jedis.decr(key);
        jedis.close();
        return value;
    }

}
