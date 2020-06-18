package com.viettel.arpu.service.mb;

import com.viettel.arpu.model.dto.LoanDTO;
import com.viettel.arpu.model.dto.mb.MbLoanLimitDTO;
import com.viettel.arpu.model.request.mb.*;
import com.viettel.arpu.model.response.mb.MbBaseResponse;



/**
 * @Author VuHQ
 * @Since 5/29/2020
 */
public interface MbRegisterLoanService {


    MbBaseResponse sendToMBApprove(MbLoanRegistrationForm mbLoanRegistrationForm);


    MbBaseResponse getHistories(MbHistoriesLoanForm mbHistoriesLoanForm);


    MbBaseResponse updateLimit(MbChangeLimitForm mbChangeLimitForm, Long id);


    MbBaseResponse checkLoanLimit(Long id, MbChangeLimitForm mbChangeLimitForm);


    MbBaseResponse checkPayLoan(MbPayLoanForm mbPayLoanForm, Long id);


    MbBaseResponse confirmPayLoan(MbPayLoanForm mbPayLoanForm, Long id);


    MbLoanLimitDTO getLoanLimitInMB(MbGetLimitInMBForm mbGetLimitInMBForm);

    MbBaseResponse checkFinalLoan(MbFinalLoanForm mbPayLoanForm, Long id);

    MbBaseResponse confirmFinalLoan(MbFinalLoanForm mbPayLoanForm, Long id);

}
