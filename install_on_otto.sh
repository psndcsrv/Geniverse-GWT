#!/bin/sh

scp -r war/* otto.concord.org:/web/aaron/geniverse-jetty/webapps/dev/
# scp geniverse.war otto.concord.org:/web/aaron/geniverse-jetty/webapps/
ssh otto.concord.org 'chmod -R g+w /web/aaron/geniverse-jetty/webapps/dev/*'
ssh otto.concord.org 'cd /web/aaron/geniverse-jetty/'
ssh otto.concord.org 'cd /web/aaron/geniverse-jetty/ && ./bin/jetty.sh stop'
ssh otto.concord.org 'cd /web/aaron/geniverse-jetty/ && nohup ./bin/jetty.sh start &'
