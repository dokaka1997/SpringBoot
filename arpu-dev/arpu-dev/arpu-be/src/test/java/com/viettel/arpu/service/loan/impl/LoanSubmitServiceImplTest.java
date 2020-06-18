//package loan;
//
//import com.viettel.arpu.model.dto.CustomerDTO;
//import com.viettel.arpu.model.dto.LoanDTO;
//import com.viettel.arpu.model.dto.LoanSubmitDTO;
//import com.viettel.arpu.model.dto.ReferenceDTO;
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
//public class LoanSubmitServiceImplTest extends TestCase {
//    @InjectMocks
//    LoanSubmitServiceImpl loanSubmitService;
//    @Mock
//    LoanInfoRepository loanInfoRepository;
//
//    @Mock
//    CustomerInfoRepository customerInfoRepository;
//
//    @Mock
//    ReferencerInfoRepository referencerInfoRepository;
//
//    @Test()
//    @DisplayName("Get Loan Submit Success")
//    public void testGetInfoLoanSubmitHasValue() {
//        LoanInfo loanInfo = new LoanInfo();
//        CustomerInfo customerInfo = new CustomerInfo();
//        ReferenceInfo referenceInfo = new ReferenceInfo();
//        Optional<LoanInfo> optionalLoanInfo = Optional.of(loanInfo);
//        Optional<CustomerInfo> optionalCustomerInfo = Optional.of(customerInfo);
//        Optional<ReferenceInfo> optionalReferenceInfo = Optional.of(referenceInfo);
//        when(loanInfoRepository.findById(1)).thenReturn(optionalLoanInfo);
//        when(customerInfoRepository.findById(1)).thenReturn(optionalCustomerInfo);
//        when(referencerInfoRepository.findById(1)).thenReturn(optionalReferenceInfo);
//
//        CustomerDTO customerDTO;
//        LoanDTO loanDTO;
//        ReferenceDTO referenceDTO;
//        customerDTO = ObjectMapperUtils.map(customerInfo, CustomerDTO.class);
//        loanDTO = ObjectMapperUtils.map(loanInfo, LoanDTO.class);
//        referenceDTO = ObjectMapperUtils.map(referenceInfo, ReferenceDTO.class);
//
//        Optional<LoanSubmitDTO> loanSubmitDTO = loanSubmitService.getInfoLoanSubmit(1);
//
//        assertEquals(loanDTO.toString(), loanSubmitDTO.get().getLoanInfo().toString());
//        assertEquals(customerDTO.toString(), loanSubmitDTO.get().getCustomerInfo().toString());
//        assertEquals(referenceDTO.toString(), loanSubmitDTO.get().getReferenceInfo().toString());
//    }
//
//}
