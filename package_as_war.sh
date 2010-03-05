#!/bin/sh

cd `dirname $0`
cd war/WEB-INF/lib
mv biologica-0.1.0-SNAPSHOT.jar ../../../biologica-0.1.0-SNAPSHOT.jar.old
wget http://jnlp.concord.org/dev/org/concord/biologica/biologica-0.1.0-SNAPSHOT.jar
cd -

ant -f buildwar.xml