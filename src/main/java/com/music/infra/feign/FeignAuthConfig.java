package com.music.infra.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignAuthConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {

        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getCredentials() != null) {
            String token = auth.getCredentials().toString();
            template.header("Authorization", "Bearer " + token);
        }
    }
}

