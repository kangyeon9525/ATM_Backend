#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/atm
cd $REPOSITORY

APP_NAME=dopamineKiller
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

echo "> 현재 실행 중인 애플리케이션 PID 확인"
CURRENT_PID=$(pgrep -f $APP_NAME)
echo "CURRENT_PID: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "> 종료할 애플리케이션이 없습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> Deploy - $JAR_PATH"
nohup java -jar $JAR_PATH > /home/ubuntu/atm/deploy.log 2>&1 &
echo "java -jar $JAR_PATH 명령 실행 완료"

