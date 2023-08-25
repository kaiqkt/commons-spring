package commons.spring.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(AuthenticationProperties::class)
public class SecurityWebServletConfiguration(
    private val authenticationProperties: AuthenticationProperties,
    private val restAuthenticationEntryPoint: RestAuthenticationEntryPoint
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .csrf().apply { disable() }.and()
            .headers().apply { disable() }.and()
            .authorizeHttpRequests().antMatchers(HttpMethod.GET, *authenticationProperties.ignoreGetPaths).permitAll().and()
            .authorizeHttpRequests().antMatchers(HttpMethod.POST, *authenticationProperties.ignorePostPaths).permitAll().and()
            .authorizeHttpRequests().antMatchers(HttpMethod.PUT, *authenticationProperties.ignorePutPaths).permitAll().and()
            .authorizeHttpRequests().antMatchers(HttpMethod.DELETE, *authenticationProperties.ignoreDeletePaths).permitAll().and()
            .authorizeHttpRequests().antMatchers(*PATH_MATCHERS.plus(authenticationProperties.ignoreGenericPaths)).permitAll().and()
            .authorizeHttpRequests()
            .anyRequest().authenticated().and()
            .addFilter(AuthenticationFilter(authenticationProperties, authenticationManager(), restAuthenticationEntryPoint))
    }

    public companion object {
        private val PATH_MATCHERS = arrayOf(
            "/v2/api-docs",
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui/index.html",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**",
            "/api-docs.yml",
            "/docs",
            "/health"
        )
    }
}