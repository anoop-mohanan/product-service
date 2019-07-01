FROM maven:3.6.1-jdk-8
COPY . /usr/src/app
WORKDIR /usr/src/app

RUN apt-get update && apt-get install entr -y

RUN mvn clean package --batch-mode
EXPOSE 8080:8080
ENTRYPOINT ["java", "-jar", "target/product-service.jar"]