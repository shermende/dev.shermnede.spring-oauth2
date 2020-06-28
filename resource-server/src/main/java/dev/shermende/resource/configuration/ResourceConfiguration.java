package dev.shermende.resource.configuration;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
@RequiredArgsConstructor
@EnableConfigurationProperties({
    ResourceConfiguration.ClientProperties.class,
})
public class ResourceConfiguration extends ResourceServerConfigurerAdapter {

    private final ClientProperties clientProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/user").hasAuthority("ROLE_USER")
            .antMatchers("/admin").hasAuthority("ROLE_ADMIN")
            .anyRequest().authenticated()
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
            .and().anonymous().disable().httpBasic().disable().csrf().disable()
        ;
    }

    @Bean
    public ResourceServerTokenServices tokenService() {
        final RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setClientId(clientProperties.getClient());
        tokenServices.setClientSecret(clientProperties.getSecret());
        tokenServices.setCheckTokenEndpointUrl(clientProperties.getUrl());
        return tokenServices;
    }

    @Data
    @ConfigurationProperties("oauth2.client")
    public static class ClientProperties {
        private String client = "client";
        private String secret = "secret";
        private String url = "http://localhost:8081/oauth/check_token";
    }

}