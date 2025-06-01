FROM eclipse-temurin:17-jdk-jammy
VOLUME /tmp

# Download OpenTelemetry Java Agent
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar /otel/opentelemetry-javaagent.jar

COPY target/e2e-developer-backend-0.0.1.jar e2e-developer-backend-0.0.1.jar

EXPOSE 8080

#ENTRYPOINT ["java","-jar","e2e-developer-backend-0.0.1.jar"]

#ENTRYPOINT [
#    "java",
#    "-javaagent:/otel/opentelemetry-javaagent.jar",
#    "-Dotel.exporter.otlp.endpoint=http://tempo.observability.svc.cluster.local:4317",
#    "-Dotel.service.name=e2e-developer-backend",
#    "-jar",
#    "e2e-developer-backend-0.0.1.jar"
#]


ENTRYPOINT ["java", "-javaagent:/otel/opentelemetry-javaagent.jar", "-Dotel.exporter.otlp.protocol=grpc", "-Dotel.exporter.otlp.endpoint=http://otel-collector.observability.svc.cluster.local:4317", "-Dotel.service.name=e2e-developer-backend", "-jar", "e2e-developer-backend-0.0.1.jar"]