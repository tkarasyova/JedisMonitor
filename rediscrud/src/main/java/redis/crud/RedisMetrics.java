package redis.crud;

    import org.junit.Test;
    import redis.clients.jedis.Jedis;
    import redis.clients.jedis.JedisPool;

    import java.security.Timestamp;
    import java.util.HashMap;
    import java.util.List;
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
    // check the value of some metric satisfies expected limits
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
        assertTrue(ucedCpuF < 150);

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

    @Test
    //check the last successfully saving of the dataset on disk was made less then 1 second ago
    public void lastsave() throws InterruptedException {
        Jedis jedis = pool.getResource();
        long lastSavedTimestamp = jedis.lastsave();
        long currentTimestamp = System.currentTimeMillis()/1000;
        assertTrue(currentTimestamp - lastSavedTimestamp < 1000 );
    }

    @Test
    //Retrieve the configuration of a running Redis server as a list of key-value pairs.
    public void configGet() {
        Jedis jedis = pool.getResource();
        List<String> redisProperties = jedis.configGet("*");
        assertNotNull(redisProperties);
        System.out.println(redisProperties);

        Map<String, String> propertyMap = parsConfig(redisProperties);
        String prop = propertyMap.get("hash-max-ziplist-entries");
        System.out.println("hash-max-ziplist-entries " + prop);
        Float propertyF = Float.parseFloat(prop);
        assertTrue(propertyF == 512);
    }
    public static Map<String, String> parsConfig(List<String> redisProperties) {
        Map<String, String> resultsMap = new HashMap<String, String>();
        for ( int j=0; j < redisProperties.size()-1; j++) {
            resultsMap.put((String) redisProperties.get(j), redisProperties.get(j+1));
            j++;
        }

        return resultsMap;
    }

}
