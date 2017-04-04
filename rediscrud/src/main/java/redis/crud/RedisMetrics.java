package redis.crud;

    import org.junit.Test;
    import redis.clients.jedis.Jedis;
    import redis.clients.jedis.JedisPool;
    import java.util.HashMap;
    import java.util.Map;
    import static org.junit.Assert.assertEquals;
    import static org.junit.Assert.assertNotNull;
    import static org.junit.Assert.assertTrue;

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
        String info = jedis.info();
        assertNotNull(info);
        info = jedis.info("CPU");
        System.out.println(info);
        Map<String, String> convertInfo = convert(info);
        String usedCpu = convertInfo.get("used_cpu_sys");
        //assert the usedCpu is less then a maximum value
        System.out.println("used_cpu_sys " + usedCpu);
        Float ucedCpuF = Float.parseFloat(usedCpu);
        assertTrue(ucedCpuF< 150); 

    }

    public static Map<String, String> convert(String info) {
        String[] tokens = info.split("\r\n",2)[1].split("\r\n");
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < tokens.length - 1; i++) {
            String[] token = tokens[i].split(":");
            map.put(token[0], token[1]);
        };
        return map;
    }
}
