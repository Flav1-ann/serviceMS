FROM hashicorp/terraform:latest

RUN apk add ansible
RUN apk add python3

ENTRYPOINT [ "" ]
CMD ["tail", "-f", "/dev/null"]