package com.viettel.arpu.service.mb;

import com.viettel.arpu.model.request.LoanApprovalForm;
import com.viettel.arpu.model.response.mb.MbBaseResponse;


/**
 * @author DoDV
 * @Date :6/5/2020, Fri
 */
public interface MBService {
    MbBaseResponse updateLoanInfo(LoanApprovalForm loanApprovalForm);
}
