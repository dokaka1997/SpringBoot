package com.viettel.arpu.controller.ftp;

import com.viettel.arpu.config.FtpStorageProperties;
import com.viettel.arpu.controller.ftpserver.FTPController;
import com.viettel.arpu.utils.FtpUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FTPControllerTest {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @Autowired
    FTPController ftpController;

    @Autowired
    FtpStorageProperties ftpStorageProperties;

    @Test
    @DisplayName("when download file success")
    void downloadFileSuccess() throws IOException {
        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.pdf", "text/plain", "some xml".getBytes());
        String filename = uploadFile(ftpStorageProperties, firstFile);

        Optional<String> path = Optional.of(filename);
        ResponseEntity<InputStreamResource> responseEntity = ftpController.downloadPrototypeContract(path);
        Assertions.assertEquals(responseEntity.getStatusCode().name(), "OK");

        Assertions.assertEquals(responseEntity.getStatusCode().value(), "200 OK");
        System.out.println("aaaaaaaaaaa");
    }

    @Test
    @DisplayName("when download file not existed")
    void downloadFileNotExisted() {

    }

    @Test
    @DisplayName("when connect to sftp server false")
    void connectServerFalse() {

    }

    @Test
    @DisplayName("when download file template")
    void downloadFileTemplate() {

    }

    String uploadFile(FtpStorageProperties ftpStorageProperties, MultipartFile file) throws IOException {

        String fileFormat = ".pdf";

        String remoteFile = timestamp.getTime() + fileFormat;

        FtpUtils.putFile(ftpStorageProperties, file.getInputStream(), remoteFile);

        return remoteFile;
    }

}
