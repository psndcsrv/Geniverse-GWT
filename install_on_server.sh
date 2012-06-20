#!/bin/sh

if [ -x $1 ]; then
  echo "You must specify which server to install to.\nSupported servers: production, dev"
  exit 1
fi

case "$1" in
  production)
    export SERVER=seymour.concord.org
    export SERVER_PATH="/web/production/geniverse.gwt"
    ;;
  genigames)
    echo "Compile your changes, commit and push them to the Github repo. Then use littlechef to deploy them: fix node:gwt.genigames.concord.org."
    exit 1
    ;;
  dev)
    export SERVER=otto.concord.org
    export SERVER_PATH="/web/aaron/geniverse-jetty"
    ;;
  *)
    echo "Invalid server!"
    exit 1
    ;;
esac

sh package_as_war.sh

ssh -t geniverse@$SERVER "cd $SERVER_PATH && ./bin/jetty.sh stop"

ssh geniverse@$SERVER "rm -rf $SERVER_PATH/webapps/biologica/*"
scp -r war/* geniverse@$SERVER:$SERVER_PATH/webapps/biologica/

ssh -t geniverse@$SERVER "cd $SERVER_PATH && ./start.sh && sleep 5"