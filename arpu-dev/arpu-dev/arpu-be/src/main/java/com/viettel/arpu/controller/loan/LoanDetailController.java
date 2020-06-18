package com.viettel.arpu.controller.loan;

import com.viettel.arpu.model.dto.LoanDetailDTO;
import com.viettel.arpu.model.dto.UpdateLoanDTO;
import com.viettel.arpu.model.request.mb.LoanConfirmRequestForm;
import com.viettel.arpu.model.response.BaseResponse;
import com.viettel.arpu.service.loan.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/loans")
public class LoanDetailController {
    LoanService loanService;

    @Autowired
    public LoanDetailController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<LoanDetailDTO>> getDetail(@PathVariable Long id) {
        return ResponseEntity.ok(new BaseResponse<>(loanService.getLoanDetail(id)));
    }

    @PutMapping("/update_mb_approval")
    public ResponseEntity<BaseResponse<UpdateLoanDTO>> updateToMBApproval(@Valid LoanConfirmRequestForm loanConfirmRequestForm) throws Throwable {
        return ResponseEntity.ok(new BaseResponse<>(loanService.updateToMBApproval(loanConfirmRequestForm)));
    }
}
