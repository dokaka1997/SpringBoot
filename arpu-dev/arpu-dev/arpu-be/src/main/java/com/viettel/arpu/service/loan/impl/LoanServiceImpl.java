package com.viettel.arpu.service.loan.impl;

import com.viettel.arpu.config.FtpStorageProperties;
import com.viettel.arpu.constant.LoanStatus;
import com.viettel.arpu.exception.CodeNotFoundException;
import com.viettel.arpu.exception.IdFormatException;
import com.viettel.arpu.exception.LoanNotFoundException;
import com.viettel.arpu.locale.Translator;
import com.viettel.arpu.model.dto.*;
import com.viettel.arpu.model.entity.CodeCode;
import com.viettel.arpu.model.entity.Customer;
import com.viettel.arpu.model.entity.CustomerRef;
import com.viettel.arpu.model.entity.Loan;
import com.viettel.arpu.model.request.SearchApproveForm;
import com.viettel.arpu.model.request.SearchLoanForm;
import com.viettel.arpu.model.request.mb.LoanConfirmRequestForm;
import com.viettel.arpu.repository.CodeCodeRepository;
import com.viettel.arpu.repository.LoanRepository;
import com.viettel.arpu.repository.specifications.LoanSpecifications;
import com.viettel.arpu.service.loan.LoanService;
import com.viettel.arpu.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.Arrays;
import java.util.Optional;

import static com.viettel.arpu.utils.FtpUtils.uploadFile;

/**
 * @Author VuHQ
 * @Since 5/25/2020
 */
@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;
    @Autowired
    CodeCodeRepository codeCodeRepository;
    @Autowired
    FtpStorageProperties ftpStorageProperties;

    /**
     * @param searchApproveForm
     * @param pageable
     * @return page
     * @description tìm kiếm các khoản vay theo điều kiện đầu vào
     * và có trạng thái phê duyệt
     * (PDS_01: chờ hoàn thiện hồ sơ, PDS_02: chờ MB phê duyệt)
     */
    @Override
    public Page<LoanDTO> searchApprovalByForm(SearchApproveForm searchApproveForm, Pageable pageable) {

        LoanSpecifications spec = LoanSpecifications.spec()
                .fromDate(searchApproveForm.getFromDate())
                .toDate(searchApproveForm.getToDate())
                .idCard(searchApproveForm.getIdentityNumber())
                .phoneNumber(searchApproveForm.getPhoneNumber())
                .approveStatusForMB(searchApproveForm.getApproveStatus());

        return loanRepository.findAll(spec.build(), pageable).map(Loan::toLoanDTO);
    }

    /**
     * @param searchLoanForm
     * @param pageable
     * @return page
     * @description tìm kiếm các khoản vay theo điều kiện đầu vào
     */
    @Override
    public Page<LoanDTO> searchLoanByForm(SearchLoanForm searchLoanForm, Pageable pageable) {

        LoanSpecifications spec = LoanSpecifications.spec()
                .phoneNumber(searchLoanForm.getPhoneNumber())
                .idCard(searchLoanForm.getIdentityNumber())
                .fromDate(searchLoanForm.getFromDate())
                .toDate(searchLoanForm.getToDate())
                .loanStatus(searchLoanForm.getLoanStatus())
                .approveStatus(searchLoanForm.getApproveStatus());

        return loanRepository.findAll(spec.build(), pageable).map(Loan::toLoanDTO);
    }


    /**
     * Get thông tin khoản vay từ id
     * Nhận vào id của khoản vay và show ra thông tin khoản vay trong database
     *
     * @param id
     * @return LoanDetailDTO
     */
    @Override
    public LoanDetailDTO getLoanDetail(Long id) {
        return loanRepository.findById(id).map(loan -> {
            LoanDetailDTO loanDetailDTO = new LoanDetailDTO();

            CustomerDTO customerDTO = new CustomerDTO();
            if (loan.getCustomer() != null) {
                customerDTO = customer2DTO(loan.getCustomer());
            }
            loanDetailDTO.setCustomerInfo(customerDTO);

            ReferenceDTO referenceDTO = new ReferenceDTO();

            if (loan.getCustomerRef() != null) {
                referenceDTO = reference2DTO(loan.getCustomerRef());
            }
            loanDetailDTO.setReferenceInfo(referenceDTO);

            loanDetailDTO.setContractLink(loan.getContractLink());

            loanDetailDTO.setLoanInfo(loand2DTO(loan));
            return loanDetailDTO;
        }).orElseThrow(LoanNotFoundException::new);
    }


    /**
     * Maps {@loan source} to {@loanDTO destination}.
     *
     * @param loan
     * @return LoanInfoDTO
     */
    private LoanInfoDTO loand2DTO(Loan loan) {

        LoanInfoDTO loanInfoDTO;

        loanInfoDTO = ObjectMapperUtils.map(loan, LoanInfoDTO.class);

        Optional.ofNullable(loan.getInterest())
                .ifPresent(interest -> loanInfoDTO.setLoanTerm(interest.getLoanTerm()));

        Optional.ofNullable(loan.getInterest())
                .ifPresent(interest -> loanInfoDTO.setInterestRate(interest.getInterestRate()));

        Optional.ofNullable(loan.getLoanStatus())
                .ifPresent(codeCode -> loanInfoDTO.setLoanStatus(codeCode.getCodeName()));

        Optional.ofNullable(loan.getApprovalStatus())
                .ifPresent(codeCode -> loanInfoDTO.setApprovalStatus(codeCode.getCodeName()));

        return loanInfoDTO;
    }

    /**
     * Maps {@customer source} to {@customerDTO destination}.
     *
     * @param customer
     * @return CustomerDTO
     */
    private CustomerDTO customer2DTO(Customer customer) {
        CustomerDTO customerDTO = ObjectMapperUtils.map(customer, CustomerDTO.class);

        Optional.ofNullable(customer.getGender())
                .ifPresent(gender -> customerDTO.setGender(Translator.toLocale(gender.toString())));

        Optional.ofNullable(customer.getAddress())
                .ifPresent(address -> {
                    customerDTO.setAddressDetail(address.getAddressDetail());
                    customerDTO.setDistrict(address.getDistrict());
                    customerDTO.setProvince(address.getProvince());
                    customerDTO.setVillage(address.getVillage());
                });
        return customerDTO;
    }

    /**
     * Maps {@CustomerRef source} to {@ReferenceDTO destination}.
     *
     * @param customerRef
     * @return ReferenceDTO
     */
    public ReferenceDTO reference2DTO(CustomerRef customerRef) {

        ReferenceDTO referenceDTO = new ReferenceDTO();

        Optional.ofNullable(customerRef).flatMap(reference -> Optional.ofNullable(customerRef.getReference()).map(reference1 -> {
            referenceDTO.setEmail(reference.getReference().getEmail());
            referenceDTO.setRelationship(reference.getRelationship().getCodeName());
            referenceDTO.setFullName(reference.getReference().getFullName());
            referenceDTO.setMsisdn(reference.getReference().getMsisdn());
            return referenceDTO;
        })).orElse(null);
        return referenceDTO;
    }

    /**
     * Nhận vào id và file hợp đồng pdf
     * update hợp đồng cho khoản vay lên server sftp sau đó chuyển trạng thái khoản vay thành trờ MB phê duyệt.
     *
     * @param loanConfirmRequestForm
     * @return UpdateLoanDTO
     */
    @Transactional
    @Override
    public UpdateLoanDTO updateToMBApproval(LoanConfirmRequestForm loanConfirmRequestForm) throws IOException {
        long id;
        try {
            id = Long.parseLong(loanConfirmRequestForm.getId());
        } catch (Exception e) {
            throw new IdFormatException();
        }
        Loan loan = loanRepository.findById(id)
                .orElseThrow(LoanNotFoundException::new);

        String fileName = uploadFile(ftpStorageProperties, loanConfirmRequestForm.getFile());

        CodeCode codeCode = codeCodeRepository.findById(LoanStatus.PDS_02)
                .orElseThrow(CodeNotFoundException::new);

        loan.setApprovalStatus(codeCode);
        loan.setContractLink(fileName);

        loanRepository.save(loan);

        UpdateLoanDTO updateLoanDTO = new UpdateLoanDTO();
        updateLoanDTO.setFileUpload(fileName);
        updateLoanDTO.setLoanStatus(codeCode.getCodeName());

        return updateLoanDTO;
    }

}
