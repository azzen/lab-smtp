FROM maven:3-amazoncorretto-8 AS build
RUN yum install -y git && \
    mkdir mockmock && \
    git clone  https://github.com/DominiqueComte/MockMock.git /mockmock && \
    cd mockmock && \
    mvn clean package

FROM amazoncorretto:8
COPY --from=build /mockmock/target/*.jar /usr/local/lib/app.jar
EXPOSE 25000 8000
ENTRYPOINT ["java", "-jar", "/usr/local/lib/app.jar", "-p", "25000", "-h", "8000"]

