package ssf.miniproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
	private String redisHost;
	@Value("${spring.redis.port}")
	private Integer redisPort;
	@Value("${spring.redis.database}")
	private Integer redisDB;
	@Value("${spring.redis.username}")
	private String redisUser;
	@Value("${REDIS_PASSWORD}")
	private String redisPassword;

    @Bean("redis")
    public RedisTemplate<String, Object> redisTemplate() {

        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost); 
        config.setPort(redisPort);
        config.setDatabase(redisDB);
		config.setUsername(redisUser);
        config.setPassword(redisPassword);
        

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer(getClass().getClassLoader()));
        template.setHashKeySerializer(template.getKeySerializer());
        template.setHashKeySerializer(template.getValueSerializer());

        return template;
    }
}
