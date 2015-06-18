#!/bin/bash

mvn --version
mvn install -f gapi/

mvn com.googlecode.mycontainer:mycontainer-maven-plugin:phantomjs-install -Dmycontainer.phantomjs.dest=target/phantomjs -Dmycontainer.phantomjs.version=1.9.2
mvn install

mvn test -Dselenium

