FROM openjdk:8-jdk-slim
RUN apt-get update && apt-get install -y maven
ADD pom.xml /app/
WORKDIR /app
RUN mvn compile ; exit 0
ADD . /app
RUN mvn package -DskipTests

FROM openjdk:8-jre-slim
COPY --from=0 /app/target/*.jar /app/
CMD java -jar /app/*.jar