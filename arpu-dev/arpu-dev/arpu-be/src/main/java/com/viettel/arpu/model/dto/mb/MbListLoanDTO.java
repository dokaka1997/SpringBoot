package com.viettel.arpu.model.dto.mb;

import com.viettel.arpu.constant.CommonConstant;
import com.viettel.arpu.model.dto.LoanDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MbListLoanDTO{
    private String lastStatus;
    private String totalElement = CommonConstant.DEFAULT_LIMIT;
    private List<LoanDTO> loans = Collections.EMPTY_LIST;
}
