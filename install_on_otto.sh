#!/bin/sh

sh package_as_war.sh

ssh otto.concord.org 'rm -rf /web/aaron/geniverse-jetty/webapps/biologica/*'

scp -r war/* otto.concord.org:/web/aaron/geniverse-jetty/webapps/biologica/

ssh otto.concord.org 'sudo sh -c "chown -R aunger.users /web/aaron/geniverse-jetty/webapps/biologica; chmod -R g+rw /web/aaron/geniverse-jetty/webapps/biologica"'
ssh otto.concord.org 'cd /web/aaron/geniverse-jetty/'
ssh otto.concord.org 'cd /web/aaron/geniverse-jetty/ && ./bin/jetty.sh stop'
ssh otto.concord.org 'cd /web/aaron/geniverse-jetty/ && ./bin/jetty.sh start'
