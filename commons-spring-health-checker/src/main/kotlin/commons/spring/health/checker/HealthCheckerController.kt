package commons.spring.health.checker

import org.springframework.boot.info.BuildProperties
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
public class HealthController(
    private val buildProperties: BuildProperties
) {

    @GetMapping("/health-check")
    public fun health(): Map<String, Any> {

        return mapOf(
            "application-name" to buildProperties.name,
            "application-version" to buildProperties.version
        )
    }
}