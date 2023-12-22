FROM openjdk:22-slim
# FROM openjdk:18-oracle

# docker pull openjdk:22-slim
# #VOLUME /tmp
# RUN mkdir /app && \
#     apk add tcpdump
    
RUN mkdir /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/isob.jar

WORKDIR /app 

CMD ["java", "-jar", "isob.jar"]
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]