package com.viettel.arpu.controller.mb;

import com.viettel.arpu.model.dto.LoanDTO;
import com.viettel.arpu.model.request.mb.*;
import com.viettel.arpu.model.response.BaseResponse;
import com.viettel.arpu.model.response.mb.MbBaseResponse;
import com.viettel.arpu.service.mb.MbRegisterLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author VuHQ
 * @Since 5/29/2020
 */
@RestController
@RequestMapping("/api/mb/loans")
public class MbLoanController {
    @Autowired
    MbRegisterLoanService mbRegisterLoanService;

    /**
     * @description gửi hồ sơ sang cho MB phê duyệt
     * @param mbLoanRegistrationForm
     * @return
     */
    @PostMapping("/approval")
    public ResponseEntity<BaseResponse<MbBaseResponse>> sendToMBApproval(@RequestBody @Valid MbLoanRegistrationForm mbLoanRegistrationForm){
        return ResponseEntity.ok(new BaseResponse<MbBaseResponse>(mbRegisterLoanService.sendToMBApprove(mbLoanRegistrationForm)));
    }

    /**
     * @description gửi yêu cầu lấy lịch sử 5 giao dịch gần nhất cho MB
     * @param mbHistoriesLoanForm
     * @return
     */
    @PostMapping("/histories")
    public ResponseEntity<BaseResponse<MbBaseResponse>> getLoanHistoriesInMB(@RequestBody @Valid MbHistoriesLoanForm mbHistoriesLoanForm){
        return ResponseEntity.ok(new BaseResponse<MbBaseResponse>(mbRegisterLoanService.getHistories(mbHistoriesLoanForm)));
    }

    /**
     * @description lấy thông tin hạn mức của khoản vay từ MB khi tạo tài khoản vay
     * (lúc này khoản vay chưa tồn tại trong hệ thống để lấy về)
     * @param mbGetLimitInMBForm
     * @return
     */
    @PostMapping("/customers/limit")
    public ResponseEntity<BaseResponse<MbBaseResponse>> getLoanLimitInMB(@RequestBody @Valid MbGetLimitInMBForm mbGetLimitInMBForm){
        return ResponseEntity.ok(new BaseResponse<MbBaseResponse>(
                mbRegisterLoanService.getLoanLimitInMB(mbGetLimitInMBForm)));
    }

    /**
     * @description gửi yêu cầu kiểm tra thay đổi hạn mức sang cho MB
     * @param id
     * @param mbChangeLimitForm
     * @return
     */
    @PostMapping("/{id}/limit")
    public ResponseEntity<BaseResponse<MbBaseResponse>> checkLoanLimitInMB(@PathVariable(value = "id", required = true) Long id,
                                                                           @RequestBody @Valid MbChangeLimitForm mbChangeLimitForm){
        return ResponseEntity.ok(new BaseResponse<>(mbRegisterLoanService.checkLoanLimit(id, mbChangeLimitForm)));
    }

    /**
     * @description gửi xác nhận tăng hạn mức(chỉ khi kiểm tra thay đổi hạn mức ok thì ms xác nhận)
     * @param id
     * @param mbChangeLimitForm
     * @return
     */
    @PutMapping("{id}/limit")
    public ResponseEntity<BaseResponse<MbBaseResponse>> changeLimitInMB(@PathVariable(value = "id", required = true) Long id
            , @RequestBody @Valid MbChangeLimitForm mbChangeLimitForm){
        return ResponseEntity.ok(new BaseResponse<>(mbRegisterLoanService.updateLimit(mbChangeLimitForm, id)));
    }

    /**
     * @descritpion gửi yêu cầu kiểm tra thanh toán sang cho MB
     * @param id
     * @param mbPayLoanForm
     * @return
     */
    @PostMapping("/{id}/pay")
    public ResponseEntity<BaseResponse<MbBaseResponse>> checkPayLoanInMB(@PathVariable(value = "id", required = true) Long id
            , @RequestBody @Valid MbPayLoanForm mbPayLoanForm){
        return ResponseEntity.ok(new BaseResponse<>(mbRegisterLoanService.checkPayLoan(mbPayLoanForm, id)));
    }

    /**
     * @description gửi xác nhận thanh toán sang cho MB(chỉ xác nhận khi kiểm tra thành công)
     * @param id
     * @param mbPayLoanForm
     * @return
     */
    @PutMapping("/{id}/pay")
    public ResponseEntity<BaseResponse<MbBaseResponse>> confirmPayLoanInMB(@PathVariable(value = "id", required = true) Long id
            , @RequestBody @Valid MbPayLoanForm mbPayLoanForm){
        return ResponseEntity.ok(new BaseResponse<>(mbRegisterLoanService.confirmPayLoan(mbPayLoanForm, id)));
    }

    /**
     * @description gửi yêu cầu kiểm tra tất toán sang cho MB
     * @param id
     * @param mbFinalLoanForm
     * @return
     */
    @PostMapping("/{id}/final")
    public ResponseEntity<BaseResponse<MbBaseResponse>> checkFinalLoanInMB(@PathVariable(value = "id", required = true) Long id
            , @RequestBody @Valid MbFinalLoanForm mbFinalLoanForm){
        return ResponseEntity.ok(new BaseResponse<>(mbRegisterLoanService.checkFinalLoan(mbFinalLoanForm, id)));
    }

    /**
     * @description gửi xác nhận tất toán sang cho MB(chỉ gửi khi kết quả kiểm tra thành công)
     * @param id
     * @param mbFinalLoanForm
     * @return
     */
    @PutMapping("/{id}/final")
    public ResponseEntity<BaseResponse<MbBaseResponse>> confirmFinalLoanInMB(@PathVariable(value = "id", required = true) Long id
            , @RequestBody @Valid MbFinalLoanForm mbFinalLoanForm){
        return ResponseEntity.ok(new BaseResponse<>(mbRegisterLoanService.confirmFinalLoan(mbFinalLoanForm, id)));
    }

}
