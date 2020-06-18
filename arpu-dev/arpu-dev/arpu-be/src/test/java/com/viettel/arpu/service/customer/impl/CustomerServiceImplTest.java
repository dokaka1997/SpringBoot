//package com.viettel.arpu.service.customer.impl;
//
//import com.viettel.arpu.MainApplication;
//import com.viettel.arpu.constant.CommonCode;
//import com.viettel.arpu.locale.Translator;
//import com.viettel.arpu.model.entity.CodeCode;
//import com.viettel.arpu.model.entity.Customer;
//import com.viettel.arpu.model.response.CustomerResponse;
//import com.viettel.arpu.repository.CustomerRefRepository;
//import com.viettel.arpu.repository.CustomerRepository;
//import com.viettel.arpu.repository.LoanRepository;
//import com.viettel.arpu.repository.VersionRepository;
//import com.viettel.arpu.utils.ObjectMapperUtils;
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.support.ResourceBundleMessageSource;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.Mockito.when;
//
///**
// * @author trungnb3
// * @Date :6/4/2020, Thu
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = MainApplication.class)
//public class CustomerServiceImplTest {
//
//    @MockBean
//    CustomerRepository customerRepository;
//
//    @MockBean
//    VersionRepository versionRepository;
//
//    @MockBean
//    LoanRepository loanRepository;
//
//    @MockBean
//    CustomerRefRepository customerRefRepository;
//
//    @MockBean
//    ResourceBundleMessageSource messageSource;
//
//    @Autowired
//    VersionServiceImpl versionService;
//
//    @Autowired
//    CustomerServiceImpl customerService;
//
//    @Autowired
//    Translator translator;
//
//    @Test
//    @DisplayName("it should be ok")
//    public void getWhiteListsTest() {
//
//        Customer entity = new Customer();
//        entity.setFullName("name");
//
//        List<Customer> entities = new ArrayList<>();
//        entities.add(entity);
//        Pageable pageable = PageRequest.of(1, 1);
//
//        Page<Customer> pageResult = new PageImpl<Customer>(entities, pageable, pageable.getPageSize());
//
//        Pageable pageAble = PageRequest.of(1, 1);
//        when(customerRepository.findAll(pageAble)).thenReturn(pageResult);
//
//        CustomerResponse customerResponseModel = new CustomerResponse();
//        customerResponseModel.setMsisdn("111");
//        Page<Customer> responses = customerRepository.findAll(pageable);
//
//        Assertions.assertTrue(responses.getContent().size() == 1);
//
//
//    }
//
//
//    @Test
//    @DisplayName("it should be Customer has status equal LOCK")
//    public void lockWhiteListById() {
//        Customer customer = new Customer();
//        customer.setStatus(new CodeCode(CommonCode.STATUS_CUSTOMER_WHITELIST.DANG_HOAT_DONG));
//
//        Optional<Customer> var1 = Optional.of(customer);
//        when(customerRepository.findById(1l)).thenReturn(var1);
//        CustomerResponse var2 = ObjectMapperUtils.map(var1.get(), CustomerResponse.class);
//
//        customerService.lockCustomerById(1l);
//
//        Assertions.assertEquals(customer.getStatus().getId(), CommonCode.STATUS_CUSTOMER_WHITELIST.KHOA_CHO_VAY);
//    }
//
//    @Test
//    @DisplayName("it should be Customer has status equal LOCK")
//    public void unLockWhiteListById() {
//        Customer customer = new Customer();
//        customer.setStatus(new CodeCode(CommonCode.STATUS_CUSTOMER_WHITELIST.DANG_HOAT_DONG));
//        customer.setVersion(1l);
//
//        Optional<Customer> var1 = Optional.of(customer);
//        when(customerRepository.findById(1l)).thenReturn(var1);
//        when(versionService.getLatestVersionForBatchId(1l)).thenReturn(1l);
//        CustomerResponse var2 = ObjectMapperUtils.map(var1.get(), CustomerResponse.class);
//        Long lastestVersion = versionService.getLatestVersionForBatchId(1l);
//
//        customerService.unLockCustomerById(1l);
//
//        boolean isUnlock = customer.getStatus() == null ? true : CommonCode.STATUS_CUSTOMER_WHITELIST.DANG_HOAT_DONG.equals(customer.getStatus().getId());
//        Assertions.assertTrue(isUnlock);
//    }
//}
