package com.viettel.arpu.service.sftp.whitelist;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("endOfWhitelistProcessor")
@Slf4j
public class EndOfWhitelistProcessor {

    @Transactional
    public void process() {
        log.info("----------------------End Of Processor------------------------");
    }
}
