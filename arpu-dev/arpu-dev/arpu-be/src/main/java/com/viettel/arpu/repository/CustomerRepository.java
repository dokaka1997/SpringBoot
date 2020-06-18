package com.viettel.arpu.repository;

import com.viettel.arpu.model.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * DAO lấy thông tin về khách hàng
 *
 * @author trungnb3
 * @Date :5/21/2020, Thu
 */
@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>
        , JpaSpecificationExecutor<Customer> {

    /**
     * Tìm tất cả các customer theo điều kiện cho trước
     * @param spec Điều kiện lọc
     * @param pageable phân trang
     * @return Danh sách khách hàng có phân trang
     */
    Page<Customer> findAll(Specification<Customer> spec, Pageable pageable);

    /**
     * Tìm customer theo số phone
     * @param phone số phone
     * @return Customer nếu có
     */
    Optional<Customer> findByMsisdn(String phone);

    /**
     * Kiểm tra điều khóa customer:
     * khoản vay ở trạng thái chưa vay, đã tất toán và từ chối khoản vay thì có thể khóa.
     * @param id customer Id
     * @return Customer có thể khóa
     */
    @Query("select c from Customer c join c.loans l where c.id =:id and (l.loanStatus.id = 'KVS_04' or l.loanStatus.id = 'KVS_01' or l.loanStatus.id = 'KVS_05')")
    Customer customerValid2Lock(@Param("id") Long id);

    /**
     * Kiểm tra xem customer có khoản vay nào hay chưa
     * @param id customer Id
     * @return Customer không có khoản vay
     */
    @Query("select c from Customer c where c.id=:id and c.loans is empty")
    Customer customerHasNoLoan(@Param("id") Long id);
}
