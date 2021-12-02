to build and run issue-tracker:<br/>
1- First install and run docker and docker composer.<br/>
2- After that run following commands (Linux or Windows):<br/>
$ cd /[path to issue-tracker project]/<br/>
$ mvn clean<br/>
$ mvn package<br/>
$ docker image build -t issue-tracker<br/>
$ docker-compose up<br/>
3- root address (locally) of program to browse in internet browsers or postman <br/>
   is http://localhost:8080 and follow by RestControllers mapping pathes (include in source files).<br/>
