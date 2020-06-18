package com.viettel.arpu.repository.specification;

import com.viettel.arpu.repository.specifications.LoanSpecifications;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @Author VuHQ
 * @Since 5/22/2020
 */
class LoanSpecificationsTest {

    @Nested
    class SpecificationReturnNull{
        @Test
        @DisplayName("greaterThanOrEqualToFromDate when input null return not null")
        void greaterThanOrEqualToFromDateReturnNullWhenInputNull(){
            assertNotNull(LoanSpecifications.spec().fromDate(null), "should return not null");
        }
        @Test
        @DisplayName("lessThanOrEqualToToDate when input null return null")
        void lessThanOrEqualToToDateReturnNullWhenInputNull(){
            assertNotNull(LoanSpecifications.spec().toDate(null), "should return not null");
        }
        @Test
        @DisplayName("hasPhoneNumber when input null return null")
        void hasPhoneNumberReturnNullWhenInputNull(){
            assertNotNull(LoanSpecifications.spec().phoneNumber(null), "should return not null");
        }
        @Test
        @DisplayName("hasIdCard when input null return null")
        void hasIdCardFromDateReturnNullWhenInputNull(){
            assertNotNull(LoanSpecifications.spec().idCard(null), "should return not null");
        }
        @Test
        @DisplayName("hasApproveStatus when input null return null")
        void hasApproveStatusFromDateReturnNullWhenInputNull(){
            assertNotNull(LoanSpecifications.spec().approveStatus(null), "should return not null");
        }
        @Test
        @DisplayName("hasListApproveStatus when input null return null")
        void hasListApproveStatusReturnNullWhenInputNull(){
            assertNotNull(LoanSpecifications.spec().hasListApproveStatus(null), "should return not null");
        }
    }

    @Nested
    class SpecificationReturnData{
        @Test
        @DisplayName("greaterThanOrEqualToFromDate when input not null return data")
        void greaterThanOrEqualToFromDateReturnDataWhenInputNotNull(){
            LocalDate toDate = LocalDate.of(2020,5,14);
            assertNotNull(LoanSpecifications.spec().fromDate(toDate), "should return data");
        }
        @Test
        @DisplayName("lessThanOrEqualToToDate when input not null return data")
        void lessThanOrEqualToToDateReturnDataWhenInputNotNull(){
            LocalDate toDate = LocalDate.of(2020,5,14);
            assertNotNull(LoanSpecifications.spec().toDate(toDate), "should return data");
        }
        @Test
        @DisplayName("hasPhoneNumber when input not null return data")
        void hasPhoneNumberReturnDataWhenInputNotNull(){
            assertNotNull(LoanSpecifications.spec().phoneNumber("abc"), "should return data");
        }
        @Test
        @DisplayName("hasIdCard when input not null return data")
        void hasIdCardFromDateReturnDataWhenInputNotNull(){
            assertNotNull(LoanSpecifications.spec().idCard("abc"), "should return data");
        }
        @Test
        @DisplayName("hasApproveStatus when input not null return data")
        void hasApproveStatusFromDateReturnDataWhenInputNotNull(){
            assertNotNull(LoanSpecifications.spec().approveStatus("abc"), "should return data");
        }
        @Test
        @DisplayName("hasListApproveStatus when input not null return data")
        void hasListApproveStatusReturnDataWhenInputNotNull(){
            assertNotNull(LoanSpecifications.spec().hasListApproveStatus(Arrays.asList("abc")), "should return data");
        }

        @Test
        @DisplayName("loanStatus when input not null return data")
        void loanStatusReturnDataWhenInputNotNull(){
            assertNotNull(LoanSpecifications.spec().loanStatus("abc"), "should return data");
        }

        @Test
        @DisplayName("build return data")
        void buildReturnDataWhenInputNotNull(){
            assertNotNull(LoanSpecifications.spec().build(), "should return data");
        }

        @Test
        @DisplayName("hasphonenumber when input data return data")
        void hasPhoneNumberWhenInputNotNull(){
            assertNotNull(LoanSpecifications.spec().hasPhoneNumber("123456789"), "should return data");
        }

        @Test
        @DisplayName("hasIdCard when input data return data")
        void hasIdCardWhenInputNotNull(){
            assertNotNull(LoanSpecifications.spec().hasIdCard("123456789"), "should return data");
        }

        @Test
        @DisplayName("hasApproveStatus when input data return data")
        void hasApproveStatusWhenInputNotNull(){
            assertNotNull(LoanSpecifications.spec().hasApproveStatus("PDS_01"), "should return data");
        }

        @Test
        @DisplayName("hasLoanStatus when input data return data")
        void hasLoanStatusWhenInputNotNull(){
            assertNotNull(LoanSpecifications.spec().hasLoanStatus("KVS_02"), "should return data");
        }

        @Test
        @DisplayName("hasLoanStatus when input all return data")
        void hasLoanStatusWhenInputAll(){
            assertNotNull(LoanSpecifications.spec().hasLoanStatus("All"), "should return data");
        }
    }

}
