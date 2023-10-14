FROM registry.access.redhat.com/ubi9/openjdk-17-runtime@sha256:5ce6423557629f5ecca005c2084b4afa8629ea87c93a7bdc530b4a650e48b436
VOLUME /tmp
COPY build/service-a-v2-1.0.0-SNAPSHOT-runner.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]