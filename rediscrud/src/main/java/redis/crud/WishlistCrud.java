package redis.crud;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.Set;

public class WishlistCrud {
    private static final String redisHost = "localhost";
    private static final Integer redisPort = 6379;
    private static JedisPool pool = null;

    public WishlistCrud() {
        pool = new JedisPool(redisHost, redisPort);
    }

    public boolean createWishlist(String userId, String product) {
        Jedis jedis = pool.getResource();
        try {
            if (jedis.sadd(userId, product) == 1) return true;


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

    public Set<String> getWishlist(String userId) {
        Jedis jedis = pool.getResource();
        Set<String> productIds = null;
        try {

            productIds = jedis.smembers(userId);

        } catch (JedisException e) {
            if (jedis != null) {
                pool.returnBrokenResource(jedis);
            }
        } finally {
            if (jedis != null) {
                pool.returnResource(jedis);
            }
        }
        return productIds;
    }

}
