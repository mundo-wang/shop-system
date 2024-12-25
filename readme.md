后台界面地址：

http://localhost:8280/backend/page/login/login.html

http://123.60.188.152:8280/backend/page/login/login.html

前台界面地址：

http://localhost:8280/front/page/login.html

http://123.60.188.152:8280/front/page/login.html

打包命令：
```shell
mvn clean package
```

Linux服务器运行脚本（生产环境指定配置文件为prod，可按需自行修改）
```shell
#!/bin/bash

# 检查8280端口是否被占用
if lsof -i:8280 > /dev/null; then
    echo "端口8280已被占用"
    exit 1
fi

# 执行 Java 命令并记录日志
nohup java -jar /opt/shop/ujs_shop_system-1.0.jar --spring.profiles.active=prod >> /opt/shop/output.log 2>&1 &

# 获取执行程序的进程ID
JAVA_PID=$!
echo "程序正在运行，进程ID为：$JAVA_PID"
```

Linux服务器停止脚本
```shell
#!/bin/bash

# 检查8280端口是否被占用
PID=$(lsof -t -i:8280)

if [ -z "$PID" ]; then
    echo "端口8280未被占用，无需停止程序"
    exit 0
fi

# 杀死占用8280端口的进程
kill -9 "$PID"

if [ $? -eq 0 ]; then
    echo "成功杀死占用8280端口的进程，进程ID：$PID"
else
    echo "无法杀死占用8280端口的进程，请检查权限"
    exit 1
fi
```