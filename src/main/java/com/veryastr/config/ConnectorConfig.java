package com.veryastr.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@ConfigurationProperties(prefix = "fix")
@Getter
@Setter
public class ConnectorConfig {
    private Resource cfg;
    private String socketConnectHost;
    private String socketConnectPort;
}
