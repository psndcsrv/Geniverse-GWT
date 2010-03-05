#!/bin/sh

scp geniverse.war otto.concord.org:/web/aaron/geniverse-jetty/webapps/
ssh otto.concord.org 'cd /web/aaron/geniverse-jetty/; ./bin/jetty.sh stop; ./bin/jetty.sh start'