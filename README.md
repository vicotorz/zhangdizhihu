# 仿知乎论坛社区网站-问答系统
### 使用技术：
#### mysql，springboot，velocity，mybatis，redis，异步队列，邮件发送，timeline，七牛sdk，solr 

# 知识点总结
## mysql导出数据，导入数据
* 1 导出-->mysqldump -uroot -p wenda > ....(sql文件位置)
* 2 导入-->mysql -uroot -p wenda < ...(sql文件位置)

## solr配置
* 1 managed-schema添加中文分词
* 2 managed-schema建立字段
* 3 solrconfig.xml加入jar包ext
* 4 solrconfig.xml加入导入数据库的request handler
* 5 添加solr-data-config.xml

## redis启动
* 切换到目录 cd D:\Redis
* redis-server.exe redis.windows.conf
* 另外启动一个cmd，切换目录redis-cli.exe -h 127.0.0.1 -p 6379

## 导出表和数据
* mysqldump -uroot -p wenda > e:\test.sql
## 导入表和数据
* mysqldump -uroot -p wenda < e:\test.sql

## 查看8080被占用
* netstat  -aon|findstr "8080"
* cmd-->netstat -ano-->查看pid-->结束进程

## 工程发布
* 1 application继承SpringBootServletInitializer
* 2 pom.xml打包成war
* 3 mvn package -Dmaven.test.skip=true
* 4 去除多余main函数

## 遇到的问题总结

* Injection of autowired dependencies failed; nested exception is org.springframework.beans.factory.BeanCreationException:   Could  not autowire field:
** spring上下文中不存在Bean,@Mapper 失效
** pom的问题-->重新copy了一份pom文件

* alter table表名 modify字段名字段类型(长度) null;

* errorRequired String parameter 'Name' is not present  因为RequestParam导致
* Request method 'POST' not supported-->重加注册页面
* 找不到status--->dao里面没加@Param

* implements InitializingBean
* 弹窗找不到--写在js之中，在tail中缺少js引用
* Dao内找不到变量--因为@Param没有加
* @Select List<xx>查不到(model里面属性有下划线，去掉下划线就好了)==>针对comment

