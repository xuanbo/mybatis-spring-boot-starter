package tk.fishfish.mybatis.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 通用Mapper自动配置
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@Configuration
public class MapperAutoConfiguration {

    private static final String MAPPER_HELPER_PREFIX = "mybatis.mapper";

    /**
     * 通用mapper的配置
     *
     * @return Properties
     */
    @Bean
    @ConfigurationProperties(MAPPER_HELPER_PREFIX)
    public Properties mapperProperties() {
        return new Properties();
    }

}
