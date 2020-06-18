package com.viettel.arpu.controller.loan;

import com.viettel.arpu.model.dto.LoanDTO;
import com.viettel.arpu.model.request.SearchLoanForm;
import com.viettel.arpu.service.loan.LoanService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author User
 * @Since 5/22/2020
 */
class LoanControllerTest {

    @InjectMocks
    private LoanController loanController;

    @Mock
    private LoanService loanService;

    Pageable pageable;
    LoanDTO loanDTO;
    Page page;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        loanDTO = new LoanDTO();
        List<LoanDTO> listResult = new ArrayList<>();
        pageable = PageRequest.of(0,3);
        page = new PageImpl<>(listResult, pageable, 3);
    }

    @Test
    @DisplayName("when call api search all loan then return data")
    void searchAllLoanReturnData(){
        SearchLoanForm searchLoanForm = new SearchLoanForm();
        searchLoanForm.setApproveStatus("PDS_02");
        searchLoanForm.setFromDate(LocalDate.now());
        searchLoanForm.setToDate(LocalDate.now());
        searchLoanForm.setPhoneNumber("0356447841");
        searchLoanForm.setLoanStatus("KVS_02");
        Mockito.when(loanService.searchLoanByForm(searchLoanForm, pageable))
                .thenReturn(page);
        Assertions.assertEquals(3,
                loanController.searchLoan(searchLoanForm, pageable)
                        .getBody().getData().getTotalElements(),"should return data");
    }
}
