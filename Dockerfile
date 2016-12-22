FROM tomcat:8-jre8

RUN rm -rf /usr/local/tomcat/webapps

ADD ./target/simple-web.war /usr/local/tomcat/webapps/simple-web.war