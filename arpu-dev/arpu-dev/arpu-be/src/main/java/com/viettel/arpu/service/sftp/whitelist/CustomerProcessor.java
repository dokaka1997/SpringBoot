package com.viettel.arpu.service.sftp.whitelist;

import com.viettel.arpu.model.entity.Customer;
import com.viettel.arpu.model.entity.VersionId;
import com.viettel.arpu.repository.CustomerRepository;
import com.viettel.arpu.utils.SyncStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component("customerProcessor")
@Slf4j
public class CustomerProcessor implements Processor {
    CustomerRepository customerRepository;

    @Autowired
    private EndOfWhitelistProcessor endOfWhitelistProcessor;

    @Autowired
    public CustomerProcessor(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void process(Exchange exchange) throws IOException {
        VersionId versionId = (VersionId) exchange.getMessage().getHeader("Version");
        List<Customer> lstCustomer = (List<Customer>) exchange.getMessage().getBody();
        log.info("Result list customer: {}", lstCustomer);
        lstCustomer.forEach(customer -> {
            try {
                log.info("=========================Sync one Customer=============================");
                if (!Objects.equals(customer.getMsisdn(), null)) {
                    Optional<Customer> optCustomer = customerRepository.findByMsisdn(customer.getMsisdn());
                    if (optCustomer.isPresent()) {
                        log.info("Found customer with phoneNumber({})", customer.getMsisdn());
                        customer.setId(optCustomer.get().getId());
                        customer.setSyncStatus(SyncStatus.UPDATE);
                    } else {
                        log.info("New Customer");
                        customer.setSyncStatus(SyncStatus.INSERT);
                    }
                    customer.setVersion(versionId.getVersion());
                    customerRepository.save(customer);
                    endOfWhitelistProcessor.process();
                }
            } catch (Exception ex) {
                log.error("Processor - Error save Customer: " + ex.getStackTrace());
            }
        });
    }
}

