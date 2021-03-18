FROM java:8
VOLUME /tmp
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/Shanghai" > /etc/timezone
COPY target/system-1.0.0-SNAPSHOT.jar system.jar
RUN bash -c "touch /system.jar"
EXPOSE 42000
ENV w=0 d=0 p=""
ENTRYPOINT ["java","-Xms256m","-Xmx256m","-XX:+HeapDumpOnOutOfMemoryError","-XX:HeapDumpPath=/heapdump.hprof","-jar","system.jar","--snowflake.id.worker.workerId=${w}","--snowflake.id.worker.datacenterId=${d}","${p}"]