FROM openjdk:17-slim-bullseye

ENV TZ=PRC

RUN apt-get -y update \
    && apt-get -y install procps \
    && apt-get -y install bash-completion \
    && apt-get -y install vim \
    && mkdir -p /app/bin

WORKDIR /app

COPY bin/*  /app/bin/

VOLUME ["/app/data"]

EXPOSE 49001
EXPOSE 49002

ENTRYPOINT ["sh","-c","/app/bin/cmd_run_app.sh"]
