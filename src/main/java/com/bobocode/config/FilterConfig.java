package com.bobocode.config;

import jakarta.servlet.DispatcherType;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<ShallowEtagHeaderFilter> shallowEtagHeaderFilter() {
        var registrationBean = new FilterRegistrationBean<ShallowEtagHeaderFilter>();
        registrationBean.setFilter(new ShallowEtagHeaderFilter());
        registrationBean.addUrlPatterns("/users/*");
        registrationBean.setDispatcherTypes(DispatcherType.ASYNC, DispatcherType.REQUEST,
                DispatcherType.FORWARD, DispatcherType.INCLUDE);
        return registrationBean;
    }
}
