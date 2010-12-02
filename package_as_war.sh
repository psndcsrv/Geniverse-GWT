#!/bin/sh

cd `dirname $0`
cd war/WEB-INF/lib
rm biologica-0.1.0-*.jar
wget 'http://source.concord.org/nexus/service/local/artifact/maven/redirect?r=cc-repo-internal-snapshot&g=org.concord&a=biologica&v=0.1.0-SNAPSHOT&e=jar'
cd -

ant -f buildwar.xml