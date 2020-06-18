package com.viettel.arpu.model.request;

import com.viettel.arpu.model.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

/**
 * @author trungnb3
 * @Date :5/26/2020, Tue
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSearchForm {
    public enum LOCK_REQUEST {
        ALL, LOCK, UNLOCK;

        public static LOCK_REQUEST of(String name) {
            for(LOCK_REQUEST prop : values()){
                if(prop.name().equals(name)){
                    return prop;
                }
            }
            return ALL;
        }
    }


    @Size(max = 12)
    private String msisdn;

    @Size(min = 1, max = 30)
    private String lockStatus;

    @NotBlank
    @Size(min = 1, max = 30)
    private String activeStatus;

    public LOCK_REQUEST lockRequest() {
        return Optional.ofNullable(lockStatus).map(s -> LOCK_REQUEST.of(lockStatus.toUpperCase())).orElse(LOCK_REQUEST.ALL);
    }
}
