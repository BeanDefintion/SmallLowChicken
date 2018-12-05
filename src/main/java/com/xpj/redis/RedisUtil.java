package com.xpj.redis;

import com.mysql.cj.util.StringUtils;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RedisUtil implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    private static volatile JedisPool jedisPool = null;

    /**
     * session 在redis过期时间是30分钟30*60
     */
    private static int expireTime = 1800;

    /**
     * Ip
     */
    private static String redisIp = ConfigFactory.load().getString("redis.ip");

    /**
     * Port
     */
    private static int redisPort = ConfigFactory.load().getInt("redis.port");

    /**
     * passWord
     */
    private static String passWord = ConfigFactory.load().getString("redis.passWord");

    /**
     * jedis的最大活跃连接数
     */
    private static int maxActive = 200;

    /**
     * jedis最大空闲连接数
     */
    private static int maxIdle = 200;

    /**
     * jedis池没有连接对象返回时，等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。
     * 如果超过等待时间，则直接抛出JedisConnectionException
     */
    private static long maxWait = 5000;

    private static Object obj = new Object();

    static {
        initPool();
    }

    /**
     * 初始化JedisPool(连接池的性能更优秀),所以连接池用1个，而连接不能只用1个，单例模式也不能乱用
     *
     * @return
     */

    private static JedisPool initPool() {
        if (jedisPool == null) {
            synchronized (obj) {
                if (jedisPool == null) {
                    JedisPoolConfig config = new JedisPoolConfig();
                    config.setMaxTotal(maxActive);
                    config.setMaxIdle(maxIdle);
                    config.setMaxWaitMillis(maxWait);
                    config.setTestOnBorrow(false);
                    jedisPool = new JedisPool(config, redisIp, redisPort, 10000, passWord);
                }
            }
        }

        return jedisPool;
    }

    /**
     * 获得Jedis连接(这里如果用双重隔离锁的话?)
     *
     * @return
     */

    private static Jedis getJedis() {
        Jedis jedis = null;
        if (jedis == null) {
            synchronized (obj) {
                if (jedis == null) {
                    jedis = jedisPool.getResource();
                }
            }
        }

        return jedis;
    }

    /**
     * 回收Jedis连接
     *
     * @param jedis
     */

    private static void recycleJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 存储String-String的数据
     *
     * @param key
     * @param value
     */

    public static void setString(String key, String value) {
        Jedis jedis = getJedis();
        if (jedis != null) {
            try {
                jedis.set(key, value);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                recycleJedis(jedis);
            }
        }
    }

    /**
     * 读取String-String的数据
     *
     * @param key
     */

    public static String getString(String key) {
        Jedis jedis = getJedis();
        String value = null;
        if (jedis != null) {
            try {
                value = jedis.get(key);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                recycleJedis(jedis);
            }
        }
        return value;
    }

    /**
     * 创建set集合
     *
     * @param key
     * @param members
     */

    public static void sadd(String key, String... members) {
        Jedis jedis = getJedis();
        if (jedis != null) {
            try {
                jedis.sadd(key, members);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                recycleJedis(jedis);
            }
        }
    }

    /**
     * 判断redis中是否存在某个key
     *
     * @param key
     * @return
     */

    public static boolean exists(String key) {
        Jedis jedis = getJedis();
        return jedis.exists(key);
    }

    /**
     * Set集合获取所有keys
     *
     * @param pattern
     * @return
     */

    public static Set<String> getAllKeys(String pattern) {
        Jedis jedis = getJedis();
        if (jedis != null) {
            try {
                return jedis.keys(pattern);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                recycleJedis(jedis);
            }
        }

        return null;
    }

    /**
     * 保存byte类型数据
     * 有默认过期时间的
     *
     * @param key
     * @param value
     */

    public static void setObject(byte[] key, byte[] value) {
        Jedis jedis = getJedis();
        if (jedis != null) {
            try {
                jedis.set(key, value);
                jedis.expire(key, expireTime);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                recycleJedis(jedis);
            }
        }
    }

    /**
     * 获取byte类型数据
     *
     * @param key
     * @return
     */

    public static byte[] getObject(byte[] key) {
        Jedis jedis = getJedis();
        byte[] bytes = null;
        if (jedis != null) {
            try {
                bytes = jedis.get(key);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                recycleJedis(jedis);
            }
        }

        return bytes;
    }

    /**
     * 存储obj实例
     *
     * @param key
     * @param obj
     */

    public static void setObject(String key, Object obj) {
        Jedis jedis = getJedis();
        if (jedis != null) {
            try {
                byte[] value = serialize(obj);
                jedis.set(key.getBytes(), value);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                recycleJedis(jedis);
            }
        }
    }

    /**
     * 获取byte类型数据
     *
     * @param key
     * @return
     */

    public static Object getObject(String key) {
        Jedis jedis = getJedis();
        Object obj = null;
        if (jedis != null) {
            try {
                byte[] bytes = jedis.get(key.getBytes());
                obj = unserialize(bytes);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                recycleJedis(jedis);
            }
        }

        return obj;
    }

    /**
     * 存储String类型的哈希
     * Redis hash 是一个string类型的field和value的映射表，hash特别适合用于存储对象。
     *
     * @param key
     * @param filed
     * @param value
     */
    public static void hset(String key, String filed, String value) {
        Jedis jedis = getJedis();
        try {
            jedis.hset(key, filed, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            recycleJedis(jedis);
        }
    }

    /**
     * 删除某个key的值
     *
     * @param key
     */
    public static void del(String key) {
        Jedis jedis = getJedis();
        try {
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            recycleJedis(jedis);
        }
    }

    /**
     * 封装一个通用的redis取值的接口用于处理数据库与缓存的同步
     * 这里里面处理的 set zset list hash 都是String类型的 望君注意
     *
     * @param key
     * @param specialType 特殊的不属于redis的类型 例如说obj
     * @return 封装的RedisTable对象
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object getValueOfObject(String key, String specialType) {
        Jedis jedis = getJedis();
//        RedisTable redisTable = new RedisTable();

        /**
         * type用于返回key对应存储值的类型
         */
        String redisType = jedis.type(key);
        System.err.println("key对应的类型是： " + redisType);
//        redisTable.setRedisType(redisType);

        /**
         * 处理特殊类型-object
         *
         */
        if ("obj".equalsIgnoreCase(specialType)) {
            Object object = getObject(key);
//            redisTable.setValue_obj(object);
//            printRedis(redisTable);
            return object;
        }

        //set集合
        if ("set".equalsIgnoreCase(redisType)) {
            Set<String> setStrings = jedis.smembers(key);
            return setStrings;
//            redisTable.setValue_set(setStrings);
//            printRedis(redisTable);
            //hash集合
        } else if ("hash".equalsIgnoreCase(redisType)) {
            Set<String> hashSets = jedis.hkeys(key);
//            redisTable.setValue_set(hashSets);
            ArrayList arrayList = new ArrayList();
            if (null != hashSets && !hashSets.isEmpty()) {
                Iterator setIterator = hashSets.iterator();
                while (setIterator.hasNext()) {
                    String objectName = setIterator.next() + "";
                    String value = jedis.hget(key, objectName);
                    arrayList.add(value);
                }
            }
            return arrayList;
//            redisTable.setValue_hash(arrayList);
//            printRedis(redisTable);
            //list集合
        } else if ("list".equalsIgnoreCase(redisType)) {
            List<String> listLists = jedis.lrange(key, 0, -1);
            return listLists;
//            redisTable.setValue_list(listLists);
//            printRedis(redisTable);
            //sortedset集合
        } else if ("sortedset".equalsIgnoreCase(redisType)) {
            Set<String> sortedsets = jedis.zrange(key, 0, -1);
            return sortedsets;
//            redisTable.setValue_set(sortedsets);
//            if (null != sortedsets && !sortedsets.isEmpty()) {
//                Iterator setIterator = sortedsets.iterator();
//                while (setIterator.hasNext()) {
//                    String sortedMember = setIterator.next() + "";
//                    redisTable.setRedisValue(sortedMember);
//                    redisTable.setScore("" + jedis.zscore(key, sortedMember));
//                    printRedis(redisTable);
//                }
//            }
            //string集合
        } else if ("string".equalsIgnoreCase(redisType)) {
            String value = jedis.get(key);
            return value;
//            redisTable.setValue_string(value);
//            printRedis(redisTable);
        } else {
            System.err.println("缓存读取错误");
            logger.error("UnknowRedisType-----redisType: " + redisType + "objValue: " + jedis.get(key));
        }
        return null;
    }

    /**
     * 根据key和value的值来进行不同的存储
     *
     * @param key
     * @param value
     */
    public static void saveValueOfObject(Object key, Object value) {
        if (key instanceof String) {
            if (value instanceof String) {
                setString((String) key, (String) value);
            } else {
                setObject((String) key, value);
            }
        } else if (key instanceof byte[]) {
            if (value instanceof byte[]) {
                setObject((byte[]) key, (byte[]) value);
            }
        }

    }

    public static void printRedis(RedisTable redisTable) {
        System.out.println("redisType的值是: " + redisTable.getRedisType()
                + " ValueString的值是: " + redisTable.getValue_string()
                + " ValueList的值是: " + redisTable.getValue_list()
                + " ValueSet的值是: " + redisTable.getValue_set()
                + " ValueHash的值是: " + redisTable.getValue_hash()
                + " ValueObject的值是: " + redisTable.getValue_obj()
        );
    }

    /**
     * 获取byte[]类型Key
     *
     * @param object
     * @return
     */

    public static byte[] getBytesKey(Object object) {
        if (object instanceof String) {
            return StringUtils.getBytes((String) object);
        } else {
            return serialize(object);
        }
    }

    /**
     * Object转换byte[]类型
     *
     * @param object
     * @return
     */

    public static byte[] toBytes(Object object) {
        return serialize(object);
    }

    /**
     * byte[]型转换Object
     *
     * @param bytes
     * @return
     */

    public static Object toObject(byte[] bytes) {

        return unserialize(bytes);

    }

    /**
     * object序列化成byte[]
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            if (object != null) {
                if (object instanceof String) {
                    return ((String) object).getBytes();
                }
                baos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                return baos.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * byte[]反序列化成Object
     *
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            if (bytes != null && bytes.length > 0) {
                bais = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bais);
                return ois.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        sadd("xpj", "SHuaiB", "SBBB", "ssssss");
        setString("1", "2");
        setString("1", "田帅是个智障");
        hset("key", "11", "田帅是狗");
        hset("key", "22", "田帅是🐖");
        RedisUtil redisUtil = new RedisUtil();
        setObject("2", serialize(redisUtil));
//        RedisUtil.getValueOfObject("1");
//        RedisUtil.getValueOfObject("xpj");
//        RedisUtil.getValueOfObject("key");
//        getValueOfObject("2", "obj");
    }

}
