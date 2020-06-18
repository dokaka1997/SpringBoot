package com.viettel.arpu.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author User
 * @Since 6/9/2020
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "arpu.sftp")
public class FtpStorageProperties {
    private String host;
    private String port;
    private String user;
    private String batchUser;
    private String pwd;
    private String btachPwd;
    private String uploadDir;
    private String batchDir;
    private String templateFile;
    private String whitelistFile;
}
