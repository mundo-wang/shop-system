该项目的后端部分使用`SpringBoot`+`MybatisPlus`框架编写，数据库部分使用到了`MySQL`和`Redis`，前端使用`Vue`框架，前后端不分离。

拿到项目后，首先更改`src/main/resources`目录下的`application.yml`与`application-prod.yml`这两个文件，具体更改以下内容：

1. `MySQL`地址
2. `Redis`地址
3. 图片保存路径
4. 端口号（若需要）

在`common`目录下有一个`init.sql`文件，里面是`MySQL`数据库的所有建表语句，需要执行一遍。

如果在本地运行，更改完上面的配置文件后，直接运行项目入口`UJSShopSystemApplication`即可，然后访问下方网址即可查看界面。

后台界面地址：http://localhost:8280/backend/page/login/login.html

前台界面地址：http://localhost:8280/front/page/login.html

如果是打包部署到`Linux`上运行，步骤参考下面内容。

打包命令：

```shell
mvn clean package
```

接着从生成的`target`目录下拿到`shop-system-1.0.jar`文件，放置到`Linux`合适的目录位置，例如`/opt/shop`。

`Linux`服务器项目运行脚本，可以命名为`run.sh`（生产环境指定配置文件为`prod`，可按需自行修改）：

```shell
#!/bin/bash

# 检查8280端口是否被占用
if lsof -i:8280 > /dev/null; then
    echo "端口8280已被占用"
    exit 1
fi

# 执行Java命令并记录日志
nohup java -jar /opt/shop/shop-system-1.0.jar --spring.profiles.active=prod >> /opt/shop/output.log 2>&1 &

# 获取执行程序的进程ID
JAVA_PID=$!
echo "程序正在运行，进程ID为：$JAVA_PID"
```

`Linux`服务器项目停止脚本，可以命名为`stop.sh`：

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

项目运行起来后，查看`output.log`日志文件是否已经启动成功，然后访问下面的页面地址，这里的`IP`部分要改为自己的服务器地址：

后台界面地址：http://123.60.188.152:8280/backend/page/login/login.html

前台界面地址：http://123.60.188.152:8280/front/page/login.html

如有任何问题或建议，请通过以下方式联系我：

> - 邮箱：userwsj@126.com
> - 微信：13136163259

感谢您的积极反馈！