#!/bin/bash

CAFEID=`jps | grep cafe | awk '{ print $1 }'`

if [ -z $CAFEID ]; then
        echo "동작중인 서버가 없습니다."
else
        echo "$CAFEID 프로세스를 삭제합니다."
        kill -9 $CAFEID
fi

echo "서버 시작"
nohup java -jar ~/ubuntu/app/build/libs/cafe-0.0.1-SNAPSHOT.jar > ../log.txt 2>&1 &
echo "배포 완료!"

