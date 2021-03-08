package tk.fishfish.mybatis.enums.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import tk.fishfish.mybatis.enums.EnumType;

/**
 * 枚举配置
 *
 * @author 奔波儿灞
 * @version 1.0.0
 */
public class EnumTypeConfiguration {

    @Bean
    public SimpleModule enumTypeModule() {
        SimpleModule module = new SimpleModule("enumType");
        module.addSerializer(EnumType.class, new EnumTypeJsonSerializer());
        module.addDeserializer(Enum.class, new EnumTypeJsonDeserializer());
        return module;
    }

}
