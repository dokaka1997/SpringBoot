//package com.viettel.arpu.controller.loan;
//
//import com.viettel.arpu.constant.AppConstants;
//import com.viettel.arpu.constant.ErrorConstant;
//import com.viettel.arpu.exception.LoanHasBeenApprovedException;
//import com.viettel.arpu.exception.LoanNotFoundException;
//import com.viettel.arpu.locale.Translator;
//import com.viettel.arpu.model.dto.LoanDetailDTO;
//import com.viettel.arpu.model.dto.LoanInfoDTO;
//import com.viettel.arpu.model.entity.*;
//import com.viettel.arpu.model.request.LoanApprovalForm;
//import com.viettel.arpu.model.response.BaseResponse;
//import com.viettel.arpu.repository.CodeCodeRepository;
//import com.viettel.arpu.repository.CustomerRepository;
//import com.viettel.arpu.repository.LoanRepository;
//import com.viettel.arpu.repository.ReferenceRepository;
//import com.viettel.arpu.service.loan.LoanService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.math.BigDecimal;
//import java.util.HashSet;
//import java.util.Objects;
//import java.util.Set;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class LoanDetailControllerTest {
//
//    @Autowired
//    LoanDetailController loanDetailController;
//    @Autowired
//    LoanService loanService;
//    @MockBean
//    CustomerRepository customerRepository;
//    @MockBean
//    LoanRepository loanRepository;
//    @MockBean
//    ReferenceRepository referenceRepository;
//    @MockBean
//    CodeCodeRepository codeCodeRepository;
//
//    @org.junit.Test
//    public void init() {
//
//    }
//
//    @Test
//    @DisplayName("when call api get loan detail success")
//    void getLoanDetailSuccess() {
//        Loan loan = initData();
//        Mockito.when(loanRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(loan));
//        ResponseEntity<BaseResponse<LoanDetailDTO>> result = loanDetailController.getDetail(1L);
//        Assertions.assertEquals(Objects.requireNonNull(result.getBody()).getCode(), AppConstants.SUCCESS.getCode());
//        Assertions.assertEquals(result.getBody().getMessage(), Translator.toLocale(AppConstants.SUCCESS.getMessage()));
//    }
//
//    @Test
//    @DisplayName("when loan id not found")
//    void getLoanDetailFalse() {
//        Mockito.when(loanRepository.findById(1L)).thenReturn(java.util.Optional.empty());
//        try {
//            loanDetailController.getDetail(1L);
//        } catch (LoanNotFoundException exception) {
//            Assertions.assertEquals(exception.getMessage(), Translator.toLocale(AppConstants.LOAN_NOT_FOUND.getMessage()));
//        }
//    }
//
//
//    @Test
//    @DisplayName("when approval loan with code id is PDS_03")
//    void approvalLoan() {
//        Loan loan = initData();
//
//        CodeCode PDS_03 = new CodeCode();
//        PDS_03.setId("PDS_03");
//        PDS_03.setCodeTypeName("Trạng thái phê duyệt");
//        PDS_03.setCodeType("2");
//        PDS_03.setCodeName("Đã phê duyệt");
//
//        CodeCode KVS_02 = new CodeCode();
//        KVS_02.setId("KVS_02");
//        KVS_02.setCodeTypeName("Trạng thái khoản vay");
//        KVS_02.setCodeType("3");
//        KVS_02.setCodeName("Đang vay");
//
//        loan.setLoanStatus(PDS_03);
//        loan.setReasonRejection("test");
//
//        Mockito.when(loanRepository.findById(1L)).thenReturn(java.util.Optional.of(loan));
//
//        Mockito.when(codeCodeRepository.findById("PDS_03")).thenReturn(java.util.Optional.of(PDS_03));
//
//        Mockito.when(codeCodeRepository.findById("KVS_02")).thenReturn(java.util.Optional.of(KVS_02));
//
//        Mockito.when(loanRepository.save(loan)).thenReturn(loan);
//
//        LoanApprovalForm loanApprovalForm = new LoanApprovalForm();
//        loanApprovalForm.setCodeId("PDS_03");
//        loanApprovalForm.setLoanId(1L);
//
//        ResponseEntity<BaseResponse<LoanInfoDTO>> resutl =
//                loanDetailController.updateLoanInfo(loanApprovalForm);
//
//        LoanInfoDTO loanInfoDTO = Objects.requireNonNull(resutl.getBody()).getData();
//
//        Assertions.assertEquals(loanInfoDTO.getApprovalStatus(), KVS_02.getCodeName());
//        Assertions.assertEquals(resutl.getBody().getMessage(), Translator.toLocale(AppConstants.SUCCESS.getMessage()));
//        Assertions.assertEquals(resutl.getBody().getCode(), AppConstants.SUCCESS.getCode());
//
//    }
//
//
//    @Test
//    @DisplayName("when reject loan with code id is KVS_05")
//    void rejectLoanWithCodeKVS_05() {
//        Loan loan = initData();
//
//        CodeCode KVS_05 = new CodeCode();
//        KVS_05.setId("KVS_05");
//        KVS_05.setCodeTypeName("Trạng thái khoản vay");
//        KVS_05.setCodeType("3");
//        KVS_05.setCodeName("Từ chối khoản vay");
//
//        CodeCode PDS_04 = new CodeCode();
//        PDS_04.setId("PDS_04");
//        PDS_04.setCodeTypeName("Trạng thái phê duyệt");
//        PDS_04.setCodeType("2");
//        PDS_04.setCodeName("Từ chối phê duyệt");
//
//        loan.setLoanStatus(KVS_05);
//        loan.setReasonRejection("test");
//
//        Mockito.when(loanRepository.findById(1L)).thenReturn(java.util.Optional.of(loan));
//
//        Mockito.when(codeCodeRepository.findById("KVS_05")).thenReturn(java.util.Optional.of(KVS_05));
//
//        Mockito.when(codeCodeRepository.findById("PDS_04")).thenReturn(java.util.Optional.of(PDS_04));
//
//        Mockito.when(loanRepository.save(loan)).thenReturn(loan);
//        LoanApprovalForm loanApprovalForm = new LoanApprovalForm();
//        loanApprovalForm.setCodeId("KVS_05");
//        loanApprovalForm.setLoanId(1L);
//        loanApprovalForm.setReason("Test");
//
//        ResponseEntity<BaseResponse<LoanInfoDTO>> resutl =
//                loanDetailController.updateLoanInfo(loanApprovalForm);
//
//        LoanInfoDTO loanInfoDTO = Objects.requireNonNull(resutl.getBody()).getData();
//
//        Assertions.assertEquals(loanInfoDTO.getReasonRejection(), loanApprovalForm.getReason());
//        Assertions.assertEquals(loanInfoDTO.getApprovalStatus(), PDS_04.getCodeName());
//        Assertions.assertEquals(resutl.getBody().getMessage(), Translator.toLocale(AppConstants.SUCCESS.getMessage()));
//        Assertions.assertEquals(resutl.getBody().getCode(), AppConstants.SUCCESS.getCode());
//
//    }
//
//    @Test
//    @DisplayName("when reject loan with code id is PDS_01")
//    void rejectLoanWithCodePDS_01() {
//        Loan loan = initData();
//
//        CodeCode PDS_01 = new CodeCode();
//        PDS_01.setId("PDS_01");
//        PDS_01.setCodeTypeName("Trạng thái phê duyệt");
//        PDS_01.setCodeType("2");
//        PDS_01.setCodeName("Chờ hoàn thiện hồ sơ");
//
//        CodeCode KVS_01 = new CodeCode();
//        KVS_01.setId("KVS_01");
//        KVS_01.setCodeTypeName("Trạng thái khoản vay");
//        KVS_01.setCodeType("3");
//        KVS_01.setCodeName("Chưa vay");
//
//        loan.setLoanStatus(PDS_01);
//        loan.setReasonRejection("test");
//
//        Mockito.when(loanRepository.findById(1L)).thenReturn(java.util.Optional.of(loan));
//
//        Mockito.when(codeCodeRepository.findById("PDS_01")).thenReturn(java.util.Optional.of(PDS_01));
//
//        Mockito.when(codeCodeRepository.findById("KVS_01")).thenReturn(java.util.Optional.of(KVS_01));
//
//        Mockito.when(loanRepository.save(loan)).thenReturn(loan);
//        LoanApprovalForm loanApprovalForm = new LoanApprovalForm();
//        loanApprovalForm.setCodeId("PDS_01");
//        loanApprovalForm.setLoanId(1L);
//        loanApprovalForm.setReason("Test");
//
//        ResponseEntity<BaseResponse<LoanInfoDTO>> resutl =
//                loanDetailController.updateLoanInfo(loanApprovalForm);
//        LoanInfoDTO loanInfoDTO = Objects.requireNonNull(resutl.getBody()).getData();
//        Assertions.assertEquals(loanInfoDTO.getReasonRejection(), loanApprovalForm.getReason());
//        Assertions.assertEquals(loanInfoDTO.getApprovalStatus(), PDS_01.getCodeName());
//        Assertions.assertEquals(resutl.getBody().getMessage(), Translator.toLocale(AppConstants.SUCCESS.getMessage()));
//        Assertions.assertEquals(resutl.getBody().getCode(), AppConstants.SUCCESS.getCode());
//    }
//
//    @Test
//    @DisplayName("when approval a loan has been approved")
//    void loanHasBeenApproved() {
//        Loan loan = initData();
//
//        CodeCode PDS_03 = new CodeCode();
//        PDS_03.setId("PDS_03");
//        PDS_03.setCodeTypeName("Trạng thái phê duyệt");
//        PDS_03.setCodeType("2");
//        PDS_03.setCodeName("Đã phê duyệt");
//
//        CodeCode KVS_02 = new CodeCode();
//        KVS_02.setId("KVS_02");
//        KVS_02.setCodeTypeName("Trạng thái khoản vay");
//        KVS_02.setCodeType("3");
//        KVS_02.setCodeName("Đang vay");
//
//        loan.setApprovalStatus(PDS_03);
//        loan.setLoanStatus(KVS_02);
//        loan.setReasonRejection("test");
//
//        Mockito.when(loanRepository.findById(1L)).thenReturn(java.util.Optional.of(loan));
//
//        Mockito.when(codeCodeRepository.findById("PDS_03")).thenReturn(java.util.Optional.of(PDS_03));
//
//        Mockito.when(codeCodeRepository.findById("KVS_02")).thenReturn(java.util.Optional.of(KVS_02));
//
//        Mockito.when(loanRepository.save(loan)).thenReturn(loan);
//        LoanApprovalForm loanApprovalForm = new LoanApprovalForm();
//        loanApprovalForm.setCodeId("PDS_03");
//        loanApprovalForm.setLoanId(1L);
//        loanApprovalForm.setReason("Test");
//        try {
//            loanDetailController.updateLoanInfo(loanApprovalForm);
//        } catch (LoanHasBeenApprovedException exception) {
//            Assertions.assertEquals(exception.getMessage(), ErrorConstant.LOAN_HAS_BEEN_APPROVED.getMessage());
//        }
//    }
//
//
//    @Test
//    @DisplayName("when reject a loan has been rejected with code KVS_05 ")
//    void loanHasBeenRejectWithCodeKVS_05() {
//        Loan loan = initData();
//
//        CodeCode KVS_05 = new CodeCode();
//        KVS_05.setId("KVS_05");
//        KVS_05.setCodeTypeName("Trạng thái khoản vay");
//        KVS_05.setCodeType("3");
//        KVS_05.setCodeName("Từ chối khoản vay");
//
//        CodeCode PDS_04 = new CodeCode();
//        PDS_04.setId("PDS_04");
//        PDS_04.setCodeTypeName("Trạng thái phê duyệt");
//        PDS_04.setCodeType("2");
//        PDS_04.setCodeName("Từ chối phê duyệt");
//
//        loan.setApprovalStatus(PDS_04);
//        loan.setLoanStatus(KVS_05);
//        loan.setReasonRejection("test");
//
//        Mockito.when(loanRepository.findById(1L)).thenReturn(java.util.Optional.of(loan));
//
//        Mockito.when(codeCodeRepository.findById("KVS_05")).thenReturn(java.util.Optional.of(KVS_05));
//
//        Mockito.when(codeCodeRepository.findById("PDS_04")).thenReturn(java.util.Optional.of(PDS_04));
//
//        Mockito.when(loanRepository.save(loan)).thenReturn(loan);
//        LoanApprovalForm loanApprovalForm = new LoanApprovalForm();
//        loanApprovalForm.setCodeId("KVS_05");
//        loanApprovalForm.setLoanId(1L);
//        loanApprovalForm.setReason("Test");
//        try {
//            loanDetailController.updateLoanInfo(loanApprovalForm);
//        } catch (LoanHasBeenApprovedException exception) {
//            Assertions.assertEquals(exception.getMessage(), ErrorConstant.LOAN_HAS_BEEN_APPROVED.getMessage());
//        }
//    }
//
//    @Test
//    @DisplayName("when reject a loan has been rejected with code PDS_01 ")
//    void loanHasBeenRejectWithCodePDS_01() {
//        Loan loan = initData();
//
//        CodeCode PDS_01 = new CodeCode();
//        PDS_01.setId("PDS_01");
//        PDS_01.setCodeTypeName("Trạng thái khoản vay");
//        PDS_01.setCodeType("3");
//        PDS_01.setCodeName("Từ chối khoản vay");
//
//        CodeCode KVS_01 = new CodeCode();
//        KVS_01.setId("KVS_01");
//        KVS_01.setCodeTypeName("Trạng thái phê duyệt");
//        KVS_01.setCodeType("2");
//        KVS_01.setCodeName("Từ chối phê duyệt");
//
//        loan.setApprovalStatus(PDS_01);
//        loan.setLoanStatus(KVS_01);
//        loan.setReasonRejection("test");
//
//        Mockito.when(loanRepository.findById(1L)).thenReturn(java.util.Optional.of(loan));
//
//        Mockito.when(codeCodeRepository.findById("KVS_01")).thenReturn(java.util.Optional.of(KVS_01));
//
//        Mockito.when(codeCodeRepository.findById("PDS_01")).thenReturn(java.util.Optional.of(PDS_01));
//
//        Mockito.when(loanRepository.save(loan)).thenReturn(loan);
//        LoanApprovalForm loanApprovalForm = new LoanApprovalForm();
//        loanApprovalForm.setCodeId("PDS_01");
//        loanApprovalForm.setLoanId(1L);
//        loanApprovalForm.setReason("Test");
//        try {
//            loanDetailController.updateLoanInfo(loanApprovalForm);
//        } catch (LoanHasBeenApprovedException exception) {
//            Assertions.assertEquals(exception.getMessage(), ErrorConstant.LOAN_HAS_BEEN_APPROVED.getMessage());
//        }
//    }
//
//    public Loan initData() {
//        Loan loan = new Loan();
//        CodeCode codeCode = new CodeCode();
//        Customer customer = new Customer();
//        Address address = new Address();
//        Set<CustomerRef> customerRefSet = new HashSet<>();
//        Reference reference = new Reference();
//        Interest interest = new Interest();
//        CustomerRef customerRef = new CustomerRef();
//
//        // init data code
//        codeCode.setId("MQHS_01");
//        codeCode.setCodeName("Bố");
//        codeCode.setCodeType("4");
//        codeCode.setCodeTypeName("Mối quan hệ");
//
//        // init data address
//        address.setAddressDetail("To 1");
//        address.setVillage("La Khe");
//        address.setDistrict("Ha Dong");
//        address.setProvince("Ha Noi");
//
//        // init data customerRef
//        customerRef.setRelationship(codeCode);
//        customerRef.setCustomer(customer);
//        customerRef.setRelationship(codeCode);
//        customerRef.setReference(reference);
//
//        customerRefSet.add(customerRef);
//
//        // init data customer
//        customer.setAddress(address);
//        customer.setCustomerRef(customerRefSet);
//
//        // init data loan
//        loan.setCustomer(customer);
//        loan.setLoanStatus(codeCode);
//        loan.setApprovalStatus(codeCode);
//        loan.setInterest(interest);
//        loan.setArpuLatestThreeMonths(new BigDecimal("10000"));
//        loan.setFee(new BigDecimal("10000"));
//        loan.setContractLink("google.com");
//        loan.setLoanAccount("DoDV");
//        loan.setAmountSpent(new BigDecimal("10000"));
//        loan.setLimitRemaining(new BigDecimal("10000"));
//        loan.setProfitAmount(new BigDecimal("10000"));
//        loan.setAmountPay(new BigDecimal("10000"));
//
//        return loan;
//    }
//}
