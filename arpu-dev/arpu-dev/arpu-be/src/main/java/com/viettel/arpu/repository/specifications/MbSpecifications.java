package com.viettel.arpu.repository.specifications;

import com.viettel.arpu.model.entity.Customer;
import com.viettel.arpu.model.entity.Customer_;
import com.viettel.arpu.model.entity.Loan;
import com.viettel.arpu.model.entity.Loan_;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

/**
 * @Author VuHQ
 * @Since 5/29/2020
 */
public class MbSpecifications {
    public static Specification<Loan>  hasPhoneNumber(String phoneNumber) {
        return StringUtils.isEmpty(phoneNumber) ? null : (root, query, criteriaBuilder) ->{
            Join<Loan, Customer> itemNode = root.join(Loan_.customer);
            query.orderBy(criteriaBuilder.desc(root.get(Loan_.CREATED_DATE)));
            query.distinct(true);
            return criteriaBuilder.equal(itemNode.get(Customer_.MSISDN), phoneNumber.trim());
        };
    }

}
