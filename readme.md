## 项目简介

本项目的后端基于`SpringBoot`+`MyBatis-Plus`，数据库采用`MySQL`和`Redis`，前端使用`Vue`，并采用前后端不分离的架构。

### 初始化与本地运行

#### 1. 配置文件修改

获取项目后，首先修改`src/main/resources`目录下的`application.yml`与`application-prod.yml`文件，需调整以下内容：

- `MySQL`连接地址、用户名、密码
- `Redis`连接地址、端口
- 图片存储路径
- 服务暴露端口（如有必要）

#### 2. 初始化数据库

在`common`目录下的`init.sql`文件中包含了所有`MySQL`建表语句，可以在你的数据库中执行该文件，完成数据库初始化。

#### 3. 运行项目

配置完成后，直接运行主入口`UJSShopSystemApplication`启动项目。

访问以下地址查看界面：

- 后台管理系统：http://localhost:8280/backend/page/login/login.html
- 前台商城系统：http://localhost:8280/front/page/login.html

### 生产环境部署（`Linux`服务器）

#### 1. 代码打包

使用`Maven`进行打包，生成可执行`JAR`文件：

```sh
mvn clean package
```

打包完成后，将`target`目录下的`shop-system-1.0.jar`文件拷贝至服务器指定目录，例如`/opt/shop`。

#### 2. 运行脚本（`run.sh`）

在`/opt/shop`目录下创建`run.sh`脚本，用于启动项目（指定生产环境配置`prod`）：

```sh
#!/bin/bash

# 检查端口是否被占用
if lsof -i:8280 > /dev/null; then
    echo "端口8280已被占用"
    exit 1
fi

# 启动服务，并将日志重定向到文件
nohup java -jar /opt/shop/shop-system-1.0.jar --spring.profiles.active=prod >> /opt/shop/output.log 2>&1 &

# 输出进程ID
JAVA_PID=$!
echo "程序正在运行，进程ID：$JAVA_PID"
```

赋予执行权限并运行脚本：

```
chmod +x run.sh
./run.sh
```

#### 3. 停止脚本（`stop.sh`）

在`/opt/shop`目录下创建`run.sh`脚本，用于停止项目：

```
#!/bin/bash

# 获取占用8280端口的进程ID
PID=$(lsof -t -i:8280)

if [ -z "$PID" ]; then
    echo "端口8280未被占用，无需停止程序"
    exit 0
fi

# 终止进程
kill -9 "$PID"

if [ $? -eq 0 ]; then
    echo "成功停止程序，进程ID：$PID"
else
    echo "无法停止进程，请检查权限"
    exit 1
fi
```

赋予执行权限并运行脚本：

```sh
chmod +x stop.sh
./stop.sh
```

#### 4. 访问系统

项目启动后，可通过以下地址访问（请将`123.60.188.152`替换为您的服务器`IP`）：

- **后台管理系统**：http://123.60.188.152:8280/backend/page/login/login.html
- **前台商城系统**：http://123.60.188.152:8280/front/page/login.html

## 联系方式

如有任何问题或建议，请联系：

- 邮箱：userwsj@126.com
- 微信：13136163259

感谢您的支持！