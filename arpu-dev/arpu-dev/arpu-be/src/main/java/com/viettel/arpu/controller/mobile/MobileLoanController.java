package com.viettel.arpu.controller.mobile;

import com.viettel.arpu.model.dto.LoanDTO;
import com.viettel.arpu.model.dto.ReferenceDTO;
import com.viettel.arpu.model.dto.mb.MbCustomerDTO;
import com.viettel.arpu.model.dto.mb.MbExistPhoneNumberDTO;
import com.viettel.arpu.model.dto.mb.MbListLoanDTO;
import com.viettel.arpu.model.dto.mb.MbLoanLimitDTO;
import com.viettel.arpu.model.request.mb.MbCheckCustomerForm;
import com.viettel.arpu.model.request.mb.MbLoanRegistrationForm;
import com.viettel.arpu.model.response.BaseResponse;
import com.viettel.arpu.model.response.CollectionResponse;
import com.viettel.arpu.service.loan.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author VuHQ
 * @Since 6/11/2020
 */
@RestController
@RequestMapping("/api/mb/loans")
public class MobileLoanController {
    @Autowired
    MobileService mobileService;

    /**
     * @description api lấy ra toàn bộ khoản vay của khách hàng, trong đó có trạng thái vay của khoản vay cuối cùng
     * và tổng số khoản vay của khách hàng
     * @param mbCheckCustomerForm
     * @return
     */
    @GetMapping
    public ResponseEntity<BaseResponse<MbListLoanDTO>> getAllLoan(@Valid MbCheckCustomerForm mbCheckCustomerForm){
        return ResponseEntity.ok(
                new BaseResponse<MbListLoanDTO>(mobileService.getLoansByPhone(mbCheckCustomerForm)));
    }

    /**
     * @description lưu thông tin khoản vay vào hệ thống
     * @param mbLoanRegistrationForm
     * @return
     */
    @PostMapping
    public ResponseEntity<BaseResponse<LoanDTO>> saveLoan(@RequestBody @Valid MbLoanRegistrationForm mbLoanRegistrationForm){
        return ResponseEntity.ok(
                new BaseResponse<LoanDTO>(mobileService.saveLoan(mbLoanRegistrationForm))
        );
    }

    /**
     * @description kiểm tra xem khách hàng có tồn tại trong while list không
     * @param mbCheckCustomerForm
     * @return
     */
    @GetMapping("/customers/exist")
    public ResponseEntity<BaseResponse<MbExistPhoneNumberDTO>> isExistCustomer(@Valid MbCheckCustomerForm mbCheckCustomerForm){
        return ResponseEntity.ok(
                new BaseResponse<MbExistPhoneNumberDTO>(mobileService.isExistCustomer(mbCheckCustomerForm))
        );
    }

    @GetMapping("customers/reference")
    public ResponseEntity<BaseResponse<MbCustomerDTO>> getRelationshipCustomer(@Valid MbCheckCustomerForm mbCheckCustomerForm) {
        return ResponseEntity.ok(
                new BaseResponse<>(mobileService.getCustomer(mbCheckCustomerForm)));
    }
    /**
     * @description lấy hạn mức vay của khoản vay
     * @param id
     * @return
     */
    @GetMapping("/{id}/limit")
    public ResponseEntity<BaseResponse<MbLoanLimitDTO>> getLoanLimit(@PathVariable(value = "id", required = true) Long id){
        return ResponseEntity.ok(
                new BaseResponse<MbLoanLimitDTO>(mobileService.getLoanLimit(id)));
    }

}
