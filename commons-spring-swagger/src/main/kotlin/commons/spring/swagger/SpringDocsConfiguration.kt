package commons.spring.swagger

import org.springdoc.core.SpringDocConfigProperties
import org.springdoc.core.SpringDocConfiguration
import org.springdoc.core.providers.ObjectMapperProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
public open class SpringDocsConfiguration {
    @Bean
    public open fun springDocConfiguration(): SpringDocConfiguration {
        return SpringDocConfiguration()
    }

    @Bean
    public open fun springDocConfigProperties(): SpringDocConfigProperties {
        return SpringDocConfigProperties()
    }

    @Bean
    public open fun objectMapperProvider(springDocConfigProperties: SpringDocConfigProperties?): ObjectMapperProvider {
        return ObjectMapperProvider(springDocConfigProperties)
    }
}
