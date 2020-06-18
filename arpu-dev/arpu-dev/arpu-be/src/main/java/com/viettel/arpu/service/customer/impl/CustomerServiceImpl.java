package com.viettel.arpu.service.customer.impl;

import com.viettel.arpu.exception.CustomerNotFoundException;
import com.viettel.arpu.exception.CustomerVersionInvalidException;
import com.viettel.arpu.model.entity.CodeCode;
import com.viettel.arpu.model.entity.Customer;
import com.viettel.arpu.model.request.CustomerSearchForm;
import com.viettel.arpu.repository.CustomerRepository;
import com.viettel.arpu.repository.specifications.CustomerSpecifications;
import com.viettel.arpu.service.customer.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author trungnb3
 * @Date :5/21/2020, Thu
 */
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Page<Customer> all(CustomerSearchForm customerRequest, Pageable pageable) {
        return customerRepository.findAll(CustomerSpecifications
                .spec()
                .msisdn(customerRequest.getMsisdn())
                .lock(customerRequest.lockRequest())
                .build(), pageable);
    }

    public Page<Customer> active(CustomerSearchForm customerRequest, Long latestVersion, Pageable pageable) {
        return customerRepository.findAll(CustomerSpecifications
                .spec()
                .msisdn(customerRequest.getMsisdn())
                .lock(customerRequest.lockRequest())
                .active(latestVersion)
                .build(), pageable);
    }

    public Page<Customer> inactive(CustomerSearchForm customerRequest, Long latestVersion, Pageable pageable) {
        return customerRepository.findAll(CustomerSpecifications
                .spec()
                .msisdn(customerRequest.getMsisdn())
                .lock(customerRequest.lockRequest())
                .inactive(latestVersion)
                .build(), pageable);
    }

    /**
     * lay trang thai khoan vay cua customer theo cusId
     * trang thai khoan vay la chua tat toan | dang vay -> thong bao MSG_31
     * khoa thanh cong -> thong bao MSG_32
     *
     * @param id
     * @return
     */
    @Override
    public void lock(Long id) {
        customerRepository
                .findById(id).orElseThrow(CustomerNotFoundException::new);

        Customer customer = customerRepository.customerHasNoLoan(id);
        customer = Optional.ofNullable(customer)
                        .orElse(customerRepository.customerValid2Lock(id));

        Optional.ofNullable(customer)
                .map(c -> {
                    c.setLockStatus(Customer.LOCK_STATUS.LOCK);
                    return customerRepository.save(c);
                })
                .orElseThrow(CustomerVersionInvalidException::new);
    }

    /**
     * nếu customer có trạng thái status = Lock -> thuc hien unlock
     * @param id
     * @return
     */
    @Override
    public void unlock(Long id) {
        customerRepository
                .findById(id)
                .map(customer -> {
                    customer.setLockStatus(null);
                    return customerRepository.save(customer);
                }).orElseThrow(CustomerNotFoundException::new);
    }
}


