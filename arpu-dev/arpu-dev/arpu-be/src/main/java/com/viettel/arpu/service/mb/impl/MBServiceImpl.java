package com.viettel.arpu.service.mb.impl;

import com.viettel.arpu.config.MbStorageProperties;
import com.viettel.arpu.constant.HeaderConstants;
import com.viettel.arpu.constant.LoanStatus;
import com.viettel.arpu.constant.MBConstant;
import com.viettel.arpu.exception.*;
import com.viettel.arpu.model.dto.mb.MbConfirmCreateLoan;
import com.viettel.arpu.model.entity.CodeCode;
import com.viettel.arpu.model.entity.Loan;
import com.viettel.arpu.model.request.LoanApprovalForm;
import com.viettel.arpu.model.response.mb.MbBaseResponse;
import com.viettel.arpu.repository.CodeCodeRepository;
import com.viettel.arpu.repository.LoanRepository;
import com.viettel.arpu.service.mb.AbstractMbService;
import com.viettel.arpu.service.mb.MBService;
import com.viettel.arpu.utils.ObjectMapperUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.viettel.arpu.constant.MBConstant.KYC_APPROVAL;
import static com.viettel.arpu.constant.MBConstant.KYC_REFUSE;

@Service
public class MBServiceImpl extends AbstractMbService implements MBService {

    @Autowired
    MbStorageProperties mbStorageProperties;
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    CodeCodeRepository codeCodeRepository;

    /**
     * Gửi request tới API MB
     * Nếu kết quả trả về mã lỗi là 00(Thành công)
     * thì update lại trạng thái duyệt và trạng thái của khoản vay.
     * Nếu mã lỗi khác 00 thì trả về mã lỗi và không update trạng thái của khoản vay
     *
     * @param loanApprovalForm
     * @return MbBaseResponse
     */
    @Override
    public MbBaseResponse updateLoanInfo(LoanApprovalForm loanApprovalForm) {

        MbBaseResponse loanResponse = sendToMb(loanApprovalForm);

        if (!MBConstant.SUCCESS.equals(loanResponse.getErrorCode())) {
            return loanResponse;
        }

        Loan loan = loanRepository.findById(loanApprovalForm.getLoanId())
                .orElseThrow(LoanNotFoundException::new);

        if (!LoanStatus.PDS_02.equals(loan.getApprovalStatus().getId())) {
            throw new LoanStatusInvalidException();
        }

        checkLoanValid(loan, loanApprovalForm);

        loan = updateLoanStatus(loan, loanApprovalForm);

        loanRepository.save(loan);

        return loanResponse;
    }

    /**
     * Gửi request tới API MB và nhận response trả về
     *
     * @param loanApprovalForm
     * @return MbBaseResponse
     */
    public MbBaseResponse sendToMb(LoanApprovalForm loanApprovalForm) {

        MbConfirmCreateLoan mbConfirmCreateLoan = ObjectMapperUtils.map(loanApprovalForm, MbConfirmCreateLoan.class);
        if (LoanStatus.PDS_03.equals(loanApprovalForm.getCodeId())) {
            mbConfirmCreateLoan.setKyc(KYC_APPROVAL);
        } else {
            mbConfirmCreateLoan.setKyc(KYC_REFUSE);
        }

        return sendToMbAPI(mbConfirmCreateLoan, HttpMethod.POST,
                mbStorageProperties.getKyc(), MbBaseResponse.class,
                HeaderConstants.POST_KYC);
    }


    /**
     * Check trạng thái khoản vay và mã code
     * Nếu khoản vay đã được approved hoặc rejected rồi thì k được phép thực hiện lại
     * Chỉ nhận vào 3 loại trạng thái của phê duyệt, nếu truyền sai sẽ không xử lý
     * Nếu từ chối mà không truyền vào lý do từ chối sẽ thông báo lỗi
     * sẽ trả ra ngoại lệ nếu có bất kì lỗi xảy ra
     *
     * @param loanApprovalForm
     */
    void checkLoanValid(Loan loan, LoanApprovalForm loanApprovalForm) {
        List<String> approvalStatus = new ArrayList<>();
        approvalStatus.add(LoanStatus.PDS_03);
        approvalStatus.add(LoanStatus.PDS_04);
        approvalStatus.add(LoanStatus.PDS_01);

        if (approvalStatus.contains(loan.getApprovalStatus().getId())) {
            throw new LoanHasBeenApprovedException();
        }

        if (!approvalStatus.contains(loanApprovalForm.getCodeId())) {
            throw new CodeInvalidException();
        }

        if (!loanApprovalForm.getCodeId().equals(LoanStatus.PDS_03)
                && StringUtils.isEmpty(loanApprovalForm.getReason())) {
            throw new ReasonMissingException();
        }

    }

    /**
     * Nhận vào thông tin khoản vay và thông tin update
     * Tìm kiếm xem khoản vay có tồn tại hay không, neesy không trả ra thông báo lỗi tìm kiếm.
     * Update lại trạng thái khoản vay theo trạng thái status nhận vào
     *
     * @param loan
     * @param loanApprovalForm
     * @return Loan
     */
    Loan updateLoanStatus(Loan loan, LoanApprovalForm loanApprovalForm) {
        CodeCode codeCode = codeCodeRepository.findById(loanApprovalForm.getCodeId())
                .orElseThrow(CodeNotFoundException::new);

        switch (loanApprovalForm.getCodeId()) {
            case LoanStatus.PDS_04: {
                loan.setApprovalStatus(codeCode);
                if (StringUtils.isNotEmpty(loanApprovalForm.getReason())) {
                    loan.setReasonRejection(loanApprovalForm.getReason());
                }
                CodeCode code = codeCodeRepository.findById(LoanStatus.KVS_05)
                        .orElseThrow(CodeNotFoundException::new);
                loan.setLoanStatus(code);
                break;
            }
            case LoanStatus.PDS_01: {
                if (StringUtils.isNotEmpty(loanApprovalForm.getReason())) {
                    loan.setReasonRejection(loanApprovalForm.getReason());
                }
                CodeCode code = codeCodeRepository.findById(LoanStatus.KVS_01)
                        .orElseThrow(CodeNotFoundException::new);
                loan.setLoanStatus(code);
                loan.setApprovalStatus(codeCode);
                break;
            }
            case LoanStatus.PDS_03: {
                loan.setApprovalStatus(codeCode);
                CodeCode code = codeCodeRepository.findById(LoanStatus.KVS_02)
                        .orElseThrow(CodeNotFoundException::new);
                loan.setLoanStatus(code);
                break;
            }
        }
        return loan;
    }

}
