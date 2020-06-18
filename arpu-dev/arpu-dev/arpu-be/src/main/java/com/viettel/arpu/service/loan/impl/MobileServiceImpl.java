package com.viettel.arpu.service.loan.impl;

import com.viettel.arpu.constant.CommonConstant;
import com.viettel.arpu.constant.enums.ApprovalStatus;
import com.viettel.arpu.constant.enums.LoanStatus;
import com.viettel.arpu.constant.enums.PayType;
import com.viettel.arpu.exception.MbNotFoundException;
import com.viettel.arpu.locale.Translator;
import com.viettel.arpu.model.dto.CustomerDTO;
import com.viettel.arpu.model.dto.LoanDTO;
import com.viettel.arpu.model.dto.ReferenceDTO;
import com.viettel.arpu.model.dto.mb.MbCustomerDTO;
import com.viettel.arpu.model.dto.mb.MbExistPhoneNumberDTO;
import com.viettel.arpu.model.dto.mb.MbListLoanDTO;
import com.viettel.arpu.model.dto.mb.MbLoanLimitDTO;
import com.viettel.arpu.model.entity.*;
import com.viettel.arpu.model.request.CustomerSearchForm;
import com.viettel.arpu.model.request.mb.MbCheckCustomerForm;
import com.viettel.arpu.model.request.mb.MbLoanRegistrationForm;
import com.viettel.arpu.repository.*;
import com.viettel.arpu.repository.specifications.CustomerSpecifications;
import com.viettel.arpu.repository.specifications.MbSpecifications;
import com.viettel.arpu.service.loan.MobileService;
import com.viettel.arpu.utils.ObjectMapperUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author VuHQ
 * @Since 6/5/2020
 */
@Service
public class MobileServiceImpl implements MobileService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VersionRepository versionRepository;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    CodeCodeRepository codeCodeRepository;

    @Autowired
    InterestRepository interestRepository;

    @Autowired
    ReferenceRepository referenceRepository;

    @Autowired
    Environment env;

    @Override
    public MbExistPhoneNumberDTO isExistCustomer(MbCheckCustomerForm mbCheckCustomerForm) {
        Optional<Customer> result = customerRepository.findOne(CustomerSpecifications
                .spec()
                .msisdn(mbCheckCustomerForm.getPhoneNumber())
                .active(CommonConstant.BATCH_WHITELIST_ID)
                .lock(CustomerSearchForm.LOCK_REQUEST.UNLOCK)
                .build());
        return new MbExistPhoneNumberDTO(result.isPresent());
    }

    @Override
    public MbCustomerDTO getCustomer(MbCheckCustomerForm mbCheckCustomerForm) {
        Customer customer = getCustomerByMsisdn(mbCheckCustomerForm.getPhoneNumber());
        List<ReferenceDTO> referenceDTOs = new ArrayList<>();
        ReferenceDTO referenceDTO;
        MbCustomerDTO mbCustomerDTO = new MbCustomerDTO();

        mbCustomerDTO.setCustomerDTO(toMbCustomerDTO(customer));

        for (CustomerRef customerRefElement: customer.getCustomerRef()) {
            referenceDTO = ObjectMapperUtils.map(customerRefElement.getReference(), ReferenceDTO.class);
            referenceDTO.setRelationship(Optional.ofNullable(customerRefElement.getRelationship())
                    .flatMap(codeCode -> Optional.ofNullable(codeCode.getCodeName()))
                    .orElseThrow(() -> new MbNotFoundException("error.msg.loan.status.notfound")));
            referenceDTOs.add(referenceDTO);
        }

        mbCustomerDTO.setListReference(referenceDTOs);
         return mbCustomerDTO;
    }

    @Override
    public MbListLoanDTO getLoansByPhone(MbCheckCustomerForm mbCheckCustomerForm) {
        List<Loan> results = loanRepository.findAll(MbSpecifications.hasPhoneNumber(mbCheckCustomerForm.getPhoneNumber()));
        MbListLoanDTO mbListLoanDTO = new MbListLoanDTO();

        if (CollectionUtils.isEmpty(results)) {
            return mbListLoanDTO;
        }

        Loan lastElement = results.get(0);
        mbListLoanDTO.setTotalElement(String.valueOf(results.size()));
        mbListLoanDTO.setLoans(results.stream().map(Loan::toLoanDTO).collect(Collectors.toList()));
        mbListLoanDTO.setLastStatus(Optional.ofNullable(lastElement.getLoanStatus())
                .flatMap(codeCode -> Optional.of(codeCode.getId()))
                .orElseThrow(() -> new MbNotFoundException("error.msg.loan.status.notfound")));
        return mbListLoanDTO;
    }

    @Override
    public MbLoanLimitDTO getLoanLimit(Long id) {

        Loan loanResult = loanRepository.findById(id).orElseThrow(() -> new MbNotFoundException("error.msg.loan.limit.notfound"));

        return MbLoanLimitDTO.builder().maxAmount(Optional.ofNullable(loanResult.getMaximumLimit())
                .map(bigDecimal -> String.valueOf(bigDecimal.intValue()))
                .orElse(CommonConstant.DEFAULT_LIMIT))
                .minAmount(Optional.ofNullable(loanResult.getMaximumLimit())
                        .map(bigDecimal -> String.valueOf(bigDecimal.intValue()))
                        .orElse(CommonConstant.DEFAULT_LIMIT))
                .currentLimit(Optional.ofNullable(loanResult.getMaximumLimit())
                        .map(bigDecimal -> String.valueOf(bigDecimal.intValue()))
                        .orElse(CommonConstant.DEFAULT_LIMIT)).build();
    }

    @Override
    @Transactional
    public LoanDTO saveLoan(MbLoanRegistrationForm mbLoanRegistrationForm) {
        return loanRepository.save(convertFormToEntity(mbLoanRegistrationForm)).toLoanDTO();
    }

    private Loan convertFormToEntity(MbLoanRegistrationForm mbLoanRegistrationForm) {
        Loan loan = new Loan();
        //TODO lấy thông tin khoản vay từ MB và lưu vào db
        Customer customer = getCustomerByMsisdn(mbLoanRegistrationForm.getSourceMobile());

        Set<CustomerRef> customerRefs = Optional.ofNullable(customer.getCustomerRef()).orElseGet(HashSet::new);

        CustomerRef customerRef = getCustomerRef(mbLoanRegistrationForm, customer
                , getReference(mbLoanRegistrationForm));
        customerRefs.add(customerRef);
        customer.setCustomerRef(customerRefs);

        loan.setCustomerRef(customerRef);

        customer.setAddress(getAddress(mbLoanRegistrationForm, customer.getAddress()));

        loan.setCustomer(customer);
        loan.setLoanAmount(mbLoanRegistrationForm.getAmount());
        loan.setFee(mbLoanRegistrationForm.getFee());

        loan.setInterest(getInterest(mbLoanRegistrationForm.getTerm()));
        loan.setMaximumLimit(customer.getLoanMaximum());
        loan.setMinimumLimit(customer.getLoanMinimum());
        loan.setArpuLatestThreeMonths(customer.getArpuLatestThreeMonths());
        loan.setRepaymentForm(Translator
                .toLocale(PayType.valueOf(mbLoanRegistrationForm.getPayType()).name()));

        codeCodeRepository.findById(LoanStatus.NOT_BORROWED.getStatus()).ifPresent(loan::setLoanStatus);
        loan.setApprovalStatus(getStatusByIdentifier(env.getProperty("time.identifier")));
        loan.setIsAutomaticPayment(mbLoanRegistrationForm.getIsAutomaticPayment());
        return loan;
    }

    private Interest getInterest(String term) {
        return interestRepository.findByLoanTerm(term)
                    .orElseThrow(() -> new MbNotFoundException("error.msg.interest.not.found"));
    }

    private Address getAddress(MbLoanRegistrationForm mbLoanRegistrationForm, Address addressOfCustomer) {
        Address address = Optional.ofNullable(addressOfCustomer).orElseGet(Address::new);
        address.setVillage(mbLoanRegistrationForm.getVillage());
        address.setDistrict(mbLoanRegistrationForm.getDistrict());
        address.setProvince(mbLoanRegistrationForm.getProvince());
        address.setAddressDetail(mbLoanRegistrationForm.getAddressDetail());
        return address;
    }

    private CustomerRef getCustomerRef(MbLoanRegistrationForm mbLoanRegistrationForm, Customer customer, Reference reference) {
        CustomerRef customerRef = new CustomerRef();
        codeCodeRepository.findById(mbLoanRegistrationForm.getReferencerType())
                .ifPresent(customerRef::setRelationship);

        customerRef.setReference(reference);
        customerRef.setCustomer(customer);
        return customerRef;
    }

    private Reference getReference(MbLoanRegistrationForm mbLoanRegistrationForm) {
        Reference reference = referenceRepository.findByMsisdn(mbLoanRegistrationForm.getReferencerMobile())
                .orElseGet(Reference::new);
        reference.setFullName(mbLoanRegistrationForm.getReferencerEmail());
        reference.setEmail(mbLoanRegistrationForm.getReferencerEmail());
        reference.setMsisdn(mbLoanRegistrationForm.getReferencerMobile());
        return reference;
    }

    private CodeCode getStatusByIdentifier(String property) {
        Date identifierDate = com.viettel.arpu.utils.DateUtils.convertStringToDate(property);
        String status = !DateUtils.isSameDay(identifierDate, new Date()) && (new Date().before(identifierDate)) ?
                ApprovalStatus.WAIT_FINALIZING_CASE.getStatus() : ApprovalStatus.WAIT_MB_APPROVAL.getStatus();

        return codeCodeRepository.findById(status)
                .orElseThrow(() -> new MbNotFoundException("error.msg.status.not.found"));
    }

    private Customer getCustomerByMsisdn(String phoneNumber) {
        return customerRepository.findByMsisdn(phoneNumber).orElseThrow(
                () -> new MbNotFoundException("error.msg.customer.not.found"));
    }

    private CustomerDTO toMbCustomerDTO(Customer customer) {
        CustomerDTO mbCustomerDTO = ObjectMapperUtils.map(customer, CustomerDTO.class);
        mbCustomerDTO.setGender(Translator.toLocale(customer.getGender().toString()));
        Address address = customer.getAddress();
        if (address != null) {
            BeanUtils.copyProperties(address, mbCustomerDTO);
        }
        return mbCustomerDTO;
    }
}
