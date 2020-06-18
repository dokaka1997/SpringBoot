//package loan;
//
//import com.viettel.arpu.model.dto.CustomerDTO;
//import com.viettel.arpu.model.dto.LoanDTO;
//import com.viettel.arpu.model.dto.LoanDetailDTO;
//import com.viettel.arpu.utils.ObjectMapperUtils;
//import junit.framework.TestCase;
//import org.junit.Test;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.Optional;
//
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class LoanApprovalDetailServiceImplTest extends TestCase {
//
//    @InjectMocks
//    LoanApprovalDetailServiceImpl loanApprovalDetailService;
//
//    @Mock
//    LoanInfoRepository loanInfoRepository;
//
//    @Mock
//    CustomerInfoRepository customerInfoRepository;
//
//    @Test()
//    @DisplayName("Get Loan Approval Success")
//    public void testGetInfoLoanApprovalHasValue() {
//        LoanInfo loanInfo = new LoanInfo();
//        CustomerInfo customerInfo = new CustomerInfo();
//        Optional<LoanInfo> optionalLoanInfo = Optional.of(loanInfo);
//        Optional<CustomerInfo> optionalCustomerInfo = Optional.of(customerInfo);
//        when(loanInfoRepository.findById(1)).thenReturn(optionalLoanInfo);
//        when(customerInfoRepository.findById(1)).thenReturn(optionalCustomerInfo);
//        CustomerDTO customerDTO;
//        LoanDTO loanDTO;
//        loanDTO = ObjectMapperUtils.map(loanInfo, LoanDTO.class);
//        customerDTO = ObjectMapperUtils.map(customerInfo, CustomerDTO.class);
//        Optional<LoanDetailDTO> loanDetailDTO = loanApprovalDetailService.getLoanApprovalDetai(1);
//        assertEquals(loanDTO.toString(), loanDetailDTO.get().getLoanInfo().toString());
//        assertEquals(customerDTO.toString(), loanDetailDTO.get().getCustomerInfo().toString());
//    }
//
//    @Test()
//    @DisplayName("Update Loan Submit Success")
//    public void testUpdateInfoLoanSubmitHasValue() {
//        LoanInfo loanInfo = new LoanInfo();
//        loanInfo.setStatus("status");
//        loanInfo.setId(1);
//        Optional<LoanInfo> optionalLoanInfo = Optional.of(loanInfo);
//        when(loanInfoRepository.findById(1)).thenReturn(optionalLoanInfo);
//        when(loanInfoRepository.save(loanInfo)).thenReturn(loanInfo);
//        LoanDTO loanDTO = ObjectMapperUtils.map(loanInfo, LoanDTO.class);
//        Optional<LoanDTO> optionalLoanDTO = loanApprovalDetailService.updateStatusLoanDetail(1, "status");
//        assertEquals(loanDTO.toString(), optionalLoanDTO.get().toString());
//    }
//
//
//}
