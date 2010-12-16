#!/bin/sh

sh package_as_war.sh

ssh -t geniverse@otto.concord.org 'cd /web/aaron/geniverse-jetty/ && ./bin/jetty.sh stop'

ssh geniverse@otto.concord.org 'rm -rf /web/aaron/geniverse-jetty/webapps/biologica/*'
scp -r war/* geniverse@otto.concord.org:/web/aaron/geniverse-jetty/webapps/biologica/

ssh -t geniverse@otto.concord.org 'cd /web/aaron/geniverse-jetty/ && nohup ./bin/jetty.sh start &'