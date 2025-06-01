package com.uk.mamin11.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ServiceTwo {
    @Autowired
    private ServiceThree serviceThree;

    public String getServiceTwoMessage() {
        log.info("Service Two is processing the request");
        serviceThree.getServiceThreeMessage();
        return "Service Two Message";
    }
}
