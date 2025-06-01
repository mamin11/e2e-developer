package com.uk.mamin11.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ServiceOne {
    public String getServiceOneMessage() {
        log.info("Service One is processing the request");
        return "Service One Message";
    }
}
