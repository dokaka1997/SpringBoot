//package com.viettel.arpu.controller.customer;
//
//
//import com.viettel.arpu.constant.CommonCode;
//import com.viettel.arpu.constant.CommonConstant;
//import com.viettel.arpu.constant.enums.Gender;
//import com.viettel.arpu.model.dto.IBaseDTO;
//import com.viettel.arpu.model.entity.Address;
//import com.viettel.arpu.model.entity.CodeCode;
//import com.viettel.arpu.model.entity.Customer;
//import com.viettel.arpu.model.entity.Customer_;
//import com.viettel.arpu.model.request.CustomerSearchForm;
//import com.viettel.arpu.model.response.BaseResponse;
//import com.viettel.arpu.model.response.CustomerResponse;
//import com.viettel.arpu.repository.CustomerRepository;
//import com.viettel.arpu.repository.CustomerRepositoryImpl;
//import com.viettel.arpu.repository.VersionRepository;
//import com.viettel.arpu.service.customer.impl.CustomerServiceImpl;
//import com.viettel.arpu.service.customer.impl.VersionServiceImpl;
//import org.apache.logging.log4j.util.Strings;
//import org.junit.Assert;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.validation.Validation;
//import javax.validation.Validator;
//import javax.validation.ValidatorFactory;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.when;
//import static org.powermock.api.mockito.PowerMockito.mock;
//
///**
// * @author trungnb3
// * @Date :6/8/2020, Mon
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class CustomerControllerTest {
//
//    private static final String URI_GET_CUSTOMERS = "http://localhost:8084/api/customer/search";
//
//    @MockBean
//    CustomerRepository customerRepository;
//
//    @MockBean
//    CustomerRepositoryImpl customerRepositoryImpl;
//
//    @MockBean
//    VersionRepository versionRepository;
//
//    @MockBean
//    VersionServiceImpl versionService;
//
//    @Autowired
//    CustomerServiceImpl customerService;
//
//    @Autowired
//    CustomerController customerController;
//
//    private static ValidatorFactory validatorFactory;
//    private static Validator validator;
//
//    @BeforeClass
//    public static void createValidator() {
//        validatorFactory = Validation.buildDefaultValidatorFactory();
//        validator = validatorFactory.getValidator();
//    }
//
//    @Test
//    public void getCustomers_Ok() {
//        Long version = 3l;
//        List<Customer> customers = prepareCustomer(version);
//
//        Page<Customer> pageRes = new PageImpl<Customer>(customers, PageRequest.of(1, 1), customers.size());
//
//        Mockito.when(versionService.getLatestVersionForBatchId(CommonConstant.BATCH_WHITELIST_ID)).thenReturn(3l);
//
//        //CustomerRepository mock = mock(CustomerRepository.class);
//        when(customerRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(pageRes);
//
//        CustomerSearchForm customerSearchForm = new CustomerSearchForm();
//        customerSearchForm.setPageSize(1);
//        customerSearchForm.setPageNum(1);
//        customerSearchForm.setStatus("all");
//
//        List<CustomerResponse> response = customerService.getCustomers(customerSearchForm);
//        List<CustomerResponse> expect = CustomerResponse.getWhiteListResponseModels(customers, version);
//
//        Assert.assertTrue(response.equals(expect));
//    }
//
//    private List<Customer> prepareCustomer(Long version) {
//        Customer var1 = new Customer(1l);
//        var1.setVersion(version);
//        var1.setStatus(new CodeCode("KHS_01"));
//        var1.setLastModifiedDate(Instant.now());
//        Address address = new Address();
//        address.setId(1l);
//        address.setAddressDetail("ha noi");
//        var1.setAddress(address);
//        var1.setArpu(300l);
//        var1.setBatchStatus("batchStatus");
//        var1.setCustomerAccount("customerAccount");
//        var1.setDateOfBirth(LocalDate.of(2020, 1, 8));
//        var1.setGender(Gender.MALE);
//        var1.setDateOfIssue(LocalDate.of(2020, 1, 8));
//        var1.setEmail("example.com");
//        var1.setFullName("fullName");
//        var1.setIdentityType("CMTND");
//        var1.setMsisdn("12345678");
//        var1.setScoreMax(1234);
//        var1.setScoreMax(5678);
//        var1.setNationality("vietname");
//
//        Customer var2 = new Customer(2l);
//        var2.setVersion(version);
//        var2.setStatus(new CodeCode("KHS_01"));
//        var2.setLastModifiedDate(Instant.now());
//        Address address2 = new Address();
//        address.setId(1l);
//        address.setAddressDetail("ha noi");
//        var2.setAddress(address);
//        var2.setArpu(300l);
//        var2.setBatchStatus("batchStatus");
//        var2.setCustomerAccount("customerAccount");
//        var2.setDateOfBirth(LocalDate.of(2020, 1, 8));
//        var2.setGender(Gender.MALE);
//        var2.setDateOfIssue(LocalDate.of(2020, 1, 8));
//        var2.setEmail("example.com");
//        var2.setFullName("fullName");
//        var2.setIdentityType("CMTND");
//        var2.setMsisdn("12345678");
//        var2.setScoreMax(1234);
//        var2.setScoreMax(5678);
//        var2.setNationality("vietname");
//
//        return Arrays.asList(var1, var2);
//    }
//
//    @Test
//    public void lockCustomerById_Ok() {
//        Customer customer = new Customer(1l);
//        customer.setVersion(3l);
//        customer.setStatus(new CodeCode(CommonCode.LOAN_STATUS.KVS_01));
//        Optional optionalCustomer = Optional.of(customer);
//
//        when(customerRepository.save(customer)).thenReturn(customer);
//        when(customerRepository.findById(1l)).thenReturn(optionalCustomer);
//        when(customerRepositoryImpl.getLatestLoanForCustomer(customer.getId())).thenReturn(CommonCode.LOAN_STATUS.KVS_05);
//        when(versionService.getLatestVersionForBatchId(CommonConstant.BATCH_WHITELIST_ID)).thenReturn(3l);
//
//        ResponseEntity<BaseResponse<?>> response = customerController.lockWhiteListById(1l);
//
//        StringBuilder builderRes = new StringBuilder();
//        builderRes
//                .append(response.getStatusCodeValue())
//                .append(response.getBody().getRequestId())
//                .append(response.getBody().getData())
//                .append(response.getBody().getMessage())
//                .append(response.getBody().getCode());
//
//        ResponseEntity<BaseResponse<IBaseDTO>> expected = ResponseEntity.ok(new BaseResponse<>());
//        StringBuilder builderExpected = new StringBuilder();
//        builderExpected
//                .append(expected.getStatusCodeValue())
//                .append(expected.getBody().getRequestId())
//                .append(expected.getBody().getData())
//                .append(expected.getBody().getMessage())
//                .append(expected.getBody().getCode());
//
//        Assert.assertTrue(builderRes.toString().equals(builderExpected.toString()));
//    }
//
//    private Specification<Customer> getSpecificationModel(String msisdn, String searchType, Long versionLatest) {
//        Specification<Customer> specMsisdn = null;
//        if (!Strings.isEmpty(msisdn)) {
//            specMsisdn = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Customer_.msisdn), msisdn.trim());
//        }
//
//        if (Strings.isEmpty(searchType) || versionLatest == null) return null;
//
//        Specification<Customer> specLoadStatus = null;
//        switch (searchType.toLowerCase()) {
//            case "all":
//                break;
//            case "active":
//                specLoadStatus = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
//                        criteriaBuilder.equal(root.get(Customer_.version), versionLatest),
//                        criteriaBuilder.notEqual(root.get(Customer_.status), new CodeCode(CommonCode.STATUS_CUSTOMER_WHITELIST.KHOA_CHO_VAY)));
//                break;
//            case "inactive":
//                specLoadStatus = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThan(root.get(Customer_.version), versionLatest);
//                break;
//            case "lock":
//                specLoadStatus = (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Customer_.status), new CodeCode(CommonCode.STATUS_CUSTOMER_WHITELIST.KHOA_CHO_VAY));
//                break;
//            default:
//                return null;
//        }
//
//        return specMsisdn == null ? Specification.where(specLoadStatus) : Specification.where(specMsisdn).and(specLoadStatus);
//    }
//
//    private Pageable getPageRequest(Integer pageNum, Integer pageSize) {
//        if (pageNum == null || pageNum <= 0) {
//            pageNum = 0;
//        } else {
//            pageNum = pageNum - 1;
//        }
//
//        if (pageSize == null || pageSize <= 0 || pageSize > 10000) {
//            pageSize = 1;
//        }
//        return PageRequest.of(pageNum, pageSize);
//    }
//}
//
//
//
