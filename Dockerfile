FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/aiDeepSeek-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/application.properites.docker ./application.properties

EXPOSE 8080

ENTRYPOINT ["java", \
  "--add-opens=java.base/jdk.internal.access=ALL-UNNAMED", \
  "--add-opens=java.base/jdk.internal.misc=ALL-UNNAMED", \
  "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED", \
  "--add-opens=java.base/sun.util.calendar=ALL-UNNAMED", \
  "--add-opens=java.management/com.sun.jmx.mbeanserver=ALL-UNNAMED", \
  "--add-opens=jdk.internal.jvmstat/sun.jvmstat.monitor=ALL-UNNAMED", \
  "--add-opens=java.base/sun.reflect.generics.reflectiveObjects=ALL-UNNAMED", \
  "--add-opens=jdk.management/com.sun.management.internal=ALL-UNNAMED", \
  "--add-opens=java.base/java.io=ALL-UNNAMED", \
  "--add-opens=java.base/java.nio=ALL-UNNAMED", \
  "--add-opens=java.base/java.net=ALL-UNNAMED", \
  "--add-opens=java.base/java.util=ALL-UNNAMED", \
  "--add-opens=java.base/java.util.concurrent=ALL-UNNAMED", \
  "--add-opens=java.base/java.util.concurrent.locks=ALL-UNNAMED", \
  "--add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED", \
  "--add-opens=java.base/java.lang=ALL-UNNAMED", \
  "--add-opens=java.base/java.lang.invoke=ALL-UNNAMED", \
  "--add-opens=java.base/java.math=ALL-UNNAMED", \
  "--add-opens=java.sql/java.sql=ALL-UNNAMED", \
  "--add-opens=java.base/java.lang.reflect=ALL-UNNAMED", \
  "--add-opens=java.base/java.time=ALL-UNNAMED", \
  "--add-opens=java.base/java.text=ALL-UNNAMED", \
  "--add-opens=java.management/sun.management=ALL-UNNAMED", \
  "--add-opens=java.desktop/java.awt.font=ALL-UNNAMED", \
  "-Xms512m", \
  "-Xmx512m", \
  "-XX:MaxDirectMemorySize=512m", \
  "-Dcom.sun.management.jmxremote", \
  "-Dcom.sun.management.jmxremote.port=9999", \
  "-Dcom.sun.management.jmxremote.authenticate=false", \
  "-Dcom.sun.management.jmxremote.ssl=false", \
  "-Djava.rmi.server.hostname=localhost", \
  "-Djava.net.preferIPv4Stack=true", \
  "-jar", "app.jar", \
  "--spring.config.location=classpath:/,file:./application.properties"]