package commons.spring.swagger

import org.springdoc.core.SpringDocConfigProperties
import org.springdoc.core.SpringDocConfiguration
import org.springdoc.core.providers.ObjectMapperProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
public open class SpringDocsConfiguration {
    @Bean
    @Primary
    public open fun springDocConfiguration(): SpringDocConfiguration {
        return SpringDocConfiguration()
    }

    @Bean
    public open fun springDocConfigProperties(): SpringDocConfigProperties {
        return SpringDocConfigProperties()
    }

    @Bean
    public fun objectMapperProvider(springDocConfigProperties: SpringDocConfigProperties?): ObjectMapperProvider {
        return ObjectMapperProvider(springDocConfigProperties)
    }
}
