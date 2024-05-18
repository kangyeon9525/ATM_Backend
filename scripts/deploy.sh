#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/atm
cd $REPOSITORY

APP_NAME=ATM_Backend
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

echo "> 현재 실행 중인 애플리케이션 PID 확인"
CURRENT_PID=$(pgrep -fl $JAR_PATH | grep java | awk '{print $1}')
echo "CURRENT_PID: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "> 종료할 애플리케이션이 없습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5

  # 프로세스가 종료되었는지 확인
  CURRENT_PID=$(pgrep -fl $JAR_PATH | grep java | awk '{print $1}')
  if [ -n "$CURRENT_PID" ]; then
    echo "> 프로세스 강제 종료"
    kill -9 $CURRENT_PID
  fi
fi

echo "> Deploy - $JAR_PATH"
nohup java -jar $JAR_PATH > /home/ubuntu/atm/deploy.log 2>&1 &
echo "java -jar $JAR_PATH 명령 실행 완료"


