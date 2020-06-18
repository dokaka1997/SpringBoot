package com.viettel.arpu.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("app")
public class AppConfig {

    @Value("${application-configuration}")
    String applicationConfiguration;

    @Value("${application-short-name}")
    String applicationShortName;
}
