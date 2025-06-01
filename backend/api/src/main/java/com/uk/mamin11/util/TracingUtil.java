package com.uk.mamin11.util;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.GlobalOpenTelemetry;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.context.Scope;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TracingUtil {

    public static Builder span(String spanName) {
        return new Builder(spanName);
    }

    public static class Builder {
        private final String spanName;
        private String instrumentationScope = "default-scope";
        private SpanKind spanKind = SpanKind.INTERNAL;
        private final Map<String, String> attributes = new HashMap<>();

        private Builder(String spanName) {
            this.spanName = spanName;
        }

        public Builder withScope(String instrumentationScope) {
            this.instrumentationScope = instrumentationScope;
            return this;
        }

        public Builder withSpanKind(SpanKind kind) {
            this.spanKind = kind;
            return this;
        }

        public Builder withAttribute(String key, String value) {
            attributes.put(key, value);
            return this;
        }

        public <T> T run(Supplier<T> operation) {
            Tracer tracer = GlobalOpenTelemetry.getTracer(instrumentationScope);
            Span span = tracer.spanBuilder(spanName).setSpanKind(spanKind).startSpan();

            attributes.forEach((key, value) -> span.setAttribute(key, value));
            try (Scope scope = span.makeCurrent()) {
                return operation.get();
            } catch (Exception e) {
                span.recordException(e);
                span.setStatus(StatusCode.ERROR, "Exception occurred");
                throw e;
            } finally {
                span.end();
            }
        }

        public void run(Runnable operation) {
            run(() -> {
                operation.run();
                return null;
            });
        }
    }
}


