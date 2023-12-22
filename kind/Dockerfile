# FROM kindest/node:v1.19.1
FROM kindest/node:v1.27.3


RUN apt-get -y update && \
    apt-get -y install vim && \
    apt-get -y install telnet


ENTRYPOINT [ "/usr/local/bin/entrypoint", "/sbin/init"]
