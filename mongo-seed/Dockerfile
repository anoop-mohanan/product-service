FROM mongo
RUN apt-get update && apt-get install netcat -y
COPY prices.json /prices.json
COPY wait-check.sh .
ENTRYPOINT ["/bin/sh", "wait-check.sh"]