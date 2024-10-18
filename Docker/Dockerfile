FROM eclipse-temurin:21-alpine
VOLUME /tmp
EXPOSE 80

ARG JAR_FILE=./locamailapi.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]