package redis.crud;

    import org.junit.Test;
    import redis.clients.jedis.Jedis;
    import redis.clients.jedis.JedisPool;
    import java.util.HashMap;
    import java.util.Map;
    import static org.junit.Assert.assertEquals;
    import static org.junit.Assert.assertNotNull;

public class RedisMetrics {

    private static final String redisHost = "localhost";
    private static final Integer redisPort = 6379;
    private static JedisPool pool = null;

    public RedisMetrics() {
        pool = new JedisPool(redisHost, redisPort);
    }


    @Test
    public void info() {
        Jedis jedis = pool.getResource();
        int allowed_used_cpu_sys = 150;
        String info = jedis.info();
        assertNotNull(info);
        info = jedis.info("CPU");
        Map<String, String> convertInfo = convert(info);
        String usedCpu = convertInfo.get("used_cpu_sys");
        //assert the usedCpu is less then a maximum value
    }

    public static Map<String, String> convert(String info) {
        String[] tokens = info.split("\r\n|:");
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < tokens.length - 1; ) map.put(tokens[i++], tokens[i++]);
        return map;
    }
}
