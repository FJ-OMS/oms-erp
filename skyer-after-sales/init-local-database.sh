#!/usr/bin/env bash
mkdir -p target
if [ ! -f target/skyer-tool-liquibase.jar ]
then
    curl https://nexus.skyer.com.cn/repository/skyer-release/io/skyer/skyer-tool-liquibase/0.8.1.RELEASE/skyer-tool-liquibase-0.8.1.RELEASE.jar -o target/skyer-tool-liquibase.jar
fi

java -Dspring.datasource.url="jdbc connection url" \
	 -Dspring.datasource.username=username \
	 -Dspring.datasource.password=password \
	 -Ddata.drop=false -Ddata.init=init \
	 -Ddata.dir=src/main/resources \
	 -jar target/skyer-tool-liquibase.jar
