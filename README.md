#微信小程序示例项目
* 包含中间件，基础后台系统业务逻辑，授权管理，文件管理
* 微信小程序用户授权及信息基础，基础微信小程序应用接口

### 目录结构
├── common 公共应用<br>
│   ├─ all-common 公共应用所有引用<br>
│   ├─ base-application 基础服务应用<br>
│   ├─ minio 文件<br>
│   ├─ quartz 定时任务<br>
│   ├─ redis 缓存<br>
│   ├─ util 工具<br>
│   └─ validator 自定义校验工具<br>
├── dao 数据交互<br>
│   ├─ all-dao 数据交互所有引用<br>
│   ├─ base-dao 基础数据交互引用<br>
│   └─ system-dao 后台系统数据交互<br>
├── generator 代码生成<br>
│   ├─ base-generator 基础代码生成引用<br>
│   └─ generator-modules 代码生成模块<br>
├── manager 公共管理层<br>
│   ├─ miniapp-auth 微信小程序服务授权<br>
│   └─ system-auth 后台服务授权<br>
├── model 公共实体层<br>
│   ├─ all-model 所有实体引用<br>
│   ├─ mapstruct 实体映射层<br>
│   └─ vo 表现层对象<br>
├── modules 业务模块层<br>
│   ├─ miniapp 微信小程序服务<br>
│   └─ system 后台服务<br>
├── sql 数据脚本<br>
└── wx 微信基础层<br>
&nbsp;&nbsp; &nbsp;├─ all-wx 微信基础层所有引用<br>
&nbsp;&nbsp; &nbsp;├─ wx-cp 微信企业号<br>
&nbsp;&nbsp; &nbsp;├─ wx-miniapp 微信小程序<br>
&nbsp;&nbsp; &nbsp;├─ wx-mp 微信公众号<br>
&nbsp;&nbsp; &nbsp;├─ wx-open 微信开放平台<br>
&nbsp;&nbsp; &nbsp;└─ wx-pay 微信支付<br>

### 服务划分
|名称|定义|端口|workerId|
|:----: |:----:|:----:|:----:|
|system|后台服务|42000|0|
|miniapp|小程序服务|43000|1|

* 使用分布式id生成需配置workerId和datacenterId，需保证每个服务workerId和datacenterId组合均不一致，docker容器初始化时workerId为默认设置，datacenterId则根据端口号生成，如40001的datacenterId即为1，以此规则命名

### Docker环境搭建
|应用|获取|运行|
|:----:|:-----|:-----|
|redis|docker pull redis|docker run --restart=always -d --name redis -p 6379:6379 -v /etc/localtime:/etc/localtime redis redis-server --appendonly yes --requirepass "密码"|
|rabbitmq|docker pull rabbitmq:management|docker run --restart=always --name rabbitmq -d -p 15672:15672 -p 5672:5672 rabbitmq:management|
|minio|docker pull minio/minio|docker run --restart=always --name minio -d -p 9000:9000 -e "MINIO_ACCESS_KEY=账号" -e "MINIO_SECRET_KEY=密码" -v /mnt/data:/data -v /mnt/config:/root/.minio minio/minio server /data|

* 生产环境IDEA链接Docker开放端口2375请配置好ca证书
* docker run添加--cap-add=SYS_PTRACE参数解决无法使用JVM命令问题

### 预设初始账号密码
* redis 
  >密码:
* rabbitmq
  >账号:admin
  >密码:admin
* minio
  >账号:admin
  >密码:12345678
  
 ### 相关环境及技术栈
 * JDK：Java 8
 * 容器化部署工具：Docker
 * 数据库：MySQL
 * 服务框架：Spring Boot
 * 缓存：Redis
 * 消息队列：RabbitMQ
 * 定时任务：Quartz
 * 文件服务：Minio
 * 鉴权：Shiro+JWT
 * Mybatis插件：[Mybatis Plus](https://baomidou.com/guide/)
 * 分页插件：PageHelper
 * 多数据源组件：Dynamic Datasource
 * 接口文档组件：knife4j
 * JDBC组件：Druid 
 * 公共工具组件：[Hutool](https://www.hutool.cn/docs/#/) 
 * 验证组件：Hibernate Validator
 * Excel工具：[EasyExcel](https://www.yuque.com/easyexcel/doc/easyexcel)
 * 高性能实体映射工具：MapStruct
 * 微信SDK：WxJava