package com.viettel.arpu.service.mb.impl;

import com.viettel.arpu.config.MbStorageProperties;
import com.viettel.arpu.constant.HeaderConstants;
import com.viettel.arpu.constant.ParamsHeader;
import com.viettel.arpu.constant.enums.PayType;
import com.viettel.arpu.constant.enums.Relationship;
import com.viettel.arpu.exception.MbNotFoundException;
import com.viettel.arpu.model.dto.LoanDTO;
import com.viettel.arpu.model.dto.mb.MbLoanLimitDTO;
import com.viettel.arpu.model.entity.Customer;
import com.viettel.arpu.model.entity.Loan;
import com.viettel.arpu.model.request.mb.*;
import com.viettel.arpu.model.response.mb.MbBaseResponse;
import com.viettel.arpu.repository.CustomerRepository;
import com.viettel.arpu.repository.LoanRepository;
import com.viettel.arpu.service.mb.AbstractMbService;
import com.viettel.arpu.service.mb.MbRegisterLoanService;
import com.viettel.arpu.utils.DateUtils;
import com.viettel.arpu.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Author VuHQ
 * @Since 5/29/2020
 */
@Service
public class MbRegisterLoanServiceImpl extends AbstractMbService implements MbRegisterLoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    MbStorageProperties mbStorageProperties;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public MbBaseResponse sendToMBApprove(MbLoanRegistrationForm mbLoanRegistrationForm) {
        Customer customer = getCustomerByMsisdn(mbLoanRegistrationForm.getSourceMobile());

        MbSendToMbApprovalForm mbSendToMbApprovalForm = ObjectMapperUtils
                .map(mbLoanRegistrationForm, MbSendToMbApprovalForm.class);

        mbSendToMbApprovalForm.setReferencerType(Relationship.
                valueOf(mbLoanRegistrationForm.getReferencerType()).getId());
        mbSendToMbApprovalForm.setPayType(PayType
                .valueOf(mbLoanRegistrationForm.getPayType()).getId());

        mbSendToMbApprovalForm.setScoreAve(customer.getArpuLatestThreeMonths());
        mbSendToMbApprovalForm.setScoreMax(customer.getScoreMax());
        mbSendToMbApprovalForm.setScoreMin(customer.getScoreMin());

        return sendToMbAPI(mbSendToMbApprovalForm, HttpMethod.POST
                , mbStorageProperties.getCreateloan(), MbBaseResponse.class
                , HeaderConstants.POST_CREATE_LOAN);
    }


    @Override
    public MbBaseResponse getHistories(MbHistoriesLoanForm mbHistoriesLoanForm) {
        return sendToMbAPI(mbHistoriesLoanForm, HttpMethod.POST
                , mbStorageProperties.getHistoryloan(), MbBaseResponse.class
                , HeaderConstants.POST_HISTORY_LOAN);
    }

    @Override
    public MbBaseResponse updateLimit(MbChangeLimitForm mbChangeLimitForm, Long id) {
        return getMbBaseResponse(id, mbChangeLimitForm, HttpMethod.PUT);
    }

    @Override
    public MbBaseResponse checkLoanLimit(Long id, MbChangeLimitForm mbChangeLimitForm) {
        return getMbBaseResponse(id, mbChangeLimitForm, HttpMethod.POST);
    }

    private MbBaseResponse getMbBaseResponse(Long id, MbChangeLimitForm mbChangeLimitForm, HttpMethod httpMethod) {
        Loan loanResult = loanRepository.findById(id).orElseThrow(() -> new MbNotFoundException("error.msg.loan.notfound"));

        if ((mbChangeLimitForm.getChangeAmount()).compareTo(loanResult.getLoanAmount()) <= 0) {
            return sendToReduceLimitApi(mbChangeLimitForm, httpMethod);
        } else {
            return sendToIncreaseLimitApi(mbChangeLimitForm, httpMethod);
        }

    }

    private MbBaseResponse sendToIncreaseLimitApi(MbChangeLimitForm mbChangeLimitForm, HttpMethod httpMethod) {
        String url = mbStorageProperties.getIncreaselimit();
        MbIncreaLimitForm mbIncreaLimitForm = ObjectMapperUtils.map(mbChangeLimitForm, MbIncreaLimitForm.class);
        mbIncreaLimitForm.setIncreaseAmount(mbChangeLimitForm.getChangeAmount());
        mbIncreaLimitForm.setIncreaseRequestId(getRequestIdInHeader());

        ParamsHeader paramsHeader = HttpMethod.POST.compareTo(httpMethod) == 0
                ? HeaderConstants.POST_INCREASE_LIMIT
                : HeaderConstants.PUT_INCREASE_LIMIT;

        return sendToMbAPI(mbIncreaLimitForm, httpMethod, url, MbBaseResponse.class
                , paramsHeader);
    }

    private String getRequestIdInHeader() {
        return ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest().getHeader("requestId");
    }

    private MbBaseResponse sendToReduceLimitApi(MbChangeLimitForm mbChangeLimitForm, HttpMethod httpMethod) {
        String url = mbStorageProperties.getReducelimit();
        MbReduceLimitForm formSendToMb = ObjectMapperUtils.map(mbChangeLimitForm, MbReduceLimitForm.class);
        formSendToMb.setReduceAmount(mbChangeLimitForm.getChangeAmount());
        formSendToMb.setReduceRequestId(getRequestIdInHeader());

        ParamsHeader paramsHeader = HttpMethod.POST.compareTo(httpMethod) == 0
                ? HeaderConstants.POST_REDUCE_LIMIT
                : HeaderConstants.PUT_REDUCE_LIMIT;

        return sendToMbAPI(formSendToMb, httpMethod, url, MbBaseResponse.class
                , paramsHeader);
    }

    @Override
    public MbBaseResponse checkPayLoan(MbPayLoanForm mbPayLoanForm, Long id) {
        return sendToMbAPI(mbPayLoanForm, HttpMethod.POST, mbStorageProperties.getPayloan()
                , MbBaseResponse.class, HeaderConstants.POST_PAY_LOAN);
    }

    @Override
    public MbBaseResponse confirmPayLoan(MbPayLoanForm mbPayLoanForm, Long id) {
        return sendToMbAPI(mbPayLoanForm, HttpMethod.PUT, mbStorageProperties.getPayloan()
                , MbBaseResponse.class, HeaderConstants.PUT_PAY_LOAN);
    }

    @Override
    public MbBaseResponse checkFinalLoan(MbFinalLoanForm mbFinalLoanForm, Long id) {
        return sendToMbAPI(mbFinalLoanForm, HttpMethod.POST, mbStorageProperties.getPayloan()
                , MbBaseResponse.class, HeaderConstants.POST_PAY_LOAN);
    }

    @Override
    public MbBaseResponse confirmFinalLoan(MbFinalLoanForm mbFinalLoanForm, Long id) {
        return sendToMbAPI(mbFinalLoanForm, HttpMethod.PUT, mbStorageProperties.getPayloan()
                , MbBaseResponse.class, HeaderConstants.PUT_PAY_LOAN);
    }

    @Override
    public MbLoanLimitDTO getLoanLimitInMB(MbGetLimitInMBForm mbGetLimitInMBForm) {
        return sendToMbAPI(mbGetLimitInMBForm, HttpMethod.GET
                , mbStorageProperties.getCheckloan()
                , MbLoanLimitDTO.class, HeaderConstants.POST_CHECK_LOAN);
    }

    private Customer getCustomerByMsisdn(String phoneNumber) {
        return customerRepository.findByMsisdn(phoneNumber).orElseThrow(
                () -> new MbNotFoundException("error.msg.customer.not.found"));
    }

}
