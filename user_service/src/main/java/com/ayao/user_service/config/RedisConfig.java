package com.ayao.user_service.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author ：ayao
 * @date ：Created in 2019/5/18 14:18
 * @version:
 */

/**
 * @AutoConfigureAfter(RedisAutoConfiguration.class)
 * 是让我们这个配置类在内置的配置类之后在配置，
 * 这样就保证我们的配置类生效，并且不会被覆盖配置。
 */
@Configuration
@EnableCaching
//@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 360)
public class RedisConfig {


  /**
   * 设置自动key生成规则，匿名内部类
   * 可以用lambda（较难看懂）替换
   */
  @Bean
  public KeyGenerator keyGenerator(){
    return new KeyGenerator() {
      @Override
      public Object generate(Object target, Method method, Object... params) {
        //格式化缓存key字符串
        StringBuilder sb = new StringBuilder();
        //追加类名
        sb.append(target.getClass().getName());
        //追加方法名
        sb.append(method.getName());
        //遍历参数并且追加
        for (Object obj:params){
          sb.append(obj.toString());
        }
        return sb.toString();
      }
    };
  }
  /**
   * json序列化
   */
  @Bean
  public RedisSerializer<Object> jackson2JsonRedisSerializer(){
    //使用jackson2JsonRedisSerializer来序列化和反序列化redis的value
    Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
    //ObjectMapper：对象映射
    ObjectMapper om = new ObjectMapper();
    /*
      设置可见性
      JsonAutoDetect：自动检测 Visibility：可见性
      PropertyAccessor：属性访问器能够读取（并可能写入）对象的属性
     */
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    //默认类型：不是最终的
    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    serializer.setObjectMapper(om);
    return serializer;
  }


  /**
   * 配置缓存管理器
   * 返回一个redis的缓存空间，然后在方法里头设置添加在该空间的缓存槽，
   * 最终就可以将每个element放到redis
   * @return
   */
  @Bean
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
    /*
      通过spring提供的RedisCacheConfiguration类，
      构造自己的redis配置类，
      从该配置类中可以设置一些初始化的缓存命名空间
          生成一个默认配置，通过config对象即可对缓存进行自定义配置
     */
    RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
    //1.设置缓存的默认过期时间，使用Duration设置
    config = config.entryTtl(Duration.ofMinutes(1))
        //2.设置key为String序列化 RedisSerializationContext：序列化上下文  SerializationPair：序列化对
        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
        //3.设置value为json序列化
        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer()));
        //4.不缓存空值
//       .disableCachingNullValues();

    //5.设置一个初始化的缓存空间set集合
    Set<String> cacheNames = new HashSet<>();
    cacheNames.add("timeGroup");
    cacheNames.add("user");
    cacheNames.add("user2");
    cacheNames.add("item");
    cacheNames.add("getitem");
    cacheNames.add("order");

    //6.对每个缓存空间应用不同的配置
    Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
    configMap.put("timeGroup",config);
    configMap.put("user",config.entryTtl(Duration.ofMinutes(5)));
    configMap.put("user2",config.entryTtl(Duration.ofMinutes(5)));
    configMap.put("item",config.entryTtl(Duration.ofMinutes(5)));
    configMap.put("getitem",config.entryTtl(Duration.ofSeconds(5)));
    configMap.put("order",config.entryTtl(Duration.ofMinutes(10)));
    //使用自定义的缓存配置初始化一个CacheManager
    RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
        //一定要先调用该方法设置初始化的缓存名，再初始化相关配置
        .initialCacheNames(cacheNames)
        .withInitialCacheConfigurations(configMap)
        .build();
    return cacheManager;
  }

  /**
   * 配置自定义RedisTemplate
   * 需要注意的就是方法名一定要叫redisTemplate
   * 因为@Bean注解是根据方法名配置这个bean的name的。
   * @param
   * @return
   */
  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory){
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory);

    //使用StringRedisSerializer来序列化和反序列化redis的key值
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    // value值的序列化采用jackson2JsonRedisSerializer
    redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
    redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer());
    redisTemplate.afterPropertiesSet();
    return redisTemplate;
  }
}
