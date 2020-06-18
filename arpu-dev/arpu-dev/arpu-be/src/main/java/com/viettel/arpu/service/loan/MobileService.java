package com.viettel.arpu.service.loan;

import com.viettel.arpu.model.dto.LoanDTO;
import com.viettel.arpu.model.dto.mb.MbCustomerDTO;
import com.viettel.arpu.model.dto.mb.MbExistPhoneNumberDTO;
import com.viettel.arpu.model.dto.mb.MbListLoanDTO;
import com.viettel.arpu.model.dto.mb.MbLoanLimitDTO;
import com.viettel.arpu.model.request.mb.MbCheckCustomerForm;
import com.viettel.arpu.model.request.mb.MbLoanRegistrationForm;

/**
 * @Author VuHQ
 * @Since 6/5/2020
 */
public interface MobileService {
    MbExistPhoneNumberDTO isExistCustomer(MbCheckCustomerForm mbCheckCustomerForm);
    MbListLoanDTO getLoansByPhone(MbCheckCustomerForm mbCheckCustomerForm);
    MbLoanLimitDTO getLoanLimit(Long id);
    LoanDTO saveLoan(MbLoanRegistrationForm mbLoanRegistrationForm);
    MbCustomerDTO getCustomer(MbCheckCustomerForm mbCheckCustomerForm);
}
