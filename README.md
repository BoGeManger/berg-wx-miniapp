#微信小程序示例项目

### 架构分层简介
|名称|定义|端口|workerId|
|:----: |:----:|:----:|:----:|
|common|公共类|||
|model|实体|||
|dao|数据交互|||
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
 * Excel文件处理：AutoPOI
 * 鉴权框架：Shiro+JWT
 * Mybatis插件：Mybatis Plus,PageHelper,dynamic datasource
 * 接口文档框架：Swagger 2
 * JDBC组件：Druid 
 * 公共工具：Hutool 
 * 验证框架：Hibernate Validator
 * 微信SDK：WxJava