FROM frangarcia/vertx:3.7.0
MAINTAINER Fran Garcia
ADD ./ /

WORKDIR /

CMD ["vertx", "run", "GameDeployer.groovy"]
