package com.viettel.arpu.controller.ftpserver;

import com.viettel.arpu.config.FtpStorageProperties;
import com.viettel.arpu.config.SftpInfo;
import com.viettel.arpu.exception.FileInvalidException;
import com.viettel.arpu.utils.FtpUtils;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class FTPController {

    @Autowired
    private FtpStorageProperties ftpStorageProperties;


    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadPrototypeContract(
            @Required @RequestParam("path") Optional<String> path) throws IOException {
        if (path.isPresent() && "".equals(path.get())) {
            throw new FileInvalidException();
        }
        String path2File;
        path2File = path.map(s -> ftpStorageProperties.getUploadDir() + s).orElse(ftpStorageProperties.getTemplateFile());

        SftpInfo sftpInfoEx = SftpInfo.builder()
                .password(ftpStorageProperties.getPwd())
                .path2File(path2File)
                .port(Integer.parseInt(ftpStorageProperties.getPort()))
                .username(ftpStorageProperties.getUser())
                .serverAddress(ftpStorageProperties.getHost())
                .build();

        Resource pdfFile = new ByteArrayResource(FtpUtils.fileInBytesFromFtp(sftpInfoEx));
        return fileFromFTP(pdfFile);
    }

    private ResponseEntity<InputStreamResource> fileFromFTP(Resource pdfFile) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "inline;filename=" + "construct.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(pdfFile.contentLength())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(new InputStreamResource(pdfFile.getInputStream()));
    }


}
