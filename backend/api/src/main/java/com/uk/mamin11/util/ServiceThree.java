package com.uk.mamin11.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ServiceThree {
    public void getServiceThreeMessage() {
        log.info("Service three is processing the request");
    }
}
