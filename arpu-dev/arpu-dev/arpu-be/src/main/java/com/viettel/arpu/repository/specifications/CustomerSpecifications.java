package com.viettel.arpu.repository.specifications;

import com.viettel.arpu.model.entity.Customer;
import com.viettel.arpu.model.entity.Customer_;
import com.viettel.arpu.model.request.CustomerSearchForm;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * @author trungnb3
 * @Date :6/9/2020, Tue
 */
public class CustomerSpecifications {
    private final List<Specification<Customer>> specifications = new ArrayList<>();

    public static CustomerSpecifications spec() {
        return new CustomerSpecifications();
    }

    public CustomerSpecifications msisdn(String msisdn) {
        if (!Strings.isEmpty(msisdn)) {
            specifications.add((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Customer_.msisdn), msisdn.trim()));
        }
        return this;
    }

    public CustomerSpecifications active(Long versionLatest) {
        specifications.add((root, criteriaQuery, criteriaBuilder) ->criteriaBuilder.equal(root.get(Customer_.version), versionLatest));
        return this;
    }

    public CustomerSpecifications inactive(Long versionLatest) {
        specifications.add((root, criteriaQuery, criteriaBuilder) ->criteriaBuilder.lessThan(root.get(Customer_.version), versionLatest));
        return this;
    }

    public CustomerSpecifications lock(CustomerSearchForm.LOCK_REQUEST lockRequest) {
        if (lockRequest != CustomerSearchForm.LOCK_REQUEST.ALL) {
            specifications.add((root, criteriaQuery, criteriaBuilder) ->
                    lockRequest == CustomerSearchForm.LOCK_REQUEST.LOCK ?
                            criteriaBuilder.equal(root.get(Customer_.lockStatus), Customer.LOCK_STATUS.LOCK)
                            : criteriaBuilder.or(criteriaBuilder.isNull(root.get(Customer_.lockStatus)),
                                criteriaBuilder.equal(root.get(Customer_.lockStatus), Customer.LOCK_STATUS.UNLOCK)));
        }
        return this;
    }

    public Specification<Customer> build() {
        return specifications.stream().reduce(allSpec(), Specification::and);
    }

    /**
     * @description khi không thỏa mãn đầu vào thì trả về search tất cả
     * @return specification
     */
    private Specification<Customer> allSpec() {
        return (root, cq, cb) -> cb.equal(cb.literal(1), 1);
    }

}
