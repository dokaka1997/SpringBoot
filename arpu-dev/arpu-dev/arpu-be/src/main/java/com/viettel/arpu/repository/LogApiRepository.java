package com.viettel.arpu.repository;

import com.viettel.arpu.model.entity.LogApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author trungnb3
 * @Date :5/29/2020, Fri
 */
@Repository
public interface LogApiRepository extends CrudRepository<LogApi, String> {
}
