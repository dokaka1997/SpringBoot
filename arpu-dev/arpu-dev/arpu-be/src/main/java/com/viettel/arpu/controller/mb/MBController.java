package com.viettel.arpu.controller.mb;

import com.viettel.arpu.model.request.LoanApprovalForm;
import com.viettel.arpu.model.response.BaseResponse;
import com.viettel.arpu.model.response.mb.MbBaseResponse;
import com.viettel.arpu.service.mb.MBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/mb/loans")
public class MBController {
    @Autowired
    MBService mbService;

    @PutMapping("/loan_approval")
    public ResponseEntity<BaseResponse<MbBaseResponse>> updateLoanInfo(@Valid LoanApprovalForm loanApprovalForm) {
        return ResponseEntity.ok(new BaseResponse<>(mbService.updateLoanInfo(loanApprovalForm)));
    }
}
