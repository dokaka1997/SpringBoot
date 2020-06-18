package com.viettel.arpu.model.request.mb;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;


@Setter
@Getter
public class LoanConfirmRequestForm {
    @NotNull
    private String id;

    @NotNull
    MultipartFile file;
}
