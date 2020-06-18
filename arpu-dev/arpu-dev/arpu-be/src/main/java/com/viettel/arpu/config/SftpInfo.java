package com.viettel.arpu.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SftpInfo {
    // Create the SFTP URI using the host name, userid, password, remote, path and file name
    String username;
    String password;
    String serverAddress;
    int port;
    String path2File;
}
