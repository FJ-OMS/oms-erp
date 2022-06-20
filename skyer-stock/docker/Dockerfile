FROM openjdk:8
RUN mkdir /skyer && chown -R www-data:www-data /skyer
COPY --chown=www-data:www-data app.jar /skyer/skyer-stock.jar
WORKDIR /skyer
USER 33
CMD java $JAVA_OPTS $SKYWALKING_OPTS -jar /skyer/skyer-stock.jar