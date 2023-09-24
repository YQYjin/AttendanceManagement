- dataBase包用于存放数据库中的各表对应关系
- 注意:若数据库中的字段名用下划线链接,则mybaits默认使用驼峰命名匹配
  - 如worker_num字段会被Mybatis自动匹配到workerNum字段,因此在命名时尽量使用驼峰命名
  - 可以在Mybatis配置文件中关闭驼峰命名,此时将映射变量命名为worker_num可以被匹配
- 注意:在映射时使用驼峰命名,但在设置查询条件时,必须与数据库列名一致,不能将下划线转为驼峰命名
- mybatis-plus使用ID作为主键,若表中没有ID字段,应添加注释来使mybatis获取主键,注意主键名应与Mysql严格对应,保留下划线
  ```java
  @TableId(value = "attendance_num")
  private int attendanceNum;
  ```