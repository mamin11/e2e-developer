package com.uk.mamin11.controller;

import com.uk.mamin11.util.ServiceOne;
import com.uk.mamin11.util.ServiceTwo;
import com.uk.mamin11.util.TracingUtil;
import io.opentelemetry.api.trace.SpanKind;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    Logger log = LoggerFactory.getLogger(TestController.class);

    private final ServiceOne serviceOne;
    private final ServiceTwo serviceTwo;

    @GetMapping("/hello")
    public String hello() {
        log.info("Hello endpoint was called");

        return TracingUtil.span("test-controller-hello-span")
                .withScope("test-controller")
                .withSpanKind(SpanKind.INTERNAL)
                .withAttribute("custom-attribute", "value")
                .run(() -> {
                    // Simulate some processing
                    try {
                        Thread.sleep(100); // Simulating delay
                        return serviceOne.getServiceOneMessage();
                    } catch (InterruptedException e) {
                        log.error("Error during processing", e);
                    }
                    return null;
                });
    }

    @GetMapping("/hello2")
    public String hello2() {
        log.info("Hello2 endpoint was called");
        return TracingUtil.span("test-controller-hello2-span")
                .withScope("test-controller")
                .withSpanKind(SpanKind.INTERNAL)
                .withAttribute("custom-attribute", "value")
                .run(() -> {
                    // Simulate some processing
                    try {
                        Thread.sleep(100); // Simulating delay
                        return serviceTwo.getServiceTwoMessage();
                    } catch (InterruptedException e) {
                        log.error("Error during processing", e);
                    }
                    return null;
                });
    }
    
}
