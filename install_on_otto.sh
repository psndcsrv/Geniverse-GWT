#!/bin/sh

scp -r war/* otto.concord.org:/web/aaron/geniverse-jetty/webapps/geniverse/
# scp geniverse.war otto.concord.org:/web/aaron/geniverse-jetty/webapps/
ssh otto.concord.org 'chmod -R g+w /web/aaron/geniverse-jetty/webapps/geniverse/*'
ssh otto.concord.org 'cd /web/aaron/geniverse-jetty/; ./bin/jetty.sh stop; ./bin/jetty.sh start'
