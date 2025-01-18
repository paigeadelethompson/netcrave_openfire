FROM openjdk:11-jdk

ENV OPENFIRE_USER=openfire \
    OPENFIRE_DIR=/usr/local/openfire \
    OPENFIRE_DATA_DIR=/var/lib/openfire \
    OPENFIRE_LOG_DIR=/var/log/openfire \
    OPENFIRE_HOME="/usr/local/openfire"

RUN apt-get update -qq \
    && apt-get install -yqq sudo build-essential maven \
    && adduser --disabled-password --quiet --system --home $OPENFIRE_DATA_DIR --gecos "Openfire XMPP server" --group $OPENFIRE_USER \
    && rm -rf /var/lib/apt/lists/*

RUN mkdir /tmp/openfire

WORKDIR /tmp/openfire

ADD . .

RUN ./mvnw verify -pl distribution -am 

# RUN make -j$(nproc) build-openfire

RUN mv distribution/target/distribution-base /usr/local/openfire

RUN chown -R openfire:openfire /usr/local/openfire

RUN mv ${OPENFIRE_DIR}/conf ${OPENFIRE_DIR}/conf_org \
    && mv ${OPENFIRE_DIR}/plugins ${OPENFIRE_DIR}/plugins_org \
    && mv ${OPENFIRE_DIR}/resources/security ${OPENFIRE_DIR}/resources/security_org

RUN rm -rf /tmp/openfire 

RUN rm -rf ${OPENFIRE_DIR}/{conf,resources/security}

RUN ln -sf ${OPENFIRE_DATA_DIR}/conf ${OPENFIRE_DIR}/

RUN ln -sf ${OPENFIRE_DATA_DIR}/plugins ${OPENFIRE_DIR}/

RUN ln -sf ${OPENFIRE_DATA_DIR}/conf/security ${OPENFIRE_DIR}/resources/

RUN mkdir -p ${OPENFIRE_DATA_DIR}

RUN chmod -R 0750 ${OPENFIRE_DATA_DIR}

RUN chown -R ${OPENFIRE_USER}:${OPENFIRE_USER} ${OPENFIRE_DATA_DIR}

RUN cp -a ${OPENFIRE_DIR}/conf_org ${OPENFIRE_DATA_DIR}/conf

RUN cp -a ${OPENFIRE_DIR}/plugins_org ${OPENFIRE_DATA_DIR}/plugins

RUN cp -a ${OPENFIRE_DIR}/resources/security_org ${OPENFIRE_DATA_DIR}/conf/security

RUN mkdir -p ${OPENFIRE_LOG_DIR}

RUN chmod -R 0755 ${OPENFIRE_LOG_DIR}

RUN chown -R ${OPENFIRE_USER}:${OPENFIRE_USER} ${OPENFIRE_LOG_DIR}

RUN rm -rf ${OPENFIRE_DIR}/logs

RUN ln -sf ${OPENFIRE_LOG_DIR} ${OPENFIRE_DIR}/logs

# RUN find /opt/plugins/*.jar | xargs -i cp {} "${OPENFIRE_DIR}/plugins/"

USER openfire

LABEL maintainer="florian.kinder@fankserver.com"

WORKDIR /usr/local/openfire

VOLUME ["${OPENFIRE_DATA_DIR}"]

ENTRYPOINT [ "/usr/local/openfire/bin/openfire.sh" ]
