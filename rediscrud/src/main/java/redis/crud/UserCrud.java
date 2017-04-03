package redis.crud;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Map;

public class UserCrud {
    private static final String redisHost = "localhost";
    private static final Integer redisPort = 6379;
    private static JedisPool pool = null;

    public UserCrud() {
        pool = new JedisPool(redisHost, redisPort);
    }

    public void createUserHash(String userId, Map<String, String> map) {
        Jedis jedis = pool.getResource();

        try {

            jedis.hmset(userId, map);

        } catch (JedisException e) {
            if (jedis != null) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (jedis != null) {
                pool.returnResource(jedis);
            }
        }

    }

    public Map<String, String> getAllUserFields(String userId) {
        Jedis jedis = pool.getResource();
        Map<String, String> user = null;

        try {
            user = jedis.hgetAll(userId);

        } catch (JedisException e) {
            if (jedis != null) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (jedis != null) {
                pool.returnResource(jedis);
            }
        }
        return user;
    }



    public String getUserField(String userId, String field) {
        Jedis jedis = pool.getResource();
        String result=null;
        try {

            result=jedis.hget(userId, field);

        } catch (JedisException e) {
            if (jedis != null) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (jedis != null) {
                pool.returnResource(jedis);
            }
        }
        return result;

    }

    public boolean removeUser(String userId) {
        Jedis jedis = pool.getResource();

        try {
            if (jedis.del(userId) == 1)
                return true;

        } catch (JedisException e) {
            if (jedis != null) {
                pool.returnBrokenResource(jedis);
            }
        } finally {
            if (jedis != null) {
                pool.returnResource(jedis);
            }
        }
        return false;
    }

    public boolean updateUser(String userId, String field, String value) {
        Jedis jedis = pool.getResource();
        try {
            if(jedis.hset(userId,field,value) == 0)return true;

        } catch (JedisException e) {
            if (jedis != null) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (jedis != null) {
                pool.returnResource(jedis);
            }
        }
        return false;
    }

}
