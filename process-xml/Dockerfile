FROM maven:alpine
RUN apk update;apk add git
WORKDIR /app
RUN git clone https://phabricator.wikimedia.org/diffusion/MWDU/mwdumper.git;
RUN cd mwdumper;ls;mvn compile;mvn package;mkdir /sql;mkdir /dumps
RUN cd mwdumper/target;mv mwdumper*.jar ../../mwdumper.jar
COPY scripts/*.sh ./
ENTRYPOINT ["/app/convert.sh"]
