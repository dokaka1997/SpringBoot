package com.viettel.arpu.service.customer;

import com.viettel.arpu.model.entity.Customer;
import com.viettel.arpu.model.request.CustomerSearchForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author trungnb3
 * @Date :5/21/2020, Thu
 */
public interface CustomerService {

    Page<Customer> all(CustomerSearchForm customerRqModel, Pageable pageable);

    Page<Customer> active(CustomerSearchForm customerRqModel, Long latestVersion, Pageable pageable);

    Page<Customer> inactive(CustomerSearchForm customerRqModel, Long latestVersion, Pageable pageable);

    void lock(Long id);

    void unlock(Long id);

}
