#!/bin/sh
#
#该脚本为Linux下启动java程序的通用脚本。即可以作为开机自启动service脚本被调用，
#也可以作为启动java程序的独立脚本来使用。
#
#警告!!!：该脚本stop部分使用系统kill命令来强制终止指定的java程序进程。
#在杀死进程前，未作任何条件检查。在某些情况下，如程序正在进行文件或数据库写操作，
#可能会造成数据丢失或数据不完整。如果必须要考虑到这类情况，则需要改写此脚本，
#增加在执行kill命令前的一系列检查。
#
#
###############################################
#环境变量及程序执行参数
#需要根据实际环境以及Java程序名称来修改这些参数
###############################################
#获取当前脚本所在路径
basepath=$(cd `dirname $0`; pwd)
cd $basepath

#JDK所在路径
#JAVA_HOME="$basepath/jre"

#需要启动的Java主程序（main方法类）
APP_MAINCLASS=com.anima.basic.starter.AppStartup

#拼凑完整的classpath参数，包括指定lib目录下所有的jar
CLASSPATH=:$basepath/lib/*:$basepath/*

#java虚拟机启动参数
JVM_OPTS="-Xms2048m -Xmx4096m -XX:PermSize=256m -XX:MaxPermSize=512m -Djava.awt.headless=true -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8"

###################################
#(函数)判断程序是否已启动
#
#说明：
#使用ps -ef命令及grep命令组合，准确查找pid
#使用awk，分割出pid ($2部分)
###################################
#初始化psid变量（全局）
psid=0

checkpid() {
   #javaps=`$JAVA_HOME/bin/jps -l | grep $APP_MAINCLASS`
   javaps=`ps -ef | grep java | grep $basepath | grep $APP_MAINCLASS | sed -n  '1P'`
   if [ -n "$javaps" ]; then
      psid=`echo $javaps | awk '{print $2}'`
   else
      psid=0
   fi
}

###################################
#(函数)启动程序
#
#说明：
#1. 首先调用checkpid函数，刷新$psid全局变量
#2. 如果程序已经启动（$psid不等于0），则提示程序已启动
#3. 如果程序没有被启动，则执行启动命令行
#4. 启动命令执行后，再次调用checkpid函数
#5. 如果步骤4的结果能够确认程序的pid,则打印[OK]，否则打印[Failed]
#注意：echo -n 表示打印字符后，不换行
#注意: "nohup 某命令 >/dev/null 2>&1 &" 的用法
###################################
start() {
   checkpid

   if [ $psid -ne 0 ]; then
      echo "================================================="
      echo "warn: $APP_MAINCLASS already started! (pid=$psid)"
      echo "================================================="
   else
      echo -n "Starting $APP_MAINCLASS ..."

      nohup $JAVA_HOME/bin/java $JVM_OPTS -classpath $CLASSPATH $APP_MAINCLASS >/dev/null 2>&1 &
      #$JAVA_HOME/bin/java $JVM_OPTS -classpath $CLASSPATH $APP_MAINCLASS
      sleep 3
      checkpid
      if [ $psid -ne 0 ]; then
         echo "(pid=$psid) [OK]"
      else
         echo "[Failed]"
      fi
   fi
}

###################################
#(函数)停止程序
#
#说明：
#1. 首先调用checkpid函数，刷新$psid全局变量
#2. 如果程序已经启动（$psid不等于0），则开始执行停止，否则，提示程序未运行
#3. 使用kill -9 pid命令进行强制杀死进程
#4. 执行kill命令行紧接其后，马上查看上一句命令的返回值: $?
#5. 如果步骤4的结果$?等于0,则打印[OK]，否则打印[Failed]
#6. 为了防止java程序被启动多次，这里增加反复检查进程，反复杀死的处理（递归调用stop）。
#注意：echo -n 表示打印字符后，不换行
#注意: 在shell编程中，"$?" 表示上一句命令或者一个函数的返回值
###################################
stop() {
   checkpid

   if [ $psid -ne 0 ]; then
      echo -n "Stopping $APP_MAINCLASS ...(pid=$psid) "

      checkpid
      if [ $psid -ne 0 ]; then
         echo "kill 15 $psid"
         kill -15 $psid
         sleep 5
         checkpid
         if [ $psid -ne 0 ]; then
           echo "kill 9 $psid"
           kill -9 $psid
           echo "[OK]"
         else
           echo "[OK]"
         fi
      else
         echo "[OK]"
      fi
   else
      echo "==================================="
      echo "warn: $APP_MAINCLASS is not running"
      echo "==================================="
   fi
}

###################################
#(函数)检查程序运行状态
#
#说明：
#1. 首先调用checkpid函数，刷新$psid全局变量
#2. 如果程序已经启动（$psid不等于0），则提示正在运行并表示出pid
#3. 否则，提示程序未运行
###################################
status() {
   checkpid

   if [ $psid -ne 0 ];  then
      echo "$APP_MAINCLASS is running! (pid=$psid)"
   else
      echo "$APP_MAINCLASS is not running"
   fi
}

###################################
#(函数)打印系统环境参数
###################################
info() {
   echo "System Information:"
   echo "****************************"
   echo `head -n 1 /etc/issue`
   echo `uname -a`
   echo
   echo "JAVA_HOME=$JAVA_HOME"
   echo `$JAVA_HOME/bin/java -version`
   echo
   echo "APP_MAINCLASS=$APP_MAINCLASS"
   echo "****************************"
}

###################################
#读取脚本的第一个参数($1)，进行判断
#参数取值范围：{start|stop|restart|status|info}
#如参数不在指定范围之内，则打印帮助信息
###################################
case "$1" in
   '')
      start
      ;;
   'start')
      start
      ;;
   'stop')
     stop
     ;;
   'restart')
     stop
     start
     ;;
   'status')
     status
     ;;
   'info')
     info
     ;;
  *)
     echo "Usage: $0 {start|stop|restart|status|info}"
     exit 1
esac
exit 0

