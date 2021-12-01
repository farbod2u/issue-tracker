to build and run issue-tracker:
1- First install and run docker and docker composer.
2- After that run following commands (Linux or Windows):
$ cd /[path to issue-tracker project]/
$ mvn clean
$ mvn package
$ docker image build -t issue-tracker .
$ docker-compose up
3- root address (locally) of program to browse in internet browsers or postman 
   is http://localhost:8080 and follow by RestControllers mapping pathes (include in source files).