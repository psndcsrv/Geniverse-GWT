#!/bin/sh

CUR=`pwd`
cp geniverse.war /opt/jetty/webapps
cd /opt/jetty/webapps/geniverse
rm -r *
jar xvf ../geniverse.war
rm ../geniverse.war
cd ../..
bin/jetty.sh restart
cd $CUR