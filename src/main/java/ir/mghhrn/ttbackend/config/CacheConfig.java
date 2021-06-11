package ir.mghhrn.ttbackend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Value("${spring.cache.cache-names}")
    private String cacheNames;

    @Bean
    public String REGISTRATION_CACHE() {
        return cacheNames.split(",")[0];
    }
}
