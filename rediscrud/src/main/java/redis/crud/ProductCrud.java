package redis.crud;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Map;

public class ProductCrud {
    private static final String redisHost = "localhost";
    private static final Integer redisPort = 6379;
    private static JedisPool pool = null;

    public ProductCrud() {
        pool = new JedisPool(redisHost, redisPort);
    }

    public void createProduct(String productId, Map<String, String> prodMap) {
        Jedis jedis = pool.getResource();

        try {

            jedis.hmset(productId, prodMap);

        } catch (JedisException e) {
            if (jedis != null) {
                pool.returnBrokenResource(jedis);
            }
        } finally {
            if (jedis != null) {
                pool.returnResource(jedis);
            }
        }

    }

    public Map<String, String> getAllProductFields(String productId) {
        Jedis jedis = pool.getResource();
        Map<String, String> product = null;

        try {
            product = jedis.hgetAll(productId);

        } catch (JedisException e) {
            if (jedis != null) {
                pool.returnBrokenResource(jedis);
            }
        } finally {
            if (jedis != null) {
                pool.returnResource(jedis);
            }
        }
        return product;
    }

    public String getProductField(String productId, String field) {
        Jedis jedis = pool.getResource();
        String result=null;
        try {

            result=jedis.hget(productId, field);

        } catch (JedisException e) {
            if (jedis != null) {
                pool.returnBrokenResource(jedis);
            }
        } finally {
            if (jedis != null) {
                pool.returnResource(jedis);
            }
        }
        return result;

    }

    public boolean removeProduct(String productId) {
        Jedis jedis = pool.getResource();

        try {
            if (jedis.del(productId) == 1)
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

    public boolean updateProduct(String productId, String field, String value) {
        Jedis jedis = pool.getResource();
        try {
            if(jedis.hset(productId,field,value) == 0)return true;

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


}
