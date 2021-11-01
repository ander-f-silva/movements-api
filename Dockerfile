FROM openjdk:11

ENV APP_HOME "/opt/app"

ADD ./target/movements-api-3.0.0.jar $APP_HOME/movements-api-3.0.0.jar

CMD	java -Xmx512M -Xms256M -jar /opt/app/movements-api-3.0.0.jar