开发完整的项目

配置相关
1. mybatis-plus 配置和使用
逻辑删除和细节处理
2. 数据连接池 配置
Hikari是spring boot 2.0之后默认整合的数据库连接池，比druid更快的数据库连接池
datasource.type = com.zaxxer.hikari.HikariDataSource
****.hikari.pool-name
3. 前后端连接 swagger2
4. CORSConfig 跨域请求解决
5. 统一异常处理
6. 统一日志处理
7. 权限设置 注解权限 jwt,加密方式

业务需求升级，可以添加的功能
1. redis 瑞士军刀，任何和存储有关都可以用
2. mongodb
3. 分布式搜索引擎elasticsearch
4. 服务器之间的交流，降峰操作，消息队列rabbitmq

服务器搭建，与优化
1. nginx 代理服务器
2. docker 容器化
3. 分库分表，读写分离
