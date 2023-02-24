FROM registry.cn-shanghai.aliyuncs.com/c7n/javabase:0.10.0
RUN mkdir /skyer && chown -R www-data:www-data /skyer
COPY --chown=www-data:www-data app.jar /skyer/skyer-tags.jar
WORKDIR /skyer
USER 33
CMD java $JAVA_OPTS $SKYWALKING_OPTS -jar /skyer/skyer-tags.jar